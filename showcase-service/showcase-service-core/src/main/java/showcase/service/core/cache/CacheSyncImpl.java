package showcase.service.core.cache;

import javax.inject.Named;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import showcase.service.api.dto.ContactDto;

@Named
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
