package zipgo.admin.dto;

public record FunctionalitySelectResponse(
        Long id,
        String name
) {

    public static FunctionalitySelectResponse of(Long id, String name) {
        return new FunctionalitySelectResponse(id, name);
    }

}
