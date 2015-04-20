---
layout: post
title: Server API
date: 2015-04-20 10:59:17
---

With the server API mostly done, it's time to discuss it.

Receiving Player Points
-----------------------

To retrieve all player points, simply go to [http://dedominic.pw/csc-311/php/get\_users.php](http://dedominic.pw/csc-311/php/get_users.php).
All that has to be specified is what your user name is so you don't get your data.
you can specify that by adding ?users=YOURUSERNAMEHERE to the end of the url.

If you just want all the users, ommit this entry

What is returned is a csv, example:
	ASD,41.75709191,-72.49118687,AC:22:0B:60:40:E3
	enemy1,41.7575,-72.49055,60:03:08:a6:b3:be
	MotoX,41.75700635,-72.49120105,CC:8C:E3:71:7B:B0
Fields are: username, latitude, longitude, MAC address

Receiving Messages
-------------------

Similar to above, link is [http://dedominic.pw/csc-311/php/get\_messages.php](http://dedominic.pw/csc-311/php/get_messages.php)

Two other parameters are available.
user is the name of the user.
team is the name of the team.
eg:
	?user=ASD&team=blue

these need to be set to fetch messages, otherwise the script gives you an error messag e and exits. 

Results will look like this, a CSV:
	Hello There,45,70
Fields are: message, latitude, longitude.

Uploading Location data and MAC address
----------------------------------------

Lik all the others, it uses GET. Script is at [http://dedominic.pw/csc-311/php/update\_location.php](http://dedominic.pw/csc-311/php/update_location.php)

Four fields need to be set:
user, the username
lat, the latitude of player
lon, the longitude of player
mac, the user's MAC address

all of these fields need to be set or the system errors out.

example:
	?user=ASD&lat=45&lon=72&mac=01:23:45:67:89:ab

