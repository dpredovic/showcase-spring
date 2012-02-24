package showcase.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import showcase.persistence.unit.Customer;

@Transactional(propagation = Propagation.MANDATORY)
public interface CustomerRepository extends JpaRepository<Customer, Long>, QueryDslPredicateExecutor<Customer> {

}
