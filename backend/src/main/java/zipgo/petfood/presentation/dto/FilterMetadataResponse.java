package zipgo.petfood.presentation.dto;

import java.util.List;
import zipgo.brand.domain.Brand;

public record FilterMetadataResponse(
        List<String> keywords,
        FilterResponse filters
) {

    public static FilterMetadataResponse of(FilterResponse filterResponse, List<Brand> brands) {
        return new FilterMetadataResponse(
                List.of("영양기준", "주원료", "브랜드", "기능성"),
                filterResponse
        );
    }

}
