import { styled } from 'styled-components';

import { Review } from '@/types/review/client';

import ReviewItem from '../ReviewItem/ReviewItem';

interface ReviewListProps {
  reviewListData: Review[];
}

const ReviewList = (reviewListProps: ReviewListProps) => {
  const { reviewListData } = reviewListProps;

  return (
    <ReviewListContainer>
      {reviewListData.map(review => (
        <ReviewItemWrapper key={review.id}>
          <ReviewItem {...review} />
        </ReviewItemWrapper>
      ))}
    </ReviewListContainer>
  );
};

export default ReviewList;

const ReviewListContainer = styled.ul`
  all: unset;

  width: 100%;
`;

const ReviewItemWrapper = styled.li`
  padding: 2rem;

  list-style: none;

  border-bottom: 1px solid ${({ theme }) => theme.color.grey200};
`;
