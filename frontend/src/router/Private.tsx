import { PropsWithChildren } from 'react';
import { Navigate } from 'react-router-dom';

import { useCheckAuth } from '@/hooks/auth';

import { PATH } from './routes';

const Private = ({ children }: PropsWithChildren) => {
  const { isLoggedIn } = useCheckAuth();

  if (!isLoggedIn) {
    alert('로그인 후 사용해 주세요');

    return <Navigate to={PATH.LOGIN} />;
  }

  return children;
};

export default Private;
