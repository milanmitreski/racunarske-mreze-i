package materials.v04.p06_query_builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QueryBuilder {

    private StringBuilder url;
    private Boolean hasQuery;

    public QueryBuilder(String baseUrl) {
        this.url = new StringBuilder(baseUrl);
        this.hasQuery = false;
    }

    // http://alas.matf.bg.ac.rs/resource append lan=cyr --> ?
    // http://alas.matf.bg.ac.rs/resource?max=20 append lan=cyr --> &
    public void appendQuery(String name, String value) {
        this.url.append(hasQuery ? '&' : '?');
        this.hasQuery = true;
        this.encode(name, value);
    }

    // Ako bi neki parametar imao vrednost "Pera Peric", s obzirom
    // da URL ne sme da sadrzi beline, to znaci da bi morali da beline
    // enkodiramo! Za to koristimo URLEncoder klasu:
    private void encode(String name, String value) {
        this.url.append(URLEncoder.encode(name, StandardCharsets.UTF_8));
        this.url.append("=");
        this.url.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return this.url.toString();
    }

    public URL toUrl() throws MalformedURLException {
        return new URL(this.url.toString());
    }
}
