package showcase.zipresolver;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("standalone")
public class DummyZipResolver implements ZipResolver {

    public static int counter = 0;

    @Override
    public String resolveCity(String countryCode, String zipCode) {
        counter++;
        return "City-" + countryCode + "/" + zipCode;
    }
}
