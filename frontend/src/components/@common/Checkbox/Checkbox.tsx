import {
  ChangeEvent,
  Children,
  cloneElement,
  ComponentPropsWithoutRef,
  isValidElement,
  ReactNode,
} from 'react';

interface CheckboxProps extends ComponentPropsWithoutRef<'input'> {
  asChild: boolean;
  children: ReactNode;
}

interface CheckboxInput {
  type: 'checkbox';
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}

const Checkbox = (CheckboxProps: Partial<CheckboxProps>) => {
  const { asChild = false, children, onChange, ...props } = CheckboxProps;

  if (asChild) {
    if (isValidElement<CheckboxInput>(children)) {
      const child = Children.only(children);

      return cloneElement(child, {
        type: 'checkbox',
        onChange,
      });
    }
  }

  return <input {...props} type="checkbox" onChange={onChange} />;
};

export default Checkbox;
