package showcase.persistence.unit;

import java.util.EnumMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import showcase.common.CommunicationType;
import showcase.common.ContactType;

@Entity
@Audited
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

    private ContactType contactType;

    @ElementCollection
    private Map<CommunicationType, String> communications = new EnumMap<CommunicationType, String>(CommunicationType.class);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public Map<CommunicationType, String> getCommunications() {
        return communications;
    }

}
