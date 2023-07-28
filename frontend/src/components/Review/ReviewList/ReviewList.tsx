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
      <ReviewAddButton type="button" aria-label="리뷰 추가">
        +
      </ReviewAddButton>
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

const ReviewAddButton = styled.button`
  all: unset;

  cursor: pointer;

  position: absolute;
  right: 1.6rem;
  bottom: 3.2rem;

  display: flex;
  justify-content: center;

  width: 56px;
  height: 56px;

  font-size: 4rem;
  font-weight: bold;
  line-height: 60px;
  color: ${({ theme }) => theme.color.white};
  text-align: center;

  background-color: ${({ theme }) => theme.color.primary};
  border-radius: 50%;
  box-shadow: 0 4px 4px rgb(0 0 0 / 25%);
`;
