import styled, { css } from 'styled-components';

import FilterSwitch from '@/components/@common/FilterSwitch/FilterSwitch';
import { useCustomReview } from '@/hooks/review/useCustomReview';
import { StyledProps } from '@/types/common/utility';

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
    <Description $skeleton />
    <ControlsContainer>
      <AlignSelect.Skeleton />
      <FilterDialog.Skeleton />
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
  padding: 0 2rem;
`;

const Description = styled.label<StyledProps>`
  display: flex;
  gap: 0.8rem;
  align-items: center;
  justify-content: center;

  min-width: 14.5rem;
  height: 100%;
  height: 3.5rem;

  font-size: 1.3rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey400};

  ${({ theme, $skeleton }) =>
    $skeleton &&
    css`
      ${theme.animation.skeleton}
    `}
`;

const ControlsContainer = styled.div`
  display: flex;
  gap: 2rem;

  height: 100%;
`;
