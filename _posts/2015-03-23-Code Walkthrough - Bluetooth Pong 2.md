---
layout: post
title: Code Walkthrough - Bluetooth Pong 2
date: 2015-03-23 14:13:32
---

Like all programs, they have to start somewhere

For [BluetoothPong2](https://github.com/adedomin/BluetoothPong2) it's MainMenuActivity

MainMenuActivity
================

Basically all MainMenuActivity does is draw buttons and waits for the user to tap one.

It implements an onClick event handler that waits for a screen tap to land on registered views (the buttons).
When such a tap occurs, the program sends a view object that includes the information about the clicked button (including the ID) to onClick(View view)
All onClick's logic does is figure out what button was pressed and how to proceed.  

### Decision

At this point there are only two relevant buttons the user could click on.
If the user clicked on the listen button, then main menu calls the game activity.

To tell the game activity what it should do once launched, the intent that calls the game activity should include a constant value.
In the case of listening it's Constants.LISTEN\_FOR\_CONNECT.

If the user clicked the connect to button, then it gets slightly more complex.

It has to launch a different activity BtConnectActivity to collect the server's mac address.

Once that activity finishes, an event occurs that gets handled by onActivityResult(int, int, Intent).
Like the listen button, this function ultimately calls the GameActivity, the key difference is it also includes an extra with the MAC Address.

BtConnectActivity
=================

Simple activity that spawns a list of paired bluetooth devices. onClickListener is registered for all list objects. Clicking a list object will cause the activity  

BluetoothService
================

Like android services in general, most of the actions here are invisible to the user.

This service contains various threads that establish and oversee the operation of the connection.

### Constructor

Takes android.os.Handler as a param.
This is necessary to communicate information back to the GameActivity.

Information such as data being read in and if the connection has been established or dropped.

### Threads -- ListenThread

The constructor builds a server socket using the UUID string (UUID is suppose to be a unique number that indicates the type of service it's expecting)

This thread simply spawns a server socket waiting for a connection.

This needs its own thread since .accept() method is blocking IO.

When it connects and doesn't error, it spawns a regular socket which is used to communicate between the two machines.

This socket is used in a new thread of type ConnectedThread()

### Threads -- JoinThread

The constructor for this thread creates a socket identified by the UUID.

This is a one shot thread that is blocking IO.
The thread will attempt to call .connect method.
If it works, the socket is good and is used for the connection.

### Threads -- ConnectedThread

Constructor takes a connection socket and spawns IOStreams that are used to form datastreams.

run() is blocking IO that attempts to read the next set of data.

Data is ordered as a identifying char and a float which is a ratio describing a position on the screen.

to find the identifying chars possible, see Constants interface class

It uses the handler passed in to send the read in data to the activity.

write() simply takes a char and a float and writes it to the out stream.

### Other functions

The other functions, connect, write, etc are simply api front ends for interfacing with or starting the various threads

GameActivity
============

This program is responsible for essentially the entire game.

The important features are the Sensor event listener and the handler classes

### Handlers

The handlers are what make the networking component of the game.

There are two because it's impossible to read and write at the same time.

The write handler is for the PongView and the read handler is for the BluetoothService.

The write handler gets messages from PongView about what to send to the opponent.

There are only three types that will actually be sent; only two will be used on each client. Data to be written is retrieved 

 1. Game Over - resets paddles and ball position
 2. Send game state - sends ball position, but also falls through and sends paddle position too
 3. Send Paddle - used by the opponent who doesn't control state.

Read handler gets messages from BluetoothService which contain data it read in from the other opponent

the read in data is then sent to the proper setters in the PongView

### Sensors

The sensor is pretty simple.

registerSensorListener() basically makes the program listen to the accelerometer.
When the accelerometer changes it calls onSensorChanged(SensorEvent)

In this function, I only need the rotation around the y axis which is the first value in the sensor event object.

PongView
========

This portion of the game is basically all the activity the user sees.

It includes classes to describe the game pieces, ball and paddles and it's own Handler.

### DrawHandler

The draw handler is basically a thread that messages itself at a fixed rate. On messaging it invalidates the view which causes the program to redraw the field with new data and to call a function to update the data.

### Game Logic - update()

In the update thread, there are a few conditionals before the game starts playing, these conditionals are in place because a new view has no clue how big it is (getWidth() and getHeight() will return 0) until it starts rendering, the first few conditionals are in place to do blank draw runs to get the globals initialized before gameplay starts due to this strange limitation.
