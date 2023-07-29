package zipgo.review.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode.Include;
import zipgo.common.entity.BaseTimeEntity;
import zipgo.member.domain.Member;
import zipgo.petfood.domain.PetFood;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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

}
