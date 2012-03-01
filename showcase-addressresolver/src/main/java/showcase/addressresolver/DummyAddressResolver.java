package showcase.addressresolver;

import java.util.concurrent.Future;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
@Profile("standalone")
public class DummyAddressResolver implements AddressResolver {

    public static int counter = 0;

    @Override
    @Async
    @Cacheable("cityCache")
    public Future<String> resolveCity(String countryCode, String zipCode) {
        counter++;
        return new AsyncResult<String>("City-" + countryCode + "/" + zipCode);
    }

    @Override
    @Async
    @Cacheable("countryCache")
    public Future<String> resolveCountry(String countryCode) {
        return new AsyncResult<String>("Country-" + countryCode);
    }
}
