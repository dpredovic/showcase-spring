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

import org.hibernate.envers.Audited;

@Entity
@Audited
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCooperationPartnerId() {
        return cooperationPartnerId;
    }

    public void setCooperationPartnerId(Long cooperationPartnerId) {
        this.cooperationPartnerId = cooperationPartnerId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(String dispatchType) {
        this.dispatchType = dispatchType;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

}
