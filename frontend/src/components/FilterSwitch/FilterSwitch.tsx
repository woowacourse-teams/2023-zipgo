import { ComponentPropsWithoutRef } from 'react';
import { css, styled } from 'styled-components';

import Checkbox from '../@common/Checkbox/Checkbox';

interface FilterSwitchProps extends ComponentPropsWithoutRef<'input'> {
  backgroundColor: string;
  filterSize: 'small' | 'medium' | 'large';
}

const FilterSwitch = (filterSwitchProps: Partial<FilterSwitchProps>) => {
  const { onChange, ...props } = filterSwitchProps;

  return (
    <Checkbox asChild onChange={onChange}>
      <FilterSwitchInput {...props} />
    </Checkbox>
  );
};

export default FilterSwitch;

const FilterSwitchInput = styled.input<Partial<FilterSwitchProps>>`
  cursor: pointer;

  position: relative;

  appearance: none;
  background-color: #afb8c1;
  border: 2px solid gray;
  border-color: #afb8c1;
  border-radius: 16px;

  &::before {
    content: '';

    position: absolute;
    left: 0;

    width: 20px;
    height: 20px;

    background-color: white;
    border-radius: 50%;

    transition: left 250ms linear;
  }

  &:checked::before {
    left: 24px;
  }

  ${({ filterSize }) => {
    if (filterSize === 'small') {
      return css`
        width: 36px;
        height: 20px;

        &::before {
          width: 16px;
          height: 16px;
        }

        &:checked::before {
          left: 16px;
        }
      `;
    }

    if (filterSize === 'large') {
      return css`
        width: 60px;
        height: 28px;

        &::before {
          width: 24px;
          height: 24px;
        }

        &:checked::before {
          left: 32px;
        }
      `;
    }

    return css`
      width: 48px;
      height: 24px;
    `;
  }}

  &:checked {
    background-color: ${({ backgroundColor, theme }) => backgroundColor || theme.color.primary};
    border-color: ${({ backgroundColor, theme }) => backgroundColor || theme.color.primary};
  }

  &:disabled {
    cursor: not-allowed;

    opacity: 0.7;
    background-color: #eaeaea;
    border-color: #eaeaea;
  }

  &:disabled::before {
    background-color: lightgray;
  }

  &:disabled + span {
    cursor: not-allowed;

    opacity: 0.7;
  }

  &:focus-visible {
    outline: 2px solid #3e5e8e;
    outline-offset: 2px;
  }

  &:enabled:hover {
    box-shadow: 0 0 0 3px rgba(0 0 0 / 10%);
  }
`;
