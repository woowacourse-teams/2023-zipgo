import { ChangeEvent, useEffect, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import Input from '@/components/@common/Input/Input';
import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetAdditionContext } from '@/context/petProfile/PetAdditionContext';
import { usePetProfileValidation } from '@/hooks/petProfile';
import { PetProfileOutletContextProps } from '@/types/petProfile/client';

const PetProfileNameAddition = () => {
  const [isValidInput, setIsValidInput] = useState(true);
  const { updateCurrentStep, updateIsValidStep } = useOutletContext<PetProfileOutletContextProps>();
  const { updatePetProfile } = usePetAdditionContext();
  const { isValidName } = usePetProfileValidation();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.NAME);
  }, []);

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    const petName = e.target.value;

    if (isValidName(petName)) {
      setIsValidInput(true);
      updateIsValidStep(true);
      updatePetProfile({ name: petName });
    }

    if (!isValidName(petName)) {
      updateIsValidStep(false);
      setIsValidInput(false);
    }
  };

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
        isValid={isValidInput}
        onChange={onChangeName}
        design="underline"
        fontSize="1.3rem"
      />
      {!isValidInput && (
        <ErrorCaption>
          아이의 이름은 1~10글자 사이의 한글, 영어, 숫자만 입력 가능합니다.
        </ErrorCaption>
      )}
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
