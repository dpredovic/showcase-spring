package showcase.service.remoting;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import showcase.service.api.ContactService;
import showcase.service.api.dto.ContactDto;
import showcase.service.api.type.ContactType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceBeanTest {

	@Mock
	private ContactService mock;
	@InjectMocks
	private ContactServiceBean underTest;

	@Test
	public void testGetContact() throws Exception {
		ContactDto expected = new ContactDto();
		when(mock.getContact(1L)).thenReturn(expected);

		ContactDto contact = underTest.getContact(1L);

		assertThat(contact).isSameAs(expected);
	}

	@Test
	public void testByCustomerAndType() throws Exception {
		ContactDto expected = new ContactDto();
		when(mock.getContactByCustomerAndType(1L, ContactType.STANDARD.toString())).thenReturn(expected);

		ContactDto contact = underTest.getContactByCustomerAndType(1L, ContactType.STANDARD.toString());

		assertThat(contact).isSameAs(expected);
	}

	@Test
	public void testByCustomer() throws Exception {
		List<ContactDto> expected = ImmutableList.of();
		when(mock.getContactsByCustomer(1L)).thenReturn(expected);

		List<ContactDto> contacts = underTest.getContactsByCustomer(1L);

		assertThat(contacts).isSameAs(expected);
	}
}

