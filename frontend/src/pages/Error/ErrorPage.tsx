import { PropsWithChildren } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

import HomeIcon from '@/assets/svg/home_icon.svg';
import RefreshIcon from '@/assets/svg/refresh_icon.svg';
import ZipgoLogo from '@/assets/svg/zipgo_logo_light.svg';
import Button from '@/components/@common/Button/Button';
import Template from '@/components/@common/Template';
import { DEFAULT_STATUS, ERROR_MESSAGE_KIT } from '@/constants/errors';
import { PATH } from '@/router/routes';
import { APIError, RuntimeError, UnexpectedError } from '@/utils/errors';

interface ErrorPageInfo {
  status: number;
  message: string;
}

interface ErrorPageProps {
  error?: Error;
  refresh?: boolean;
  reset?: VoidFunction;
}

const ErrorPage = (props: ErrorPageProps) => {
  const { error, ...restProps } = props;

  const pageInfo = getErrorPageInfo(error);

  return (
    <ErrorLayout>
      <ErrorBanner {...pageInfo} {...restProps} />
    </ErrorLayout>
  );
};

const NotFound = () => (
  <ErrorLayout>
    <ErrorBanner status={DEFAULT_STATUS} message={ERROR_MESSAGE_KIT.NOT_FOUND} />
  </ErrorLayout>
);

const ErrorLayout = ({ children }: PropsWithChildren) => (
  <Template.WithoutHeader>
    <Layout>
      <HeaderContainer>
        <img src={ZipgoLogo} alt="집사의고민 로고" />
      </HeaderContainer>
      {children}
    </Layout>
  </Template.WithoutHeader>
);

interface ErrorBannerProps extends Pick<ErrorPageProps, 'reset' | 'refresh'>, ErrorPageInfo {}

const ErrorBanner = (props: ErrorBannerProps) => {
  const { status, message, reset, refresh = false } = props;

  return (
    <BannerSection>
      <ErrorTextWrapper>
        <ErrorCodeText>{status}</ErrorCodeText>
        <ErrorSubText>{message}</ErrorSubText>
      </ErrorTextWrapper>
      <Link to={PATH.HOME}>
        <Button
          type="button"
          text="홈으로 돌아가기"
          icon={HomeIcon}
          style={{ width: '25.2rem' }}
          onClick={reset}
        />
      </Link>
      {refresh && (
        <Button
          type="button"
          text="다시 시도하기"
          icon={RefreshIcon}
          style={{ width: '25.2rem' }}
          onClick={reset}
        />
      )}
    </BannerSection>
  );
};

const getErrorPageInfo = (error?: Error): ErrorPageInfo => {
  if (error instanceof RuntimeError || error instanceof UnexpectedError) {
    return { message: error.message, status: DEFAULT_STATUS };
  }

  if (error instanceof APIError) {
    return { message: error.message, status: error.status };
  }

  return { message: ERROR_MESSAGE_KIT.UNEXPECTED_ERROR, status: DEFAULT_STATUS };
};

ErrorPage.NotFound = NotFound;

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

  width: 100%;
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

  width: 100%;
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
