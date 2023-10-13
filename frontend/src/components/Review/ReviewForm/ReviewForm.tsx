import { styled } from 'styled-components';

import Label from '@/components/@common/Label/Label';
import {
  ADVERSE_REACTIONS,
  REVIEW_ERROR_MESSAGE,
  STOOL_CONDITIONS,
  TASTE_PREFERENCES,
} from '@/constants/review';
import { REVIEW_ACTION_TYPES } from '@/hooks/review/useReviewForm';
import { ReviewData } from '@/pages/Review/ReviewFormFunnel';

interface ReviewFormProps {
  reviewData: ReviewData;
}

const ReviewForm = (props: ReviewFormProps) => {
  const {
    reviewData: { review, isValidComment, reviewDispatch, onSubmitReview },
  } = props;

  return (
    <ReviewFormContainer onSubmit={onSubmitReview}>
      <DetailReviewContainer>
        <LabelTitle htmlFor="tastePreference">기호성</LabelTitle>
        <LabelContainer role="radiogroup" id="tastePreference">
          {TASTE_PREFERENCES.map(text => (
            <Label
              data-testid="tastePreference"
              role="radio"
              key={text}
              text={text}
              clicked={review.tastePreference === text}
              aria-checked={review.tastePreference === text}
              onClick={() => {
                reviewDispatch({
                  type: REVIEW_ACTION_TYPES.SET_TASTE_PREFERENCE,
                  tastePreference: text,
                });
              }}
            />
          ))}
        </LabelContainer>
      </DetailReviewContainer>
      <DetailReviewContainer>
        <LabelTitle htmlFor="stoolCondition">대변 상태</LabelTitle>
        <LabelContainer role="radiogroup" id="stoolCondition">
          {STOOL_CONDITIONS.map(text => (
            <Label
              data-testid="stoolCondition"
              role="radio"
              key={text}
              text={text}
              clicked={review.stoolCondition === text}
              aria-checked={review.stoolCondition === text}
              onClick={() => {
                reviewDispatch({
                  type: REVIEW_ACTION_TYPES.SET_STOOL_CONDITION,
                  stoolCondition: text,
                });
              }}
            />
          ))}
        </LabelContainer>
      </DetailReviewContainer>
      <DetailReviewContainer>
        <LabelTitle htmlFor="adverseReactions">이상 반응</LabelTitle>
        <LabelContainer id="adverseReactions">
          {ADVERSE_REACTIONS.map(text => (
            <Label
              data-testid="adverseReaction"
              role="checkbox"
              key={text}
              text={text}
              clicked={review.adverseReactions.includes(text)}
              aria-checked={review.adverseReactions.includes(text)}
              onClick={() => {
                reviewDispatch({
                  type: REVIEW_ACTION_TYPES.SET_ADVERSE_REACTIONS,
                  adverseReaction: text,
                });
              }}
            />
          ))}
        </LabelContainer>
      </DetailReviewContainer>
      <DetailReviewContainer>
        <LabelTitle htmlFor="comment">상세한 후기를 써주세요</LabelTitle>
        <DetailReviewText
          id="comment"
          placeholder="제품에 대한 솔직한 리뷰를 남겨주세요. (선택)"
          value={review.comment}
          $isValid={isValidComment}
          onChange={e => {
            reviewDispatch({ type: REVIEW_ACTION_TYPES.SET_COMMENT, comment: e.target.value });
          }}
        />
        <ErrorCaption aria-live="assertive">
          {isValidComment ? '' : REVIEW_ERROR_MESSAGE.INVALID_COMMENT}
        </ErrorCaption>
      </DetailReviewContainer>
      <SubmitButton type="submit" disabled={!isValidComment}>
        작성 완료
      </SubmitButton>
    </ReviewFormContainer>
  );
};

export default ReviewForm;

const ReviewFormContainer = styled.form`
  position: relative;
`;

const LabelTitle = styled.label`
  display: block;

  margin-bottom: 2rem;

  font-size: 2rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.black};
  letter-spacing: -0.5px;
`;

const DetailReviewContainer = styled.div`
  margin-bottom: 3.6rem;
`;

const LabelContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
`;

const DetailReviewText = styled.textarea<{ $isValid: boolean }>`
  resize: none;

  width: 100%;
  min-height: 12rem;
  padding: 1.2rem;

  font-size: 1.6rem;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.grey500};

  border: 1px solid ${({ theme }) => theme.color.grey250};
  border-radius: 8px;

  &:focus {
    border-color: ${({ $isValid }) => ($isValid ? '#d0e6f9' : '#EFA9AF')};
    outline: none;
    box-shadow: ${({ $isValid }) =>
      $isValid ? '0 0 0 3px rgb(141 201 255 / 40%)' : '0 0 0 3px rgb(231 56 70 / 40%)'};
  }
`;

const SubmitButton = styled.button`
  all: unset;

  cursor: pointer;

  position: fixed;
  bottom: 0;
  left: 0;

  width: 100vw;
  height: 9rem;

  font-size: 1.6rem;
  font-weight: 700;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.white};
  text-align: center;
  letter-spacing: 0.2px;

  background-color: ${({ theme }) => theme.color.primary};

  &:disabled {
    cursor: not-allowed;

    background-color: ${({ theme }) => theme.color.grey300};
  }
`;

const ErrorCaption = styled.p`
  min-height: 1.7rem;
  margin-top: 1rem;
  margin-bottom: 10rem;

  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.warning};
  letter-spacing: -0.5px;
`;
