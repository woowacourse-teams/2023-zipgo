/* eslint-disable react/no-array-index-key */
import { useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';

import ToolTipButton from '@/assets/svg/tool_tip_btn.svg';
import Button from '@/components/@common/Button/Button';
import InfoBlock from '@/components/@common/InfoBlock/InfoBlock';
import Label from '@/components/@common/Label/Label';
import NavigationBar from '@/components/@common/NavigationBar/NavigationBar';
import PageHeader from '@/components/@common/PageHeader/PageHeader';
import ToolTip from '@/components/@common/ToolTip/ToolTip';
import BrandBlock from '@/components/Food/BrandBlock/BrandBlock';
import FoodProfile from '@/components/Food/FoodProfile/FoodProfile';
import NutritionStandardBlock from '@/components/Food/NutritionStandardBlock/NutritionStandardBlock';
import ReviewListAndChart from '@/components/Review/ReviewListAndChart/ReviewListAndChart';
import usePageTitle from '@/hooks/@common/usePageTitle';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { useFoodDetailQuery } from '@/hooks/query/food';
import { State } from '@/types/food/client';

const FoodDetail = () => {
  const { petFoodId } = useValidParams(['petFoodId']);
  const { foodData } = useFoodDetailQuery({ petFoodId });

  const navigationRef = useRef<NavigationBar>(null);

  const [pageIndex, setPageIndex] = useState<number>(0);

  if (!foodData) throw new Error('식품 상세 페이지를 불러오지 못했습니다.');

  const { name, hasStandard, primaryIngredients, brand, functionality, purchaseUrl } = foodData;

  usePageTitle(`집사의 고민 - ${name}`);

  const onClickPurchaseButton = () => {
    window.open(purchaseUrl, '_blank');
  };

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'ArrowRight' && pageIndex < 1) {
        navigationRef.current?.snapToNext();
      }

      if (e.key === 'ArrowLeft' && pageIndex > 0) {
        navigationRef.current?.snapToPrev();
      }
    };

    window.addEventListener('keydown', handleKeyDown);
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [pageIndex]);

  const [onStandardTip, setOnStandardTip] = useState<boolean>(false);

  return (
    <>
      <PageHeader title={name} />
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
            <InfoBlock
              headText="기준 충족 여부"
              subChild={
                <StandardToolTip type="button" onClick={() => setOnStandardTip(prev => !prev)}>
                  왜 필요한가요?
                  <img src={ToolTipButton} alt="tooltip" style={{ width: 16, height: 16 }} />
                  {onStandardTip && (
                    <ToolTip
                      showBubbleOnly
                      content="AAFCO(미국 기준), FEDIAF(유럽 기준) 이 두 단체는 반려동물이 필요로하는 하루 최소 요구치를 정해 업계에 알려주고 있어요. 즉 '장기 급여가 가능한 사료'라고 할 수 있어요."
                      title="이 기준들이 왜 필요한가요?"
                      left="-20rem"
                      edgeLeft="23rem"
                    />
                  )}
                </StandardToolTip>
              }
            >
              <NutritionStandard>
                <NutritionStandardBlock state={State.us} satisfied={hasStandard.us} />
                <NutritionStandardBlock state={State.eu} satisfied={hasStandard.eu} />
              </NutritionStandard>
            </InfoBlock>
            <InfoBlock headText="주원료">
              <NutritionText>{`${primaryIngredients.join(', ')}`}</NutritionText>
            </InfoBlock>
            <InfoBlock headText="기능성">
              <FunctionalList>
                {Boolean(functionality.length) ? (
                  functionality.map((functional, index) => <Label key={index} text={functional} />)
                ) : (
                  <NutritionText>특별한 기능성이 없어요.</NutritionText>
                )}
              </FunctionalList>
            </InfoBlock>
            <InfoBlock
              headText="브랜드 정보를 꼭 확인하세요!"
              subChild={<BrandIconInfo>아이콘을 클릭해보세요</BrandIconInfo>}
            >
              <BrandBlock {...brand} />
            </InfoBlock>
          </FoodDetailInfoWrapper>
        )}
        {pageIndex === 1 && <ReviewListAndChart />}
      </FoodDetailWrapper>
      <Button text="구매하러 가기" onClick={onClickPurchaseButton} fixed />
    </>
  );
};

export default FoodDetail;

const FoodDetailWrapper = styled.div`
  padding: 4rem 0 16rem;
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
  flex-wrap: wrap;
  gap: 0.8rem;
`;

const BrandIconInfo = styled.p`
  font-size: 1.2rem;
  font-weight: 400;
  color: ${({ theme }) => theme.color.grey300};
  letter-spacing: -0.05rem;
`;

const StandardToolTip = styled.button`
  display: flex;
  gap: 0.8rem;
  align-items: center;

  font-size: 1.2rem;
  font-weight: 400;
  color: ${({ theme }) => theme.color.grey300};
  letter-spacing: -0.05rem;

  background-color: transparent;
  border: none;
`;
