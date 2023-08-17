import { Link } from 'react-router-dom';
import styled from 'styled-components';

import HomeIcon from '@/assets/svg/home_icon.svg';
import ZipgoLogo from '@/assets/svg/zipgo_logo_light.svg';
import Button from '@/components/@common/Button/Button';
import Template from '@/components/@common/Template';
import { PATH } from '@/router/routes';

const ErrorPage = () => (
  <Template.WithoutHeader>
    <Layout>
      <HeaderContainer>
        <img src={ZipgoLogo} alt="집사의고민 로고" />
      </HeaderContainer>
      <BannerSection>
        <ErrorTextWrapper>
          <ErrorCodeText>404</ErrorCodeText>
          <ErrorSubText>페이지를 찾을 수 없어요</ErrorSubText>
        </ErrorTextWrapper>
        <Link to={PATH.HOME}>
          <Button
            type="button"
            text="홈으로 돌아가기"
            icon={HomeIcon}
            style={{ width: '25.2rem' }}
          />
        </Link>
      </BannerSection>
    </Layout>
  </Template.WithoutHeader>
);

export default ErrorPage;

const Layout = styled.div`
  position: relative;

  display: flex;
  align-items: center;

  width: 100%;
  min-height: calc(var(--vh, 1vh) * 100);
`;

const BannerSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
  align-items: center;
  justify-content: center;

  width: 100vw;
  height: 100vh;

  background: linear-gradient(45deg, #3e5e8e 0%, #6992c3 100%);
`;

const HeaderContainer = styled.header`
  position: absolute;
  z-index: 999;
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

const ErrorTextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: center;
  justify-content: center;
`;

const ErrorCodeText = styled.h1`
  font-size: 4.8rem;
  font-weight: 800;
  color: ${({ theme }) => theme.color.white};
`;

const ErrorSubText = styled.p`
  font-size: 1.6rem;
  font-weight: 500;
  color: ${({ theme }) => theme.color.white};
`;
