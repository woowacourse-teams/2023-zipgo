package zipgo.petfood.infra.persist;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PetFood;

import static zipgo.brand.domain.QBrand.brand;
import static zipgo.petfood.domain.QPetFood.petFood;
import static zipgo.petfood.domain.QPetFoodFunctionality.petFoodFunctionality;
import static zipgo.petfood.domain.QPetFoodPrimaryIngredient.petFoodPrimaryIngredient;
import static zipgo.review.domain.QAdverseReaction.adverseReaction;
import static zipgo.review.domain.QReview.review;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryRepository implements zipgo.petfood.domain.repository.PetFoodQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PetFood> findPagingPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList,
            Long lastPetFoodId,
            int size
    ) {
        return queryFactory
                .selectDistinct(petFood)
                .from(petFood)
                .join(petFood.brand, brand)
                .fetchJoin()
                .join(petFood.petFoodPrimaryIngredients, petFoodPrimaryIngredient)
                .join(petFood.petFoodFunctionalities, petFoodFunctionality)
                .where(
                        isLessThan(lastPetFoodId),
                        isContainBrand(brandsName),
                        isMeetStandardCondition(standards),
                        isContainPrimaryIngredients(primaryIngredientList),
                        isContainFunctionalities(functionalityList)
                )
                .orderBy(petFood.id.desc())
                .limit(size)
                .fetch();
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
                .join(petFood.petFoodPrimaryIngredients, petFoodPrimaryIngredient)
                .join(petFood.petFoodFunctionalities, petFoodFunctionality)
                .where(
                        isContainBrand(brandsName),
                        isMeetStandardCondition(standards),
                        isContainPrimaryIngredients(primaryIngredientList),
                        isContainFunctionalities(functionalityList)
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

    private BooleanExpression isContainPrimaryIngredients(List<String> primaryIngredientList) {
        if (primaryIngredientList.isEmpty()) {
            return null;
        }
        return petFood.petFoodPrimaryIngredients.any()
                .primaryIngredient.name.in(primaryIngredientList);
    }

    private BooleanExpression isContainFunctionalities(List<String> functionalityList) {
        if (functionalityList.isEmpty()) {
            return null;
        }
        return petFood.petFoodFunctionalities.any()
                .functionality.name.in(functionalityList);
    }

    public PetFood findPetFoodWithReviewsByPetFoodId(Long petFoodId) {;
        JPAQuery<PetFood> where = queryFactory
                .selectFrom(petFood)
                .leftJoin(petFood.reviews.reviews, review)
                .fetchJoin()
                .leftJoin(review.adverseReactions, adverseReaction)
                .where(petFood.id.eq(petFoodId));

        log.info("----------------1-----------");
        log.info(where.toString());
        log.info("----------------2----------");

        return where
                .fetchOne();
    }

}
