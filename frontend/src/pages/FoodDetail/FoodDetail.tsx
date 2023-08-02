/* eslint-disable react/no-array-index-key */
import { useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';

import Button from '@/components/@common/Button/Button';
import InfoBlock from '@/components/@common/InfoBlock/InfoBlock';
import Label from '@/components/@common/Label/Label';
import NavigationBar from '@/components/@common/NavigationBar/NavigationBar';
import PageHeader from '@/components/@common/PageHeader/PageHeader';
import BrandBlock from '@/components/Food/BrandBlock/BrandBlock';
import FoodProfile from '@/components/Food/FoodProfile/FoodProfile';
import NutritionStandardBlock, {
  State,
} from '@/components/Food/NutritionStandardBlock/NutritionStandardBlock';
import ReviewList from '@/components/Review/ReviewList/ReviewList';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { useFoodDetailQuery } from '@/hooks/query/food';

const FoodDetail = () => {
  const { petFoodId } = useValidParams(['petFoodId']);
  const { foodData } = useFoodDetailQuery({ petFoodId });

  const navigationRef = useRef<NavigationBar>(null);

  const [pageIndex, setPageIndex] = useState<number>(0);

  if (!foodData) throw new Error('식품 상세 페이지를 불러오지 못했습니다.');

  return (
    <>
      <PageHeader title={foodData.name} />

      <FoodDetailWrapper>
        <FoodProfileContainer>
          <FoodProfile {...foodData} />
        </FoodProfileContainer>

        <NavigationBar
          navData={[{ title: '상세정보' }, { title: '리뷰' }]}
          navIndex={pageIndex}
          onChangeNav={setPageIndex}
          ref={navigationRef}
        />
        {pageIndex === 0 && (
          <FoodDetailInfoWrapper>
            <InfoBlock headText="기존 충족 여부">
              <NutritionStandard>
                <NutritionStandardBlock state={State.us} satisfied={foodData.hasStandard.us} />
                <NutritionStandardBlock state={State.eu} satisfied={foodData.hasStandard.eu} />
              </NutritionStandard>
            </InfoBlock>
            <InfoBlock headText="주원료">
              <NutritionText>
                {`${foodData.proteinSource.primary.map(source => `${source},`)} ${
                  foodData.proteinSource.secondary
                }`}
              </NutritionText>
            </InfoBlock>
            <InfoBlock headText="기능성">
              <FunctionalList>
                {['튼튼', '건강'].map((functional, index) => (
                  <Label key={index} text={functional} />
                ))}
              </FunctionalList>
            </InfoBlock>
            <InfoBlock headText="브랜드 정보를 꼭 확인하세요!">
              <BrandBlock {...foodData.brand} />
            </InfoBlock>
          </FoodDetailInfoWrapper>
        )}
        {pageIndex === 1 && <ReviewList />}
      </FoodDetailWrapper>
      <Button text="구매하러 가기" onClick={() => {}} fixed />
    </>
  );
};

export default FoodDetail;

const FoodDetailWrapper = styled.div`
  padding: 4rem 0 12rem;
`;

const FoodProfileContainer = styled.div`
  padding: 4rem;
`;

const FoodDetailInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3.6rem;

  padding: 2.8rem 2rem;
`;

const NutritionStandard = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
  align-items: center;
`;

const NutritionText = styled.p`
  font-size: 1.7rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.05rem;
`;

const FunctionalList = styled.div`
  display: flex;
  flex-wrap: nowrap;
  gap: 0.8rem;
`;
