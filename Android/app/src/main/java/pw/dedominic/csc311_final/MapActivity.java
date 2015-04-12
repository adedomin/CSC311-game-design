/** \file MapActivity.java
 * entry point for app
 */
package pw.dedominic.csc311_final;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.view.View;

/**
 * \class MapActivity
 * This is the map activity class which is the main entry point for this app
 * The only other activities are login and battleView
 *
 * @see LoginActivity
 * @see BattleView
 */
public class MapActivity extends Activity
{
	/**
	 * Services and other classes needed
	 */
	private Functions mFunctions = new Functions();

	/**
	 * Handler Objects
	 * @see pw.dedominic.csc311_final.GameHandlers
	 */
	private GameHandlers.BtHandleRead mBtHandleRead;
	private GameHandlers.BtHandleWrite mBtHandleWrite;

	private GameHandlers.HttpServiceInfo mHttpServiceInfo;
	private GameHandlers.DesktopNotifications mDesktopNotifications;

	private GameHandlers.GpsHandler mGpsHandler;

	// bluetooth radio device.
	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	// player user name.
	private String USERNAME = "";

	// player's unhashed password; hashes at DB.
	private String UNHASHED_PASSWORD = "";

	// player's Bluetooth MAC ADDRESS.
	// Allows for seamless connection with enemies.
	private String BT_ADDR = mBluetoothAdapter.getAddress();

	/**
	 * Used to get difference to accurately Scroll MapView.
	 * @see pw.dedominic.csc311_final.MapView.viewTranslate()
	 */
	private float GPS_COORD_LAST_LATI;

	/**
	 * Used to get difference to accurately Scroll MapView.
	 * @see pw.dedominic.csc311_final.MapView.viewTranslate()
	 */
	private float GPS_COORD_LAST_LONG;

	// is connected and in battle or soon to be in battle
	private boolean IS_BT_CONNECT = false;

	// var indicates if last HTTP connection was successful
	private boolean IS_HTTP_CONNECT = false;

	// Desktop user is actively connected and sending messages
 	private boolean IS_DESKTOP_CONNECT = false;

	/**
	 * Function that takes a view and moves it by vectors defined
	 * Requires the views have some previous state
	 *
	 * @param newGPS_X new GPS coords, latitude
	 * @param newGPS_Y new GPS coords, longitude
	 */
	private void translateView(float newGPS_X, float newGPS_Y)
	{
		return;
	}

	/** \fn boolean loginResult(Intent intent)
	 * checks results of LoginActivity
	 * If passed, parses Intent for user and password entered
	 * If failed, it signals for program to terminate
	 *
	 * @param intent contains messages passed back by LoginActivity
	 * @return returns bool indicating if user logged in or cancelled
	 */

	/** \fn void startBattle(int initiator, String ENEMY_MAC_ADDR)
	 * starts battle view.
	 *
	 * @param initiator who started battle, starter controls state
	 * @param ENEMY_MAC_ADDR mac address of enemy to connect to
	 */
	void startBattle(boolean initiator, String ENEMY_MAC_ADDR)
	{
		return;
	}

	/** \fn void connectToEnemy(String MAC_ADDR)
	 * function that handles connecting opponents using BtConnectService
	 * called by startBattle()
	 *
	 * @param MAC_ADDR mac address of opponent.
	 */
	void connectToEnemy(String MAC_ADDR)
	{
		return;
	}

	/** \fn void createMapNode(double x, double y, String user_name, int team_id)
	 * creates map buttons that indicate where players are
	 * clicking on them will result in getting info about user
	 *
	 * @param x x position on map
	 * @param y y position on map
	 * @param user_name name of user
	 * @param team_id determines color of node to indicate who node belongs to
	 */
	void createMapNode(double x, double y, String user_name, int team_id)
	{
		return;
	}

	/**
	 * takes latitude and edits it to be viewable on map
	 *
	 * @param latitude latitude received from gps or database
	 * @return value minus a set constant
	 *         will be X coord on map
	 */
	float latitudeToMapCoord(float latitude)
	{
		return mFunctions.decimalDegrees_to_meters(latitude);
	}

	/** \fn double longitudeToMapCoord(double longitude)
	 * takes longitude and returns map location
	 *
	 * @param longitude longitude from gps or database
	 * @return value minus a set constant
	 */
	private

	/** \fn void toastMessage(String message)
	 * message to be read by phone user
	 *
	 * @param message the message to display to user
	 */

	/** \fn void coordMessage(double x, double y, String message)
	 * message telling user where to go + a message
	 *
	 * @param x location on map
	 * @param y location on map
	 * @param message message to pass to user
	 */
}
