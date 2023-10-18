import { Link } from 'react-router-dom';
import { styled } from 'styled-components';

import PetListBottomSheet from '@/components/PetProfile/PetListBottomSheet';
import { useAuth, useCheckAuth } from '@/hooks/auth';

const Header = () => {
  const { logout } = useAuth();
  const { isLoggedIn } = useCheckAuth();

  return (
    <HeaderContainer>
      <PetListBottomSheet />
      {isLoggedIn ? (
        <LogoutButton type="button" onClick={logout}>
          로그아웃
        </LogoutButton>
      ) : (
        <LinkButton to="login">로그인</LinkButton>
      )}
    </HeaderContainer>
  );
};

export default Header;

const HeaderContainer = styled.header`
  position: absolute;
  z-index: 999;
  top: 0;
  left: 0;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100vw;
  padding: 2rem;

  background: transparent;
`;

const LinkButton = styled(Link)`
  font-size: 1.6rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.white};
  text-decoration: none;
  letter-spacing: -0.5px;
`;

const LogoutButton = styled.button`
  font-size: 1.6rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.white};
  text-decoration: none;
  letter-spacing: -0.5px;

  background-color: transparent;
  border: none;
`;
