package zipgo.brand.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.brand.domain.Brand;
import zipgo.brand.exception.BrandNotFoundException;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    default Brand getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));
    }

}
