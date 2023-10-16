import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';

import theme from '@/styles/theme';
import { StyledProps } from '@/types/common/utility';

interface BasicLabelProps {
  /*
   * 라벨에 들어갈 텍스트를 설정합니다.
   */
  text: string;
  /*
   * 텍스트 왼 쪽에 아이콘을 설정합니다.
   */
  icon?: string;

  // 스타일링

  /*
   * 라벨 배경 색상을 설정합니다.
   */
  backgroundColor?: string;
  /*
   * 라벨 텍스트 색상을 설정합니다.
   */
  textColor?: string;
  /*
   * 라벨 텍스트 폰트 사이즈을 설정합니다.
   */
  fontSize?: number;
  /*
   * 라벨 텍스트 폰트 굵기를 설정합니다.
   */
  fontWeight?: number;
  /*
   * 라벨 가로 사이즈. 설정하지 않으면 기본으로 좌우로 패딩이 들어갑니다.
   */
  width?: number;

  // 버튼 이벤트

  /*
   * 라벨을 클릭했을 때 이벤트. 설정해주면 라벨이 버튼이 됩니다.
   */
  onClick?: VoidFunction;
  /*
   * 라벨이 클릭 된 상태. 라벨의 스타일을 클릭 된 상태로 간단하게 바꿉니다.
   */
  clicked?: boolean;
}

interface NoBorderLabelProps {
  /*
   * 라벨 Border 유무를 설정합니다.
   */
  hasBorder: false;
}

interface HasBorderLabelProps {
  /*
   * 라벨 Border 유무를 설정합니다.
   */
  hasBorder?: true;
  /*
   * 라벨 Border 색상을 설정합니다. 지정하지 않으면 Border가 설정되지 않습니다.
   */
  borderColor?: string;
}

type LabelProps = (
  | (BasicLabelProps & NoBorderLabelProps)
  | (BasicLabelProps & HasBorderLabelProps)
) &
  ComponentPropsWithoutRef<'div'>;

const Label = (labelProps: LabelProps) => {
  const {
    text,
    icon,
    hasBorder = true,
    backgroundColor = theme.color.white,
    textColor = theme.color.primary,
    fontSize = 1.4,
    fontWeight = 500,
    width,
    onClick,
    clicked,
    ...restProps
  } = labelProps;

  return (
    <LabelWrapper
      $hasBorder={hasBorder}
      $borderColor={'borderColor' in labelProps ? labelProps.borderColor : theme.color.primary}
      $backgroundColor={backgroundColor}
      $textColor={textColor}
      $width={width}
      $clicked={clicked}
      onClick={onClick}
      {...restProps}
    >
      <LabelInner>
        {icon && <Icon src={icon} alt="라벨 아이콘" />}
        <LabelText $fontSize={fontSize} $fontWeight={fontWeight}>
          {text}
        </LabelText>
      </LabelInner>
    </LabelWrapper>
  );
};

export default Label;

interface LabelStyleProps
  extends StyledProps<
    Omit<
      BasicLabelProps & HasBorderLabelProps,
      'text' | 'icon' | 'fontSize' | 'fontWeight' | 'hasBorder'
    > & { hasBorder: boolean }
  > {}

interface LabelTextStyleProps extends StyledProps<Pick<LabelProps, 'fontSize' | 'fontWeight'>> {}

const LabelWrapper = styled.div<LabelStyleProps>`
  user-select: none;

  display: flex;

  width: fit-content;
  height: 3rem;

  ${({ $width }) => ($width ? `width: ${$width}rem` : 'padding: 0 1.6rem')};

  color: ${({ $textColor }) => $textColor};

  background-color: ${({ $clicked, theme, $backgroundColor }) =>
    $clicked ? theme.color.blue : $backgroundColor};
  border-radius: 20px;

  ${({ $hasBorder, $clicked, $borderColor }) =>
    $hasBorder &&
    !$clicked &&
    `
        outline: 1px solid ${$borderColor};
        outline-offset: -1px;
    `}

  ${({ onClick }) => onClick && 'cursor: pointer'};
`;

const LabelInner = styled.div`
  display: flex;
  gap: 0.4rem;
  align-items: center;
  justify-content: center;

  width: 100%;
`;

const LabelText = styled.p<LabelTextStyleProps>`
  margin-top: 0.2rem;

  font-size: ${({ $fontSize }) => $fontSize}rem;
  font-weight: ${({ $fontWeight }) => $fontWeight};
  line-height: 2rem;
  letter-spacing: 0.04rem;
`;

const Icon = styled.img`
  max-width: 20px;
  max-height: 20px;

  object-fit: cover;
`;
