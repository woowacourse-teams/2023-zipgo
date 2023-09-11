import { styled } from 'styled-components';

import Label from '@/components/@common/Label/Label';
import { ADVERSE_REACTIONS, STOOL_CONDITIONS, TASTE_PREFERENCES } from '@/constants/review';
import { ACTION_TYPES, useReviewForm } from '@/hooks/review/useReviewForm';

interface ReviewFormProps {
  petFoodId: number;
  rating: number;
  isEditMode?: boolean;
  reviewId?: number;
}

const ReviewForm = (reviewFormProps: ReviewFormProps) => {
  const { petFoodId, rating, isEditMode = false, reviewId = -1 } = reviewFormProps;

  const { review, reviewDispatch, onSubmitReview } = useReviewForm({
    petFoodId,
    rating,
    isEditMode,
    reviewId,
  });

  return (
    <ReviewFormContainer onSubmit={onSubmitReview}>
      <DetailReviewContainer>
        <LabelTitle htmlFor="tastePreference">기호성</LabelTitle>
        <LabelContainer role="radiogroup" id="tastePreference">
          {TASTE_PREFERENCES.map(text => (
            <Label
              role="radio"
              key={text}
              text={text}
              clicked={review.tastePreference === text}
              aria-checked={review.tastePreference === text}
              onClick={() => {
                reviewDispatch({
                  type: ACTION_TYPES.SET_TASTE_PREFERENCE,
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
              role="radio"
              key={text}
              text={text}
              clicked={review.stoolCondition === text}
              aria-checked={review.stoolCondition === text}
              onClick={() => {
                reviewDispatch({
                  type: ACTION_TYPES.SET_STOOL_CONDITION,
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
              role="checkbox"
              key={text}
              text={text}
              clicked={review.adverseReactions.includes(text)}
              aria-checked={review.adverseReactions.includes(text)}
              onClick={() => {
                reviewDispatch({
                  type: ACTION_TYPES.SET_ADVERSE_REACTIONS,
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
          onChange={e => {
            reviewDispatch({ type: ACTION_TYPES.SET_COMMENT, comment: e.target.value });
          }}
        />
      </DetailReviewContainer>
      <SubmitButton type="submit">작성 완료</SubmitButton>
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

const DetailReviewText = styled.textarea`
  resize: none;

  width: 100%;
  min-height: 12rem;
  margin-bottom: 9rem;
  padding: 1.2rem;

  font-size: 1.6rem;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.grey500};

  border: 1px solid ${({ theme }) => theme.color.grey250};
  border-radius: 8px;

  &:focus {
    border-color: #d0e6f9;
    outline: none;
    box-shadow: 0 0 0 3px rgb(141 201 255 / 40%);
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
`;
