import styled from 'styled-components';

import CloseSquareIcon from '@/assets/svg/close_square_icon_light.svg';
import WidthPetIcon from '@/assets/svg/with_pet_icon.svg';
import useBoolean from '@/hooks/@common/useBoolean';

const FoodSelectionGuideBanner = () => {
  const [isOpen, , closeBanner] = useBoolean(true);

  if (!isOpen) return null;

  return (
    <BannerWrapper>
      <a
        href="https://translucent-mallet-426.notion.site/dae305b85c8146399d7de6a0e74b773d?pvs=4"
        target="blank"
      >
        <img src={WidthPetIcon} alt="" />
        <span>
          To. 초보집사
          <br />
          우리 아이 먹거리 고르는 꿀팁 보러오세요!
        </span>
      </a>
      <button type="button" aria-label="닫기" onClick={closeBanner}>
        <img src={CloseSquareIcon} alt="" />
      </button>
    </BannerWrapper>
  );
};

export default FoodSelectionGuideBanner;

const BannerWrapper = styled.div`
  user-select: none;

  position: fixed;
  z-index: 100;
  bottom: 2rem;
  left: 50%;
  transform-origin: center center;
  transform: translateX(-50%);

  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  width: 90vw;
  height: 8rem;

  background-color: ${({ theme }) => theme.color.black};
  border: none;
  border-radius: 8px;

  transition: all 0.3s ease-out;

  & a {
    display: flex;
    align-items: center;
    justify-content: space-between;

    width: 100%;
    height: 100%;

    font-size: 2.2rem;
    font-weight: 500;
    line-height: 2.4rem;
    color: ${({ theme }) => theme.color.white};
  }

  & img {
    transform: translateY(12%);

    margin-right: 1.2rem;
  }

  & span {
    width: 100%;
    padding-right: 1.2rem;
  }

  & button {
    cursor: pointer;

    position: absolute;
    top: 0.4rem;
    right: -0.4rem;

    color: ${({ theme }) => theme.color.white};

    background-color: transparent;
    border: none;
    outline: none;
  }

  &:hover::before {
    content: '';

    position: absolute;
    left: 50%;
    transform-origin: center center;
    transform: translateX(-50%);

    display: inline-block;

    width: 80vw;
    height: 8rem;

    opacity: 0;
    background-color: ${({ theme }) => theme.color.white};

    animation: ${({ theme }) => theme.animation.shiny} 2.5s ease-in-out infinite;
  }

  &:active {
    transform: translateX(-50%) scale(0.95);
  }
`;
