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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

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
	private ArrayList<PlayerPoint> POINTS = new ArrayList<>();

	// checks if map ready to render points
	private boolean IS_READY = false;

	/**
	 * In C this would be a Macro.
	 * Sets map boundaries based on Constant Value
	 * </p>
	 * @param x a longitude in decimal degress
	 * @return difference of x minus an offset (0.001)
	 */
	private double map_left_boundary(double x)
	{
		return x - Constants.PLAYER_MAP_VIEW_OFFSET;
	}

	/**
	 * In C this would be a Macro.
	 * Sets map boundary based on Constant Value
	 * </p>
	 * @param x a longitude in decimal degress
	 * @return sum of x minus an offset (0.001)
	 */
	private double map_right_boundary(double x)
	{
		return x + Constants.PLAYER_MAP_VIEW_OFFSET;
	}

	/**
	 * Sets bottom map boundary based on latitude given
	 * </p>
	 * Note: this assumes that screen is in portrait mode.
	 * @param y latitude in decimal degrees
	 * @return sum of latitude + (aspect ratio * a constant(0.001))
	 */
	private double map_bottom_boundary(double y)
	{
		return y - (.4 * Constants.PLAYER_MAP_VIEW_OFFSET);
	}

	// center's location geographically
	// in decimal degrees
	// initially -99, anything below 0 would work
	private double CENTER_LOCATION_LONGITUDE = -99;
	private double CENTER_LOCATION_LATITUDE = -99;

	// delta of Center's previous and Center's current
	private float CENTER_LOCATION_DELTA_X = 0;
	private float CENTER_LOCATION_DELTA_Y = 0;

	public MapView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void setInitialPoint(double lat, double lon)
	{
		CENTER_LOCATION_LATITUDE = lat;
		CENTER_LOCATION_LONGITUDE = lon;
	}

	/**
	 * Draws map when invalidate() is called
	 * called every 1000/30 milliseconds (30 FPS)
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (!IS_READY)
		{
			return;
		}

		PLAYERS_POINT.draw(canvas);

		for (PlayerPoint point : POINTS)
		{
			point.draw(canvas);
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
			addGeoPoint(45.234664, 70.2221, "asd4", 0xFF00FF00);
			addGeoPoint(45.23411, 70.22155, "asd3", 0xFFFF0000);
			addGeoPoint(45.23333, 70.2225, "asd2", 0xFF0000FF);

			double[] xy = decimalDegreesToPixels(
					CENTER_LOCATION_LATITUDE,CENTER_LOCATION_LONGITUDE);
			PLAYERS_POINT = new PlayerPoint((float)xy[0],(float)xy[1], 0xFF000000);
			IS_READY = true;
		}

		mDrawHandler.sleep(1000 / Constants.MAP_VIEW_FPS);
	}

	/**
	 * adds point to red black tree
	 * username is key, value is point
	 * </p>
	 * @param x x coord
	 * @param y y coord
	 * @param u user name string
	 * @param c color as a hexadecimal ARGB number
	 */
	public void addPoint(float x, float y, String u, int c)
	{
		POINTS.add(new PlayerPoint(x, y, c));
	}

	/**
	 * Add Point using Latitude and Longitude
	 * </p>
	 * calls decimalDegreesToPixel then addPoint
	 * @param lat latitude in decimal degrees
	 * @param lon longitude in decimal degrees
	 * @param u user name string
	 * @param c color as a hexadecimal ARGB number
	 */
	public boolean addGeoPoint(double lat, double lon, String u, int c)
	{
		// index 0 = x, index y = 1
		double[] xy = decimalDegreesToPixels(lat, lon);
		addPoint((float)xy[0], (float)xy[1], u, c);

		return true;
	}

	public void updatePoint(float x, float y, String u)
	{
		//POINTS.get(u).setXY(x, y);
	}

	/**
	 * Map activity will send updated GPS Coords here.
	 * Function gets the difference between the new and the old.
	 * This difference is used for translating points away or towards center
	 * </p>
	 * @param lat latitude in decimal degrees
	 * @param lon longitude in decimal degrees
	 */
	public void setCenterLocation(double lat, double lon)
	{
		// function returns an x y pair, index 0 = x; index 1 = y
		double[] xy = decimalDegreesToPixels(lat, lon);
		// Player's point is static, so points should move away when
		// center "moves" away
		CENTER_LOCATION_DELTA_X = (float) (getWidth()/2 - xy[0]);
		CENTER_LOCATION_DELTA_Y = (float) (getHeight()/2 - xy[1]);
		CENTER_LOCATION_LONGITUDE = lon;
		CENTER_LOCATION_LATITUDE = lat;
		translatePoints();
	}

	/**
	 * true mercator projection.
	 * Converts given decimal degree values
	 * and converts them to pixels on device's screen
	 * </p>
	 * @param lat latitude in decimal degrees
	 * @param lon longitude in decimal degrees
	 * @return double array with an (x,y) coordinate pair
	 */
	public double[] decimalDegreesToPixels(double lat, double lon)
	{
		double left_boundary = map_left_boundary(CENTER_LOCATION_LONGITUDE);
		double right_boundary = map_right_boundary(CENTER_LOCATION_LONGITUDE);
		double right_left_boundary_delta = right_boundary - left_boundary;

		double bottom_boundary = map_bottom_boundary(CENTER_LOCATION_LATITUDE);
		double bottom_boundary_rads = bottom_boundary * Math.PI / 180;

		double x = (lon - left_boundary) *
				(getWidth() / right_left_boundary_delta);

		double lat_rads = lat * Math.PI / 180;
		double map_width = ((getWidth() / right_left_boundary_delta) * 360)
				/ (2 * Math.PI);
		double y_offset = (map_width / 2 * Math.log(
				(1 + Math.sin(bottom_boundary_rads)) / (1 - Math.sin(bottom_boundary_rads))
		));
		double y = getWidth() - ((map_width / 2 * Math.log(
				(1 + Math.sin(lat_rads)) / (1 - Math.sin(lat_rads)))) - y_offset);

		double[] return_arr = {x,y};

		return return_arr;
	}

	/**
	 * translate all points, except center point (player) by following vectors.
	 * </p>
	 * Points are either moving away from center point (player) or,
	 * Points are moving towards center point.
	 * For all intents and purposes, center point (player) does not move
	 */
	public void translatePoints()
	{
		for (PlayerPoint point : POINTS)
		{
			point.applyVector(
					CENTER_LOCATION_DELTA_X,
					CENTER_LOCATION_DELTA_Y
			);
		}
		CENTER_LOCATION_DELTA_X = 0;
		CENTER_LOCATION_DELTA_Y = 0;
	}
	/**
	 * Class the describes a point on the map.
	 * Requires x and y and a color
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

		public void applyVector(float _x, float _y)
		{
			x += _x;
			y += _y;
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
