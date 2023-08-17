import { styled } from 'styled-components';

import BottomDropIcon from '@/assets/svg/bottom_drop_icon.svg';
import ZipgoLogo from '@/assets/svg/zipgo_logo_dark.svg';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { useUser } from '@/hooks/auth';

import { Dialog } from '../Dialog/Dialog';

const UserProfile = () => {
  const { petProfile } = usePetProfile();
  const { accessToken } = useUser();

  return (
    <Dialog.Trigger asChild>
      <UserInfoContainer>
        {accessToken ? (
          petProfile ? (
            <UserInfoContainer>
              <UserProfileImg src={petProfile.imageUrl} alt="반려견 프로필 사진" />
              <UserNickname>{petProfile.name}</UserNickname>
            </UserInfoContainer>
          ) : (
            <RegisterPetText>여기를 눌러 반려견을 등록해주세요.</RegisterPetText>
          )
        ) : (
          <img src={ZipgoLogo} alt="집사의고민 로고" />
        )}
        <img src={BottomDropIcon} alt="반려견 등록" />
      </UserInfoContainer>
    </Dialog.Trigger>
  );
};

export default UserProfile;

const UserInfoContainer = styled.button`
  display: flex;
  gap: 1.6rem;
  align-items: center;

  background-color: transparent;
  border: none;
`;

const UserProfileImg = styled.img`
  width: 5.5rem;
  height: 5.5rem;

  border: 1px solid ${({ theme }) => theme.color.white};
  border-radius: 50%;
`;

const UserNickname = styled.p`
  font-size: 2rem;
  font-weight: 700;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: -0.05rem;
`;

const RegisterPetText = styled.p`
  font-size: 1.6rem;
  font-weight: 500;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: -0.05rem;
`;
