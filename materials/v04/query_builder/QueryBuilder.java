package v04.query_builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QueryBuilder {
    private StringBuilder url;
    private boolean hasQuery;

    public QueryBuilder(String baseUrl) {
        this.url = new StringBuilder(baseUrl);
        this.hasQuery = false;
    }

    public void addQuery(String key, String value) {
        if(!hasQuery) {
            this.url.append("?");
            hasQuery = true;
        } else {
            this.url.append("&");
        }
        this.encode(key, value);
    }

    private void encode(String key, String value) {
        this.url.append(URLEncoder.encode(key, StandardCharsets.UTF_8));
        this.url.append("=");
        this.url.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return this.url.toString();
    }

    public URL toURL() throws MalformedURLException {
        return new URL(this.toString());
    }
}
