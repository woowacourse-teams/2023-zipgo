import { MouseEvent, useEffect } from 'react';
import { useNavigate, useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import PetProfileImageUploader from '@/components/PetProfile/PetProfileImageUploader';
import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetProfileContext } from '@/context/petProfile';
import { useAddPetProfileMutation } from '@/hooks/query/petProfile';
import { routerPath } from '@/router/routes';
import { PetProfileOutletContextProps } from '@/types/petProfile/client';
import { getTopicParticle } from '@/utils/getTopicParticle';

const PetProfileImageAddition = () => {
  const navigate = useNavigate();
  const { petProfile } = usePetProfileContext();
  const { updateCurrentStep } = useOutletContext<PetProfileOutletContextProps>();
  const { addPetProfileMutation } = useAddPetProfileMutation();

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.IMAGE_FILE);
  }, []);

  const onSubmitPetProfile = () => {
    addPetProfileMutation.addPetProfile(petProfile).then(() => {
      alert('반려동물 정보 등록이 완료되었습니다.');
    });

    navigate(routerPath.home());
  };

  return (
    <Container>
      <PetName>{petProfile.name}</PetName>
      <Title>{`${getTopicParticle(petProfile.name)} 어떤 모습인가요?`}</Title>
      <Content>
        <PetProfileImageUploader />
      </Content>
      <SubmitButton type="button" onClick={onSubmitPetProfile}>
        등록하기
      </SubmitButton>
    </Container>
  );
};

export default PetProfileImageAddition;

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

const Content = styled.div`
  display: flex;
  justify-content: center;
`;

const SubmitButton = styled.button`
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
