import { Children, isValidElement, PropsWithChildren, ReactElement, ReactNode } from 'react';

type WithAsChild<P> = P & {
  asChild: true;
  child: ReactElement<P>;
};

type WithoutAsChild<P> = P & {
  asChild: false;
  child: null;
};

// eslint-disable-next-line comma-spacing
const getValidChild = <P>(children: ReactNode) => {
  const child = Children.only(children);

  return isValidElement<P>(child) ? child : null;
};

export function getValidProps<P extends PropsWithChildren<{ asChild?: boolean }>>(
  props: P,
): WithAsChild<P> | WithoutAsChild<P> {
  const { asChild, children, ...restProps } = props;

  if (asChild) {
    const child = getValidChild<P>(children);

    if (child) return { ...restProps, asChild, child };
  }

  return { ...restProps, asChild: false, child: null };
}
