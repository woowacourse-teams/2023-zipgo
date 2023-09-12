export const REVIEW_SUMMARY_KEYWORDS = {
  rating: '평점',
  tastePreference: '기호성',
  stoolCondition: '대변상태',
  adverseReaction: '이상반응',
} as const;

export const REACTIONS = {
  TASTE_PREFERENCE: '기호성',
  STOOL_CONDITION: '대변상태',
  ADVERSE_REACTION: '이상반응',
  NONE: '없어요',
} as const;

export const COMMENT_VISIABLE_LINE_LIMIT = 180;
export const PROFILE_DEFAULT_IMG_URL =
  'https://github.com/woowacourse-teams/2023-zipgo/assets/24777828/936f3f87-8c5c-4b0f-acc4-f260f3311659';

export const TASTE_PREFERENCES = [
  '전혀 안 먹어요',
  '잘 안 먹어요',
  '잘 먹는 편이에요',
  '정말 잘 먹어요',
] as const;

export const STOOL_CONDITIONS = [
  '잘 모르겠어요',
  '딱딱해요',
  '설사를 해요',
  '촉촉 말랑해요',
] as const;

export const ADVERSE_REACTIONS = [
  '없어요',
  '털이 푸석해요',
  '먹고 토해요',
  '눈물이 나요',
  '몸을 긁어요',
  '발을 핥아요',
] as const;

export const SATISFACTION_MESSAGES = [
  '',
  '발전의 여지가 있어요!',
  '잠재력을 가지고 있어요!',
  '괜찮은데 더 좋아질 수 있어요!',
  '정말 좋았어요!',
  '만족도 최고예요!🔥',
] as const;

export const COMMENT_LIMIT = 255;
export const REVIEW_ERROR_MESSAGE = {
  INVALID_COMMENT: `리뷰는 ${COMMENT_LIMIT}자 이하로 작성해주세요!`,
} as const;
