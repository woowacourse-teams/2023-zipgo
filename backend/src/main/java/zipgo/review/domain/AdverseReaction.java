package zipgo.review.domain;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static lombok.EqualsAndHashCode.Include;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.review.domain.type.AdverseReactionName;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdverseReaction {

    @Id
    @Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private AdverseReactionName adverseReactionName;

    @ManyToOne(fetch = LAZY)
    private Review review;

    public void updateReview(Review review) {
        this.review = review;
    }

    public AdverseReaction(AdverseReactionName adverseReactionName) {
        this.adverseReactionName = adverseReactionName;
    }

}
