package showcase.addressresolver;

import java.util.concurrent.Future;

public interface AddressResolver {

    Future<String> resolveCity(String countryCode, String zipCode);

    Future<String> resolveCountry(String countryCode);

}
