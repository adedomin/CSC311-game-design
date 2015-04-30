---
layout: post
title: Current Game Status, Testing Done, Known Issues
date: 2015-04-28 20:22:52
---

As always, the source of the game can be found on [github](https://github.com/adedomin/CSC311-FInal-Project).

Server-side scripts can be found [here](http://dedominic.pw/csc-311/php/server_side_php.tar.gz).

If you can't open the tarball above, consider [7-Zip](http://www.7-zip.com) a free and open source software that can unpack gzip compressed UNIX tarballs on windows.

What Has Changed
------------------

Logging in
===========

Logging in has changed. Current authentication is done with the User's MAC Address;
the idea behind this is when a user dies for the day, they can't just create a new account.
Using the user's IP can be meaningless since multiple users can be associated with a shared public wireless network.

If the user's bluetooth MAC Address is already in the server database, then the user will receive his previous name and team name.
Else, then the user will have to enter a User name and Team name.

The login activity also uploads this info to the server using a new server API function.

Creating a User
===============

[create\_user.php](dedominic.pw/csc-311/php/create_user.php) gives the server the ability to add a user to the database.
It takes the following parameters

	?user=username&team=teamname

[get\_username.php](dedominic.pw/csc-311/php/get_username.php) allows user to send a mac address to a server and get back their username and teamname.

	?MAC=MA:CA:DD:43:55:00

Server returns a CSV containing:
	username,teamname
If user doesn't exists it returns the string literal:
	User does not exist


Testing
========

To confirm functionality, I inserted a new user: 
	INSERT INTO player_data (username,teamname,MAC) VALUES ("A NAME", "TEAM 1", "AC:22:0B:60:40:E3");

The MAC is associated with my Nexus 7.
So when I started the app up, I had it show a toast notification with my name and team.
I also was not prompted for a User name and Team name.

Removing the entry from the database caused the expect results, it launched me into the login activity where I was prompted for a user name and team name.

I confirmed that the login activity properly uploads the information by querying the table for the entered user.

The results are properly sent back since the game sent a toast with the values added

Node Capture
============

Nodes and capturing them has been implemented as of now.
To add this, one timer and a data processor was added.
The data processor (MainActivity.processNodeList()) is very similar to processCSV().
The key difference is that, ProcessNodeList does not append it's information to the listview on the left.
Currently, all it does is draw the node points on the map (albeit transparent and with a larger radius) and to record any node within 250 meters from the player in global variable, CURRENT\_NODE.

To have in radius node effects occur, a timer was put in place.
This timer executes every one second and only does something when CURRENT\_NODE does not equal and arbiter value, -999.

If the node is neutral, then the timer counts down from 30 to 0 and at zero tells the server that the CURRENT\_NODE which is a nodeID value, belongs to the player's team.

When the user enters a friendly node, the node timer starts counting up from zero.
Currently nothing else occurs.

When the user exits the range of the node, CURRENT\_NODE is set back to -999 and the timer stops working until CURRENT\_NODE is set again in the processNodeList function.

Server Side
============

[node\_capture.php](dedominic.pw/csc-311/php/node_capture.php) adds a php script that receives a nodeID and a team name and changes the team column for the node represented by nodeID

	?team=teamname&node=nodeID

[get\_nodes.php](dedominic.pw/csc-311/php/get_nodes.php) allows you to fetch all nodes in play.
This API takes no parameters;
just query it and it will return a csv with values:
	nodeID,latitude,longitude,team

Testing
========

Walking away from a node was tested, when I was out of range the program sent a toat to player.

When entering range, an appropriate message was sent using toast notification.

WHen standing in a neutral node for 30 seconds, it would change to a green color indicating it was captured. Since the game state is managed by the server, I checked the table to confirm capture worked.

Known Issues
-------------

If user has bluetooth and location off, the program will die.
This is a simple fix, I just haven't got around to it.

If the user changes the orientation if the screen, the MainActivity recreates itself by running onDestroy() and onCreate() and may crash due to a stray null pointer exception.
Ideal fix would be to implement a function that would gracefully handle orientation changes.
Otherwise I would have just locked the orientation to landscape.

if the data that the server returns in get node list and get users is different than expected, the program may throw a out of bounds exception.
No checking is done in these functions and no error handling is present.

Last I tried the battleActivity portion of the game, the game would throw a null pointer exception when going back to MainActivity.
No checking has done, I planned on investigating when gameplay would have been implemented (Brandon's part).

If the HttpURLConnect object in the HttpService.class were to fail due to any issue the user would be left mostly unaware.
The same issue is possible in uploading data;
client does not check if it successfully conected which can lead to issues.
User should at least know when the client can't retrieve/upload information.

Distance Away From Goal
-----------------------

Once all the above issues are fixed, the only thing that really needs to be implemented to make it a full game is a battle element.
I know Alex basically took my bluetooth pong game for his battle component and I could have easily done the same with minimal effort, but pong isn't a fun game.

I originally left the ultimate gameplay plan to Brandon;
I have received his component, however it didn't do much more than my current battle template.

The idea of the gameplay was that player/s would be able to drop onto some 2D field with random terrain and the goal was for the players to kill each other with artillery fire.

I basically had the actual firing of the projectile in the template, but no more. Brandon's seems to implement drawing player pieces and a divider.
He had also implemented hit collision, which basically checked the distance between two circle centers and if distance was equal or less than their combined radius, hit was registered.

Object Relationship Model
--------------------------

A quick sketch showing how the various services and activites interact with the main activity.

![Alt](http://dedominic.pw/media/001.png)


Ultimate Status of Development
------------------------------

As of now, the project is essentially dead.

Despite how close I am to the goal, I feel the overall design is flawed.
I feel the game is too dependent on a reachable internet server.
Without internet, users can't capture nodes or even know of the existence of nearby players.
I also think the use of old bluetooth foolish;
I do not use any of the security features of RFComms and the active energy cost of listening on RFComms is greater than the passive nature of bluetooth LE.

A recreation of the game would use bluetooth LE.
Such technologies would allow for users to discover nearby players WITHOUT a need for a server.
It would also allow for a lower power listening for battle challenges.
