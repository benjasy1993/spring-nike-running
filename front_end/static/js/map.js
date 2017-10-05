var map, infoWindow, pos;
var start, end;
var startMarker, endMarker;
var polyline;
var directionsDisplay, directionsService;
var directionsResult;
var runners;
var connect;
var stompClient = null;
const SOCKET_ENDPOINT = 'http://localhost:9007'
function initMap() {
  //set up directions service and display
  directionsDisplay = new google.maps.DirectionsRenderer({
    draggable: true,
    suppressMarkers: true
  });
  directionsDisplay.addListener('directions_changed', function() {
    directionsResult = directionsDisplay.getDirections();
  });
  directionsService = new google.maps.DirectionsService;
  if (!runners) {
    runners = new Map();
  }

  infoWindow = new google.maps.InfoWindow;
  map = new google.maps.Map(document.getElementById('map'), {
    // center: {lat: 40.714, lng: -74.005},
    zoom: 15
  });
  //find current position and set map center
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      infoWindow.setPosition(pos);
      infoWindow.setContent('You are here');
      infoWindow.open(map);
      map.setCenter(pos);
    }, function() {
      handleLocationError(true, infoWindow, map.getCenter());
    });
  } else {
    // Browser doesn't support Geolocation
    handleLocationError(false, infoWindow, map.getCenter());
  }

  directionsDisplay.setMap(map);

  //Add listener for marker
  google.maps.event.addListener(map, "click", function(event) {
    if (!start) {
      // var startWindow = new google.maps.InfoWindow;
      startMarker = new google.maps.Marker({
        label: 'Start',
        map: map,
        position: event.latLng
      });
      start = event.latLng;
    } else if (!end) {
      // var endWindow = new google.maps.InfoWindow;
      endMarker = new google.maps.Marker({
        label: 'End',
        map: map,
        position: event.latLng
      });
      end = event.latLng;
    }

    if (start && end) {
      if (!directionsResult) {
        calcRoute(start, end, directionsDisplay, directionsService);
        if (directionsDisplay.getMap() == null) {
          directionsDisplay.setMap(map);
        }
      }
      document.getElementById('configure').disabled = false;
    }
    if (start == null || end == null) {
      document.getElementById('configure').disabled = true;
    }
    if (start != null || end != null) {
      document.getElementById('clear').disabled = false;
    }
  });

  if (!connect) {
    connect = function() {
        var socket = new SockJS(SOCKET_ENDPOINT+'/stomp');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            // setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/locations', function (greeting) {
                // a js object that contains all running data
                var data = JSON.parse(greeting.body);
                var runningId = data['runningId'];
                console.log(typeof data['location']['latitude']);
                var location = new google.maps.LatLng({
                  lat: data['location']['latitude'],
                  lng: data['location']['longitude']
                });
                if (runners.has(runningId)) {
                  runners.get(runningId).setPosition(location);
                } else {
                  runners.set(runningId, new google.maps.Marker({
                      icon: {
                        path: google.maps.SymbolPath.CIRCLE,
                        scale: 5
                      },
                      title: 'you',
                      position: location,
                      map: map
                  }));
                }
            });
        });
    }
  }

}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
  infoWindow.setPosition(pos);
  infoWindow.setContent(browserHasGeolocation ?
                        'Error: The Geolocation service failed.' :
                        'Error: Your browser doesn\'t support geolocation.');
                        infoWindow.open(map);
}

function calcRoute(start, end, directionsDisplay, directionsService) {
  var request = {
    origin: start,
    destination: end,
    travelMode: 'WALKING'
  };
  directionsService.route(request, function(result, status) {
    if (status == 'OK') {
      directionsDisplay.setDirections(result);
      console.log(result);
    }
  });
}

function handleClear() {
  //clear directionsDisplay view and start and end points
  start = null;
  end = null;
  directionsResult = null;
  if (startMarker) {
    startMarker.setMap(null);
  }
  if (endMarker) {
    endMarker.setMap(null);
  }
  if (directionsDisplay) {
    directionsDisplay.setMap(null);
  }
  document.getElementById('clear').disabled = true;
  document.getElementById('configure').disabled = true;
}

function handleConfigure() {
  polyline = directionsResult["routes"][0]["overview_polyline"];
  document.getElementById('polyline').value = polyline;
}
