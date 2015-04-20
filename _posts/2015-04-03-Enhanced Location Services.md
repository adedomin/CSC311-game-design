---
layout: post
title: Enhanced Location Services
date: 2015-04-03 15:35:36
---

Sadly, GPS isn't always 100% practical for getting pinpoint accurate location.
To alleviate this, most enhanced location services like google's, use nearby [wireless routers](https://support.google.com/gmm/answer/2839911?hl=en) to better lock down where someone is located.

To offer an even better location service, bluetooth le becaons could be used.
Bluetooth LE allows for things like beacons which broadcast a simple signal that carries a URI and UUID.
These beacons can allow for better location pinpointing and also allow for more interactivity with the physical world

Players in the space
=====================

Google is a major player in the space as it can greatly enhance interactivity of smartdevices and the world.
To make this a reality, google released its [PhysicalWeb](https://google.github.io/physical-web/) project.
The idea behind physical web is that users can aproch things like bus stops and get a webpage showing the bus schedule or when the bus will arrive.
The other enhancement is that it eliminates the need for specialized apps.

Apple has also stepped in by offering their [iBeacon](ihttps://developer.apple.com/ibeacon/).
Like the physical web, Apple suggests this can add more interactivity with the real world through smartdevices.
Unlike Physical web however, Apple's implementation only allows for broadcasting a UUID, and 2, 2 byte, values that are implemented by the developer.
This means that iBeacon technologies would require specialized apps to give this interactivity.
The goal of the iBeacon overall is less ambitious.
Apple does not recommend it to be used for location and that it is only a uni-directional broadcast; 
users can't use ibeacon for passive communication between each other.

Health monitoring sensor providers, like fitbit benefit from this technology.
Many of their products are very small, so there isn't much room for a battery.
However, bluetooth LE allows for a much [lower energy](http://www.bluetooth.com/Pages/Bluetooth-Smart.aspx) broadcasting solution.

Overall, there are many players in the space, as [this list](http://www.bluetooth.com/Pages/Bluetooth-Smart-Devices-List.aspx) shows.
It makes sense too since this technology has many [advantages](https://en.wikipedia.org/wiki/Bluetooth_low_energy#Radio_interface) over the connection oriented bluetooth of the past.
It's the ideal technology for a smart device world where everything can be interfaced with.

Game Enhancements
==================

If the player's phones are themselves acting as beacons, then players could use this technology to home in on their opponents
As they get closer in proximity, the game will eventually switch to a battle mode.

This can also help with location in general.
bluetooth beacons could be placed in accurately measured locations and can broadcast a continuous signal.
The strength of this signal and the beacons transmission power can be used to determine proximity to said beacons [src](https://en.wikipedia.org/wiki/Bluetooth_low_energy#Proximity_sensing).
If enough becaons (3) are placed in range, then a fixed point can be derived using triangulation.

In areas, like buildings where GPS may not penetrate, these bluetooth beacons can improve accuracy or at least confirm that a client is in a building.

The other plus is the game could be implemented in javascript, and be servered over the web.
This would eliminate the need for an app altogether.

How it Works - The Protocol
===========================

The receiving device passively listens for these signals and then measures RSSI (received signal strength indicator).

### Programmatic

programmatically, to start scanning for Bluetooth Low Energy, you need the BluetoothLeScanner object which is returned by BluetoothAdapter.getLeScanner()

This allows performing scanning.

When running startScan(); the function will create an event that is handled by an anonymous ScanCallback class or an ScanCallback extended class.
The extended class must implement onScanResult();

For URI beacons, there is a UriBeacon object that takes a byte array which can be taken from ScanResult.getScanRecord().getBytes()

rssi can be collected by ScanResult.getRssi(); this is useful for deriving range measurements

Eaxample code in action can be found at: [physicalweb](https://github.com/google/physical-web/blob/master/android/PhysicalWeb/app/src/main/java/org/physical_web/physicalweb/UriBeaconDiscoveryService.java)

### calculate distance

To get a distance estimate, it's required to know the transmission power of the beacon and the received signal strength.
This can be used to derive a equation to find the distance:

[d = 10 ^ ((TxPower - RSSI) / (10 * n))](http://stackoverflow.com/questions/22784516/estimating-beacon-proximity-distance-based-on-rssi-bluetooth-le)

where d = distance, A = txPower, n = signal propagation constant and [RSSI] = dBm.

### Triangulation

To triangulate, there needs to be at least 3 beacons that know where they are located.
From the beacon out to the phone is a radius in a circle drawn around the beacons;
this distance is found in the above equation.
Using the circle formula: 
	(x - latitude)^2 + (y - longitude)^2 = radius^2
with these equations for each circle, you can find point of intersection by solving the system of equations.
