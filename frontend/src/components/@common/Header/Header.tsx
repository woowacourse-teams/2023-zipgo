import { Link } from 'react-router-dom';
import { styled } from 'styled-components';

import ZipgoLogo from '@/assets/svg/zipgo_logo_light.svg';

const Header = () => (
  <HeaderContainer>
    <img src={ZipgoLogo} alt="집사의고민 로고" />
    <LinkButton to="login">로그인</LinkButton>
  </HeaderContainer>
);

export default Header;

const HeaderContainer = styled.header`
  position: absolute;
  z-index: 9999;
  top: 0;
  left: 0;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100vw;
  height: 8rem;
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
