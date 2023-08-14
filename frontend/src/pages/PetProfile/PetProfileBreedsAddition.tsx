import { ChangeEvent, useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { PetProfileOutletContextProps } from '@/types/petProfile/client';

const PetProfileBreedsAddition = () => {
  const { petName, updateCurrentStep, updateIsValidStep } =
    useOutletContext<PetProfileOutletContextProps>();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.BREEDS);
    updateIsValidStep(false);
  }, []);

  return (
    <Container>
      <PetName>{petName}</PetName>
      <Title>는 어떤 아이인가요?</Title>
    </Container>
  );
};

export default PetProfileBreedsAddition;

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