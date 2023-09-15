import { PropsWithChildren } from 'react';
import styled, { css } from 'styled-components';

import QueryBoundary from '@/components/@common/QueryBoundary';
import StarRatingDisplay from '@/components/@common/StarRating/StarRatingDisplay/StartRatingDisplay';
import Tabs from '@/components/@common/Tabs/Tabs';
import { REVIEW_SUMMARY_KEYWORDS } from '@/constants/review';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { useReviewSummaryQuery } from '@/hooks/query/review';
import { ChartInfo, SummaryKeywordsEn } from '@/types/review/client';
import { invariantOf } from '@/utils/invariantOf';
import { translateSummaryKeyword } from '@/utils/review';

const SummaryChart = () => {
  const summaryKeywords = Object.keys(invariantOf(REVIEW_SUMMARY_KEYWORDS));

  return (
    <Tabs defaultValue={summaryKeywords[0]} asChild>
      <ChartContainer>
        <Tabs.List aria-label="리뷰 요약 카테고리 탭 목록" asChild>
          <NavList>
            {summaryKeywords.map(keyword => (
              <Tabs.Trigger key={keyword} value={keyword} asChild>
                {({ selected }) => (
                  <NavItem $selected={selected}>{translateSummaryKeyword({ keyword })}</NavItem>
                )}
              </Tabs.Trigger>
            ))}
          </NavList>
        </Tabs.List>
        {summaryKeywords.map(keyword => (
          <Tabs.Content key={keyword} value={keyword} asChild>
            <SummaryChartContentWrapper>
              <QueryBoundary loadingFallback={<SummaryChartContent.Skeleton />}>
                <SummaryChartContent keyword={keyword} />
              </QueryBoundary>
            </SummaryChartContentWrapper>
          </Tabs.Content>
        ))}
      </ChartContainer>
    </Tabs>
  );
};

export default SummaryChart;

interface SummaryChartContentProps {
  keyword: SummaryKeywordsEn;
}

const SummaryChartContent = (props: SummaryChartContentProps) => {
  const { keyword } = props;
  const { petFoodId } = useValidParams(['petFoodId']);
  const { summaryInfo } = useReviewSummaryQuery({ petFoodId });

  if (!summaryInfo) return null;

  const summaryInfoByKeyword = summaryInfo[keyword];

  if ('average' in summaryInfoByKeyword) {
    return (
      <ChartContentLayout>
        <AverageContainer>
          <Average>{summaryInfoByKeyword.average}</Average>
          <StarRatingDisplay rating={summaryInfoByKeyword.average} />
        </AverageContainer>
        <Chart chartInfo={summaryInfoByKeyword.rating} />
      </ChartContentLayout>
    );
  }

  return (
    <ChartContentLayout>
      <Chart chartInfo={summaryInfoByKeyword} />
    </ChartContentLayout>
  );
};

const Skeleton = () => <ChartContentLayout skeleton />;

SummaryChartContent.Skeleton = Skeleton;

interface ChartProps {
  chartInfo: ChartInfo;
}

const Chart = (props: PropsWithChildren<ChartProps>) => {
  const { chartInfo } = props;

  return (
    <ChartLayout>
      {chartInfo.map(({ name, percentage }) => (
        <Data key={name}>
          <DataName>{name}</DataName>
          <Bar>
            <BarBackground />
            <BarData percentage={percentage} />
          </Bar>
          <Percentage>{percentage}%</Percentage>
        </Data>
      ))}
    </ChartLayout>
  );
};

const ChartLayout = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;

  width: 100%;
  height: 100%;
  padding: 2.5rem;
`;

const Data = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;
  justify-content: space-between;
`;

const DataName = styled.div`
  overflow: hidden;

  max-width: 28%;

  font-size: 1.2rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey500};
  text-align: center;
  text-overflow: ellipsis;
  letter-spacing: -0.5px;
  white-space: nowrap;
`;

const Bar = styled.div`
  position: relative;

  flex-grow: 1;

  max-width: 18rem;
  height: 0.6rem;
  margin-left: auto;

  @media (width >= 500px) {
    max-width: 30rem;
  }
`;

const BarBackground = styled.div`
  position: absolute;

  width: 100%;
  height: 100%;

  background: ${({ theme }) => theme.color.grey100};
  border-radius: 20px;
`;

const BarData = styled.div<{
  percentage: number;
}>`
  position: absolute;

  width: ${({ percentage }) => `${percentage}%`};
  height: 100%;

  background: ${({ theme }) => theme.color.primary};
  border-radius: 20px;
`;

const Percentage = styled.div`
  flex-basis: 2.5rem;
  flex-shrink: 0;

  font-size: 1.2rem;
  font-weight: 500;
  font-style: normal;
  line-height: 12px;
  color: ${({ theme }) => theme.color.grey300};
  text-align: end;
  letter-spacing: -0.5px;
`;

const SummaryChartContentWrapper = styled.div`
  width: 100%;
  padding: 2rem;
`;

const ChartContentLayout = styled.div<{
  skeleton?: boolean;
}>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  max-width: 50rem;
  height: 15rem;
  margin: 0 auto;

  background: ${({ theme }) => theme.color.grey200};
  border-radius: 15px;

  ${({ skeleton }) =>
    skeleton &&
    css`
      background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
      background-size: 200% 100%;

      animation: shimmer 1.5s infinite;
    `}

  @keyframes shimmer {
    to {
      background-position: 200% 0;
    }
  }
`;

const ChartContainer = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  background-color: ${({ theme }) => theme.color.white};
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
`;

const NavList = styled.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 6rem;

  list-style: none;

  border-bottom: 1px solid ${({ theme }) => theme.color.grey250};
`;

const NavItem = styled.button<{
  $selected: boolean;
}>`
  cursor: pointer;

  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;

  height: 100%;
  padding: 0;

  font-size: 1.5rem;
  font-weight: 500;
  font-style: normal;
  line-height: normal;
  color: ${({ theme }) => theme.color.black};
  letter-spacing: -0.5px;

  background-color: transparent;
  border: none;
  outline: none;

  transition: all 200ms ease-in-out;

  ${({ $selected }) =>
    $selected
      ? css`
          border-bottom: 3px solid ${({ theme }) => theme.color.primary};
        `
      : css`
          border-bottom: 3px solid #d9d9d9;
        `}
`;

const AverageContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
  align-items: center;

  width: 50%;
  padding: 2.5rem;
`;

const Average = styled.div`
  font-size: 2.5rem;
  font-weight: 700;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey500};
  text-align: center;
  letter-spacing: -0.5px;
`;
