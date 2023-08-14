import { useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import Label from '@/components/@common/Label/Label';
import { BREEDS_SIZES, PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetProfileContext } from '@/context/petProfile';
import { BreedsSize, PetProfileOutletContextProps } from '@/types/petProfile/client';

const PetProfileBreedsSizeAddition = () => {
  const { updateCurrentStep } = useOutletContext<PetProfileOutletContextProps>();
  const { petProfile, updatePetProfile } = usePetProfileContext();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.BREEDS_SIZE);
  }, []);

  const onClickBreedsSize = (size: BreedsSize) => updatePetProfile({ breedsSize: size });

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>의 크기는 어느 정도인가요?</Title>
      <InputLabel htmlFor="pet-breeds-size">크기 선택</InputLabel>
      <BreedsSizeContainer role="radiogroup" id="pet-breeds-size">
        {BREEDS_SIZES.map(size => (
          <Label
            key={size}
            role="radio"
            text={size}
            onClick={() => onClickBreedsSize(size)}
            clicked={petProfile.breedsSize === size}
          />
        ))}
      </BreedsSizeContainer>
    </Container>
  );
};

export default PetProfileBreedsSizeAddition;

const Container = styled.div`
  margin-top: 4rem;
`;

const Title = styled.h2`
  display: inline-block;

  margin-bottom: 6rem;

  font-size: 2.4rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;

const PetName = styled.span`
  font-size: 2.4rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.primary};
  letter-spacing: -0.5px;
`;

const InputLabel = styled.label`
  display: block;

  margin-bottom: 1.6rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;

const BreedsSizeContainer = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.8rem;
  align-items: center;
`;
