import styled from 'styled-components';

import QueryBoundary from '@/components/@common/QueryBoundary';

import AlignSelect from './AlignSelect/AlignSelect';
import FilterDialog from './FilterDialog/FilterDialog';

const ReviewControls = () => (
  <Layout>
    <Description>﹒우리 아이 맞춤 리뷰 보기</Description>
    <ControlsContainer>
      <QueryBoundary>
        <AlignSelect />
        <FilterDialog />
      </QueryBoundary>
    </ControlsContainer>
  </Layout>
);

export default ReviewControls;

const Layout = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  padding: 0 2rem;
`;

const Description = styled.div`
  font-size: 1.3rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey400};
`;

const ControlsContainer = styled.div`
  display: flex;
  gap: 2rem;
`;
