import styled from 'styled-components';

import ZipgoBanner from '@/assets/png/landing_banner.png';
import Header from '@/components/@common/Header/Header';
import LoadingSpinner from '@/components/@common/LoadingSpinner';
import Template from '@/components/@common/Template';
import FilterBottomSheet from '@/components/Food/FilterBottomSheet/FilterBottomSheet';
import FoodList from '@/components/Food/FoodList/FoodList';
import { useInfiniteFoodListScroll } from '@/hooks/food';

const Landing = () => {
  const { foodList, hasNextPage, targetRef, isFetching } = useInfiniteFoodListScroll();

  if (!foodList) return null;

  return (
    <Template staticHeader={Header}>
      <Layout>
        <BannerSection>
          <BannerText>
            <TitleContainer>
              <BannerSubTitle>
                사료 선택이 어려운
                <br />
                초보 집사들을 위해
              </BannerSubTitle>
              <BannerTitle>집사의 고민</BannerTitle>
            </TitleContainer>
          </BannerText>
          <BannerImg src={ZipgoBanner} />
        </BannerSection>
        <ListSection>
          <FilterBottomSheet />
          <FoodList foodListData={foodList} />
          <ObserverTarget
            ref={targetRef}
            aria-label={hasNextPage ? '' : '모든 식품 목록을 불러왔습니다'}
          />
        </ListSection>
      </Layout>
      {isFetching && <LoadingSpinner />}
    </Template>
  );
};

export default Landing;

const Layout = styled.div`
  width: 100%;
  height: 100%;
`;

const BannerSection = styled.section`
  display: flex;
  align-items: center;

  width: 100vw;
  height: 42rem;
  margin-top: -8rem;
  padding-top: 8rem;

  background: linear-gradient(45deg, #3e5e8e 0%, #6992c3 100%);
`;

const BannerText = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;

  width: 100%;

  color: white;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.7rem;
`;

const BannerSubTitle = styled.span`
  font-size: 1.8rem;
  font-weight: 500;
  color: #f3f3f3;
`;

const BannerTitle = styled.span`
  flex-grow: 1;

  font-size: 3rem;
  font-weight: 700;
  color: ${({ theme }) => theme.color.white};
`;

const BannerImg = styled.img`
  width: 21.9rem;
  height: 20.9rem;
`;

const ListSection = styled.section`
  padding: 2rem;
`;

const ObserverTarget = styled.p`
  font-size: 1.4rem;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;
`;
