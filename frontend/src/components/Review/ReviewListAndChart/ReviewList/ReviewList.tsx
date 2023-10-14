import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import WriteIcon from '@/assets/svg/write_btn.svg';
import { useValidParams } from '@/hooks/@common/useValidParams';
import useValidQueryString from '@/hooks/common/useValidQueryString';
import { useReviewListQuery } from '@/hooks/query/review';
import { routerPath } from '@/router/routes';
import { zipgoLocalStorage } from '@/utils/localStorage';

import ReviewItem from '../../ReviewItem/ReviewItem';

const ReviewList = () => {
  const navigate = useNavigate();
  const { petFoodId } = useValidParams(['petFoodId']);
  const queryString = useValidQueryString(['petSizes', 'ageGroups', 'breeds', 'sortBy']);
  const { reviewList } = useReviewListQuery({
    petFoodId,
    size: 100,
    petSizeId: queryString.petSizes,
    breedId: queryString.breeds,
    ageGroupId: queryString.ageGroups,
    sortById: queryString.sortBy,
  });

  const hasReview = Boolean(reviewList?.length);

  const userInfo = zipgoLocalStorage.getUserInfo();

  const goReviewWrite = () => navigate(routerPath.reviewAddition({ petFoodId }));

  if (!reviewList) return null;

  return (
    <Layout>
      {hasReview ? (
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
      {userInfo?.hasPet && (
        <ReviewAddButton type="button" aria-label="리뷰 작성" onClick={goReviewWrite}>
          <WriteIconImage src={WriteIcon} alt="" />
        </ReviewAddButton>
      )}
    </Layout>
  );
};

const Skeleton = () => (
  <Layout>
    {Array(5)
      .fill('')
      .map((_, i) => (
        <ReviewItemWrapper key={i}>
          <ReviewItem.Skeleton />
        </ReviewItemWrapper>
      ))}
  </Layout>
);

ReviewList.Skeleton = Skeleton;

export default ReviewList;

const Layout = styled.ul`
  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.color.grey200};
  }
`;

const ReviewItemWrapper = styled.li`
  padding: 2rem;

  list-style: none;
`;

const NoReviewText = styled.p`
  margin-top: 6rem;

  font-size: 1.3rem;
  font-weight: 400;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey300};
  text-align: center;
  letter-spacing: -0.05rem;
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
