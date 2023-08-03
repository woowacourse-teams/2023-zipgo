import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';

import DogIcon from '@/assets/svg/dog_icon.svg';
import StarRatingDisplay from '@/components/@common/StarRating/StarRatingDisplay/StartRatingDisplay';
import { COMMENT_VISIABLE_LINE_LIMIT, REACTIONS } from '@/constants/review';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { useFoodDetailQuery } from '@/hooks/query/food';
import { useRemoveReviewMutation } from '@/hooks/query/review';
import { Review } from '@/types/review/client';

interface ReviewItemProps extends Review {}

const ReviewItem = (reviewItemProps: ReviewItemProps) => {
  const { name, profileImgUrl } = JSON.parse(
    localStorage.getItem('userInfo') ?? JSON.stringify({ profileImageUrl: null, name: '노아이즈' }),
  );
  const {
    id,
    profileImageUrl = DogIcon,
    reviewerName,
    rating,
    date,
    tastePreference,
    stoolCondition,
    adverseReactions,
    comment,
  } = reviewItemProps;

  const navigate = useNavigate();
  const { petFoodId } = useValidParams(['petFoodId']);
  const { removeReviewMutation } = useRemoveReviewMutation();
  const [isCommentExpanded, setIsCommentExpanded] = useState(false);
  const { foodData } = useFoodDetailQuery({ petFoodId });

  const onClickEditButton = () => {
    navigate(`/pet-food/${petFoodId}/reviews/write`, {
      state: {
        petFoodId: foodData?.id,
        name: foodData?.foodName,
        imageUrl: foodData?.imageUrl,
        isEditMode: true,
        userRating: rating,
        reviewDetail: { reviewId: id, tastePreference, stoolCondition, adverseReactions, comment },
      },
    });
  };

  const onClickRemoveButton = () => {
    confirm('정말 삭제하시곘어요?') && removeReviewMutation.removeReview({ reviewId: id });
  };

  return (
    <div>
      <ReviewHeader>
        <ReviewImageAndNameContainer>
          <ReviewerImageWrapper>
            <ReviewerImage src={profileImageUrl} alt={`${reviewerName} 프로필`} />
          </ReviewerImageWrapper>
          <ReviewerName>{reviewerName}</ReviewerName>
        </ReviewImageAndNameContainer>
        <ButtonContainer>
          {name === reviewerName && (
            <>
              <TextButton type="button" aria-label="리뷰 수정" onClick={onClickEditButton}>
                수정
              </TextButton>
              <TextButton type="button" aria-label="리뷰 삭제" onClick={onClickRemoveButton}>
                삭제
              </TextButton>
            </>
          )}
        </ButtonContainer>
      </ReviewHeader>
      <RatingContainer>
        <StarRatingDisplay rating={rating} size="small" />
        <TimeStamp>{date}</TimeStamp>
      </RatingContainer>
      <Reactions>
        <Reaction key={REACTIONS.TASTE_PREFERENCE}>
          <ReactionTitle>{REACTIONS.TASTE_PREFERENCE}</ReactionTitle>
          <ReactionContent>{tastePreference}</ReactionContent>
        </Reaction>
        <Reaction key={REACTIONS.STOOL_CONDITION}>
          <ReactionTitle>{REACTIONS.STOOL_CONDITION}</ReactionTitle>
          <ReactionContent>{stoolCondition}</ReactionContent>
        </Reaction>
        <Reaction key={REACTIONS.ADVERSE_REACTION}>
          <ReactionTitle>{REACTIONS.ADVERSE_REACTION}</ReactionTitle>
          <ReactionContent>{adverseReactions.join(', ')}</ReactionContent>
        </Reaction>
      </Reactions>
      <Comment isExpanded={isCommentExpanded}>{comment}</Comment>
      {!isCommentExpanded && comment.length > COMMENT_VISIABLE_LINE_LIMIT && (
        <ShowMoreButton
          onClick={() => setIsCommentExpanded(prevIsExpanded => !prevIsExpanded)}
          type="button"
        >
          ..더보기
        </ShowMoreButton>
      )}
    </div>
  );
};

export default ReviewItem;

const ReviewHeader = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: space-between;

  height: 5rem;
  margin-bottom: 1.6rem;
`;

const ReviewImageAndNameContainer = styled.div`
  display: flex;
  align-items: center;
`;

const ReviewerImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 5rem;
  height: 5rem;
  margin-right: 0.8rem;

  border-radius: 50%;
`;

const ReviewerImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
`;

const ReviewerName = styled.p`
  font-size: 1.6rem;
  font-weight: bold;
  color: ${({ theme }) => theme.color.grey500};
`;

const ButtonContainer = styled.div`
  display: flex;
  gap: 0.8rem;
`;

const TextButton = styled.button`
  all: unset;

  cursor: pointer;

  font-size: 1.4rem;
  color: ${({ theme }) => theme.color.grey400};
`;

const RatingContainer = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;

  margin-bottom: 1.2rem;
`;

const TimeStamp = styled.span`
  font-size: 1.1rem;
  color: ${({ theme }) => theme.color.grey400};
`;

const Reactions = styled.ul`
  all: unset;

  display: flex;
  flex-direction: column;
  gap: 0.4rem;

  margin-bottom: 1.6rem;
`;

const Reaction = styled.li`
  all: unset;
`;

const ReactionTitle = styled.span`
  margin-right: 0.8rem;

  font-size: 1.4rem;
  color: ${({ theme }) => theme.color.grey400};
`;

const ReactionContent = styled.span`
  font-size: 1.4rem;
  color: ${({ theme }) => theme.color.grey700};
`;

const Comment = styled.p<{ isExpanded: boolean }>`
  max-height: 10rem;
  margin-bottom: 0.4rem;

  font-size: 1.3rem;
  color: ${({ theme }) => theme.color.grey500};

  ${({ isExpanded }) => {
    if (isExpanded) {
      return css`
        overflow: 'visible';
        display: block;
      `;
    }

    return css`
      overflow: hidden;
      display: -webkit-box;

      text-overflow: ellipsis;
      word-break: break-word;

      -webkit-box-orient: vertical;
      -webkit-line-clamp: 5;
    `;
  }};
`;

const ShowMoreButton = styled.button`
  all: unset;

  cursor: pointer;

  font-size: 1.3rem;
  color: ${({ theme }) => theme.color.grey300};
`;
