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

/**
 * interface describing all constants.
 * Constants range from message types to layout ratios
 */
public interface Constants
{
	/**
	 * All message types possible in game.
	 * handled by respective handlers
	 * <p/>
	 * Handlers are second term in variable name
	 */
	final public int MESSAGE_CONNECT_STATE = 0;
	final public int MESSAGE_HTTP_PARSE_CSV = 1;
//	final public int MESSAGE_;
//	final public int MESSAGE_;
//	final public int MESSAGE_;
//	final public int MESSAGE_;
//	final public int MESSAGE_;
//	final public int MESSAGE_;
//	final public int MESSAGE_;

	/**
	 * these are all layout relevant constants
	 */
	public float METERS_PER_DEGREE = (float) 111.23E3;
	public float METERS_PER_SCREEN_LENGTH = (float) 111.23;
	public double APPROX_CIRCUMFERENCE_EARTH_METERS = 40075E3;
	public double APPROX_EARTH_RADIUS = 6371E3;

	/**
	 * View specific constants
	 */
	public int NEUTRAL_COLOR = 0xFFEEEEEE;
	public int PLAYERS_COLOR = 0xFF0000FF;
	public int VIEW_BALL_RADIUS = 10;
	public int MAP_VIEW_FPS = 30;
}
