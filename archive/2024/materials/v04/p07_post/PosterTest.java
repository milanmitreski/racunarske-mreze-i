package materials.v04.p07_post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

final class PosterTest {

	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://www.cafeaulait.org/books/jnp3/postquery.phtml");

		Poster poster = new Poster(url);
		poster.add("key1", "value1");
		poster.add("key2", "value2");
		poster.add("key3", "value3");

		try {
			InputStream in = poster.post();

			// Read the response
			BufferedReader r = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = r.readLine()) != null)
				System.out.println(line);

			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
