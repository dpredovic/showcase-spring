package showcase.service.core.cache;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import showcase.service.api.dto.ContactDto;

@Component
public class CacheSyncImpl implements CacheSync {

    @Override
    @Caching(put = {
            @CachePut(value = "contact", key = "'id='+#contact.id"),
            @CachePut(value = "contact", key = "'customerId='+#contact.customerId+',type='+#contact.contactType")
    })
    public ContactDto put(ContactDto contact) {
        return contact;
    }

}
