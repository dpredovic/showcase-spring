package showcase.addressresolver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import showcase.addressresolver.config.AddressResolverConfig;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("standalone")
@ContextConfiguration(classes = AddressResolverConfig.class, loader = AnnotationConfigContextLoader.class)
public class CacheTest {

    @Autowired
    private AddressResolver addressResolver;

    @Test
    public void testCache() throws Exception {
        {
            String city = addressResolver.resolveCity("X", "Y");
            assertThat(city).isEqualTo("City-X/Y");
            assertThat(DummyAddressResolver.counter).isEqualTo(1);
        }

        {
            String city = addressResolver.resolveCity("X", "Y");
            assertThat(city).isEqualTo("City-X/Y");
            assertThat(DummyAddressResolver.counter).isEqualTo(1);
        }

        {
            String city = addressResolver.resolveCity("Y", "Z");
            assertThat(city).isEqualTo("City-Y/Z");
            assertThat(DummyAddressResolver.counter).isEqualTo(2);
        }

        {
            String city = addressResolver.resolveCity("Y", "Z");
            assertThat(city).isEqualTo("City-Y/Z");
            assertThat(DummyAddressResolver.counter).isEqualTo(2);
        }

    }
}
