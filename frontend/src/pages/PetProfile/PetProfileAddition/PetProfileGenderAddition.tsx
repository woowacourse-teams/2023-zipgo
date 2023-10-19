import { styled } from 'styled-components';

import GenderRadioInput from '@/components/PetProfile/GenderRadioInput';
import { GENDERS } from '@/constants/petProfile';
import { usePetProfileAddition } from '@/hooks/petProfile/usePetProfileAddition';

import { NextButton } from './PetProfileNameAddition';

interface PetProfileGenderAdditionProps {
  onNext: VoidFunction;
}

const PetProfileGenderAddition = (props: PetProfileGenderAdditionProps) => {
  const { onNext } = props;
  const { petProfile, onChangeGender } = usePetProfileAddition();

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
      <NextButton type="button" onClick={onNext}>
        다음
      </NextButton>
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
