package zipgo.brand.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.brand.domain.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
