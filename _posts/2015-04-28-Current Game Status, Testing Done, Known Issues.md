---
layout: post
title: Current Game Status, Testing Done, Known Issues
date: 2015-04-28 20:22:52
---

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

The results are properly sent back since the game use to send a toast with the values added

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


