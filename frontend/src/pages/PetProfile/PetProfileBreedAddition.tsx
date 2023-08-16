import { ChangeEvent, useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import PetBreedSelect from '@/components/PetProfile/PetBreedSelect';
import { MIXED_BREED, PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetProfileContext } from '@/context/petProfile';
import { PetProfileOutletContextProps } from '@/types/petProfile/client';
import { getTopicParticle } from '@/utils/getTopicParticle';

const PetProfileBreedAddition = () => {
  const { petProfile, updatePetProfile } = usePetProfileContext();
  const { updateIsMixedBreed, updateCurrentStep, updateIsValidStep } =
    useOutletContext<PetProfileOutletContextProps>();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.BREED);
    updateIsValidStep(false);
  }, []);

  const onChangeBreed = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedBreed = e.target.value;

    if (selectedBreed === MIXED_BREED) updateIsMixedBreed(true);
    if (selectedBreed !== MIXED_BREED) updateIsMixedBreed(false);

    updateIsValidStep(true);
    updatePetProfile({ breed: selectedBreed });
  };

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>{`${getTopicParticle(petProfile.name)} 어떤 아이인가요?`}</Title>
      <InputLabel htmlFor="pet-breed">견종 선택</InputLabel>
      <PetBreedSelect id="pet-breed" onChange={onChangeBreed} />
    </Container>
  );
};

export default PetProfileBreedAddition;

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
