package showcase.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import showcase.common.ContactType;
import showcase.persistence.unit.Contact;

@Transactional(propagation = Propagation.MANDATORY)
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByCustomerId(long id);

    Contact findByCustomerIdAndContactType(long id, ContactType type);

}
