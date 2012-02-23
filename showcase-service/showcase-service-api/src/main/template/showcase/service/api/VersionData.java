package showcase.service.api;

public abstract class VersionData {

    public static String jndiName(Class<?> c) {
        return "ejb:${service.name}/${service.name}/" + c.getSimpleName() + "!" + c.getCanonicalName();
    }

}
