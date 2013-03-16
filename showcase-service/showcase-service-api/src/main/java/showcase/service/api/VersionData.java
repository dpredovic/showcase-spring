package showcase.service.api;

public final class VersionData {

	public static final String IMPLEMENTATION_VERSION = VersionData.class.getPackage().getImplementationVersion();
	public static final String SPECIFICATION_VERSION = VersionData.class.getPackage().getSpecificationVersion();
	public static final String SPECIFICATION_NAME =
		VersionData.class.getPackage().getSpecificationTitle() + '-' + SPECIFICATION_VERSION;

	private VersionData() {
	}

	public static String jndiName(Class<?> c) {
		return "ejb:" + SPECIFICATION_NAME + '/' + SPECIFICATION_NAME +
			   '/' + c.getSimpleName() + '!' + c.getCanonicalName();
	}

}
