export const REACTIONS = {
  TASTE_PREFERENCE: '기호성',
  STOOL_CONDITION: '대변상태',
  ADVERSE_REACTION: '이상반응',
  NOTHING: '없어요',
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
  '털이 푸석해요',
  '먹고 토해요',
  '눈물이 나요',
  '몸을 긁어요',
  '발을 핥아요',
] as const;
