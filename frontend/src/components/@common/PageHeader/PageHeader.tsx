import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import BackBtnIcon from '@/assets/svg/back_btn.svg';
import useCurrentScroll from '@/hooks/@common/useCurrentScroll';
import { routerPath } from '@/router/routes';

interface PageHeaderProps {
  onClick?: VoidFunction;
  title?: string;
}

const PageHeader = (pageHeaderProps: PageHeaderProps) => {
  const { onClick, title } = pageHeaderProps;

  const navigate = useNavigate();

  const onClickBackButton = () => {
    onClick ? onClick() : navigate(routerPath.home());
  };

  const scrollPosition = useCurrentScroll({
    element: document.getElementById('mobile') ?? document.body,
  });

  return (
    <HeaderWrapper $scrollPosition={scrollPosition}>
      <BackButtonWrapper
        type="button"
        onClick={onClickBackButton}
        role="button"
        aria-label="뒤로가기"
      >
        <BackBtnImage src={BackBtnIcon} alt="뒤로가기 아이콘" />
      </BackButtonWrapper>
      {title && (
        <Title $scrollPosition={scrollPosition} aria-label={title}>
          {title}
        </Title>
      )}
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
  max-width: ${({ theme }) => theme.maxWidth.mobile};
  height: 8rem;
  padding: 1.6rem;

  background-color: ${({ $scrollPosition }) =>
    `rgba(255,255,255, ${($scrollPosition - 250) / 50})`};
  box-shadow: ${({ $scrollPosition }) =>
    `0 0.2rem 1.4rem 0 rgba(0, 0, 0, ${
      $scrollPosition > 280 ? '0.15' : ($scrollPosition - 250) / 200
    })`};

  transition: all 150ms ease-in-out;
`;

const BackButtonWrapper = styled.button`
  cursor: pointer;

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

  max-width: 60%;
  height: 2rem;
  margin-top: 0.2rem;

  font-size: 1.8rem;
  font-weight: 700;
  color: ${({ theme }) => theme.color.grey700};
  text-overflow: ellipsis;
  letter-spacing: -0.05rem;
  white-space: nowrap;

  opacity: ${({ $scrollPosition }) => ($scrollPosition - 250) / 50};

  transition: all 150ms ease-in-out;
`;
