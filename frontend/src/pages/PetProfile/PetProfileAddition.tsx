import { useState } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import BackBtnIcon from '@/assets/svg/back_btn.svg';
import Button from '@/components/@common/Button/Button';
import Template from '@/components/@common/Template';
import { PET_PROFILE_ADDITION_STEP, STEP_PATH } from '@/constants/petProfile';
import { routerPath } from '@/router/routes';

const PetProfileAddition = () => {
  const navigate = useNavigate();
  const [petName, setPetName] = useState('');
  const [step, setStep] = useState(0);
  const [isValidStep, setIsValidStep] = useState(false);

  const goBack = () => navigate(routerPath.back);
  const goNext = () => navigate(STEP_PATH[step + 1]);

  const updatePetName = (name: string) => setPetName(name);
  const updateCurrentStep = (step: number) => setStep(step);
  const updateIsValidStep = (isValid: boolean) => setIsValidStep(isValid);

  return (
    <Template
      staticHeader={() =>
        getPetProfileAdditionHeader({
          title: '반려동물 정보 등록',
          step,
          totalStep: Object.keys(PET_PROFILE_ADDITION_STEP).length,
          onClickBackButton: goBack,
        })
      }
      footer={false}
    >
      <ContentLayout>
        <Outlet context={{ petName, updatePetName, updateCurrentStep, updateIsValidStep }} />
      </ContentLayout>
      <Button text="다음" fixed onClick={goNext} disabled={!isValidStep} />
    </Template>
  );
};

export default PetProfileAddition;

const StaticHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 8rem;
  padding: 2rem;
`;

const Title = styled.h1`
  font-size: 1.4rem;
  font-weight: 600;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.black};
`;

const BackButtonWrapper = styled.button`
  cursor: pointer;

  position: absolute;
  top: 2rem;
  left: 0.8rem;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 4rem;
  height: 4rem;

  background-color: transparent;
  border: none;
`;

const BackBtnImage = styled.img`
  width: 3.2rem;
  height: 3.2rem;

  object-fit: cover;
`;

const ContentLayout = styled.div`
  margin: 0 2rem;
`;

const getPetProfileAdditionHeader = ({
  title,
  step,
  totalStep,
  onClickBackButton,
}: {
  title: string;
  step: number;
  totalStep: number;
  onClickBackButton: VoidFunction;
}) => (
  <StaticHeader>
    <Title>{`${title} (${step}/${totalStep})`}</Title>
    <BackButtonWrapper type="button" onClick={onClickBackButton} aria-label="뒤로가기">
      <BackBtnImage src={BackBtnIcon} alt="뒤로가기 아이콘" />
    </BackButtonWrapper>
  </StaticHeader>
);
