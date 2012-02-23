package showcase.zipresolver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import showcase.zipresolver.config.ZipResolverConfig;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("standalone")
@ContextConfiguration(classes = ZipResolverConfig.class, loader = AnnotationConfigContextLoader.class)
public class CacheTest {

    @Autowired
    private ZipResolver zipResolver;

    @Test
    public void testCache() throws Exception {
        {
            String city = zipResolver.resolveCity("X", "Y");
            assertThat(city).isEqualTo("City-X/Y");
            assertThat(DummyZipResolver.counter).isEqualTo(1);
        }

        {
            String city = zipResolver.resolveCity("X", "Y");
            assertThat(city).isEqualTo("City-X/Y");
            assertThat(DummyZipResolver.counter).isEqualTo(1);
        }

        {
            String city = zipResolver.resolveCity("Y", "Z");
            assertThat(city).isEqualTo("City-Y/Z");
            assertThat(DummyZipResolver.counter).isEqualTo(2);
        }

        {
            String city = zipResolver.resolveCity("Y", "Z");
            assertThat(city).isEqualTo("City-Y/Z");
            assertThat(DummyZipResolver.counter).isEqualTo(2);
        }

    }
}
