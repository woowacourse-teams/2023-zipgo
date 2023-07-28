package zipgo.petfood.domain;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryIngredients {

    @Default
    @Convert(converter = StringArrayConverter.class)
    private List<String> primaryIngredients = new ArrayList<>();

}
