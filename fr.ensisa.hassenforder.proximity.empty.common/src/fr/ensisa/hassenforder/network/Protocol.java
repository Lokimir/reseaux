package fr.ensisa.hassenforder.network;

public interface Protocol {

	public static final Object EXIT_TEXT = "exit";

	public static final int PROXIMITY_PORT_ID = 1337;
	
	public static final int CONNECT_QUERY = 1;
	public static final int MOVE_QUERY = 2;
	public static final int RADIUS_QUERY = 3;
	public static final int REFRESH_QUERY = 4;
	public static final int MODE_QUERY = 5;
	public static final int PREF_LEVEL_QUERY = 6;
	public static final int PREF_VISIBILITY_QUERY = 7;
	public static final int FIND_QUERY = 8;

	public static final int QUERY_OK = 1;
	public static final int QUERY_ERROR = 0;
}
