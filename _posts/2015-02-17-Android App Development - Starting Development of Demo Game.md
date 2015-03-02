---
layout: post
title: Android App Development - Starting Development of Demo Game
date: 2015-02-17 13:12:01
---

The Project
===========

now with the basics of app development down, I started working on the demo project.

This project requires that I use the following to construct a game of Pong:

 1. Game uses tilt sensors for controls
 2. Game allows opponent to connect by bluetooth for multiplayer

In order to implement these things I had to look deeper into android app development

Sensors - Accelerometer
=======================

To accomplish the first task I had to figure out how to listen for sensor changes.

### registering a listener for a sensor

Apparently it's quite easy. The Object SensorManager does all the work in regards to listening and reporting.

So All I have to do is create a SensorManager object.

	SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	
then register it mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

### getting sensor events in activity

to get events, the activity has to extent SensorEventListener and @Override and implement onSensorChanged(SensorEvent event)

Bluetooth - Networking
======================

To start, I recommend everyone review the source of the AOSP BluetoothChat program.

With that out of the way, there are two key things to do to connect two devices.

 1. Listening
 2. Connecting

### Listening

In order for a connect to even start, one of the devices has to be listening for connections

The listener must open whats called a server socket which waits for connections, because opening and listening on such a socket is blocking IO, one must spin it off in it's own thread.

when the socket.accept() call suceeds it returns a connection socket; if it fails, it will throw an IOError exception.

### Connecting

To connect, one needs the MAC ADDRESS of the host
Using a BluetoothAdapter object can give the user the ability to interface with the radio and give the user the ability to get a list of all paired bluetooth device.

using a simple activity, a ListView can be made with the data of all paired devices. 

### Connected

Once a device connects, a socket linking the two is formed. To communicate between the two devices requires a input and output stream. Because these streams only accept and read in byte arrays, they are incredibly difficult to work with. Also note that reading and writing is a blocking IO, so make sure they are contained in separate threads.

To make sending data easier, one can take these streams and create DataStream objects. DataStreams are more robust in that a user can write and read more than just byte arrays. The constructor for these objects take streams for arguments.

Handling IO requests with handlers
===================================

Now with a socket open, it's time to discuss how to use it to actually play a game.

### Handlers

A handler is an object which processes messages that it may receive, they do not block since they are threaded.

A handler can also be used to facilitate communication between two different programs or threads.

### Using them

All handler objects must implement a handleMessage() function; this function is where messages will come in.
Using a switch statement, one can determine what type of message it is and what the message should do.

by doing this, it makes it easier to handle messages of different varieties.


