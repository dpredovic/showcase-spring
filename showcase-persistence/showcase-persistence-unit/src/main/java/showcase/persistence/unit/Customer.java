package showcase.persistence.unit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Audited
@Getter
@Setter
public class Customer {

	@Id
	@GeneratedValue
	private Long id;

	@Basic(optional = false)
	private Long cooperationPartnerId;

	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date registrationDate;

	@Basic(optional = false)
	private String customerType;

	@Basic(optional = false)
	private String dispatchType;

	@ElementCollection
	private Map<String, String> properties = new HashMap<String, String>();

}
