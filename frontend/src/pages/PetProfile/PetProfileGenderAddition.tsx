import { ChangeEvent, useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import GenderRadioInput from '@/components/PetProfile/GenderRadioInput';
import { GENDERS, PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetProfileContext } from '@/context/petProfile';
import { usePetProfileValidation } from '@/hooks/petProfile';
import { PetProfileOutletContextProps } from '@/types/petProfile/client';

const PetProfileGenderAddition = () => {
  const { updateCurrentStep } = useOutletContext<PetProfileOutletContextProps>();
  const { petProfile, updatePetProfile } = usePetProfileContext();
  const { isValidGender } = usePetProfileValidation();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.GENDER);
  }, []);

  const onChangeGender = (e: ChangeEvent<HTMLInputElement>) => {
    const gender = e.target.value;

    if (isValidGender(gender)) updatePetProfile({ gender });
  };

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>의 성별은 무엇인가요?</Title>
      <InputLabel htmlFor="pet-gender">성별 선택</InputLabel>
      <GenderInputContainer>
        {GENDERS.map(gender => (
          <GenderRadioInput
            key={gender}
            text={`${gender}아`}
            gender={gender}
            checked={petProfile.gender === gender}
            onChange={onChangeGender}
          />
        ))}
      </GenderInputContainer>
    </Container>
  );
};

export default PetProfileGenderAddition;

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

const GenderInputContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
