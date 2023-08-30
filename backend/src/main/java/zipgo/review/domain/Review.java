package zipgo.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.auth.exception.AuthForbiddenException;
import zipgo.common.entity.BaseTimeEntity;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Pet;
import zipgo.petfood.domain.PetFood;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.ReviewPetInfo;
import zipgo.review.exception.ReviewSelfReactedException;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static lombok.AccessLevel.PROTECTED;

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
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pet_food_id")
    private PetFood petFood;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;

    @Embedded
    private ReviewPetInfo reviewPetInfo;

    @Default
    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = {PERSIST, REMOVE})
    private List<AdverseReaction> adverseReactions = new ArrayList<>();

    @Default
    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = ALL)
    private List<HelpfulReaction> helpfulReactions = new ArrayList<>();

    public long getAdverseReactionTypeCount(AdverseReactionType adverseReactionType) {
        return adverseReactions.stream()
                .filter(adverseReaction -> adverseReaction.isEqualToAdverseReactionType(adverseReactionType))
                .count();
    }

    public void addAdverseReactions(List<String> adverseReactionNames) {
        List<AdverseReaction> adverseReactions = adverseReactionNames.stream()
                .map(name -> new AdverseReaction(AdverseReactionType.from(name)))
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

    public void validateOwner(Long memberId) {
        if (!isWrittenBy(memberId)) {
            throw new AuthForbiddenException();
        }
    }

    private boolean isWrittenBy(Long memberId) {
        if (memberId == null) {
            return false;
        }
        return this.pet.getOwner().getId() == memberId;
    }

    public void removeAdverseReactions() {
        this.adverseReactions.clear();
    }

    public int getPetAge() {
        int createdYear = getCreatedAt().getYear();
        int birthYear = pet.getBirthYear().getValue();
        return createdYear - birthYear;
    }

    public void reactedBy(Member member) {
        if (member.equals(pet.getOwner())) {
            throw new ReviewSelfReactedException();
        }
        if (isAlreadyReactedBy(member)) {
            return;
        }
        HelpfulReaction helpfulReaction = HelpfulReaction.builder().review(this).madeBy(member).build();
        helpfulReactions.add(helpfulReaction);
    }

    private boolean isAlreadyReactedBy(Member member) {
        return helpfulReactions.stream()
                .anyMatch(reaction -> reaction.getMadeBy().equals(member));
    }

    public void removeReactionBy(Member member) {
        helpfulReactions.removeIf(reaction -> reaction.getMadeBy().equals(member));
    }

    public boolean isReactedBy(Long memberId) {
        return helpfulReactions.stream()
                .anyMatch(reaction -> reaction.getMadeBy().getId().equals(memberId));
    }

    public void updateStoolCondition(String stoolCondition) {
        reviewPetInfo.changeStoolCondition(stoolCondition);
    }

    public void updateTastePreference(String tastePreference) {
        reviewPetInfo.changeTastePreference(tastePreference);
    }

}
