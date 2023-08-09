package zipgo.petfood.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PetFood;

import static zipgo.brand.domain.QBrand.brand;
import static zipgo.petfood.domain.QFunctionality.functionality;
import static zipgo.petfood.domain.QPetFood.petFood;
import static zipgo.petfood.domain.QPrimaryIngredient.primaryIngredient;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PageImpl<PetFood> findPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList,
            Long lastPetFoodId,
            Pageable pageable
    ) {
        List<PetFood> petFoods = getPetFoods(brandsName, standards, primaryIngredientList,
                functionalityList, lastPetFoodId, pageable);
        Long count = getCount(brandsName, standards, primaryIngredientList, functionalityList);

        return new PageImpl<>(petFoods, pageable, count);
    }

    private List<PetFood> getPetFoods(List<String> brandsName, List<String> standards, List<String> primaryIngredientList, List<String> functionalityList, Long lastPetFoodId, Pageable pageable) {
        List<PetFood> petFoods = queryFactory
                .selectFrom(petFood)
                .join(petFood.brand, brand)
                .fetchJoin()
                .join(petFood.primaryIngredients, primaryIngredient)
                .fetchJoin()
                .join(petFood.functionalities, functionality)
                .where(
                        isLessThan(lastPetFoodId),
                        isContainBrand(brandsName),
                        isMeetStandardCondition(standards),
                        isContainPrimaryIngredients(primaryIngredientList),
                        isContainFunctionalities(functionalityList)
                )
                .orderBy(petFood.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
        return petFoods;
    }

    private BooleanExpression isLessThan(Long lastPetFoodId) {
        if (lastPetFoodId == null) {
            return null;
        }
        return petFood.id.lt(lastPetFoodId);
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
        return petFood.primaryIngredients.any().name.in(primaryIngredientList);
    }

    private BooleanExpression isContainFunctionalities(List<String> functionalityList) {
        if (functionalityList.isEmpty()) {
            return null;
        }
        return petFood.functionalities.any().name.in(functionalityList);
    }

    private Long getCount(List<String> brandsName, List<String> standards, List<String> primaryIngredientList, List<String> functionalityList) {
        return queryFactory
                .select(petFood.count())
                .from(petFood)
                .join(petFood.brand, brand)
                .join(petFood.primaryIngredients, primaryIngredient)
                .join(petFood.functionalities, functionality)
                .where(
                        isContainBrand(brandsName),
                        isMeetStandardCondition(standards),
                        isContainPrimaryIngredients(primaryIngredientList),
                        isContainFunctionalities(functionalityList)
                )
                .fetchOne();
    }

}
