package showcase.addressresolver;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class AsyncAddressResolverImpl implements AsyncAddressResolver {

    @Autowired
    private AddressResolver delegate;

    @Override
    public Future<String> resolveCity(String countryCode, String zipCode) {
        return new AsyncResult<String>(delegate.resolveCity(countryCode, zipCode));
    }

    @Override
    public Future<String> resolveCountry(String countryCode) {
        return new AsyncResult<String>(delegate.resolveCountry(countryCode));
    }
}
