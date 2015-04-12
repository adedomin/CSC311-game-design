package pw.dedominic.csc311_final;

/**
 * class which contain useful conversion functions
 */
public class Functions
{
	/**
	 * Takes GPS coordinates and converts them to meters
	 *
	 * Function:
	 * $X degrees \times \frac{111.23E3 meters}{1 degree} = X meters$
	 *
	 * @param decimal_degrees raw gps value in decimal degress
	 *                        e.g. 123.235656 degrees
	 * @return returns value in meters
	 */
	public float decimalDegreesToMeters(float decimal_degrees)
	{
		return decimal_degrees * Constants.METERS_PER_DEGREE;
	}

	/**
	 * This function takes value sin meters and converts them to plotable values
	 * in the MapView
	 *
	 * Function:
	 * $X meters \times \frac{Y pixels}{111.23 meters} = XY pixels$
	 *
	 * @param meters GPS data in meters minus offset
	 * @param length pixel length of screen
	 * @return returns a coordinate tht can be plotted on screen
	 */
	public float metersToCartesianCoords(float meters, int length)
	{
		return meters * (length / Constants.METERS_PER_SCREEN_LENGTH);
	}
}
