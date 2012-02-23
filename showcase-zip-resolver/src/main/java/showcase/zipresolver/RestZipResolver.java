package showcase.zipresolver;

import java.util.HashMap;
import javax.annotation.PostConstruct;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("production")
public class RestZipResolver implements ZipResolver {

    private RestTemplate restTemplate;

    // url must contain 2 variables: cc and zip - something like "http://..../country/{cc}/city/{zip}/name"
    @Value("${zip.resolver.url}")
    private String url;

    @Override
    public String resolveCity(String countryCode, String zipCode) {
        new HashMap<String, String>().put("cc", countryCode);
        new HashMap<String, String>().put("zip", zipCode);

        return restTemplate.getForObject(url, String.class, new HashMap<String, String>());
    }

    @PostConstruct
    private void init() {
        HttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate = new RestTemplate(requestFactory);
    }
}
