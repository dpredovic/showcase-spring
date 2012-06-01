package showcase.service.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import showcase.service.api.type.CustomerType;
import showcase.service.api.type.DispatchType;
import showcase.service.api.validation.CreateGroup;
import showcase.service.api.validation.InEnum;

@XmlRootElement
@Data
public class CustomerDto implements Serializable {

    @Null(groups = CreateGroup.class)
    private Long id;

    @NotNull
    private Long cooperationPartnerId;

    @NotNull
    @Past
    private Date registrationDate;

    @NotNull
    @InEnum(CustomerType.class)
    private String customerType;

    @NotNull
    @InEnum(DispatchType.class)
    private String dispatchType;

    @NotNull
    private Map<String, String> properties = new HashMap<String, String>();

}
