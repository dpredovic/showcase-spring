package showcase.zipresolver;

import org.springframework.cache.annotation.Cacheable;

public interface ZipResolver {

    @Cacheable("zipCityCache")
    String resolveCity(String countryCode, String zipCode);

}
