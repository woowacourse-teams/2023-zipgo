package zipgo.petfood.infra.persist;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.petfood.dto.response.GetPetFoodQueryResponse;
import zipgo.petfood.dto.response.QGetPetFoodQueryResponse;

import java.util.List;

import static zipgo.brand.domain.QBrand.brand;
import static zipgo.petfood.domain.QPetFood.petFood;
import static zipgo.petfood.domain.QPetFoodFunctionality.petFoodFunctionality;
import static zipgo.petfood.domain.QPetFoodPrimaryIngredient.petFoodPrimaryIngredient;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryRepositoryImpl implements PetFoodQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GetPetFoodQueryResponse> findPagingPetFoods(
            List<String> brandsName,
            List<String> standards,
            List<String> primaryIngredientList,
            List<String> functionalityList,
            Long lastPetFoodId,
            int size
    ) {
        return queryFactory
                .select(new QGetPetFoodQueryResponse(
                        petFood.id,
                        petFood.name,
                        brand.name,
                        petFood.imageUrl)
                )
                .from(petFood)
                .innerJoin(petFood.brand, brand)
                .innerJoin(petFood.petFoodPrimaryIngredients, petFoodPrimaryIngredient)
                .innerJoin(petFood.petFoodFunctionalities, petFoodFunctionality)
                .where(
                        isLessThan(lastPetFoodId),
                        isContainBrand(brandsName),
                        isMeetStandardCondition(standards),
                        isContainPrimaryIngredients(primaryIngredientList),
                        isContainFunctionalities(functionalityList)
                )
                .orderBy(petFood.id.desc())
                .groupBy(petFood.id)
                .limit(size)
                .fetch();
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

    @Override
    public Long findPetFoodsCount(
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

}
