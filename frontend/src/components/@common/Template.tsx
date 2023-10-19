/* eslint-disable consistent-return */
import { createElement, isValidElement, PropsWithChildren, ReactNode } from 'react';
import styled, { css, isStyledComponent } from 'styled-components';

import { StyledProps } from '@/types/common/utility';
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

interface TemplateProps {
  footer?: boolean;
}

const Template = <T extends React.FC>(
  props: PropsWithChildren<(FixedHeaderLayout<T> | StaticHeaderLayout<T>) & TemplateProps>,
) => {
  const { children, fixedHeader, staticHeader, footer = true } = props;
  const $header = fixedHeader ? fixedHeader({}) : staticHeader!({});

  if (!isValidElement($header)) throw new Error('Not valid header');

  const headerHeight = isStyledComponent($header.type)
    ? getComputedStyleOfSC($header.type, 'height')
    : undefined;

  return (
    <Layout>
      {createElement(fixedHeader ?? staticHeader!)}
      <Container $isFixedHeader={Boolean(fixedHeader)} $headerHeight={headerHeight}>
        {children}
      </Container>
      {footer && <Footer />}
    </Layout>
  );
};

interface WithoutHeaderProps extends TemplateProps {}

const WithoutHeader = (props: PropsWithChildren<WithoutHeaderProps>) => {
  const { children, footer } = props;

  return (
    <Layout>
      <Container>{children}</Container>
      {footer && <Footer />}
    </Layout>
  );
};

Template.WithoutHeader = WithoutHeader;

export default Template;

const Layout = styled.div`
  position: relative;

  overflow-y: auto;

  width: 100%;
  max-height: calc(var(--vh, 1vh) * 100);
`;

const Container = styled.div<
  StyledProps<
    | {
        isFixedHeader: boolean;
        headerHeight?: string;
      }
    | { isFixedHeader?: never; headerHeight?: never }
  >
>`
  width: 100%;
  height: 100%;
  min-height: calc((var(--vh, 1vh) * 100));

  ${({ $isFixedHeader, $headerHeight }) => {
    if ($isFixedHeader === true) {
      return css`
        padding-top: ${$headerHeight};
      `;
    }

    if ($isFixedHeader === false) {
      return css`
        max-height: calc((var(--vh, 1vh) * 100) - ${$headerHeight});
      `;
    }
  }}
`;
