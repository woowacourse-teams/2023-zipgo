import styled from 'styled-components';

import ZipgoBanner from '@/assets/png/landing_banner.png';
import KakaoSymbol from '@/assets/svg/kakao_symbol.svg';
import ZipgoLogo from '@/assets/svg/zipgo_logo_dark.svg';
import Template from '@/components/@common/Template';
import { useAuth } from '@/hooks/auth';

const Login = () => {
  const { getKakaoAuth } = useAuth();

  return (
    <Template.WithoutHeader>
      <Layout>
        <IntroContainer>
          <LogoBannerContainer>
            <LogoImg src={ZipgoLogo} alt="집사의고민 로고" />
            <BannerImg src={ZipgoBanner} alt="집사의고민 배너 이미지" />
          </LogoBannerContainer>
          <Intro>
            답답했던 사료 고민은
            <br />
            여기까지.
          </Intro>
        </IntroContainer>
        <KakaoLoginButton type="button" onClick={getKakaoAuth}>
          <img src={KakaoSymbol} alt="카카오 심볼" />
          <span>카카오 로그인</span>
        </KakaoLoginButton>
      </Layout>
    </Template.WithoutHeader>
  );
};

export default Login;

const Layout = styled.div`
  position: relative;

  display: flex;
  align-items: center;

  width: 100%;
  height: 100%;
`;

const IntroContainer = styled.section`
  box-sizing: border-box;
  width: 100%;
  padding-left: 2rem;
`;

const LogoBannerContainer = styled.div`
  position: relative;
  transform: translateY(20px);

  display: flex;
  align-items: flex-end;
  justify-content: space-between;

  width: 100%;
`;

const LogoImg = styled.img`
  position: absolute;
  bottom: 0;
  left: 0;
  transform: translateY(-100%);

  width: 17rem;
`;

const BannerImg = styled.img`
  position: absolute;
  right: 0;
  bottom: 0;

  width: 21.9rem;
`;

const Intro = styled.h1`
  font-size: 3.8rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey700};
  letter-spacing: -0.5px;
`;

const KakaoLoginButton = styled.button`
  position: absolute;
  bottom: 2rem;
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  align-items: center;
  justify-content: center;

  width: calc(100% - 4rem);
  height: 5.1rem;

  background: #fee500;
  border: none;
  border-radius: 12px;

  & > span {
    /* stylelint-disable-next-line declaration-property-unit-allowed-list */
    margin-left: 8px;

    font-size: 1.7rem;
    font-weight: 700;
    font-style: normal;
    color: rgb(0 0 0 / 85%);
  }
`;