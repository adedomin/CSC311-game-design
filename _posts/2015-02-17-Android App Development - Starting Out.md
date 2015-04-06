---
layout: post
title: Android App Development - Starting Out
date: 2015-02-17 12:55:30
---

Intro
=====

When it comes to android apps, there seems to be a few keys things one must be aware of:
 
 1. Activities
 2. Services
 3. Intents

These are the building blocks for a fully functional App.

Activities
==========

[Activities](https://developer.android.com/reference/android/app/Activity.html) are the entry point for all of your apps, it's basically like main.
Activities also are the ui of the program; 
they hold Views, which are areas where things are drawn, menubars and other things.
Because activities are separate, they can not directly message each other.
To send messages to each other, one must use an intent.

Intent
======

[Intents](https://developer.android.com/reference/android/content/Intent.html) are objects which allow an activity to express to another activity what it needs or wants.
They can also hold data so when another activity finishes, it can return some value back to the calling activity

### [calling an activity](https://developer.android.com/reference/android/content/Context.html#startActivity(android.content.Intent\))

simply create a new Intent object with the arguments linking to the current activity (this) and the class file of the activity you want to call.
then just call startActivity with the intent as the parameter.

	Intent intent = new Intent(this, OtherActivity.class);
	startActivity(intent);

### [returning values](https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int\))
To return a value back on activity finish(); the activity has to add a key: value pair to the intent.

before that can be done though, the activity has to be called using
	
	startActivityForResult(intent, CONTEXT);

the context is an int that describes what the called activity should do and return;
in the other actvity, to return something, create a new intent

	Intent intent = new Intent();

then use the .putExtra method to append a key : value pair

	intent.putExtra(KEY, VALUE);

[seting the result](https://developer.android.com/reference/android/app/Activity.html#setResult(int,%20android.content.Intent\)) could be helpful to handle failures or successes this is done with setResult

	setResult(Activity.RESULT_OK, INTENT_TO_RETURN);
	// then finish
	finish();

[Services](https://developer.android.com/reference/android/app/Service.html)
========

Services are the code that runs the background systems like managing connections, fetching resources, etc.
In context of my current project, building a simple game that uses bluetooth networking, the service would handle communication.
