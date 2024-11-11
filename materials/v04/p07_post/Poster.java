package url.g2.post;

// Using QueryBuilder from previous example

import url.g2.querybuilder.QueryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

final class Poster {

	private URL url;
	private QueryBuilder query;
	

	Poster(URL url) {
		if (!url.getProtocol().toLowerCase().startsWith("http"))
			throw new IllegalArgumentException("Posting only works for http(s) URLs");
		this.url = url;
		this.query = new QueryBuilder(this.url.toString());
	}

	
	void add(String name, String value) {
		this.query.appendQuery(name, value);
	}

	InputStream post() throws IOException {
		// Open the connection and prepare it for POST
		URLConnection uc = url.openConnection();
		uc.setDoOutput(true);

		OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream(), StandardCharsets.UTF_8);

		out.write(this.query.toString());
		out.flush();
		out.close();

		// Return the response
		return uc.getInputStream();
	}
}
