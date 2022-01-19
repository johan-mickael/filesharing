package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Properties_Reader {
	public static String getLine(String var, String file){
			InputStream input;
			Properties prop = new Properties();
			try {
				input = new FileInputStream(file);
				prop.load(input);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return prop.getProperty(var);
	}
	
	public InputStream regenerateFile(String f) throws FileNotFoundException {
		File file = new File(f);
		return new FileInputStream(file);
	}
}
