package showcase.service.ear;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;
import showcase.service.api.CustomerService;
import showcase.service.api.dto.CustomerDto;

import static org.fest.assertions.Assertions.assertThat;

public class RemoteInvocation {

    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        Context context = new InitialContext(jndiProperties);

        customerService = (CustomerService) context.lookup(CustomerService.JNDI_NAME);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testServiceCall() throws Exception {
        long id = 421L;
        CustomerDto customer = customerService.getById(id);
        assertThat(customer.getId()).isEqualTo(id);
    }
}
