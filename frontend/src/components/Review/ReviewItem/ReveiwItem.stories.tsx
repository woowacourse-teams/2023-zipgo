import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';
import { reactRouterParameters } from 'storybook-addon-react-router-v6';

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
  parameters: {
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { petFoodId: '1' },
      },
      routing: { path: '/pet-food/:petFoodId' },
    }),
  },
} satisfies Meta<typeof ReviewItem>;

export default meta;
type Story = StoryObj<typeof ReviewItem>;

export const Basic: Story = {
  args: {
    id: 1,
    rating: 4,
    date: '2023-05-26',
    comment:
      '우리집 강아지 사료 개좋아함 폼 미쳤음 못먹는 음식도 없고 식성도 개쩜 ㄹㅇ;;; 강아지가 잘 못먹는 사료도 있나요?? 다이어트 좀 시켜야 할 것 같은데 괜찮은 사료 있으면 추천좀 해주세요! 우리집 강아지 사료 개좋아함 폼 미쳤음 못먹는 음식도 없고 식성도 개쩜 ㄹㅇ;;; 강아지가 잘 못먹는 사료도 있나요?? 다이어트 좀 시켜야 할 것 같은데 괜찮은 사료 있으면 추천좀 해주세요!',
    tastePreference: '정말 잘 먹어요',
    stoolCondition: '촉촉 말랑해요',
    adverseReactions: [],
    petProfile: {
      id: 1,
      name: '하이브리드 샘이솟아 리오레이비',
      profileUrl:
        'https://velog.velcdn.com/images/chex/post/4d4a31d6-a3d9-4acd-8065-2486360eb8d2/image.JPG',
      writtenAge: 7,
      writtenWeight: 100,
      breed: {
        name: '믹스견',
        size: { name: '대형견' },
      },
    },
    helpfulReaction: {
      count: 5,
      reacted: true,
    },
  },
};

export const LongComment: Story = {
  args: {
    id: 2,
    rating: 5,
    date: '2023-06-26',
    comment:
      '제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요!',
    tastePreference: '정말 잘 먹어요',
    stoolCondition: '촉촉 말랑해요',
    adverseReactions: ['눈물이 나요', '발을 핥아요'],
    petProfile: {
      id: 1,
      name: '개똥이',
      profileUrl: '',
      writtenAge: 3,
      writtenWeight: 10,
      breed: {
        name: '스피츠',
        size: { name: '중형견' },
      },
    },
    helpfulReaction: {
      count: 5,
      reacted: true,
    },
  },
};

export const ShortComment: Story = {
  args: {
    id: 1,
    rating: 5,
    date: '2023-07-26',
    comment:
      '제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다.',
    tastePreference: '정말 잘 먹어요',
    stoolCondition: '딱딱해요',
    adverseReactions: [],
    petProfile: {
      id: 3,
      name: '아루',
      profileUrl:
        'https://velog.velcdn.com/images/chex/post/1b521233-131a-4b6e-bcf8-ba7eb8395160/image.jpeg',
      writtenAge: 2,
      writtenWeight: 3,
      breed: {
        name: '몰티즈',
        size: { name: '소형견' },
      },
    },
    helpfulReaction: {
      count: 5,
      reacted: true,
    },
  },
};
