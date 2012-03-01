package showcase.addressresolver;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("standalone")
public class DummyAddressResolver implements AddressResolver {

    public static int counter = 0;

    @Override
    public String resolveCity(String countryCode, String zipCode) {
        counter++;
        return "City-" + countryCode + "/" + zipCode;
    }

    @Override
    public String resolveCountry(String countryCode) {
        return "Country-" + countryCode;
    }
}
