package showcase.addressresolver;

public interface AddressResolver {

    String resolveCity(String countryCode, String zipCode);

    String resolveCountry(String countryCode);

}
