import csv
import json
import math
from collections import OrderedDict


# provide total number of LEDs and path to LXStudio-IDE project directory
total_leds = 900

# generate coordinates
# y = 2 * PI * current_led_num / total_leds
# x = cos(6 * y)
# z = sin(6 * y)
components_data = []
point_data = OrderedDict()
point_data['type'] = 'points'
point_data['coords'] = []
height_scalar = 2.6
density_scalar = 2
for n in range(total_leds):
	y = (2 * math.pi * n / total_leds) * height_scalar
	x = math.cos(density_scalar * y)
	z = math.sin(density_scalar * y)
	coords = {'x': x, 'z': z, 'y': y}
	point_data['coords'].append({'x': x, 'z': z, 'y': y})
components_data.append(point_data)

lxf_dict = OrderedDict()
lxf_dict['label'] = 'Spiral-%d' % total_leds
lxf_dict['tags'] = ['custom', 'glorb', 'spiral']
lxf_dict['components'] = components_data

with open('Fixtures/spiral-{0}.lxf'.format(total_leds), 'w+') as f:
	json.dump(lxf_dict, f, indent=2)
