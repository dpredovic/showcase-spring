package showcase.persistence.unit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Audited
@Getter
@Setter
public class Contact {

	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String street;
	private String zipCode;
	private String countryCode;
	@ManyToOne(optional = false)
	@JoinColumn
	private Customer customer;
	private String contactType;
	@ElementCollection
	private Map<String, String> communications = new HashMap<String, String>();

}
