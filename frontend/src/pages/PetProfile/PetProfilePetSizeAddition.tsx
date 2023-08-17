import { useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import Label from '@/components/@common/Label/Label';
import { PET_PROFILE_ADDITION_STEP, PET_SIZES } from '@/constants/petProfile';
import { usePetProfileContext } from '@/context/petProfile';
import { PetProfileOutletContextProps, PetSize } from '@/types/petProfile/client';

const PetProfilePetSizeAddition = () => {
  const { updateCurrentStep } = useOutletContext<PetProfileOutletContextProps>();
  const { petProfile, updatePetProfile } = usePetProfileContext();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.PET_SIZE);
  }, []);

  const onClickPetSize = (size: PetSize) => updatePetProfile({ petSize: size });

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>의 크기는 어느 정도인가요?</Title>
      <InputLabel htmlFor="pet-size">크기 선택</InputLabel>
      <PetSizeContainer role="radiogroup" id="pet-size">
        {PET_SIZES.map(size => (
          <Label
            key={size}
            role="radio"
            text={size}
            onClick={() => onClickPetSize(size)}
            clicked={petProfile.petSize === size}
          />
        ))}
      </PetSizeContainer>
    </Container>
  );
};

export default PetProfilePetSizeAddition;

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

const PetSizeContainer = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.8rem;
  align-items: center;
`;
