import { useEffect, useLayoutEffect, useState } from 'react';
import { css, keyframes, styled } from 'styled-components';

import { StyledProps } from '@/types/common/utility';
import { ToastProps } from '@/types/toast/client';

import ToastIcon from './ToastIcon';

const Toast = (props: ToastProps) => {
  const { content, type, deleted } = props;

  const [timer, setTimer] = useState<NodeJS.Timeout>();

  const [isShow, setIsShow] = useState<boolean>(false);

  useLayoutEffect(() => {
    setIsShow(true);

    const deleteAnimation = setTimeout(() => {
      setIsShow(false);
    }, 4000);
    setTimer(deleteAnimation);
  }, []);

  useEffect(() => {
    if (deleted) {
      clearTimeout(timer);
      setIsShow(false);
    }
  }, [deleted, timer]);

  return (
    <ToastWrapper $type={type} $isShow={isShow}>
      <IconWrapper>
        <ToastIcon type={type} />
      </IconWrapper>
      {typeof content === 'string' ? (
        <ToastText $type={type}>{content}</ToastText>
      ) : (
        <ToastContent>content</ToastContent>
      )}
    </ToastWrapper>
  );
};

export default Toast;

interface ToastStyleProps extends StyledProps<Pick<ToastProps, 'type'>> {}

const fadeIn = keyframes`
  from {
    opacity: 0;
    transform: translateY(200%);
  }
  to {
    opacity: 1;
    transform: translateY(-150%);
  }
`;

const fadeOut = keyframes`
  from {
    opacity: 1;
    transform: translateY(-150%);
  }
  to {
    opacity: 0;
    transform: translateY(200%);
    display: none;
  }
`;

const ToastWrapper = styled.div<ToastStyleProps & { $isShow: boolean }>`
  display: flex;
  gap: 0.8rem;
  align-items: center;

  max-width: 35rem;
  margin-bottom: 1.2rem;
  padding: 1.2rem 2rem;

  background-color: ${({ theme, $type }) => {
    if ($type === 'success') {
      return theme.color.primary;
    }
    if ($type === 'warning') {
      return theme.color.warning;
    }
    return theme.color.white;
  }};
  border-radius: 20px;
  box-shadow: 0 2px 8px 0
    ${({ $type }) => {
      if ($type === 'success') {
        return 'rgb(62 94 142 / 50%);';
      }
      if ($type === 'warning') {
        return 'rgb(231 56 70 / 50%);';
      }
      return 'rgb(0 0 0 / 15%);';
    }};

  ${({ $isShow }) => {
    if ($isShow) {
      return css`
        animation: ${fadeIn} 0.35s ease-in-out 0s 1 normal forwards;
      `;
    }
    return css`
      animation: ${fadeOut} 0.5s ease-in-out 0s 1 normal forwards;
    `;
  }};
`;

const IconWrapper = styled.div`
  width: 2.4rem;
  height: 2.4rem;
`;

const ToastText = styled.p<ToastStyleProps>`
  font-size: 1.5rem;
  font-weight: 400;
  color: ${({ theme, $type }) => {
    if ($type === 'info') {
      return theme.color.grey600;
    }
    return theme.color.white;
  }};
  letter-spacing: -0.05rem;
`;

const ToastContent = styled.div`
  display: flex;
`;
