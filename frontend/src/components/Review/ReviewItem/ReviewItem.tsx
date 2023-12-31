import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';

import DogIcon from '@/assets/svg/dog_icon.svg';
import ReactedIcon from '@/assets/svg/reacted_icon.svg';
import UnReactedIcon from '@/assets/svg/un_reacted_icon.svg';
import StarRatingDisplay from '@/components/@common/StarRating/StarRatingDisplay/StartRatingDisplay';
import SuspendedImg from '@/components/@common/SuspendedImg/SuspendedImg';
import { getPetAge } from '@/components/PetProfile/PetItem';
import { COMMENT_VISIBLE_LINE_LIMIT, REACTIONS } from '@/constants/review';
import { useValidParams } from '@/hooks/@common/useValidParams';
import { useCheckAuth } from '@/hooks/auth';
import { useRemoveReviewMutation, useToggleHelpfulReactionMutation } from '@/hooks/query/review';
import { routerPath } from '@/router/routes';
import { StyledProps } from '@/types/common/utility';
import { Review } from '@/types/review/client';
import { zipgoLocalStorage } from '@/utils/localStorage';

interface ReviewItemProps extends Review {}

const ReviewItem = (reviewItemProps: ReviewItemProps) => {
  const { isLoggedIn } = useCheckAuth();
  const user = zipgoLocalStorage.getUserInfo();

  const {
    id: reviewId,
    writerId,
    rating,
    date,
    tastePreference,
    stoolCondition,
    adverseReactions,
    comment,
    petProfile: {
      name: petName,
      profileUrl,
      writtenAge,
      writtenWeight,
      breed: {
        name: breedName,
        size: { name: sizeName },
      },
    },
    helpfulReaction: { count, reacted },
  } = reviewItemProps;

  const navigate = useNavigate();
  const { petFoodId } = useValidParams(['petFoodId']);
  const { removeReviewMutation } = useRemoveReviewMutation();
  const [isCommentExpanded, setIsCommentExpanded] = useState(false);

  const isMyReview = user?.id === writerId;

  const { toggleHelpfulReaction } = useToggleHelpfulReactionMutation(reacted);

  const onClickEditButton = () => {
    navigate(routerPath.reviewAddition({ petFoodId }), {
      state: {
        selectedRating: rating,
        isEditMode: true,
        reviewId,
      },
    });
  };

  const onClickRemoveButton = () => {
    confirm('정말 삭제하시겠어요?') && removeReviewMutation.removeReview({ reviewId, petFoodId });
  };

  const onClickHelpfulButton = () => {
    toggleHelpfulReaction({ reviewId, petFoodId });
  };

  return (
    <Layout>
      <ReviewHeader>
        <ReviewImageAndNameContainer>
          <ReviewerImage src={profileUrl || DogIcon} alt={`${petName} 프로필`} />
          <InfoContainer>
            <ReviewerName>{petName}</ReviewerName>
            <InfoDetail>
              {breedName} / {getPetAge(writtenAge)}
            </InfoDetail>
            <InfoDetail>
              {sizeName} / {writtenWeight}kg
            </InfoDetail>
          </InfoContainer>
        </ReviewImageAndNameContainer>
        <ButtonContainer>
          {isMyReview && (
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
          <ReactionContent>{adverseReactions.join(', ') || REACTIONS.NONE}</ReactionContent>
        </Reaction>
      </Reactions>
      <Comment $isExpanded={isCommentExpanded}>{comment}</Comment>
      {!isCommentExpanded && comment.length > COMMENT_VISIBLE_LINE_LIMIT && (
        <ShowMoreButton
          onClick={() => setIsCommentExpanded(prevIsExpanded => !prevIsExpanded)}
          type="button"
        >
          ..더보기
        </ShowMoreButton>
      )}
      {isLoggedIn && !isMyReview && (
        <HelpfulButton reacted={reacted} onClick={onClickHelpfulButton}>
          <HelpfulButtonIcon src={reacted ? ReactedIcon : UnReactedIcon} />
          <span>도움이 돼요</span>
          <span>{count}</span>
        </HelpfulButton>
      )}
    </Layout>
  );
};

const Skeleton = () => (
  <Layout>
    <ReviewHeader>
      <ReviewImageAndNameContainer>
        <ReviewerImage $skeleton />
        <InfoContainer $skeleton />
      </ReviewImageAndNameContainer>
    </ReviewHeader>
    <RatingContainer $skeleton />
    <Reactions $skeleton />
    <Comment $isExpanded={false} $skeleton />
  </Layout>
);

ReviewItem.Skeleton = Skeleton;

export default ReviewItem;

const Layout = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
`;

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
  gap: 1rem;
  align-items: center;
`;

const ReviewerImage = styled(SuspendedImg)<StyledProps>`
  flex-shrink: 0;

  box-sizing: border-box;
  aspect-ratio: 1/1;
  width: 5rem;

  object-fit: cover;
  ${({ theme, $skeleton }) => $skeleton && theme.animation.skeleton}
  border-radius: 50%;
`;

const ReviewerName = styled.p`
  margin-bottom: 0.5rem;

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

const RatingContainer = styled.div<StyledProps>`
  display: flex;
  gap: 0.8rem;
  align-items: center;

  height: 1.6rem;
  margin-bottom: 1.2rem;

  ${({ theme, $skeleton }) =>
    $skeleton &&
    css`
      width: 20rem;

      ${theme.animation.skeleton}
    `};
`;

const TimeStamp = styled.span`
  font-size: 1.1rem;
  color: ${({ theme }) => theme.color.grey400};
`;

const Reactions = styled.ul<StyledProps>`
  all: unset;

  display: flex;
  flex-direction: column;
  gap: 0.4rem;

  height: 5.7rem;
  margin-bottom: 1.6rem;

  ${({ theme, $skeleton }) =>
    $skeleton &&
    css`
      width: 15rem;

      ${theme.animation.skeleton}
    `};
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

const Comment = styled.p<StyledProps<{ isExpanded: boolean }>>`
  max-height: 10rem;
  margin-bottom: 0.4rem;

  font-size: 1.3rem;
  color: ${({ theme }) => theme.color.grey500};

  ${({ $isExpanded }) => {
    if ($isExpanded) {
      return css`
        display: block;

        word-break: break-word;
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

  ${({ theme, $skeleton }) =>
    $skeleton &&
    css`
      height: 10rem;

      ${theme.animation.skeleton}
    `};
`;

const ShowMoreButton = styled.button`
  all: unset;

  cursor: pointer;

  font-size: 1.3rem;
  color: ${({ theme }) => theme.color.grey300};
`;

const HelpfulButtonIcon = styled.img`
  width: 1.5rem;
`;

const HelpfulButton = styled.button<{
  reacted: boolean;
}>`
  display: flex;
  gap: 1rem;
  align-items: center;

  max-width: 14rem;
  margin: 1.6rem 0 0 auto;
  padding: 0.8rem 1.2rem;

  border-radius: 4px;

  & > span {
    padding-top: 0.2rem;

    font-size: 1.3rem;
    font-weight: 500;
    font-style: normal;
  }

  ${({ reacted }) =>
    reacted
      ? css`
          background: #e5f0ff;
          border: 1px solid ${({ theme }) => theme.color.primary};

          & > span {
            color: ${({ theme }) => theme.color.primary};
          }
        `
      : css`
          background: ${({ theme }) => theme.color.white};
          border: 1px solid ${({ theme }) => theme.color.grey300};

          & > span {
            color: ${({ theme }) => theme.color.grey300};
          }
        `}
`;

const InfoDetail = styled.p`
  font-size: 1.2rem;
  font-weight: 500;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.5px;
`;

const InfoContainer = styled.div<StyledProps>`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;

  height: 5.5rem;

  ${({ theme, $skeleton }) =>
    $skeleton &&
    css`
      width: 7rem;

      ${theme.animation.skeleton}
    `}
`;
