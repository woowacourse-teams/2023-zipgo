import { FormEvent, useEffect, useReducer } from 'react';

import {
  ADVERSE_REACTIONS,
  COMMENT_LIMIT,
  STOOL_CONDITIONS,
  TASTE_PREFERENCES,
} from '@/constants/review';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { AdverseReaction, StoolCondition, TastePreference } from '@/types/review/client';
import { PostReviewReq } from '@/types/review/remote';

import { useAddReviewMutation, useEditReviewMutation, useReviewItemQuery } from '../query/review';

export const REVIEW_ACTION_TYPES = {
  SET_PET_FOOD_ID: 'setPetFoodId',
  SET_RATING: 'setRating',
  SET_COMMENT: 'setComment',
  SET_TASTE_PREFERENCE: 'setTastePreference',
  SET_STOOL_CONDITION: 'setStoolCondition',
  SET_ADVERSE_REACTIONS: 'setAdverseReactions',
  SET_ADVERSE_REACTIONS_DEFAULT: 'setAdverseReactionsDefault',
} as const;

export type ReviewSetAction =
  | { type: 'setPetFoodId'; petFoodId: number }
  | { type: 'setRating'; rating: number }
  | { type: 'setComment'; comment: string }
  | { type: 'setTastePreference'; tastePreference: TastePreference }
  | { type: 'setStoolCondition'; stoolCondition: StoolCondition }
  | { type: 'setAdverseReactionsDefault'; adverseReactions: AdverseReaction[] }
  | { type: 'setAdverseReactions'; adverseReaction: AdverseReaction };

const reducer = (state: PostReviewReq, action: ReviewSetAction): PostReviewReq => {
  if (action.type === REVIEW_ACTION_TYPES.SET_PET_FOOD_ID) {
    return { ...state, petFoodId: action.petFoodId };
  }

  if (action.type === REVIEW_ACTION_TYPES.SET_RATING) return { ...state, rating: action.rating };
  if (action.type === REVIEW_ACTION_TYPES.SET_COMMENT) return { ...state, comment: action.comment };

  if (action.type === REVIEW_ACTION_TYPES.SET_TASTE_PREFERENCE) {
    return { ...state, tastePreference: action.tastePreference };
  }

  if (action.type === REVIEW_ACTION_TYPES.SET_STOOL_CONDITION) {
    return { ...state, stoolCondition: action.stoolCondition };
  }

  if (action.type === REVIEW_ACTION_TYPES.SET_ADVERSE_REACTIONS_DEFAULT) {
    return { ...state, adverseReactions: action.adverseReactions };
  }

  if (action.type === REVIEW_ACTION_TYPES.SET_ADVERSE_REACTIONS) {
    const NO_ADVERSE_REACTION = '없어요';
    const adverseReactionsSet = new Set(state.adverseReactions);
    const hasAdverseReaction = adverseReactionsSet.has(action.adverseReaction);
    const isCurrentlyNoAdverseReactionSelected = action.adverseReaction === NO_ADVERSE_REACTION;
    const hadOnlyOneAdverseReaction = state.adverseReactions.length === 1;

    // 선택해제 - 이미 선택된 필터칩인 경우
    if (hasAdverseReaction) {
      adverseReactionsSet.delete(action.adverseReaction); // 해당 칩 선택 해제

      // 1개만 선택된 상태에서 선택 해제한 경우 '없어요' 선택
      if (hadOnlyOneAdverseReaction) adverseReactionsSet.add(NO_ADVERSE_REACTION);

      return {
        ...state,
        adverseReactions: Array.from(adverseReactionsSet),
      };
    }

    // 선택 - '없어요'를 선택한 경우 '없어요'만 선택
    if (isCurrentlyNoAdverseReactionSelected) {
      return { ...state, adverseReactions: [action.adverseReaction] };
    }

    // 선택 - '없어요'를 제외한 다른 필터칩을 선택한 경우
    adverseReactionsSet.delete(NO_ADVERSE_REACTION); // '없어요' 선택 해제
    adverseReactionsSet.add(action.adverseReaction); // 해당 칩 선택

    return { ...state, adverseReactions: Array.from(adverseReactionsSet) };
  }

  return state;
};

interface UseReviewFormProps {
  petFoodId: number;
  rating: number;
  isEditMode: boolean;
  reviewId: number;
}

export const useReviewForm = (useReviewFormProps: UseReviewFormProps) => {
  const { petFoodId, rating, isEditMode, reviewId } = useReviewFormProps;
  const { petProfile } = usePetProfile();
  const { reviewItem } = useReviewItemQuery({ reviewId });
  const { addReviewMutation } = useAddReviewMutation();
  const { editReviewMutation } = useEditReviewMutation();

  const reviewInitialState: PostReviewReq = {
    petId: petProfile ? petProfile.id : -1,
    petFoodId,
    rating,
    comment: '',
    tastePreference: TASTE_PREFERENCES[0],
    stoolCondition: STOOL_CONDITIONS[0],
    adverseReactions: [ADVERSE_REACTIONS[0]],
  };

  const [review, reviewDispatch] = useReducer(reducer, reviewInitialState);

  const isValidComment = review.comment.length <= COMMENT_LIMIT;

  const onSubmitReview = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (isEditMode && reviewItem) {
      editReviewMutation.editReview({ reviewId, ...review });

      return;
    }

    addReviewMutation.addReview(review);
  };

  useEffect(() => {
    if (isEditMode && reviewItem) {
      reviewDispatch({
        type: REVIEW_ACTION_TYPES.SET_TASTE_PREFERENCE,
        tastePreference: reviewItem.tastePreference,
      });

      reviewDispatch({
        type: REVIEW_ACTION_TYPES.SET_STOOL_CONDITION,
        stoolCondition: reviewItem.stoolCondition,
      });

      reviewDispatch({
        type: REVIEW_ACTION_TYPES.SET_ADVERSE_REACTIONS_DEFAULT,
        adverseReactions: reviewItem.adverseReactions,
      });

      reviewDispatch({ type: REVIEW_ACTION_TYPES.SET_COMMENT, comment: reviewItem.comment });
    }
  }, [isEditMode, reviewItem, reviewDispatch]);

  return {
    review,
    reviewDispatch,
    onSubmitReview,
    isValidComment,
  };
};
