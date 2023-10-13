import { useEffect } from 'react';
import { styled } from 'styled-components';

import Input from '@/components/@common/Input/Input';
import { usePetProfileAddition } from '@/hooks/petProfile/usePetProfileAddition';

import { NextButton } from './PetProfileNameAddition';

interface PetProfileWeightAddition {
  onNext: VoidFunction;
}

const PetProfileWeightAddition = (props: PetProfileWeightAddition) => {
  const { onNext } = props;
  const { petProfile, isValidInput, isFirstRenderedOrValidInput, setIsValidInput, onChangeWeight } =
    usePetProfileAddition();

  useEffect(() => {
    if (petProfile.weight) setIsValidInput(true);
  }, []);

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>의 몸무게를 입력해주세요.</Title>
      <InputLabel htmlFor="pet-weight">몸무게 입력</InputLabel>
      <WeightInputContainer>
        <Input
          id="pet-weight"
          type="text"
          required
          minLength={1}
          placeholder="예) 7.5"
          maxLength={5}
          value={petProfile.weight}
          isValid={isFirstRenderedOrValidInput}
          onChange={onChangeWeight}
          design="underline"
          fontSize="1.3rem"
          inputMode="decimal"
        />
        <Kg>kg</Kg>
      </WeightInputContainer>
      <ErrorCaption>
        {!isFirstRenderedOrValidInput &&
          '몸무게는 0kg초과, 100kg이하 소수점 첫째짜리까지 입력이 가능합니다.'}
      </ErrorCaption>
      <NextButton type="button" onClick={onNext} disabled={!isValidInput}>
        다음
      </NextButton>
    </Container>
  );
};

export default PetProfileWeightAddition;

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

const WeightInputContainer = styled.div`
  position: relative;
`;

const ErrorCaption = styled.p`
  margin-top: 1rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.warning};
  letter-spacing: -0.5px;
`;

const Kg = styled.p`
  position: absolute;
  top: 1.2rem;
  right: 1.2rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;
