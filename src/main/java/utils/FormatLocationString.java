package utils;

public class FormatLocationString {
	
	private FormatLocationString() {}

	public static String createLocString(String string) {
		String result;
		
		result = string.replaceAll("\\s+", "+");
		
		return result;
	}
}
