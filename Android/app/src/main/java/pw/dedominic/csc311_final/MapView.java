package pw.dedominic.csc311_final;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Simple map view that renders points only
 * no imaged map
 * </p>
 * The map is suppose to be roughly ~100 meters along it's longest side.
 */
public class MapView extends View
{
	// communicate with game
	private Handler mHandler;

	// render changes every 1000/FPS millisec
	private DrawHandler mDrawHandler = new DrawHandler();

	// player's point
	private PlayerPoint PLAYERS_POINT;

	// array of all map points
	private TreeMap<String, PlayerPoint> POINTS = new TreeMap<>();

	// checks if map ready to render points
	private boolean IS_READY = false;

	// center's location, EN = Easting and North Coordinate system
	private double CENTER_LOCATION_EN_X;
	private double CENTER_LOCATION_EN_Y;

	public MapView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (!IS_READY)
		{
			return;
		}

		PLAYERS_POINT.draw(canvas);

		for (Map.Entry<String, PlayerPoint> entry : POINTS.entrySet())
		{
			entry.getValue().draw(canvas);
		}
	}

	/**
	 * This thread is basically the entry point that starts the map to render
	 * All this function does is check that constants are set and calls
	 * Draw Handler every 1000 / FPS millisecons otherwise
	 */
	public void update_map()
	{
		// if these are zero, then the field needs to be rendered
		if (getHeight() == 0 || getWidth() == 0)
		{
			mDrawHandler.sleep(1);
			return;
		}


		if (!IS_READY)
		{
			PLAYERS_POINT = new PlayerPoint(
					getWidth()/2,
					getHeight()/2,
					0xFF0000FF);
			IS_READY = true;
		}

		mDrawHandler.sleep(1000 / Constants.MAP_VIEW_FPS);
	}

	/**
	 * adds point to red black tree
	 * username is key, value is point
	 *
	 * @param x x coord
	 * @param y y coord
	 * @param u user name string
	 * @param c color as a hexadecimal ARGB number
	 */
	public void addPoint(float x, float y, String u, int c)
	{
		if (POINTS.get(u) != null)
		{
			updatePoint(x,y,u);
		}
		POINTS.put(u, new PlayerPoint(x, y, c));
	}

	public void addPoint_EN_coord(double x, double y, String u, int c)
	{
		float map_point_x = (float) Math.abs(CENTER_LOCATION_EN_X - x);
		float map_point_y = (float) Math.abs(CENTER_LOCATION_EN_Y - y);
		addPoint(map_point_x, map_point_y, u, c);
	}

	public void updatePoint(float x, float y, String u)
	{
		POINTS.get(u).setXY(x, y);
	}

	public void setCenterLocation(double x, double y)
	{
		CENTER_LOCATION_EN_X = x;
		CENTER_LOCATION_EN_Y = y;
	}

	/**
	 * translate all points, except center point (player) by following vectors.
	 * </p>
	 * Points are either moving away from center point (player) or,
	 * Points are moving towards center point.
	 * For all intents and purposes, center point (player) does not move
	 * @param x vector x
	 * @param y vector y
	 */
	public void translatePoints(float x, float y)
	{

	}

	/**
	 * Class the describes a point on the map.
	 * Requires username, x and y and a color
	 */
	private class PlayerPoint
	{
		private float x;
		private float y;
		private Paint color;

		public PlayerPoint(float _x, float _y, int player_color)
		{
			x = _x;
			y = _y;
			color = new Paint();
			color.setColor(player_color);
		}

		public void setXY(float _x, float _y)
		{
			x = _x;
			y = _y;
		}

		public void draw(Canvas canvas)
		{
			canvas.drawCircle(x, y, Constants.VIEW_BALL_RADIUS, color);
		}
	}

	/**
	 * Handler that basically invalidates the view at a fixed rate
	 * rate is defined at bottom of update_map()
	 */
	private class DrawHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			update_map();
			invalidate();
		}

		public void sleep(long milliseconds)
		{
			removeMessages(0);
			sendMessageDelayed(obtainMessage(0), milliseconds);
		}
	}
}
