package zipgo.admin.dto;

public record BrandSelectResponse(
        Long id,
        String name
) {

    public static BrandSelectResponse of(Long id, String name) {
        return new BrandSelectResponse(id, name);
    }

}
