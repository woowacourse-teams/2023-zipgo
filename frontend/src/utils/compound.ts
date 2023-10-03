import { Children, isValidElement, PropsWithChildren, ReactElement, ReactNode } from 'react';

import { RenderProps } from '@/types/common/utility';

export type AsChild = {
  asChild?: boolean;
};

export type PropsWithAsChild<P = unknown> = PropsWithChildren<P> & AsChild;

export type PropsWithRenderProps<P = unknown, R extends object = object> = Omit<P, 'children'> & {
  children?: ReactNode | RenderProps<R>;
} & AsChild;

type WithAsChild<P> = PropsWithChildren<{
  asChild: true;
  child: ReactElement<P>;
}>;

type WithoutAsChild = PropsWithChildren<{
  asChild: false;
  child: null;
}>;

// eslint-disable-next-line comma-spacing
const getValidChild = <P>(children: ReactNode) => {
  const child = Children.only(children);

  return isValidElement<P>(child) ? child : null;
};

export const getValidProps = <P, R extends object>(
  props: PropsWithRenderProps<P, R> | PropsWithAsChild<P>,
): Omit<typeof props, 'asChild' | 'children'> & {
  resolveChildren: (payload: R) => WithAsChild<P> | WithoutAsChild;
} => {
  const { asChild, children, ...restProps } = props;

  const resolveChildren = (payload: R): WithAsChild<P> | WithoutAsChild => {
    const resolvedChildren = resolveRenderProps(children, payload);

    if (asChild) {
      const child = getValidChild<P>(resolvedChildren);

      if (child) return { asChild, child, children: resolvedChildren };
    }

    return { asChild: false, child: null, children: resolvedChildren };
  };

  return { resolveChildren, ...restProps };
};

export const resolveRenderProps = <P extends object>(
  renderProps: ReactNode | RenderProps<P>,
  payload: P,
) => (typeof renderProps === 'function' ? renderProps(payload) : renderProps);
