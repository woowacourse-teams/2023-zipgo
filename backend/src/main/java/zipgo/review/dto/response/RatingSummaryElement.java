package zipgo.review.dto.response;

import java.util.List;

public record RatingSummaryElement(
        double average,
        List<SummaryElement> rating
) {

}
