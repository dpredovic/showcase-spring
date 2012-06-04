package showcase.addressresolver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public interface AddressResolver {

    @GET
    @Path("/country/{countryCode}/city/{zipCode}")
    String resolveCity(
            @PathParam("countryCode")
            String countryCode,
            @PathParam("zipCode")
            String zipCode);

    @GET
    @Path("/country/{countryCode}")
    String resolveCountry(
            @PathParam("countryCode")
            String countryCode);

}
