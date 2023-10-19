import { useState } from 'react';
import { styled } from 'styled-components';

import BackBtnIcon from '@/assets/svg/back_btn.svg';
import Template from '@/components/@common/Template';
import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import useEasyNavigate from '@/hooks/@common/useEasyNavigate';
import { useFunnel } from '@/hooks/@common/useFunnel';

import PetProfileAgeAddition from './PetProfileAgeAddition';
import PetProfileBreedAddition from './PetProfileBreedAddition';
import PetProfileGenderAddition from './PetProfileGenderAddition';
import PetProfileImageAddition from './PetProfileImageAddition';
import PetProfileNameAddition from './PetProfileNameAddition';
import PetProfilePetSizeAddition from './PetProfilePetSizeAddition';
import PetProfileWeightAddition from './PetProfileWeightAddition';

const PetProfileAdditionFormFunnel = () => {
  const { goBackSafely } = useEasyNavigate();
  const [isMixedBreed, setIsMixedBreed] = useState(false);
  const { Funnel, step, setStep } = useFunnel(
    PET_PROFILE_ADDITION_STEP,
    PET_PROFILE_ADDITION_STEP[0],
  );

  const onClickBackButton = (stepNum: number) => {
    const stepIndex = stepNum - 1;

    if (stepIndex === 0) goBackSafely();
    else if (PET_PROFILE_ADDITION_STEP[stepIndex] === 'GENDER' && !isMixedBreed) {
      setStep(prev => PET_PROFILE_ADDITION_STEP[stepIndex - 2]);
    } else setStep(prev => PET_PROFILE_ADDITION_STEP[stepIndex - 1]);
  };

  return (
    <Template
      staticHeader={() =>
        getPetProfileAdditionHeader({
          title: '반려동물 정보 등록',
          stepNum: PET_PROFILE_ADDITION_STEP.indexOf(step) + 1,
          totalStep: PET_PROFILE_ADDITION_STEP.length,
          onClickBackButton,
        })
      }
      footer={false}
    >
      <ContentLayout>
        <Funnel>
          <Funnel.Step name="NAME">
            <PetProfileNameAddition onNext={() => setStep(prev => 'AGE')} />
          </Funnel.Step>
          <Funnel.Step name="AGE">
            <PetProfileAgeAddition onNext={() => setStep(prev => 'BREED')} />
          </Funnel.Step>
          <Funnel.Step name="BREED">
            <PetProfileBreedAddition
              setIsMixedBreed={setIsMixedBreed}
              onNext={() => {
                if (isMixedBreed) setStep(prev => 'PET_SIZE');
                else setStep(prev => 'GENDER');
              }}
            />
          </Funnel.Step>
          <Funnel.Step name="PET_SIZE">
            <PetProfilePetSizeAddition onNext={() => setStep(prev => 'GENDER')} />
          </Funnel.Step>
          <Funnel.Step name="GENDER">
            <PetProfileGenderAddition onNext={() => setStep(prev => 'WEIGHT')} />
          </Funnel.Step>
          <Funnel.Step name="WEIGHT">
            <PetProfileWeightAddition onNext={() => setStep(prev => 'IMAGE_FILE')} />
          </Funnel.Step>
          <Funnel.Step name="IMAGE_FILE">
            <PetProfileImageAddition />
          </Funnel.Step>
        </Funnel>
      </ContentLayout>
    </Template>
  );
};

export default PetProfileAdditionFormFunnel;

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
  stepNum,
  totalStep,
  onClickBackButton,
}: {
  title: string;
  stepNum: number;
  totalStep: number;
  onClickBackButton: (stepNum: number) => void;
}) => (
  <StaticHeader>
    <Title>{`${title} (${stepNum}/${totalStep})`}</Title>
    <BackButtonWrapper
      type="button"
      onClick={() => onClickBackButton(stepNum)}
      aria-label="뒤로가기"
    >
      <BackBtnImage src={BackBtnIcon} alt="뒤로가기 아이콘" />
    </BackButtonWrapper>
  </StaticHeader>
);
