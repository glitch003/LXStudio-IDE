import csv
import json
import math
from collections import OrderedDict


# provide total number of LEDs and path to LXStudio-IDE project directory
total_leds = 900
lxstudio_path = './'

# generate coordinates
# z = 2 * PI * current_led_num / total_leds
# x = cos(6 * z)
# y = sin(6 * z)
components_data = []
total_leds = 900
for n in range(total_leds):
	point_data = OrderedDict()
	z = 2 * math.pi * n / total_leds
	x = math.cos(6 * z)
	y = math.sin(6 * z)
	coords = OrderedDict()
	coords = {'x': x, 'y': y, 'z': z}
	point_data['type'] = 'points'
	point_data['coords'] = [{'x': x, 'y': y, 'z': z}]
	components_data.append(point_data)

lxf_dict = OrderedDict()
lxf_dict['label'] = 'Spiral-%d' % total_leds
lxf_dict['tags'] = ['custom', 'glorb', 'spiral']
lxf_dict['components'] = components_data
lxf_dict["outputs"] = [{ "protocol": "artnet", "universe": 10, "host": "192.168.1.3", "start": 0, "num": 900 }]

with open('{0}/Fixtures/spiral-{1}.lxf'.format(lxstudio_path, total_leds), 'w') as f:
	json.dump(lxf_dict, f, indent=2)

