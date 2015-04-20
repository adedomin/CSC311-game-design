---
layout: post
title: Desktop app - Where it is at now
date: 2015-04-17 13:53:54
---

Being that I'm not working on it, it's still useful to be able to read the code and be able to understand it

The desktop app is a python script which uses pygame to render an image on a map and to register mouse clicks.

Functions
==========

The program has two at the moment, one which generates a textSurface to be rendered and another which draws the textSurfaces to the screen

Main Event Loop
================

As it stands now, All the event loop does is look forfor keydown and mouse down events.

If keydown and key has the value q, the game quits.

If the Left mouse button is pressed it drops an image on the screen as that mouse location.

RIght click is like above, but the symbol is different.

Future additions
================

Overall, this program needs some functionality.

It needs an ability to fetch data from a server for points to draw.

It needs an ability to send messages to a server.
