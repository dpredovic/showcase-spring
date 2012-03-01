package showcase.addressresolver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
@Profile("integration")
public class RestAddressResolver implements AddressResolver {

    @Autowired
    private RestOperations restTemplate;

    // url must contain 2 variables: cc and zip - something like "http://..../country/{cc}/city/{zip}/name"
    @Value("${city.address.resolver.url}")
    private String cityResolverUrl;

    // url must contain 1 variable: cc - something like "http://..../country/{cc}/name"
    @Value("${country.address.resolver.url}")
    private String countryResolverUrl;

    @Override
    @Cacheable("cityCache")
    public String resolveCity(String countryCode, String zipCode) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("cc", countryCode);
        vars.put("zip", zipCode);

        return restTemplate.getForObject(cityResolverUrl, String.class, vars);
    }

    @Override
    @Cacheable("countryCache")
    public String resolveCountry(String countryCode) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("cc", countryCode);

        return restTemplate.getForObject(countryResolverUrl, String.class, vars);
    }

}
