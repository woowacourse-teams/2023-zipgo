import { styled } from 'styled-components';

import { useValidParams } from '@/hooks/@common/useValidParams';
import useReviewListQuery from '@/hooks/query/review';

import ReviewItem from '../ReviewItem/ReviewItem';

const ReviewList = () => {
  const { petFoodId } = useValidParams(['petFoodId']);
  const { reviewList } = useReviewListQuery({ petFoodId });

  if (!reviewList) throw new Error('리뷰 목록을 불러오지 못했습니다.');

  return (
    <ReviewListContainer>
      {reviewList.map(review => (
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

  position: fixed;
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
