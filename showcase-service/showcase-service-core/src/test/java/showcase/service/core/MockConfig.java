package showcase.service.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import showcase.addressresolver.AddressResolver;

import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;

@Configuration
public class MockConfig {

    @Bean
    public AddressResolver addressResolver() {
        return mock(AddressResolver.class, RETURNS_SMART_NULLS);
    }

}
