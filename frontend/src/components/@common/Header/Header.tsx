import { styled } from 'styled-components';

import ZipgoLogo from '@/assets/zipgo_logo.svg';

const Header = () => (
  <HeaderContainer>
    <img src={ZipgoLogo} alt="집사의고민 로고" />
  </HeaderContainer>
);

export default Header;

const HeaderContainer = styled.header`
  width: 100vw;
  height: 80px;
  padding: 2rem;

  background: white;
  box-shadow: 0 2px 16px 0 rgb(0 0 0 / 15%);
`;
