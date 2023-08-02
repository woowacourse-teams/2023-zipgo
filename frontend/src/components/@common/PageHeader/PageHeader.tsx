import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import BackBtnIcon from '@/assets/svg/back_btn.svg';
import useCurrentScroll from '@/hooks/@common/useCurrentScroll';

interface PageHeaderProps {
  onClick?: VoidFunction;
  title?: string;
}

const PageHeader = (pageHeaderProps: PageHeaderProps) => {
  const { onClick, title } = pageHeaderProps;

  const navigate = useNavigate();

  const onClickBackButton = () => {
    onClick || navigate('/');
  };

  const scrollPosition = useCurrentScroll();

  return (
    <HeaderWrapper $scrollPosition={scrollPosition}>
      <BackButtonWrapper onClick={onClickBackButton}>
        <BackBtnImage src={BackBtnIcon} alt="뒤로가기" />
      </BackButtonWrapper>
      {title && <Title $scrollPosition={scrollPosition}>{title}</Title>}
    </HeaderWrapper>
  );
};

export default PageHeader;

interface BackButtonStyleProps {
  $scrollPosition: number;
  title?: string;
}

const HeaderWrapper = styled.header<BackButtonStyleProps>`
  position: fixed;
  z-index: 1000;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 8rem;
  padding: 1.6rem;

  background-color: ${({ $scrollPosition, theme }) =>
    $scrollPosition > 300 ? theme.color.white : 'transparent'};

  transition: all 150ms ease-in-out;
  ${({ $scrollPosition, theme }) =>
    $scrollPosition > 300 && 'box-shadow: 0px 2px 14px 0px rgba(0, 0, 0, 0.15);'};
`;

const BackButtonWrapper = styled.button`
  position: absolute;
  top: 2rem;
  left: 2rem;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 4rem;
  height: 4rem;

  background-color: transparent;
  border: none;
`;

const BackBtnImage = styled.img`
  width: 3.2rem;
  height: 3.2rem;

  object-fit: cover;
`;

const Title = styled.h1<BackButtonStyleProps>`
  overflow: hidden;
  display: ${({ $scrollPosition }) => ($scrollPosition > 300 ? 'box' : 'none')};

  max-width: 60%;
  height: 2rem;
  margin-top: 0.2rem;

  font-size: 1.8rem;
  font-weight: 700;
  color: ${({ theme }) => theme.color.grey700};
  text-overflow: ellipsis;
  letter-spacing: -0.05rem;
  white-space: nowrap;

  transition: all 150ms ease-in-out;
`;
