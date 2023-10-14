import styled from 'styled-components';

import QueryBoundary from '@/components/@common/ErrorBoundary/QueryBoundary/QueryBoundary';
import FilterSwitch from '@/components/@common/FilterSwitch/FilterSwitch';
import SkeletonBar from '@/components/@common/Skeleton/SkeletonBar/SkeletonBar';
import { useCustomReview } from '@/hooks/review/useCustomReview';

import AlignSelect from './AlignSelect/AlignSelect';
import FilterDialog from './FilterDialog/FilterDialog';

const ReviewControls = () => {
  const { checked, onClickCustomReviewButton } = useCustomReview();

  return (
    <Layout>
      <Description>
        ﹒우리 아이 맞춤
        <FilterSwitch checked={checked} onClick={onClickCustomReviewButton} filterSize="small" />
      </Description>
      <ControlsContainer>
        <AlignSelect />
        <FilterDialog />
      </ControlsContainer>
    </Layout>
  );
};

const Skeleton = () => (
  <Layout>
    <SkeletonBar width="13.2rem" />
    <ControlsContainer>
      <SkeletonBar width="11rem" />
      <SkeletonBar width="5.7rem" />
    </ControlsContainer>
  </Layout>
);

ReviewControls.Skeleton = Skeleton;

export default ReviewControls;

const Layout = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 3.5rem;
  padding: 0 2rem;
`;

const Description = styled.label`
  display: flex;
  gap: 0.8rem;
  align-items: center;
  justify-content: center;

  font-size: 1.3rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey400};
`;

const ControlsContainer = styled.div`
  display: flex;
  gap: 2rem;

  height: 100%;
`;
