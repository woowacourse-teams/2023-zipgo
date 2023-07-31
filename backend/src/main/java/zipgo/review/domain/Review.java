package zipgo.review.domain;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.auth.exception.AuthException;
import zipgo.common.entity.BaseTimeEntity;
import zipgo.member.domain.Member;
import zipgo.petfood.domain.PetFood;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Review extends BaseTimeEntity {

    @Id
    @Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pet_food_id")
    private PetFood petFood;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;

    @Enumerated(STRING)
    @Column(nullable = false)
    private TastePreference tastePreference;

    @Enumerated(STRING)
    @Column(nullable = false)
    private StoolCondition stoolCondition;

    @Default
    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = {PERSIST, REMOVE})
    private List<AdverseReaction> adverseReactions = new ArrayList<>();

    public void addAdverseReactions(List<String> adverseReactionNames) {
        List<AdverseReaction> adverseReactions = adverseReactionNames.stream()
                .map(AdverseReaction::new)
                .toList();

        for (AdverseReaction adverseReaction : adverseReactions) {
            adverseReaction.updateReview(this);
            this.adverseReactions.add(adverseReaction);
        }
    }

    public void updateRating(Integer rating) {
        this.rating = rating;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public void updateTastePreference(String tastePreference) {
        this.tastePreference = TastePreference.from(tastePreference);
    }

    public void updateStoolCondition(String stoolCondition) {
        this.stoolCondition = StoolCondition.from(stoolCondition);
    }

    public void validateOwner(Long memberId) {
        if (!isWrittenBy(memberId)) {
            throw new AuthException.Forbidden();
        }
    }

    private boolean isWrittenBy(Long memberId) {
        if (memberId == null) {
            return false;
        }
        return this.member.getId() == memberId;
    }

    public void removeAdverseReactions() {
        this.adverseReactions.clear();
    }

}
