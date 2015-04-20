---
layout: post
title: Final Project - Walkthrough
date: 2015-04-17 18:46:03
---

See [https://dedominic.pw/CSC311-FINAL-PROJECT-DOC/](https://dedominic.pw/CSC311-FINAL-PROJECT-DOC/) for the javadoc output of current project.

It goes over what has been done in relative detail.

Currently, only the UI has been worked on in good detail

Basically, as soon as the user starts the app, it registers the location service listener and calls for the user to login.
Once logged in, the Views of the main activity are started.
There are three views; a list view with all the players and their distance and MAC address, a text view which contains a message sent to the player and a map view which renders nearby points around the player.

HTTP Services
--------------

In order to communicate with the database, The client needs a http client.

To do this, first the app requres the Internet permissions (add to manifest.xml)
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
Now to communicate the results back to the activity, I use handlers.

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
