import { useReducer } from 'react';

import { ADVERSE_REACTIONS, STOOL_CONDITIONS, TASTE_PREFERENCES } from '@/constants/review';
import { AdverseReaction, StoolCondition, TastePreference } from '@/types/review/client';
import { PostReviewReq } from '@/types/review/remote';

export const ACTION_TYPES = {
  SET_PET_FOOD_ID: 'setPetFoodId',
  SET_RATING: 'setRating',
  SET_COMMENT: 'setComment',
  SET_TASTE_PREFERENCE: 'setTastePreference',
  SET_STOOL_CONDITION: 'setStoolCondition',
  SET_ADVERSE_REACTIONS: 'setAdverseReactions',
  SET_ADVERSE_REACTIONS_DEFAULT: 'setAdverseReactionsDefault',
} as const;

type Action =
  | { type: 'setPetFoodId'; petFoodId: number }
  | { type: 'setRating'; rating: number }
  | { type: 'setComment'; comment: string }
  | { type: 'setTastePreference'; tastePreference: TastePreference }
  | { type: 'setStoolCondition'; stoolCondition: StoolCondition }
  | { type: 'setAdverseReactionsDefault'; adverseReactions: AdverseReaction[] }
  | { type: 'setAdverseReactions'; adverseReaction: AdverseReaction };

const reducer = (state: PostReviewReq, action: Action): PostReviewReq => {
  if (action.type === ACTION_TYPES.SET_PET_FOOD_ID) {
    return { ...state, petFoodId: action.petFoodId };
  }

  if (action.type === ACTION_TYPES.SET_RATING) return { ...state, rating: action.rating };
  if (action.type === ACTION_TYPES.SET_COMMENT) return { ...state, comment: action.comment };

  if (action.type === ACTION_TYPES.SET_TASTE_PREFERENCE) {
    return { ...state, tastePreference: action.tastePreference };
  }

  if (action.type === ACTION_TYPES.SET_STOOL_CONDITION) {
    return { ...state, stoolCondition: action.stoolCondition };
  }

  if (action.type === ACTION_TYPES.SET_ADVERSE_REACTIONS_DEFAULT) {
    return { ...state, adverseReactions: action.adverseReactions };
  }

  if (action.type === ACTION_TYPES.SET_ADVERSE_REACTIONS) {
    const NO_ADVERSE_REACTION = '없어요';
    const hasAdverseReaction = state.adverseReactions.includes(action.adverseReaction);
    const hasNoAdverseReaction = state.adverseReactions.includes(NO_ADVERSE_REACTION);
    const isCurrentlyNoAdverseReactionSelected = action.adverseReaction === NO_ADVERSE_REACTION;
    const hasOnlyNoAdverseReaction =
      state.adverseReactions.length === 1 && isCurrentlyNoAdverseReactionSelected;
    const hasOnlyOneAdverseReactionWithoutNo =
      state.adverseReactions.length === 1 && !isCurrentlyNoAdverseReactionSelected;

    // 선택해제 - 이미 선택된 필터칩인 경우
    if (hasAdverseReaction) {
      if (hasOnlyNoAdverseReaction) return state; // 없어요만 선택된 경우 선택 해제 불가
      if (hasOnlyOneAdverseReactionWithoutNo) {
        // 1개만 선택된 상태에서 선택 해제하는 경우
        const filteredAdverseReactionsWithNo = state.adverseReactions
          .filter(item => item !== action.adverseReaction) // 해당 칩 선택 해제
          .concat(NO_ADVERSE_REACTION); // 없어요 추가

        return {
          ...state,
          adverseReactions: filteredAdverseReactionsWithNo,
        };
      }

      return {
        ...state,
        adverseReactions: state.adverseReactions.filter(item => item !== action.adverseReaction),
      };
    }

    // 선택 - '없어요'를 선택한 경우 다른 필터칩 선택 해제 및 '없어요' 선택
    if (isCurrentlyNoAdverseReactionSelected) {
      return { ...state, adverseReactions: [action.adverseReaction] };
    }

    // 선택 - '없어요'를 제외한 다른 필터칩을 선택한 경우(없어요 선택O) '없어요' 선택 해제 및 필터칩 선택
    if (hasNoAdverseReaction) {
      const adverseReactionsWithoutNo = [...state.adverseReactions, action.adverseReaction].filter(
        item => item !== NO_ADVERSE_REACTION,
      );

      return { ...state, adverseReactions: adverseReactionsWithoutNo };
    }

    // 선택 - '없어요'를 제외한 다른 필터칩을 선택한 경우(없어요 선택X) 필터칩 선택
    return { ...state, adverseReactions: [...state.adverseReactions, action.adverseReaction] };
  }

  return state;
};

export const useReviewForm = ({ petFoodId, rating }: { petFoodId: number; rating: number }) => {
  const reviewInitialState: PostReviewReq = {
    petFoodId,
    rating,
    comment: '',
    tastePreference: TASTE_PREFERENCES[0],
    stoolCondition: STOOL_CONDITIONS[0],
    adverseReactions: [ADVERSE_REACTIONS[0]],
  };
  const [review, reviewDispatch] = useReducer(reducer, reviewInitialState);

  return {
    review,
    reviewDispatch,
  };
};
