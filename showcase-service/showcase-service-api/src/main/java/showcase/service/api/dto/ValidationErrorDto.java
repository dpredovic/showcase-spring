package showcase.service.api.dto;

public class ValidationErrorDto {

    private int paramIndex;
    private String paramName;

    private String propertyPath;

    private String message;

    public int getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ValidationErrorDto");
        sb.append("{paramIndex=").append(paramIndex);
        sb.append(", paramName='").append(paramName).append('\'');
        sb.append(", propertyPath='").append(propertyPath).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
