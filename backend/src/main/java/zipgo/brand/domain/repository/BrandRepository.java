package zipgo.brand.domain.repository;

import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.brand.domain.Brand;
import zipgo.brand.exception.BrandNotFoundException;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    default Brand getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));
    }

    Optional<Brand> findByName(String name);

    default Brand getByName(String name) {
        return findByName(name)
                .orElseThrow(() -> new BrandNotFoundException(name));
    }

}
