package showcase.addressresolver;

import org.springframework.scheduling.annotation.AsyncResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Future;

@Named
public class AsyncAddressResolverImpl implements AsyncAddressResolver {

    @Inject
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
