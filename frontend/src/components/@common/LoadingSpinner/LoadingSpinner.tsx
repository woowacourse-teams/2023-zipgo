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
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  background-color: #fff;
  border-radius: 50%;
  box-shadow: 0 0.1rem 0.4rem 0 rgb(0 0 0 / 15%);
`;

const AnimatedSpinner = styled.img`
  animation: 1s linear 0s infinite normal none running ${rotate};
`;
