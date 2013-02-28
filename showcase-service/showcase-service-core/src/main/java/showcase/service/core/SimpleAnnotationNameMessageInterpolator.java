package showcase.service.core;

import javax.validation.MessageInterpolator;
import java.util.Locale;

public class SimpleAnnotationNameMessageInterpolator implements MessageInterpolator {

	@Override
	public String interpolate(String messageTemplate, Context context) {
		return getSimpleName(context);
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		return getSimpleName(context);
	}

	private static String getSimpleName(Context context) {
		return context.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
	}
}
