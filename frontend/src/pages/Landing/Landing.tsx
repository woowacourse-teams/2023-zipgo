import { ChangeEvent, useEffect, useState } from 'react';
import styled from 'styled-components';

import ZipgoBanner from '@/assets/png/landing_banner.png';
import FilterSwitch from '@/components/@common/FilterSwitch/FilterSwitch';
import Header from '@/components/@common/Header/Header';
import Template from '@/components/@common/Template';
import FoodList from '@/components/Food/FoodList/FoodList';
import usePageTitle from '@/hooks/@common/usePageTitle';
import useValidQueryString from '@/hooks/common/useValidQueryString';
import { useFoodListQuery } from '@/hooks/query/food';
import { KeywordEn } from '@/types/food/client';

const Landing = () => {
  const queries = useValidQueryString<KeywordEn>([
    'nutritionStandard',
    'mainIngredients',
    'brands',
    'functionalities',
  ]);

  const { foodList, refetch } = useFoodListQuery(queries);

  usePageTitle('집사의 고민');

  useEffect(() => {
    refetch();
  }, Object.values(queries));

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
        <ToggleBox>
          <ToggleLabel htmlFor="filter">다이어트 특화 식품 보기</ToggleLabel>
        </ToggleBox>
        <ListSection>
          <FoodList foodListData={foodList} />
        </ListSection>
      </Layout>
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

const ToggleBox = styled.div`
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: center;

  width: 36rem;
  height: 10rem;

  background-color: ${({ theme }) => theme.color.white};
  filter: drop-shadow(0 4px 4px rgb(0 0 0 / 25%));
  border: ${({ theme }) => `1px solid ${theme.color.white}`};
  border-radius: 15px;
`;

const ListSection = styled.section`
  padding-top: 5rem;
`;

const ToggleLabel = styled.label`
  margin-left: 1rem;

  font-size: 1.8rem;
  font-weight: 500;
  color: #3c3c32;
  text-align: center;
`;
