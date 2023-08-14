package zipgo.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import zipgo.petfood.infra.persist.PetFoodQueryRepository;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewQueryRepositoryImpl;

@TestConfiguration
public class QueryDslTestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public PetFoodQueryRepository petFoodQueryRepository() {
        return new PetFoodQueryRepository(jpaQueryFactory());
    }

    @Bean
    public ReviewQueryRepository reviewQueryRepository() {
        return new ReviewQueryRepositoryImpl(jpaQueryFactory());
    }

}
