import { cloneElement, ComponentPropsWithoutRef, PropsWithChildren } from 'react';

import { getValidProps } from '@/utils/compound';

interface CheckboxProps extends ComponentPropsWithoutRef<'input'> {
  asChild?: boolean;
}

const Checkbox = (props: PropsWithChildren<CheckboxProps>) => {
  const { resolveChildren, ...restProps } = getValidProps(props);

  const resolved = resolveChildren({});

  if (resolved.asChild) {
    return cloneElement(resolved.child, {
      ...restProps,
      type: 'checkbox',
    });
  }

  return <input {...restProps} type="checkbox" />;
};

export default Checkbox;
