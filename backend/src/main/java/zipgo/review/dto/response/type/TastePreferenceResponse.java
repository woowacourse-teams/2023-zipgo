package zipgo.review.dto.response.type;

import zipgo.review.domain.type.TastePreference;

public record TastePreferenceResponse(
        String name,
        int percentage
) {

    public static TastePreferenceResponse of(TastePreference tastePreference, int percentage) {
        return new TastePreferenceResponse(tastePreference.getDescription(), percentage);
    }

}
