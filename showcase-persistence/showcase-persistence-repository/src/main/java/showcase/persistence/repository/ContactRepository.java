package showcase.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import showcase.persistence.unit.Contact;

@Transactional(propagation = Propagation.MANDATORY)
public interface ContactRepository extends JpaRepository<Contact, Long>, QueryDslPredicateExecutor<Contact> {

    List<Contact> findByCustomerId(long id);

    Contact findByCustomerIdAndContactType(long id, String type);

}
