package showcase.addressresolver;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Profile("integration")
public class RestAddressResolver implements AddressResolver {

    @Inject
    private JAXRSClientFactoryBean clientFactoryBean;
    private AddressResolver delegate;

    @Override
    @Cacheable("cityCache")
    public String resolveCity(String countryCode, String zipCode) {
        return delegate.resolveCity(countryCode, zipCode);
    }

    @Override
    @Cacheable("countryCache")
    public String resolveCountry(String countryCode) {
        return delegate.resolveCountry(countryCode);
    }

    @PostConstruct
    private void init() {
        delegate = clientFactoryBean.create(AddressResolver.class);
    }

}
