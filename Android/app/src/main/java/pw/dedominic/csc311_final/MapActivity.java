/*
 * Copyright (c) 2015. Anthony DeDominic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pw.dedominic.csc311_final;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * \class MapActivity
 * This is the map activity class which is the main entry point for this app
 * The only other activities are login and battleView
 * </p>
 * @see pw.dedominic.csc311_final.MapView
 */
public class MapActivity extends Activity
{
	// the map drawn on screen
	private MapView mMapView;

	/**
	 * Handler Objects
	 */
	private BtHandleRead mBtHandleRead;
	private BtHandleWrite mBtHandleWrite;

	private HttpServiceInfo mHttpServiceInfo;
	private DesktopNotifications mDesktopNotifications;

	private GpsHandler mGpsHandler;

	// bluetooth radio device.
	//private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	// player user name.
	private String USERNAME = "";

	// player's unhashed password; hashes at DB.
	private String UNHASHED_PASSWORD = "";

	// player's Bluetooth MAC ADDRESS.
	// Allows for seamless connection with enemies.
	//private String BT_ADDR = mBluetoothAdapter.getAddress();

	/**
	 * Used to get difference to accurately Scroll MapView.
	 */
	private float GPS_COORD_LAST_LATI;

	/**
	 * Used to get difference to accurately Scroll MapView.
	 */
	private float GPS_COORD_LAST_LONG;

	// is connected and in battle or soon to be in battle
	private boolean IS_BT_CONNECT = false;

	// var indicates if last HTTP connection was successful
	private boolean IS_HTTP_CONNECT = false;

	// Desktop user is actively connected and sending messages
	private boolean IS_DESKTOP_CONNECT = false;

	/**
	 * entry point
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		mMapView = (MapView) findViewById(R.id.mapView);
		mMapView.setInitialPoint(45.2340,70.2220);
		mMapView.update_map();

	}

	/** \fn boolean loginResult(Intent intent)
	 * checks results of LoginActivity
	 * If passed, parses Intent for user and password entered
	 * If failed, it signals for program to terminate
	 *
	 * @param intent contains messages passed back by LoginActivity
	 * @return returns bool indicating if user logged in or cancelled
	 */

	/**
	 * \fn void startBattle(int initiator, String ENEMY_MAC_ADDR)
	 * starts battle view.
	 *
	 * @param initiator      who started battle, starter controls state
	 * @param ENEMY_MAC_ADDR mac address of enemy to connect to
	 */
	void startBattle(boolean initiator, String ENEMY_MAC_ADDR)
	{
		return;
	}

	/**
	 * \fn void connectToEnemy(String MAC_ADDR)
	 * function that handles connecting opponents using BtConnectService
	 * called by startBattle()
	 *
	 * @param MAC_ADDR mac address of opponent.
	 */
	void connectToEnemy(String MAC_ADDR)
	{
		return;
	}

	/**
	 * This is the read handler class for the battleView activity
	 * This handler allows for communicating from the btService to the battleView
	 * methods in this handler call various setters in battleView
	 * <p/>
	 * This is a subclass of MapActivity
	 *
	 * @see pw.dedominic.csc311_final.BattleView
	 * @see pw.dedominic.csc311_final.BluetoothConnectService
	 */
	public class BtHandleRead extends Handler
	{
		/**
		 * This where messages are handled
		 * switch statement checks msg.what param for message type and
		 * handles it accordingly
		 *
		 * @param msg a object of type message
		 *            contains information on what it is
		 *            and what to do with any data included
		 */
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case Constants.MESSAGE_CONNECT_STATE:
					break;
			}
		}
	}

	/**
	 * This class allows for battleView to communicate to the MapActivity
	 * battleView will send a message and MapView.btHandleWrite will fetch neccesary
	 * information to send where it needs to go.
	 *
	 * @see pw.dedominic.csc311_final.BattleView
	 * @see pw.dedominic.csc311_final.BluetoothConnectService
	 */
	public class BtHandleWrite extends Handler
	{
		/**
		 * Handles messages from BattleView to be written to bluetooth socket
		 *
		 * @param msg
		 */
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case Constants.MESSAGE_CONNECT_STATE:
					break;
			}
		}
	}

	/**
	 * Informs the MapActivity about current location as reported by GPS
	 *
	 * @see pw.dedominic.csc311_final.GpsService
	 */
	public class GpsHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case Constants.MESSAGE_CONNECT_STATE:
					break;
				default:
			}
		}
	}

	/**
	 * \class HttpServiceInfo extends Handler
	 * This handler returns messages informing newly found database info
	 * MapActivity fetches 2d arrays of newly discovered entries to be turned into
	 * buttons on the map view
	 */
	public class HttpServiceInfo extends Handler
	{
		/**
		 * Handles csv's to be parsed
		 *
		 * @param msg includes an unparsed csv
		 */
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case Constants.MESSAGE_CONNECT_STATE:
					break;
				case Constants.MESSAGE_HTTP_PARSE_CSV:
					break;
			}
		}
	}

	/**
	 * This handler connects to a server which waits for desktop users to push info
	 * info is handled here
	 *
	 * @see pw.dedominic.csc311_final.DesktopNotificationServices
	 */
	public class DesktopNotifications extends Handler
	{
		/**
		 * Handles all messages the desktop will send to the player
		 *
		 * @param msg contains type of message and data associated with type
		 */
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case Constants.MESSAGE_CONNECT_STATE:
					break;
				//etc
			}
		}
	}
}
