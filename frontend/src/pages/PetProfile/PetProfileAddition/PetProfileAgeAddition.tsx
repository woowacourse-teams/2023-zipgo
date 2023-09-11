import { useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import PetAgeSelect from '@/components/PetProfile/PetAgeSelect';
import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetProfileAddition } from '@/hooks/petProfile/usePetProfileAddition';
import { PetAdditionOutletContextProps } from '@/types/petProfile/client';

const PetProfileAgeAddition = () => {
  const { petProfile, onChangeAge } = usePetProfileAddition();
  const { updateIsValidStep, updateCurrentStep } =
    useOutletContext<PetAdditionOutletContextProps>();

  useEffect(() => {
    updateIsValidStep(false);
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.AGE);
  }, []);

  return (
    <Container>
      <Title>
        <PetName>{`${petProfile.name},`}</PetName>
        귀여운 이름이에요. 나이는요?
      </Title>
      <InputLabel htmlFor="pet-age">나이 선택</InputLabel>
      <PetAgeSelect id="pet-age" onChange={onChangeAge} />
    </Container>
  );
};

export default PetProfileAgeAddition;

const Container = styled.div`
  margin-top: 4rem;
`;

const Title = styled.h2`
  margin-bottom: 6rem;

  font-size: 2.4rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;

const PetName = styled.p`
  margin-bottom: 1.6rem;

  font-size: inherit;
  font-weight: inherit;
  line-height: inherit;
  color: ${({ theme }) => theme.color.primary};
  letter-spacing: inherit;
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
