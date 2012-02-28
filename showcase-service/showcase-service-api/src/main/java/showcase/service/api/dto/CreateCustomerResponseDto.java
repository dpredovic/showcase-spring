package showcase.service.api.dto;

public class CreateCustomerResponseDto extends ValidationResponseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateCustomerResponseDto");
        sb.append("{id=").append(id);
        sb.append('}');
        sb.append("->\n");
        sb.append(super.toString());
        return sb.toString();
    }
}
