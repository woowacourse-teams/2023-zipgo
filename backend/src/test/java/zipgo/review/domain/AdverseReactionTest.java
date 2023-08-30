package zipgo.review.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.review.domain.type.AdverseReactionType.*;

class AdverseReactionTest {

    @Test
    void 이상반응_타입_이름이_일치하면_True를_반환한다() {
        // given
        AdverseReaction adverseReaction = AdverseReaction.builder()
                .adverseReactionType(VOMITING)
                .build();

        // when
        boolean result = adverseReaction.isEqualToAdverseReactionType(
                VOMITING);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 이상반응_타입_이름이_일치하지_않는다면_False를_반환한다() {
        // given
        AdverseReaction adverseReaction = AdverseReaction.builder()
                .adverseReactionType(VOMITING)
                .build();

        // when
        boolean result = adverseReaction.isEqualToAdverseReactionType(FRIZZY_FUR);

        // then
        assertThat(result).isFalse();
    }

}
