import { useEffect } from 'react';
import { styled } from 'styled-components';

import Input from '@/components/@common/Input/Input';
import { usePetProfileAddition } from '@/hooks/petProfile/usePetProfileAddition';

interface PetProfileNameAdditionProps {
  onNext: VoidFunction;
}

const PetProfileNameAddition = (props: PetProfileNameAdditionProps) => {
  const { onNext } = props;
  const { petProfile, isValidInput, isFirstRenderedOrValidInput, setIsValidInput, onChangeName } =
    usePetProfileAddition();

  useEffect(() => {
    if (petProfile.name) setIsValidInput(true);
  }, []);

  return (
    <Container>
      <Title>아이의 이름이 무엇인가요?</Title>
      <InputLabel htmlFor="pet-name">이름 입력</InputLabel>
      <Input
        id="pet-name"
        type="text"
        required
        minLength={1}
        placeholder="여기를 눌러 아이의 이름을 입력해주세요."
        maxLength={10}
        value={petProfile.name}
        isValid={isFirstRenderedOrValidInput}
        onChange={onChangeName}
        design="underline"
      />
      <ErrorCaption>
        {!isFirstRenderedOrValidInput &&
          '아이의 이름은 1~10글자 사이의 한글, 영어, 숫자만 입력 가능합니다.'}
      </ErrorCaption>
      <NextButton type="button" onClick={onNext} disabled={!isValidInput}>
        다음
      </NextButton>
    </Container>
  );
};
export default PetProfileNameAddition;

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

const InputLabel = styled.label`
  display: block;

  margin-bottom: 1.6rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;

const ErrorCaption = styled.p`
  margin-top: 1rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.warning};
  letter-spacing: -0.5px;
`;

export const NextButton = styled.button`
  cursor: pointer;

  position: fixed;
  bottom: 4rem;
  left: 2rem;

  width: calc(100% - 4rem);
  height: 5.1rem;

  font-size: 1.6rem;
  font-weight: 700;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: 0.02rem;

  background-color: ${({ theme }) => theme.color.primary};
  border: none;
  border-radius: 16px;
  box-shadow: 0 -8px 20px #fff;

  transition: all 100ms ease-in-out;

  &:active {
    scale: 0.98;
  }

  &:disabled {
    cursor: not-allowed;

    background-color: ${({ theme }) => theme.color.grey300};
  }
`;
