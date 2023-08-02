import { cloneElement, ComponentPropsWithoutRef, PropsWithChildren } from 'react';

import { getValidProps } from '@/utils/compound';

interface CheckboxProps extends ComponentPropsWithoutRef<'input'> {
  asChild?: boolean;
}

const Checkbox = (props: PropsWithChildren<CheckboxProps>) => {
  const { asChild, child, ...restProps } = getValidProps(props);

  if (asChild) {
    return cloneElement(child, {
      ...restProps,
      type: 'checkbox',
    });
  }

  return <input {...restProps} type="checkbox" />;
};

export default Checkbox;
