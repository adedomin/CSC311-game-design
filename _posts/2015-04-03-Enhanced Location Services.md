---
layout: post
title: Enhanced Location Services
date: 2015-04-03 15:35:36
---

Sadly, GPS isn't always 100% practical for getting pinpoint accurate location.
To alleviate this, most enhanced location services like google's, use nearby wireless routers to better lock down where someone is located.

To offer an even better location service, pushes are being made in Bluetooth LE enabled devices.
Bluetooth LE allows for things like beacons which broadcast a simple signal that carries a URI and UUID.
These beacons can allow for better location pinpointing and also allow for more interactivity with the physical world

Game Enhancements
==================

If the player's phones are themselves acting as beacons, then players could use this technology to home in on their opponents
As they get closer in proximity, the game will eventually switch to a battle mode.

This can also help with location in general.
bluetooth beacons could be placed in accurately measured locations and can broadcast a continuous signal.
The strength of this signal can be used to determine proximity to said beacons.
If enough becaons (3) are placed in range, then a fixed point can be derived using triangulation.

In areas, like building where GPS may not penetrate, these bluetooth beacons can drastically improve accuracy.

The old bluetooth standard is basically a serial cable emulator, basically a replacement for wires;
Bluetooth LE is radically different, allowing for more passive communication and unreliable data in general.
Because of this, devices can use less energy by not actively listening and maintaining a connection.
Overall, Bluetooth LE is better geared for the internet of things and physical web.

How it Works - The Protocol
===========================

The receiving device passively listens for these signals and then measures RSSI (received signal strength indicator).
If the RSSI exceeds a certain threshold, the device assumes it's right on top of the beacon. 
