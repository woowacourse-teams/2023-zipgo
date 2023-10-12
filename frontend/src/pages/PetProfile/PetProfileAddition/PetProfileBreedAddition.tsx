import { useEffect } from 'react';
import { styled } from 'styled-components';

import PetBreedSelect from '@/components/PetProfile/PetBreedSelect';
import { usePetProfileAddition } from '@/hooks/petProfile/usePetProfileAddition';
import { usePetProfileValidation } from '@/hooks/petProfile/usePetProfileValidation';
import { getTopicParticle } from '@/utils/getTopicParticle';

import { NextButton } from './PetProfileNameAddition';

interface PetProfileBreedAdditionProps {
  onNext: (breed: string) => void;
  setIsMixedBreed: React.Dispatch<React.SetStateAction<boolean>>;
}

const PetProfileBreedAddition = (props: PetProfileBreedAdditionProps) => {
  const { onNext, setIsMixedBreed } = props;
  const { isMixedBreed } = usePetProfileValidation();
  const { petProfile, isValidInput, onChangeBreed } = usePetProfileAddition();

  useEffect(() => {
    setIsMixedBreed(isMixedBreed(petProfile.breed));
  }, [petProfile.breed]);

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>{`${getTopicParticle(petProfile.name)} 어떤 아이인가요?`}</Title>
      <InputLabel htmlFor="pet-breed">견종 선택</InputLabel>
      <PetBreedSelect id="pet-breed" onChange={onChangeBreed} />
      <NextButton type="button" onClick={() => onNext(petProfile.breed)} disabled={!isValidInput}>
        다음
      </NextButton>
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
