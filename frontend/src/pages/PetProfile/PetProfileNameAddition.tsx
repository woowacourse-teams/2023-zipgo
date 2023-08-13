import { useEffect } from 'react';
import { useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import Input from '@/components/@common/Input/Input';
import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';

interface OutletContextProps {
  updateCurrentStep: (step: number) => void;
}

const PetProfileNameAddition = () => {
  const { updateCurrentStep } = useOutletContext<OutletContextProps>();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.NAME);
  }, []);

  return (
    <TitleAndInputContainer>
      <Title>아이의 이름이 무엇인가요?</Title>
      <InputLabel htmlFor="pet-name">이름 입력</InputLabel>
      <Input
        id="pet-name"
        type="text"
        required
        minLength={1}
        placeholder="여기를 눌러 아이의 이름을 입력해주세요."
        maxLength={10}
        design="underline"
        fontSize="1.3rem"
      />
    </TitleAndInputContainer>
  );
};
export default PetProfileNameAddition;

const TitleAndInputContainer = styled.div`
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
