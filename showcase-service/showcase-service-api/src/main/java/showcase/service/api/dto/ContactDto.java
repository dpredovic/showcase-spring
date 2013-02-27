package showcase.service.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import showcase.service.api.type.CommunicationType;
import showcase.service.api.type.ContactType;
import showcase.service.api.validation.AllKeysInEnum;
import showcase.service.api.validation.CreateGroup;
import showcase.service.api.validation.InEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto implements Serializable {

	@Null(groups = CreateGroup.class)
	private Long id;
	@Null(groups = CreateGroup.class)
	private Long customerId;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String street;
	@NotNull
	private String zipCode;
	@Null
	private String city;
	@NotNull
	private String countryCode;
	@Null
	private String countryName;
	@InEnum(ContactType.class)
	private String contactType;
	@NotNull
	@AllKeysInEnum(CommunicationType.class)
	@Size(min = 1)
	private Map<String, String> communications = new HashMap<String, String>();

}
