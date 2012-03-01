package showcase.addressresolver;

import org.springframework.cache.annotation.Cacheable;

public interface AddressResolver {

    @Cacheable("zipCityCache")
    String resolveCity(String countryCode, String zipCode);

    String resolveCountry(String countryCode);

}
