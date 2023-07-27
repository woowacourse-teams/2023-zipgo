import type { Meta, StoryObj } from '@storybook/react';

import ReviewList from './ReviewList';

const meta = {
  title: 'Review/ReviewList',
  component: ReviewList,
} satisfies Meta<typeof ReviewList>;

export default meta;
type Story = StoryObj<typeof ReviewList>;

export const Basic: Story = {
  args: {
    reviewListData: [
      {
        id: 1,
        reviewerName: '에디',
        rating: 5,
        date: '2023-05-26',
        comment: '제가 먹어도 맛있네요',
        tastePreference: '정말 잘 먹어요',
        stoolCondition: '촉촉 말랑해요',
        adverseReactions: [],
      },
      {
        id: 2,
        reviewerName: '노아이즈',
        rating: 5,
        date: '2023-07-01',
        comment:
          '제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요!',
        tastePreference: '잘 먹는 편이에요',
        stoolCondition: '잘 모르겠어요',
        adverseReactions: [],
      },
      {
        id: 3,
        reviewerName: '롤로노아 로지',
        rating: 2,
        date: '2023-07-26',
        comment:
          '저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧 저희집 베베가 잘 안 먹어요🦧',
        tastePreference: '잘 안 먹어요',
        stoolCondition: '설사를 해요',
        adverseReactions: ['눈물이 나요', '털이 푸석해요'],
      },
    ],
  },
};
