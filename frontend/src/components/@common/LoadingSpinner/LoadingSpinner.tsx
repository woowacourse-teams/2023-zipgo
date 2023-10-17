import { keyframes, styled } from 'styled-components';

import LoadingSpinnerImg from '@/assets/svg/loading_spinner.svg';

const LoadingSpinner = () => (
  <SpinnerWrapper>
    <AnimatedSpinner src={LoadingSpinnerImg} alt="로딩중" />
  </SpinnerWrapper>
);

export default LoadingSpinner;

const rotate = keyframes`
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
`;

const SpinnerWrapper = styled.div`
  position: fixed;
  z-index: 10;
  bottom: 50%;
  transform: translateY(-50%);

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  max-width: ${({ theme }) => theme.maxWidth.mobile};
`;

const AnimatedSpinner = styled.img`
  animation: 1s linear 0s infinite normal none running ${rotate};
`;
