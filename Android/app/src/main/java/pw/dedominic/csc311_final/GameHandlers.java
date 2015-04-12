package pw.dedominic.csc311_final;

import android.os.Handler;
import android.os.Message;

/**
 * Various handlers that will assist in organization by keeping them separate
 * From the activities they belong to
 *
 * @see pw.dedominic.csc311_final.MapActivity
 * @see pw.dedominic.csc311_final.MapView
 */
public class GameHandlers
{
	/**
	 * This is the read handler class for the battleView activity
	 * This handler allows for communicating from the btService to the battleView
	 * methods in this handler call various setters in battleView
	 *
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
			switch(msg.what)
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

	/** \class HttpServiceInfo extends Handler
	 * This handler returns messages informing newly found database info
	 * MapActivity fetches 2d arrays of newly discovered entries to be turned into
	 * buttons on the map view
	 *
	 * @see pw.dedominic.csc311_final.GameHandlers.HttpServiceInfo
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
