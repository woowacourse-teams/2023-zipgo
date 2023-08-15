import { useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetProfileContext } from '@/context/petProfile';
import { PetProfileOutletContextProps } from '@/types/petProfile/client';
import { getTopicParticle } from '@/utils/getTopicParticle';

const PetProfileBreedAddition = () => {
  const { updateCurrentStep, updateIsValidStep } = useOutletContext<PetProfileOutletContextProps>();
  const { petProfile, updatePetProfile } = usePetProfileContext();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.BREED);
    // updateIsValidStep(false);
  }, []);

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>{`${getTopicParticle(petProfile.name)} 어떤 아이인가요?`}</Title>
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
