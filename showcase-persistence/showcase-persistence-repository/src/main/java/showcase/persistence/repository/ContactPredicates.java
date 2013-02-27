package showcase.persistence.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import showcase.persistence.unit.QContact;

public abstract class ContactPredicates {

	private static QContact $ = QContact.contact;

	public static Predicate containsCommunication(String type, String... values) {
		BooleanBuilder bb = new BooleanBuilder();
		for (String value : values) {
			Predicate predicate;
			if (type == null) {
				predicate = $.communications.containsValue(value);
			} else {
				predicate = $.communications.contains(type, value);
			}
			bb.or(predicate);
		}
		return bb.getValue();
	}

}
