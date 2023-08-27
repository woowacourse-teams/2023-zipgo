package zipgo.petfood.infra.persist;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodEffect;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.domain.type.PetFoodOption;

import static zipgo.brand.domain.QBrand.brand;
import static zipgo.petfood.domain.QPetFood.petFood;
import static zipgo.petfood.domain.QPetFoodEffect.petFoodEffect;
import static zipgo.review.domain.QAdverseReaction.adverseReaction;
import static zipgo.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryRepositoryImpl implements PetFoodQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PetFood> findPagingPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> functionalityList,
            List<String> primaryIngredientList,
            Long lastPetFoodId,
            int size
    ) {
        List<PetFood> petFoods = queryFactory
                .selectDistinct(petFood)
                .from(petFood)
                .innerJoin(petFood.brand, brand)
                .fetchJoin()
                .innerJoin(petFood.petFoodEffects, petFoodEffect)
                .fetchJoin()
                .where(
                        isLessThan(lastPetFoodId),
                        isContainBrand(brandsName),
                        isMeetStandardCondition(standards),
                        isContainPetFoodEffect(functionalityList),
                        isContainPetFoodEffect(primaryIngredientList)
                )
                .orderBy(petFood.id.desc())
                .limit(size)
                .fetch();
        System.out.println("petFoods.size = " + petFoods.size());
        return petFoods;
    }

    @Override
    public Long getCount(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList
    ) {
        return queryFactory
                .select(petFood.id.countDistinct())
                .from(petFood)
                .join(petFood.brand, brand)
                .where(
                        isContainBrand(brandsName),
                        isMeetStandardCondition(standards),
                        isContainPetFoodEffect(primaryIngredientList),
                        isContainPetFoodEffect(functionalityList)
                )
                .fetchOne();
    }

    private BooleanExpression isLessThan(Long lastPetFoodId) {
        if (lastPetFoodId == null) {
            return null;
        }
        return petFood.id.loe(lastPetFoodId);
    }

    private BooleanExpression isContainBrand(List<String> brandsName) {
        if (brandsName.isEmpty()) {
            return null;
        }
        return petFood.brand.name.in(brandsName);
    }

    private BooleanExpression isMeetStandardCondition(List<String> standards) {
        if (standards.isEmpty()) {
            return null;
        }
        for (String standard : standards) {
            if (standard.equals("미국")) {
                return petFood.hasStandard.unitedStates.isTrue();
            }
            if (standard.equals("유럽")) {
                return petFood.hasStandard.europe.isTrue();
            }
        }
        return null;
    }

    private BooleanExpression isContainPetFoodEffect(List<String> petFoodEffects) {
        if (petFoodEffects.isEmpty()) {
            return null;
        }
        return petFoodEffect.description.in(petFoodEffects);
    }

    public PetFood findPetFoodWithReviewsByPetFoodId(Long petFoodId) {
        return queryFactory
                .selectFrom(petFood)
                .leftJoin(petFood.reviews.reviews, review)
                .fetchJoin()
                .leftJoin(review.adverseReactions, adverseReaction)
                .where(petFood.id.eq(petFoodId))
                .fetchOne();
    }

    public List<PetFoodEffect> findPetFoodEffectsBy(PetFoodOption petFoodOption) {
        return queryFactory
                .selectFrom(petFoodEffect)
                .where(isEqualTo(petFoodOption))
                .fetch();
    }

    private BooleanExpression isEqualTo(PetFoodOption petFoodOption) {
        return petFoodEffect.petFoodOption.eq(petFoodOption);
    }

}
