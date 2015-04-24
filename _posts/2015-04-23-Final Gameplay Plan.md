---
layout: post
title: Final Gameplay Plan
date: 2015-04-23 19:56:19
---

With the semester wrapping up, I'd like to take a moment to discuss my current plan for gameplay.

Goal
------

The goal of the game is for a team of players to take as many nodes as possible.
The nodes would be randomly spawned in an area about a degree of latitude long and a degree of longitude wide.

Smart Devices player
=====================

Players would play some type of game, yet to be exactly determined (Brandon is working on this), and if they lose, they lose one life of three.
After a battle players would be incapable of action until a set period of time.
If a player is in the radius of a node their team owns, the player would regenerate health at a set period of time.
If a player is in the radius of an unclaimed node without enemy interference, a countdown would start before it is captured.
If a player is in the radius of a claimed node that is owned by an enemy, a countdown would start before capture, however it is much slower.

All that needs to be done to add this functionality to my app is to add some timers and fetch more server data and process it.
Other functions needed are an ability to create a user and/or team and be able to authenticate.
Also, a battle element is needed; as I understand it, Brandon is working on this portion.

Desktop player
===============

To incorporate desktop players, they would see all the nodes and could message players and point them to the next node.
If players were to die, the desktop player could cannibalize some nodes to restore a player back to life.

This is my idea of the desktop app, I'm unaware of my team's progress towards this.

Server side
============

The server would be responsible for spawning nodes, retaining most of the state and indicating who the winners were.

What is needed is more PHP scripts and some updated database tables.
This is the easiest part.
