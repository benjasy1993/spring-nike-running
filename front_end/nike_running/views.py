# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.http import HttpResponse

from forms import ConfigForm
import requests
import json
from uuid import uuid4

# Constant
BACKEND_ENDPOINT = 'http://localhost:9005/api/simulation'
gpsSimulatorRequest = {
					      "move": True,
					      "exportPositionsToMessaging": True,
					      "secondsToError": 0,
					      "runnerStatus": "SUPPLY_SOON",
					      "medicalInfo": {
					        "bandMake": "Fitbit",
					        "medCode": "FMW",
					        "medicalInfoId": "Fitbit",
					        "medicalInfoClassification": "SupplyInfo",
					        "description": "Energy Drink Required",
					        "aidInstructions": "Check energy level before taking energy drink",
					        "fmi": "14",
					        "bfr": "171"
					      }
					    }
# Create your views here.
def index(request):
	form = ConfigForm()
	return render(request, 'nike_running/index.html', context={'form': form})

def simulate(request):
	if request.method == 'POST':
		response = None
		form = ConfigForm(request.POST)
		if form.is_valid():
			gpsSimulatorRequest['polyline'] = form.cleaned_data['polyline']
			gpsSimulatorRequest['speed'] = form.cleaned_data['speed']
			gpsSimulatorRequest['reportInterval'] = form.cleaned_data['interval']
			gpsSimulatorRequest['runningId'] = str(uuid4())
			print json.dumps(gpsSimulatorRequest, indent=2)
			# post this data into backend
			response = requests.post(BACKEND_ENDPOINT, json={
				'numberOfGpsSimulatorRequests': 1,
				'gpsSimulatorRequests': [gpsSimulatorRequest]
			})
			print response
			if response.ok:
				return HttpResponse('Simulation started')
			else:
				return HttpResponse('simulation failed')
		else:
			return HttpResponse('invalid')
	if request.method == 'GET':
		return render(request, 'nike_running/socket.html')
