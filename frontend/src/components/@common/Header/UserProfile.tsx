import { styled } from 'styled-components';

import ZipgoLogo from '@/assets/svg/zipgo_logo_light.svg';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { useAuth } from '@/hooks/auth';

import { Dialog } from '../Dialog/Dialog';

const UserProfile = () => {
  const { petProfile } = usePetProfile();
  const { isLoggedIn } = useAuth();

  return isLoggedIn ? (
    <Dialog.Trigger asChild>
      <UserInfoContainer>
        {petProfile ? (
          <>
            <UserProfileImg
              src={
                petProfile.imageUrl.length
                  ? petProfile.imageUrl
                  : 'https://velog.velcdn.com/images/chex/post/9505d4fb-5850-4ce8-9575-04cece41a185/image.png'
              }
              alt="반려견 프로필 사진"
            />
            <UserNickname>{petProfile.name}</UserNickname>
          </>
        ) : (
          <RegisterPetText>여기를 눌러 반려견을 등록해주세요.</RegisterPetText>
        )}
      </UserInfoContainer>
    </Dialog.Trigger>
  ) : (
    <Logo src={ZipgoLogo} alt="집사의고민 로고" />
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

  object-fit: cover;
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

const Logo = styled.img`
  width: 11.3rem;
  height: 3.6rem;
`;
