import { styled } from 'styled-components';

import PetAgeSelect from '@/components/PetProfile/PetAgeSelect';
import { usePetProfileAddition } from '@/hooks/petProfile/usePetProfileAddition';

import { NextButton } from './PetProfileNameAddition';

interface PetProfileAgeAdditionProps {
  onNext: VoidFunction;
}

const PetProfileAgeAddition = (props: PetProfileAgeAdditionProps) => {
  const { onNext } = props;
  const { petProfile, isValidInput, onChangeAge } = usePetProfileAddition();

  return (
    <Container>
      <Title>
        <PetName>{`${petProfile.name},`}</PetName>
        귀여운 이름이에요. 나이는요?
      </Title>
      <InputLabel htmlFor="pet-age">나이 선택</InputLabel>
      <PetAgeSelect id="pet-age" onChange={onChangeAge} />
      <NextButton type="button" onClick={onNext} disabled={!isValidInput}>
        다음
      </NextButton>
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
