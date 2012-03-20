package showcase.addressresolver;

import javax.inject.Named;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;

@Named
@Profile("standalone")
public class DummyAddressResolver implements AddressResolver {

    public static int counter = 0;

    @Override
    @Cacheable("cityCache")
    public String resolveCity(String countryCode, String zipCode) {
        counter++;
        return "City-" + countryCode + "/" + zipCode;
    }

    @Override
    @Cacheable("countryCache")
    public String resolveCountry(String countryCode) {
        return "Country-" + countryCode;
    }
}
