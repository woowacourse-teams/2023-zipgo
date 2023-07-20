import { createElement, isValidElement, PropsWithChildren } from 'react';
import styled, { css, isStyledComponent } from 'styled-components';

import { getComputedStyleOfSC } from '@/utils/styled-components';

import Footer from './Footer/Footer';

interface FixedHeaderLayout<T> {
  fixedHeader: T;
  staticHeader?: never;
}

interface StaticHeaderLayout<T> {
  fixedHeader?: never;
  staticHeader: T;
}

const Template = <T extends React.FC>(
  props: PropsWithChildren<FixedHeaderLayout<T> | StaticHeaderLayout<T>>,
) => {
  const { children, fixedHeader, staticHeader } = props;
  const $header = fixedHeader ? fixedHeader({}) : staticHeader!({});

  if (!isValidElement($header)) throw new Error('Not valid header');

  const paddingTop = isStyledComponent($header.type)
    ? getComputedStyleOfSC($header.type, 'height')
    : undefined;

  return (
    <Layout>
      {createElement(fixedHeader ?? staticHeader!)}
      <Container paddingTop={fixedHeader && paddingTop}>{children}</Container>
      <Footer />
    </Layout>
  );
};

export default Template;

const Layout = styled.div`
  width: 100vw;
  min-height: calc(var(--vh, 1vh) * 100);
`;

const Container = styled.div<{
  paddingTop?: string;
}>`
  width: 100%;
  min-height: calc(var(--vh, 1vh) * 100);

  ${({ paddingTop }) =>
    paddingTop &&
    css`
      padding-top: ${paddingTop};
    `}
`;
