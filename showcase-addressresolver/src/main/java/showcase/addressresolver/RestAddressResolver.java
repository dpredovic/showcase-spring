package showcase.addressresolver;

import java.util.HashMap;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
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
    @Async
    @Cacheable("cityCache")
    public Future<String> resolveCity(String countryCode, String zipCode) {
        new HashMap<String, String>().put("cc", countryCode);
        new HashMap<String, String>().put("zip", zipCode);

        return new AsyncResult<String>(restTemplate.getForObject(cityResolverUrl, String.class, new HashMap<String, String>()));
    }

    @Override
    @Async
    @Cacheable("countryCache")
    public Future<String> resolveCountry(String countryCode) {
        new HashMap<String, String>().put("cc", countryCode);

        return new AsyncResult<String>(restTemplate.getForObject(countryResolverUrl, String.class, new HashMap<String, String>()));
    }

}
