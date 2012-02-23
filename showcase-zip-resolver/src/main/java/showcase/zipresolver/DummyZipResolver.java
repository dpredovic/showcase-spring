package showcase.zipresolver;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("standalone")
public class DummyZipResolver implements ZipResolver {

    @Override
    public String resolveCity(String countryCode, String zipCode) {
        return "City-" + countryCode + "/" + zipCode;
    }
}
