import { styled } from 'styled-components';

import ZipgoLogo from '@/assets/svg/zipgo_logo_light.svg';

const Header = () => (
  <HeaderContainer>
    <img src={ZipgoLogo} alt="집사의고민 로고" />
  </HeaderContainer>
);

export default Header;

const HeaderContainer = styled.header`
  position: relative;
  z-index: 9999;

  width: 100vw;
  height: 8rem;
  padding: 2rem;

  background: transparent;
`;
