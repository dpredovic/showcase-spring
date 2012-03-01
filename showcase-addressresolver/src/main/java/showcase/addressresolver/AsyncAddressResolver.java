package showcase.addressresolver;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;

@Async
public interface AsyncAddressResolver {

    Future<String> resolveCity(String countryCode, String zipCode);

    Future<String> resolveCountry(String countryCode);

}
