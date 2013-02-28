package showcase.service.api;

public final class VersionData {

	private VersionData() {
	}

	public static String jndiName(Class<?> c) {
		return "ejb:${service.name}/${service.name}/" + c.getSimpleName() + "!" + c.getCanonicalName();
	}

}
