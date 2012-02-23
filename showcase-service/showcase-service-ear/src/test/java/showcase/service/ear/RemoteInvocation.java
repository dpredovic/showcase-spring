package showcase.service.ear;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.CustomerDto;

import static org.fest.assertions.Assertions.assertThat;

public class RemoteInvocation {

    @Test
    @SuppressWarnings("unchecked")
    public void testServiceCall() throws Exception {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        CustomerService customerService = (CustomerService) context.lookup(CustomerService.JNDI_NAME);

        CustomerDto byId = customerService.getById(1L);
        assertThat(byId.getId()).isEqualTo(1);
    }
}
