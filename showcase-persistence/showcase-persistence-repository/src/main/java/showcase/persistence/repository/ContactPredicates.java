package showcase.persistence.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import showcase.persistence.unit.QContact;

public final class ContactPredicates {

	private static final QContact $ = QContact.contact;

	private ContactPredicates() {
	}

	public static Predicate containsCommunication(String type, String... values) {
		BooleanBuilder bb = new BooleanBuilder();
		for (String value : values) {
			Predicate predicate =
				type == null ? $.communications.containsValue(value) : $.communications.contains(type, value);
			bb.or(predicate);
		}
		return bb.getValue();
	}

}
