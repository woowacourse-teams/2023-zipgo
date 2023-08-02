import { Children, isValidElement, PropsWithChildren, ReactElement, ReactNode } from 'react';

export type AsChild = {
  asChild?: boolean;
};

export type PropsWithAsChild<P = unknown> = PropsWithChildren<P> & AsChild;

export type PropsWithRenderProps<P = unknown, R extends object = object> = Omit<P, 'children'> & {
  children?: ReactNode | ((payload: R) => JSX.Element);
} & AsChild;

type WithAsChild<P> = Omit<P, 'children'> & {
  asChild: true;
  child: ReactElement<P>;
};

type WithoutAsChild = PropsWithChildren<{
  asChild: false;
  child: null;
}>;

// eslint-disable-next-line comma-spacing
const getValidChild = <P>(children: ReactNode) => {
  const child = Children.only(children);

  return isValidElement<P>(child) ? child : null;
};

export function getValidProps<P, R extends object>(
  props: PropsWithRenderProps<P, R> | PropsWithAsChild<P>,
): Omit<typeof props, 'asChild' | 'children'> & {
  resolveChildren: (payload: R) => WithAsChild<P> | WithoutAsChild;
} {
  const { asChild, children, ...restProps } = props;

  const resolveChildren = (payload: R): WithAsChild<P> | WithoutAsChild => {
    const resolvedChildren = typeof children === 'function' ? children(payload) : children;

    if (asChild) {
      const child = getValidChild<P>(resolvedChildren);

      if (child) return { asChild, child };
    }

    return { asChild: false, child: null, children: resolvedChildren };
  };

  return { resolveChildren, ...restProps };
}
