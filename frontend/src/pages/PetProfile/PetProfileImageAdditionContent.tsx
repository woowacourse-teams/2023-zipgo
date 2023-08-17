import { useEffect } from 'react';
import { useNavigate, useOutletContext } from 'react-router-dom';
import { styled } from 'styled-components';

import PetProfileImageUploader from '@/components/PetProfile/PetProfileImageUploader';
import { PET_PROFILE_ADDITION_STEP } from '@/constants/petProfile';
import { usePetProfileContext } from '@/context/petProfile';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { useAddPetProfileMutation, useBreedListQuery } from '@/hooks/query/petProfile';
import { routerPath } from '@/router/routes';
import { PetProfile, PetProfileOutletContextProps } from '@/types/petProfile/client';
import { getTopicParticle } from '@/utils/getTopicParticle';

const PetProfileImageAdditionContent = () => {
  const navigate = useNavigate();
  const { petProfile } = usePetProfileContext();
  const { updateCurrentStep } = useOutletContext<PetProfileOutletContextProps>();
  const { addPetProfileMutation } = useAddPetProfileMutation();
  const { breedList } = useBreedListQuery();
  const { updatePetProfile } = usePetProfile(); // 에디가 만든 context

  useEffect(() => {
    updateCurrentStep(PET_PROFILE_ADDITION_STEP.IMAGE_FILE);
  }, []);

  const onSubmitPetProfile = () => {
    addPetProfileMutation
      .addPetProfile(petProfile)
      .then(async () => {
        const userInfo = JSON.parse(
          localStorage.getItem('userInfo') ??
            JSON.stringify({ name: '노아이즈', profileImageUrl: null, hasPet: false }),
        );

        const userPetBreed = breedList?.find(breed => breed.name === petProfile.breed);
        const petProfileWithId = {
          ...petProfile,
          id: 1,
          petSize: userPetBreed || '소형견',
        } as PetProfile;

        updatePetProfile(petProfileWithId); // 헤더 유저 프로필 정보 업데이트

        localStorage.setItem('userInfo', JSON.stringify({ ...userInfo, hasPet: true }));

        alert('반려동물 정보 등록이 완료되었습니다.');
      })
      .catch(error => {
        alert('반려동물 정보 등록에 실패했습니다.');
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

export default PetProfileImageAdditionContent;

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
