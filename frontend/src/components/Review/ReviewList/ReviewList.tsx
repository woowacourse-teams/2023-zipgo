import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import WriteIcon from '@/assets/svg/write_btn.svg';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { useFoodDetailQuery } from '@/hooks/query/food';
import { useReviewListQuery } from '@/hooks/query/review';

import ReviewItem from '../ReviewItem/ReviewItem';

const ReviewList = () => {
  const navigate = useNavigate();
  const { petFoodId } = useValidParams(['petFoodId']);
  const { reviewList } = useReviewListQuery({ petFoodId });
  const { foodData } = useFoodDetailQuery({ petFoodId });

  if (!reviewList) throw new Error('리뷰 리스트를 찾을 수 없습니다.');

  return (
    <ReviewListContainer>
      {Boolean(reviewList.length) ? (
        reviewList.map(review => (
          <ReviewItemWrapper key={review.id}>
            <ReviewItem {...review} />
          </ReviewItemWrapper>
        ))
      ) : (
        <NoReviewText>
          아직 리뷰가 없어요.
          <br />
          해당 식품의 첫 번째 리뷰어가 되어보세요!
        </NoReviewText>
      )}
      <ReviewAddButton
        type="button"
        aria-label="리뷰 작성"
        onClick={() =>
          navigate(`/pet-food/${petFoodId}/reviews/write`, {
            state: {
              petFoodId: foodData?.id,
              name: foodData?.foodName,
              imageUrl: foodData?.imageUrl,
            },
          })
        }
      >
        <WriteIconImage src={WriteIcon} alt="" />
      </ReviewAddButton>
    </ReviewListContainer>
  );
};

export default ReviewList;

const NoReviewText = styled.p`
  margin-top: 6rem;

  font-size: 1.3rem;
  font-weight: 400;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey300};
  text-align: center;
  letter-spacing: -0.05rem;
`;

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
  bottom: 12rem;

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
  box-shadow: 0 0 2rem rgb(62 94 142 / 50%);
`;

const WriteIconImage = styled.img`
  border-radius: 50%;
  box-shadow: 0 0 40px rgb(62 94 142 / 50%);
`;
