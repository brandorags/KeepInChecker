package keepinchecker.contstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contstants {
	
	public static final List<String> objectionableWords = new ArrayList<>(Arrays.asList(
			"stackoverflow", "reddit", "imgur", "awesome", "the"));
	
	public static final List<String> packetKeywords = new ArrayList<>(Arrays.asList(
			"GET", "Host", "Referer"));
	
	public static final Map<String, String> thirdParyLibrariesMap = new HashMap<>();
	
	public static Object currentUser = null;
	
	public static String databasePath = null;
	
}
