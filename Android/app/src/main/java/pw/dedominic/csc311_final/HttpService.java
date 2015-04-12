/** \file HttpService.java
 * HTTP connection parser and manager
 */

/** \class HttpService
 * connects to server and sends data use get
 * reads returned data and parses it for values
 */

/** \var boolean pendingCSV
 * indicates that there is pending data to be read
 */

/** var String[][] CSV_data
 * data returned from database describing nearby users and locations
 * note, not threadsafe, pendingCSV locks this field
 */

/** \fn boolean login(String username, String pass, String MAC_ADDR)
 * contacts database to check if username is valid
 * also tells database the phone's mac_addr and sets log in flag to one
 *
 * @param username the user's name
 * @param pass user's unhashed password
 * @param MAC_ADDR phone's bluetooth MAC_ADDR
 * @see processForm()
 * @return returns an int
 *         0 = failed to talk to server
 *         1 = there is form data to be read
 *         2 = query successful, process form data
 */

/** \fn void logout(String username, String pass)
 * tells database that player is no longer active
 *
 * @param username the user's name
 * @param pass user's pass
 * @see processForm()
 */

/** \fn boolean updateLoc(double latitude, double longitude)
 * tells database new location
 *
 * @param latitude
 * @param longitude
 * @see processForm()
 * @return returns an int
 *         0 = failed to talk to server
 *         1 = there is form data to be read
 *         2 = query successful, process form data
 */

/** \fn int getloc(double radius)
 * get users in radius passed in
 *
 * @param radius area 
 * @see processForm()
 * @return returns an int
 *         0 = failed to talk to server
 *         1 = there is form data to be read
 *         2 = query successful, process form data
 */

/** \fn boolean processForm(BufferedInputStream in, int type)
 * takes a http stream in and reads it based on type
 *
 * @param in data server sent
 * @param type type of info to expect in query
 * @return returns false if no data or data is not the expect type
 */

/** \fn String[][] returnCsv()
 * returns pending CSV, this function also resets the lock to allow more queries
 *
 * @return returns 2 dimensional array
 */
