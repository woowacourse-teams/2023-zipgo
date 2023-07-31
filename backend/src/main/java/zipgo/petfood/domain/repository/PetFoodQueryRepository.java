package zipgo.petfood.domain.repository;

import static org.springframework.util.StringUtils.hasText;
import static zipgo.brand.domain.QBrand.brand;
import static zipgo.petfood.domain.QKeyword.keyword;
import static zipgo.petfood.domain.QPetFood.petFood;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredients;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetFoodQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PetFood> findPetFoods(String keywordName, String brandName, String primaryIngredients) {
        return queryFactory
                .selectFrom(petFood)
                .join(petFood.keyword, keyword)
                .fetchJoin()
                .join(petFood.brand, brand)
                .fetchJoin()
                .where(
                        equalsKeyword(keywordName),
                        equalsBrand(brandName),
                        equalsPrimaryIngredients(primaryIngredients)
                )
                .fetch();
    }

    private BooleanExpression equalsKeyword(String keywordName) {
        if (hasText(keywordName)) {
            return keyword.name.eq(keywordName);
        }
        return null;
    }

    private BooleanExpression equalsBrand(String brandName) {
        if (hasText(brandName)) {
            return brand.name.eq(brandName);
        }
        return null;
    }

    private BooleanExpression equalsPrimaryIngredients(String primaryIngredients) {
        if (hasText(primaryIngredients)) {
            PrimaryIngredients request = new PrimaryIngredients(List.of(primaryIngredients));
            return petFood.primaryIngredients.in(request);
        }
        return null;
    }

}
