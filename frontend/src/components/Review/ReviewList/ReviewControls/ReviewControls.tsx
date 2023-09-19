import styled from 'styled-components';

import FilterSwitch from '@/components/@common/FilterSwitch/FilterSwitch';
import QueryBoundary from '@/components/@common/QueryBoundary';
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
        <QueryBoundary loadingFallback={null}>
          <AlignSelect />
          <FilterDialog />
        </QueryBoundary>
      </ControlsContainer>
    </Layout>
  );
};

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
`;
