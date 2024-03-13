package cstest.tohtml;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GenerateLimbMeasureTable {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("data/limbKeys.txt"));
		String html = "";
		String line;
		while (input.hasNextLine()) {
			line = input.nextLine().trim();
			if (line != null && line.length() != 0) {
				if (line.indexOf("\t") < 0) {
					html += "<h3>" + line + "</h3>\n" +
							"<table>\n";
				}
				else {
					
				}
			}
		}

	}

}
