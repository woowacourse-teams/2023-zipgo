import { PropsWithChildren } from 'react';
import { styled } from 'styled-components';
import { DesktopView, useDesktopView } from 'zipgo-layout';

import BackgroundImg from '@/assets/svg/background_img.svg';
import ZipgoLogo from '@/assets/svg/zipgo_logo_light.svg';
import ZipgoTextLogo from '@/assets/svg/zipgo_text_logo.svg';
import theme from '@/styles/theme';

const RenderSub = () => (
  <SupporterWrapper>
    <ZipgoLogoImg src={ZipgoLogo} alt="집사의고민 로고" />
    <div>
      <SubTextContainer>
        <SubText>초보 집사들의</SubText>
        <SubText>사료 선택 기준.</SubText>
      </SubTextContainer>
      <MainTextContainer>
        <img src={ZipgoTextLogo} alt="집사의고민 글씨로고" />
      </MainTextContainer>
    </div>
    <SmallTextContainer>
      <SmallText>우아한테크코스 집사의고민팀</SmallText>
      <SmallText>문의: team.zipgo@gmail.com</SmallText>
    </SmallTextContainer>
  </SupporterWrapper>
);

interface DesktopViewProps extends PropsWithChildren {}

const MobileToDesktop = (props: DesktopViewProps) => {
  const { children } = props;

  return (
    <DesktopView
      backgroundImage={BackgroundImg}
      renderSub={RenderSub()}
      maxWidth={theme.maxWidth.mobile}
    >
      {children}
    </DesktopView>
  );
};

export default MobileToDesktop;

const SupporterWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  height: 100vh;
  padding: 2rem 0;
`;

const ZipgoLogoImg = styled.img`
  width: 11.3rem;
  height: 3.6rem;
`;

const SubTextContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
`;

const SubText = styled.p`
  font-size: 3rem;
  color: #fff;
`;

const MainTextContainer = styled.div`
  margin-top: 3rem;
`;

const SmallTextContainer = styled.div`
  display: flex;
  gap: 0.8rem;
`;

const SmallText = styled.p`
  font-size: 1.2rem;
  color: #fff;
`;
