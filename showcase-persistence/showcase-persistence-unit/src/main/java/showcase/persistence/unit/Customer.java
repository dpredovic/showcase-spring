package showcase.persistence.unit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

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
