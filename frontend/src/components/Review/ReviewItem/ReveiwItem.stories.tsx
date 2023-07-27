import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import ReviewItem from './ReviewItem';

const meta = {
  title: 'Review/ReviewItem',
  component: ReviewItem,
  tags: ['autodocs'],
  decorators: [
    Story => (
      <div style={{ width: 400, backgroundColor: 'white', padding: 20 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof ReviewItem>;

export default meta;
type Story = StoryObj<typeof ReviewItem>;

export const Basic: Story = {
  args: {
    id: 1,
    reviewerName: '에디',
    rating: 4,
    date: '2023-05-26',
    comment:
      '우리집 강아지 사료 개좋아함 폼 미쳤음 못먹는 음식도 없고 식성도 개쩜 ㄹㅇ;;; 강아지가 잘 못먹는 사료도 있나요?? 다이어트 좀 시켜야 할 것 같은데 괜찮은 사료 있으면 추천좀 해주세요! 우리집 강아지 사료 개좋아함 폼 미쳤음 못먹는 음식도 없고 식성도 개쩜 ㄹㅇ;;; 강아지가 잘 못먹는 사료도 있나요?? 다이어트 좀 시켜야 할 것 같은데 괜찮은 사료 있으면 추천좀 해주세요!',
    tastePreference: '정말 잘 먹어요',
    stoolCondition: '촉촉 말랑해요',
    adverseReactions: [],
  },
};

export const LongComment: Story = {
  args: {
    id: 1,
    reviewerName: '민무',
    rating: 5,
    date: '2023-06-26',
    comment:
      '제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요!',
    tastePreference: '정말 잘 먹어요',
    stoolCondition: '촉촉 말랑해요',
    adverseReactions: ['눈물이 나요', '발을 핥아요'],
  },
};

export const ShortComment: Story = {
  args: {
    id: 1,
    reviewerName: '가비',
    rating: 5,
    date: '2023-07-26',
    comment:
      '제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다.',
    tastePreference: '정말 잘 먹어요',
    stoolCondition: '딱딱해요',
    adverseReactions: [],
  },
};
