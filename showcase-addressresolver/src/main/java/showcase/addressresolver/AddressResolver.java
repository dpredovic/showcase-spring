package showcase.addressresolver;

import org.springframework.cache.annotation.Cacheable;

public interface AddressResolver {

    @Cacheable("cityCache")
    String resolveCity(String countryCode, String zipCode);

    @Cacheable("countryCache")
    String resolveCountry(String countryCode);

}
