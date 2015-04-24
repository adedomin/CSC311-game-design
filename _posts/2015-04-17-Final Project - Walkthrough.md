---
layout: post
title: Final Project - Walkthrough
date: 2015-04-17 18:46:03
---

See [https://dedominic.pw/CSC311-FINAL-PROJECT-DOC/](https://dedominic.pw/CSC311-FINAL-PROJECT-DOC/) for the javadoc output of current project.

It goes over what has been done in relative detail.

Currently, only the UI has been worked on in good detail.

HTTP Services
--------------

In order to communicate with the database, The client needs a http client.

To do this, first the app requires the Internet permissions (add to manifest.xml)
	    <uses-permission android:name="android.permission.INTERNET" />
Second, android does not allow you to do http services in a regular thread.
So to solve this, the program needs an AsyncTask.

AsyncTask
==========

To set up one, A class that extends an AsyncTask needs to be made.
e.g.
	private class getCSVTask extends AsyncTask<URL, Void, Void>
	{
		protected Void doInBackground(URL... params)
Then simply implement doInBackground(ARGTYPE... params);

Note, that the values in the carets <> indicate argument types, in progress and return type, in that order.
These values can not be primitives which is why Void is used.
Void is basically null but in a nonprimitive implementation.
Do in background has to accept infinite arguments.
So argtype and ellipsis with a variable name creates an array of type argtype that is as long as there are arguments.

To call this task, just call the execute(args) method.
Note that once a task has run its course, you must recreate it (task = new youTask()) to execute it again.
To communicate results back to an activity, simply use a handler object.

Location Services
------------------

Location was really not hard. Basically just create a LocationManager object.
	LocationManager mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
Now you just have to register a LocationListener.
	mLocationListener = new LocationListener()
	{
		@Override
		public void onLocationChanged(Location location)
	{
		PLAYER_LATITUDE = location.getLatitude();
		PLAYER_LONGITUDE = location.getLongitude();
	});
This listener executes onLocationChanged() when location changes.
Now just tell your LocationManager object to use this listener and the service provider.
	mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
There is also a LocationManager.NETWORK\_PROVIDER provider also.

Walkthrough of the Structure of the Game
--------------------------------------

[MainActivity](https://github.com/adedomin/CSC311-FInal-Project/blob/master/app/src/main/java/pw/dedominic/csc311_final_project/MainActivity.java)
==============

Main activity is the entry of point of the application and includes most of the UI elements of the app.
This app houses three views, a list view which contains all the players participating in the game, a text view which shows the latest message sent to the player and a [Custom view](https://github.com/adedomin/CSC311-FInal-Project/blob/master/app/src/main/java/pw/dedominic/csc311_final_project/MapView.java) which renders the nearby players on a map.

This activity also contains various handlers, some that are effectively timers.
The handlers that implement a sleep(long) function are timers;
They only serve the purpose of executing their handleMessage() code at a fixed interval.
The purpose of these timers are to execute threaded http clients to retrieve game data.

To process server obtained data, the String.split method is used.
String split is useful to create an array of strings delimited by a regular expression.
Since all the server data is in a CSV format, the regex would just have to find the comma.

The list view comes with an onClickListener, so when a list view entry is clicked, it's text can be processed to initialize a battle.
Currently only the MAC Address is processed (last 17 characters in the entry).
For demonstration purposes, the MAC address is used to initiate a connection, but the connection is unused and closed soon after the listener fetches the MAC address.

MapView
========

This view takes a set of points with locations in latitude and longitude then makes a [Mercator plot](http://stackoverflow.com/questions/2103924/mercator-longitude-and-latitude-calculations-to-x-and-y-on-a-cropped-map-of-the/10401734#10401734) of a small area.

Other than that, there is nothing non-trivial in this view.

[HttpService](https://github.com/adedomin/CSC311-FInal-Project/blob/master/app/src/main/java/pw/dedominic/csc311_final_project/HttpService.java)
==============

All of these views get their data from AsyncTasks located in HttpService.
GetCSVTask fetches the list of all users, GetMessageTask fetches the message from the desktop player for the mobile device player and UploadLocationTask which updates the user's current location.

[LoginActivity](https://github.com/adedomin/CSC311-FInal-Project/blob/master/app/src/main/java/pw/dedominic/csc311_final_project/LoginActivity.java)
======================

Login activity is called at the start of the app. It's used to get the user's name currently;
In later versions it would be used to verify the ownership of the user name using password authentication.

Once sent, it returns to MainActivity.onActivityResult() with the user's name in the intent.

[BluetoothService](https://github.com/adedomin/CSC311-FInal-Project/blob/master/app/src/main/java/pw/dedominic/csc311_final_project/BluetoothService.java)
========================

Service similar to the one in BluetoothPong2 project. It's mostly for demonstration purposes. 
Devices would listen on an insecure RFComm socket waiting for other users to connect.
When a connect occurs, both devices should enter battle.
RFComm insecure allows for unpaired devices to communicate, but it's unencrypted;
this lack of encryption may be a plus when it comes to network and processing performance.

BattleView and BattleActivity
==============================

Currently these are sparse in implementation and the current focus as of now.
Basically the current plan is to make a game where two players are somewhat randomly placed on a map with some kind of randomly varying obstructions.
The goal would be for the players to shoot projectiles in an arc over these obstructions and try to hit the enemy.

Currently, only drawing and creating a projectiles is possible, no game play or networking is implemented.

Work Needed
------------

For the most part, only a complete gameplay element is needed + some minor things and the App portion of the game is done.
