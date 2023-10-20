import styled from 'styled-components';

import ZipgoBanner from '@/assets/webp/landing_banner.webp';
import ZipgoBannerAwake from '@/assets/webp/landing_banner_awake.webp';
import Header from '@/components/@common/Header/Header';
import Template from '@/components/@common/Template';
import FilterBottomSheet from '@/components/Food/FilterBottomSheet/FilterBottomSheet';
import FoodList from '@/components/Food/FoodList/FoodList';
import FoodSelectionGuideBanner from '@/components/FoodSelectionGuideBanner/FoodSelectionGuideBanner';
import useEasterEgg from '@/hooks/@common/useEasterEgg';

const Landing = () => (
  <Template staticHeader={Header}>
    <FoodSelectionGuideBanner />
    <Layout>
      <BannerSection>
        <BannerText>
          <TitleContainer>
            <BannerSubTitle>
              사료 선택이 어려운
              <br />
              초보 집사들을 위해
            </BannerSubTitle>
            <BannerTitle>집사의고민</BannerTitle>
          </TitleContainer>
        </BannerText>
        <EasterEggBanner />
      </BannerSection>
      <ListSection>
        <FilterBottomSheet />
        <FoodList />
      </ListSection>
    </Layout>
  </Template>
);

export default Landing;

const EasterEggBanner = () => {
  const { isDogAwake, onTouchDog } = useEasterEgg();

  return (
    <BannerImg
      src={isDogAwake ? ZipgoBannerAwake : ZipgoBanner}
      onClick={onTouchDog}
      alt="집사의고민 배너 이미지"
    />
  );
};

const Layout = styled.div`
  width: 100%;
  height: 100%;
`;

const BannerSection = styled.section`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 24rem;
  padding-top: 8rem;

  background: linear-gradient(45deg, #3e5e8e 0%, #6992c3 100%);
`;

const BannerText = styled.div`
  display: flex;
  align-items: center;

  width: 100%;

  color: white;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.7rem;

  padding-left: 4rem;
`;

const BannerSubTitle = styled.span`
  user-select: none;

  font-size: 1.8rem;
  font-weight: 500;
  color: #f3f3f3;

  -webkit-user-drag: none;
`;

const BannerTitle = styled.span`
  user-select: none;

  flex-grow: 1;

  font-size: 3rem;
  font-weight: 700;
  color: ${({ theme }) => theme.color.white};

  -webkit-user-drag: none;
`;

const BannerImg = styled.img`
  user-select: none;

  width: 21.9rem;
  height: 20.9rem;

  -webkit-user-drag: none;
`;

const ListSection = styled.section`
  padding: 2rem;
`;
