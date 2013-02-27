package showcase.addressresolver;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

@Async
public interface AsyncAddressResolver {

	Future<String> resolveCity(String countryCode, String zipCode);

	Future<String> resolveCountry(String countryCode);

}
