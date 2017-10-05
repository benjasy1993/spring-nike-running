from django import forms

class ConfigForm(forms.Form):
    # speed = forms.ChoiceField(choices=(
    #     ('Slow', 'slow'),
    #     ('Medium', 'medium'),
    #     ('Fast', 'fast'),
    # ))
    speed = forms.IntegerField(label='speed', initial=50, min_value=20, max_value=100)
    polyline = forms.CharField(label='Polyline', widget=forms.TextInput(
        attrs = {'id': 'polyline'}
    ))
    interval = forms.IntegerField(label='Report Interval', initial=1000)
