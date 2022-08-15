import csv
import json
import math
from collections import OrderedDict


# provide total number of LEDs and path to LXStudio-IDE project directory
total_leds = 900
lxstudio_path = 'Documents/LXStudio-IDE-master'

# generate coordinates
# z = 2 * PI * current_led_num / total_leds
# x = cos(6 * z)
# y = sin(6 * z)
components_data = []
point_data = OrderedDict()
point_data['type'] = 'points'
point_data['coords'] = [OrderedDict()]
for n in range(total_leds):
	z = 2 * math.pi * n / total_leds
	x = math.cos(6 * z)
	y = math.sin(6 * z)
	coords = {'x': x, 'y': y, 'z': z}
	point_data['type'] = 'points'
	point_data['coords'].append({'x': x, 'y': y, 'z': z})
components_data.append(point_data)

lxf_dict = OrderedDict()
lxf_dict['label'] = 'Spiral-%d' % total_leds
lxf_dict['tags'] = ['custom', 'glorb', 'spiral']
lxf_dict['components'] = components_data

with open('spiral-{0}.lxf'.format(total_leds), 'w+') as f:
	json.dump(lxf_dict, f, indent=2)
