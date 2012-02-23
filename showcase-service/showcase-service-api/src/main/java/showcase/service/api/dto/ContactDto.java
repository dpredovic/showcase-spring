package showcase.service.api.dto;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

import showcase.common.CommunicationType;
import showcase.common.ContactType;

@XmlRootElement
public class ContactDto implements Serializable {

    private Long id;

    private String firstName;
    private String lastName;

    private String street;
    private String zipCode;
    private String countryCode;

    private ContactType contactType;

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

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public Map<CommunicationType, String> getCommunications() {
        return communications;
    }

    public void setCommunications(Map<CommunicationType, String> communications) {
        this.communications = communications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactDto that = (ContactDto) o;

        if (communications != null ? !communications.equals(that.communications) : that.communications != null)
            return false;
        if (contactType != that.contactType) return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (contactType != null ? contactType.hashCode() : 0);
        result = 31 * result + (communications != null ? communications.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ContactDto");
        sb.append("{id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append(", countryCode='").append(countryCode).append('\'');
        sb.append(", contactType=").append(contactType);
        sb.append(", communications=").append(communications);
        sb.append('}');
        return sb.toString();
    }
}
