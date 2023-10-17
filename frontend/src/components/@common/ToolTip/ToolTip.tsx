import { useState } from 'react';
import { styled } from 'styled-components';

import ToolTipButton from '@/assets/svg/tool_tip_btn.svg';
import { StyledProps } from '@/types/common/utility';

interface ToolTipProps {
  showBubbleOnly?: boolean;
  title: string;
  content: string;
  width?: string;
  direction?: 'top' | 'bottom';
  left?: string;
  edgeLeft?: string;
}

const ToolTip = (props: ToolTipProps) => {
  const {
    showBubbleOnly = false,
    title,
    content,
    width = '30.5rem',
    direction = 'bottom',
    left,
    edgeLeft,
  } = props;

  const [showBubble, setShowBubble] = useState<boolean>(showBubbleOnly);

  const onClickToolTip = () => {
    setShowBubble(prev => !prev);
  };

  return (
    <ToolTipWrapper>
      <BackDrop />
      {!showBubbleOnly && (
        <ToolTipContainer type="button" onClick={onClickToolTip}>
          <img src={ToolTipButton} alt="tooltip" />
        </ToolTipContainer>
      )}
      {showBubble && (
        <BubbleWrapper $width={width} $direction={direction} $left={left} $edgeLeft={edgeLeft}>
          <BubbleTitle>{title}</BubbleTitle>
          <BubbleContent>{content}</BubbleContent>
        </BubbleWrapper>
      )}
    </ToolTipWrapper>
  );
};

export default ToolTip;

interface BubbleWrapperProps
  extends StyledProps<Pick<ToolTipProps, 'width' | 'direction' | 'left' | 'edgeLeft'>> {}

const BackDrop = styled.div`
  position: fixed;
  z-index: 1;
  inset: 0;

  width: 100%;
  height: 100vh;
`;

const ToolTipWrapper = styled.div`
  position: absolute;
`;

const ToolTipContainer = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 2.7rem;
  height: 2.7rem;

  border: none;
  border-radius: 50%;
`;

const BubbleWrapper = styled.div<BubbleWrapperProps>`
  position: absolute;
  z-index: 2;
  ${({ $left }) => ($left ? `left: ${$left};` : 'left: 50%; transform: translateX(-50%);')}

  display: flex;
  flex-direction: column;
  gap: 1.2rem;
  align-items: flex-start;
  justify-content: center;

  ${({ $direction }) => {
    if ($direction === 'top') {
      return 'margin-bottom: 2rem';
    }

    return 'margin-top: 2rem';
  }};

  ${({ $direction }) => $direction === 'top' && 'bottom: 0'};

  padding: 2.4rem;

  background-color: ${({ theme }) => theme.color.grey600};
  border-radius: 20px;

  ${({ $width }) => `width: ${$width}`};

  &::before {
    content: '';

    position: absolute;
    ${({ $direction }) =>
      $direction === 'top' ? 'bottom: -3rem' : 'top: -1rem; margin-top: 2rem'};
    ${({ $edgeLeft }) =>
      $edgeLeft ? `left: ${$edgeLeft};` : 'left: 50%; transform: translateX(-50%);'}

    margin-top: -2rem;

    border-top: 15px solid
      ${({ $direction, theme }) => ($direction === 'top' ? theme.color.grey800 : 'transparent')};
    border-right: 10px solid transparent;
    border-bottom: 15px solid
      ${({ $direction, theme }) => ($direction === 'bottom' ? theme.color.grey800 : 'transparent')};
    border-left: 10px solid transparent;
  }
`;

const BubbleTitle = styled.h2`
  font-size: 1.6rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey200};
  letter-spacing: -0.05rem;
`;

const BubbleContent = styled.p`
  font-size: 1.4rem;
  font-weight: 400;
  line-height: 2.2rem;
  color: ${({ theme }) => theme.color.grey250};
  text-align: left;
  letter-spacing: -0.05rem;
`;
