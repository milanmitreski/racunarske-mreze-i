package p06_filtered_copy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

final class FilteredCopyMain {

	public static void main(String[] args) {

		/*
			BufferedReader vs Scanner

			As an alternative, we could use the Scanner class to
			achieve the same functionality as with BufferedReader.

			However, there are significant differences between
			these two classes which can make them either more or
			less convenient for us, depending on our use case:

			- BufferedReader is synchronized (thread-safe) while
			Scanner is not
			- Scanner can parse primitive types and strings using
			regular expressions
			- BufferedReader allows for changing the size of the
			buffer while Scanner has a fixed buffer size
			- BufferedReader has a larger default buffer size
			- Scanner hides IOException, while BufferedReader
			forces us to handle it
			- BufferedReader is usually faster than Scanner
			because it only reads the data without parsing it

			With these in mind, if we are parsing individual tokens
			in a file, then Scanner will feel a bit more natural than
			BufferedReader. But, just reading a line at a time is where
			BufferedReader shines.
		 */
		try (Scanner in = new Scanner(
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream("p01_Streams/p06_filtered_copy/in.txt"),
								StandardCharsets.UTF_8
						)
				)
			);
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream("p01_Streams/p06_filtered_copy/out.txt"),
							StandardCharsets.UTF_8
					)
			);
		) {
			in.useDelimiter("\\b");

			while (in.hasNext()) {
				String word = in.next();
				if (isName(word)) {
					out.write(word);
					out.newLine();
				}
			}
			
			System.out.println("Done!");
		} catch (IOException e) {
			System.err.println("Failed to write to output file!");
		}
	}

	// Assuming words are separated by whitespace only
	private static boolean isName(String word) {
		if (word.length() < 2)
			return false;
		
		if (!Character.isUpperCase(word.charAt(0)))
			return false;

		return word.chars()
				.skip(1)
				.allMatch(Character::isLowerCase)
				;
	}
	
}
