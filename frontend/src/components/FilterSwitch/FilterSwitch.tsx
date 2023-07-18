import { ComponentPropsWithoutRef, Dispatch, SetStateAction } from 'react';
import { CSSProp, styled } from 'styled-components';

interface FilterSwitchProps extends ComponentPropsWithoutRef<'input'> {
  isActive: boolean;
  setIsActive: Dispatch<SetStateAction<boolean>>;
  labelText?: string;
  backgroundColor?: string;
  filterSize?: 'small' | 'medium' | 'large';
  css?: CSSProp;
}

const FilterSwitch = (filterSwitchProps: FilterSwitchProps) => {
  const { isActive, setIsActive, labelText, filterSize = 'medium', ...props } = filterSwitchProps;

  const toggleFilter = () => setIsActive(!isActive);

  return (
    <Label className={filterSize}>
      <Checkbox
        role="switch"
        type="checkbox"
        className={filterSize}
        onChange={toggleFilter}
        {...props}
      />
      <span>{labelText}</span>
    </Label>
  );
};

export default FilterSwitch;

const Label = styled.label`
  cursor: pointer;

  display: flex;
  gap: 1rem;
  align-items: center;

  font-weight: 700;
  color: #333d4b;

  &.small span {
    font-size: 1.2rem;
  }

  &.medium span {
    font-size: 1.6rem;
  }

  &.large span {
    font-size: 1.8rem;
  }
`;

const Checkbox = styled.input<Partial<FilterSwitchProps>>`
  cursor: pointer;

  position: relative;

  appearance: none;
  background-color: #afb8c1;
  border: 2px solid gray;
  border-color: #afb8c1;
  border-radius: 16px;

  ${({ css }) => css}

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

  &.small {
    width: 36px;
    height: 20px;

    &::before {
      width: 16px;
      height: 16px;
    }

    &:checked::before {
      left: 16px;
    }
  }

  &.medium {
    width: 48px;
    height: 24px;
  }

  &.large {
    width: 60px;
    height: 28px;

    &::before {
      width: 24px;
      height: 24px;
    }

    &:checked::before {
      left: 32px;
    }
  }

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
