import { InputHTMLAttributes } from 'react';
import { css, styled } from 'styled-components';

import { StyledProps } from '@/types/common/utility';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  design?: 'underline';
  fontSize?: string;
  isValid?: boolean;
}
const Input = (inputProps: InputProps) => {
  const { design, fontSize, isValid = true, ...restProps } = inputProps;

  return <InputWrapper $design={design} $fontSize={fontSize} $isValid={isValid} {...restProps} />;
};

export default Input;

type InputStyleProps = StyledProps<Pick<InputProps, 'design' | 'fontSize' | 'isValid'>>;

const InputWrapper = styled.input<InputStyleProps>`
  width: 100%;
  height: 100%;
  padding: 1.2rem;

  font-size: ${({ $fontSize }) =>
    ($fontSize && $fontSize < '1.6rem') || !$fontSize ? '16px' : $fontSize};

  border: none;
  outline: none;

  ${({ $design }) => {
    if ($design === 'underline') {
      return css`
        border-bottom: 1px solid ${({ theme }) => theme.color.grey400};
      `;
    }

    return css`
      border: 1px solid ${({ theme }) => theme.color.grey400};
      border-radius: 8px;
    `;
  }}

  ${({ $isValid }) =>
    !$isValid &&
    css`
      border-color: ${({ theme }) => theme.color.warning};
    `};

  &::placeholder {
    color: ${({ theme }) => theme.color.grey400};
  }

  &:focus {
    border-color: #d0e6f9;
    border-width: 2px;
  }

  &:disabled {
    cursor: not-allowed;

    opacity: 0.7;
    background-color: ${({ theme }) => theme.color.grey300};
    border-color: ${({ theme }) => theme.color.grey300};
  }
`;
