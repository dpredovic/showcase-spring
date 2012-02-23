package showcase.zipresolver;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
@Profile("integration")
public class RestZipResolver implements ZipResolver {

    @Autowired
    private RestOperations restTemplate;

    // url must contain 2 variables: cc and zip - something like "http://..../country/{cc}/city/{zip}/name"
    @Value("${zip.resolver.url}")
    private String url;

    @Override
    public String resolveCity(String countryCode, String zipCode) {
        new HashMap<String, String>().put("cc", countryCode);
        new HashMap<String, String>().put("zip", zipCode);

        return restTemplate.getForObject(url, String.class, new HashMap<String, String>());
    }

}
