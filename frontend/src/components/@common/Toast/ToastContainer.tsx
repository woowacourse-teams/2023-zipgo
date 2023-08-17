import { PropsWithChildren, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components';

import { Toast as ToastType } from '@/types/toast/client';

import Toast from './Toast';

interface ToastContainerProps {
  currentToast?: ToastType[];
}

const Portal = ({ children }: PropsWithChildren) => createPortal(children, document.body);

const ToastContainer = (props: ToastContainerProps) => {
  const { currentToast } = props;

  if (!currentToast?.length) return null;

  return (
    <Portal>
      <ToastContainerWrapper>
        {currentToast.map(toast => (
          <Toast
            key={toast.option.toastId}
            type={toast.option.type}
            content={toast.content}
            deleted={toast.deleted}
          />
        ))}
      </ToastContainerWrapper>
    </Portal>
  );
};

export default ToastContainer;

const ToastContainerWrapper = styled.div`
  position: fixed;
  bottom: 3.2rem;
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;

  transition: all 0.5s ease;
`;
