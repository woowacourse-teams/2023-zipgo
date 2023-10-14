import { Suspense } from 'react';
import { styled } from 'styled-components';

import ReviewControls from './ReviewControls/ReviewControls';
import ReviewList from './ReviewList/ReviewList';
import SummaryChart from './SummaryChart/SummaryChart';

const ReviewListAndChart = () => (
  <Layout>
    <SummaryChart />
    <Suspense fallback={<ReviewControls.Skeleton />}>
      <ReviewControls />
    </Suspense>
    <Suspense fallback={<ReviewList.Skeleton />}>
      <ReviewList />
    </Suspense>
  </Layout>
);

export default ReviewListAndChart;

const Layout = styled.div`
  all: unset;

  width: 100%;
`;
