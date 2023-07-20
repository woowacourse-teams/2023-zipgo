import { createElement, isValidElement, PropsWithChildren } from 'react';
import styled, { css, isStyledComponent } from 'styled-components';

import { getComputedStyleOfSC } from '@/utils/styled-components';

import Footer from './Footer/Footer';

interface LayoutProps<T> {
  header: T;
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const Template = <T extends React.FC<any>>(props: PropsWithChildren<LayoutProps<T>>) => {
  const { children, header } = props;
  const $header = header({});

  if (!isValidElement($header)) throw new Error('Not valid header');

  const paddingTop = isStyledComponent($header.type)
    ? getComputedStyleOfSC($header.type, 'height')
    : undefined;

  return (
    <Layout>
      {createElement(header)}
      <Container paddingTop={paddingTop}>{children}</Container>
      <Footer />
    </Layout>
  );
};

export default Template;

const Layout = styled.div`
  min-height: calc(var(--vh, 1vh) * 100);
`;

const Container = styled.div<{
  paddingTop?: string;
}>`
  width: 100vw;
  min-height: calc(var(--vh, 1vh) * 100);

  ${({ paddingTop }) =>
    paddingTop &&
    css`
      padding-top: ${paddingTop};
    `}
`;
