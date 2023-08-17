import { ButtonHTMLAttributes, CSSProperties } from 'react';
import { styled } from 'styled-components';

import { StyledProps } from '@/types/common/utility';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  text: string;
  kind?: 'primary' | 'secondary';
  fixed?: boolean;
  style?: CSSProperties;
  disabled?: boolean;
}

const Button = (buttonProps: ButtonProps) => {
  const { text, onClick, kind = 'primary', fixed, style, disabled, ...restProps } = buttonProps;

  return (
    <ButtonOuter $fixed={fixed}>
      <ButtonWrapper
        onClick={onClick}
        $kind={kind}
        $fixed={fixed}
        style={style}
        $disabled={disabled}
        disabled={disabled}
        {...restProps}
      >
        {text}
      </ButtonWrapper>
    </ButtonOuter>
  );
};

export default Button;

interface ButtonStyleProps extends StyledProps<Omit<ButtonProps, 'onClick' | 'text'>> {}

const ButtonOuter = styled.div<ButtonStyleProps>`
  position: ${({ $fixed }) => ($fixed ? 'fixed' : 'inherit')};

  ${({ $fixed, theme }) =>
    $fixed &&
    `
    z-index: 10;
    width: 100%;
    bottom: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: ${theme.color.white};
    padding: 0 1.6rem;
  `}
`;

const ButtonWrapper = styled.button<ButtonStyleProps>`
  cursor: ${({ $disabled }) => ($disabled ? 'not-allowed' : 'pointer')};

  width: 100%;
  height: 5.1rem;

  font-size: 1.6rem;
  font-weight: 700;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: 0.02rem;

  background-color: ${({ $kind, theme, $disabled }) => {
    if ($disabled) {
      return theme.color.grey300;
    }

    if ($kind === 'primary') {
      return theme.color.primary;
    }

    return theme.color.blue;
  }};
  border: none;
  border-radius: 16px;
  box-shadow: 0 -8px 20px #fff;

  transition: all 100ms ease-in-out;

  ${({ $disabled }) =>
    !$disabled &&
    ` &:active {
            scale: 0.98;
        }
    `}

  ${({ $fixed }) =>
    $fixed &&
    `
    margin-bottom: 4rem;
  `}
`;
