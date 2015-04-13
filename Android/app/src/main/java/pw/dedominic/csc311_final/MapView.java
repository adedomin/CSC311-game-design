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

	private double map_left_boundry(double x)
	{
		return x - Constants.PLAYER_MAP_VIEW_OFFSET;
	}

	private double map_right_boundry(double x)
	{
		return x - Constants.PLAYER_MAP_VIEW_OFFSET;
	}

	private double map_bottom_bountry(double y)
	{
		double aspect_ratio = getWidth()/getHeight();
		return y - (aspect_ratio * Constants.PLAYER_MAP_VIEW_OFFSET);
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

	public void addGeoPoint(double lat, double lon, String u, int c)
	{
		// index 0 = x, index y = 1
		double[] xy = decimalDegreesToPixels(lat, lon);
		addPoint((float)xy[0], (float)xy[1], u, c);
	}

	public void updatePoint(float x, float y, String u)
	{
		POINTS.get(u).setXY(x, y);
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
		if (CENTER_LOCATION_LATITUDE < 0 || CENTER_LOCATION_LONGITUDE < 0)
		{
			CENTER_LOCATION_LATITUDE = lat;
			CENTER_LOCATION_LONGITUDE = lon;
		}

		// function returns an x y pair, index 0 = x; index 1 = y
		double[] xy = decimalDegreesToPixels(lat, lon);
		// Player's point is static, so points should move away when
		// center "moves" away
		CENTER_LOCATION_DELTA_X = (float) (getWidth()/2 - xy[0]);
		CENTER_LOCATION_DELTA_Y = (float) (getHeight()/2 - xy[1]);
		CENTER_LOCATION_LONGITUDE = lon;
		CENTER_LOCATION_LATITUDE = lat;
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
		double left_boundary = map_left_boundry(CENTER_LOCATION_LONGITUDE);
		double right_boundary = map_right_boundry(CENTER_LOCATION_LONGITUDE);
		double bottom_boundary = map_bottom_bountry(CENTER_LOCATION_LATITUDE);

		double x = (lon - left_boundary) *
				(getWidth() / (right_boundary - left_boundary));

		lat = lat * Math.PI / 180;
		double map_width = ((getWidth() / right_boundary - left_boundary) * 360)
				/ (2 * Math.PI);
		double y_offset = (map_width / 2 * Math.log(
				(1 + Math.sin(bottom_boundary)) / (1 - Math.sin(bottom_boundary))
		));
		double y = getHeight() - ((map_width / 2 * Math.log(
				(1 + Math.sin(lat)) / (1 - Math.sin(lat)))) - y_offset);

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
		for (Map.Entry<String, PlayerPoint> entry : POINTS.entrySet())
		{
			entry.getValue().applyVector(
					CENTER_LOCATION_DELTA_X,
					CENTER_LOCATION_DELTA_Y
			);
		}
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
