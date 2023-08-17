package zipgo.pet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.Year;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.common.entity.BaseTimeEntity;
import zipgo.member.domain.Member;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.EqualsAndHashCode.Include;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Pet extends BaseTimeEntity {

    @Id
    @Include
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Member owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Year birthYear;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Breeds breeds;

    @Enumerated(value = STRING)
    private Gender gender;

    @Column(nullable = false)
    private Double weight;

    private String imageUrl;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateBirthYear(Year year) {
        this.birthYear = year;
    }

    public void updateBreeds(Breeds breeds) {
        this.breeds = breeds;
    }

    public void updateWeight(double weight) {
        this.weight = weight;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void validateOwner(Member other) {
        if (!this.owner.equals(other)) {
            throw new IllegalArgumentException("반려견과 주인이 일치하지 않습니다.");
        }
    }

    public int calculateCurrentAge() {
        return Year.now().getValue() - birthYear.getValue();
    }

}

