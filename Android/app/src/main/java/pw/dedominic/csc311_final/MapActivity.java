/** \file MapActivity.java
 * entry point for app
 */

/** \class MapActivity
 * This is the map activity class which is the main entry point for this app
 * The only other activities are login and battleView
 *
 * @see LoginActivity 
 * @see battleView
 */

/** \var BluetoothAdapter mBluetoothAdapter
 * The bluetooth radio
 */

/** \var String USERNAME
 * player user name
 */

/** \var String UNHASHED_PASSWORD
 * player password for database authentication
 */

/** \var String BT_ADDR
 * String which contains the user's bluetooth MAC_ADDR
 */

/** \var double ImageViewLast_X
 * Used to get difference to accurately Scroll ImageViews
 */

/** \var double ImageViewLast_Y
 * Used to get difference to accurately Scroll ImageViews

/** \var boolean IS_BT_CONNECT
 * is connected and in battle or soon to be in battle
 */

/** \var boolean IS_HTTP_CONNECT
 * Last connection to database was successful
 */

/** \var boolean IS_DESKTOP_CONNECT
 * Desktop user is actively connected and watching a battle
 */ 

/** \class BtHandleRead extends Handler
 * This is the read handler class for the battleView activity
 * This handler allows for communicating from the btService to the battleView
 * methods in this handler call various setters in battleView
 * 
 * This is a subclass of MapActivity
 *
 * @see battleView
 * @see btConnectService
 */

/** \class BtHandleWrite extends Handler
 * This class allows for battleView to communicate to the MapActivity
 * battleView will send a message and MapView.btHandleWrite will fetch neccesary
 * information to send where it needs to go.
 *
 * @see battleView
 * @see btConnectService
 */

/** \class GpsServiceInfo extends Handler
 * Informs the MapActivity about current location as reported by GPS
 *
 * @see gpsService
 */

/** \class HttpServiceInfo extends Handler
 * This handler returns messages informing newly found database info
 * MapActivity fetches 2d arrays of newly discovered entries to be turned into
 * buttons on the map view
 * @see HttpService
 */

/** \class DesktopInterfaceHandler extends Handler
 * This handler connects to a server which waits for desktop users to push info
 * info is handled here
 */

/** \class DesktopViewShareHandler extends Handler
 * Allows desktop user to get state of battle to render it on their screen
 */

/** \fn void translateView(View view, double x_vector, double y_vector)
 * Function that takes a view and moves it by vectors defined
 * Requires the views have some previous state
 *
 * @param view any class that extends view
 * @param x_vector movement on x
 * @param y_vector movement on y
 * @see onTouch()
 */

/** \fn void zoomView(View view, double factor)
 * Function that grows or shrinks views
 *
 * @param view view being zoomed
 * @param factor how much it's being zoomed
 */

/** \fn void startLogin()
 * initializes the login Activity if user not logged in
 */

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
 * @param initiator who started battle, starter controlls state
 * @param ENEMY_MAC_ADDR mac address of enemy to connect to
 */

/** \fn void connectToEnemy(String MAC_ADDR)
 * function that handles connecting opponents using BtConnectService
 * called by startBattle()
 *
 * @param MAC_ADDR mac address of opponent.
 */

/** \fn void createMapNode(double x, double y, String user_name, int team_id)
 * creates map buttons that indicate where players are
 * clicking on them will result in getting info about user
 *
 * @param x x position on map
 * @param y y position on map
 * @param user_name name of user
 * @param team_id determines color of node to indicate who node belongs to
 */

/** \fn double latitudeToMapCoord(double Latitude)
 * takes latitude and edits it to be viewable on map
 * 
 * @param latitude latitude received from gps or database
 * @return value minus a set constant
 */

/** \fn double longitudeToMapCoord(double longitude)
 * takes longitude and returns map location
 *
 * @param longitude longitude from gps or database
 * @return value minus a set constant
 */

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
