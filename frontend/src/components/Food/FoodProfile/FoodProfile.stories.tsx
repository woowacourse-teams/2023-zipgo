import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import FoodProfile from './FoodProfile';

const meta = {
  title: 'Food/FoodProfile',
  component: FoodProfile,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof FoodProfile>;

export default meta;
type Story = StoryObj<typeof FoodProfile>;

export const Basic: Story = {
  args: {
    name: '나우프레시 강아지용 프레시 사료',
    imageUrl:
      'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
    brand: {
      name: '오리젠',
    },
    rating: 4.12,
  },
};

export const LongName: Story = {
  args: {
    name: '나우프레시 강아지용 익스트림 프레시 프리미엄 S+ 참치로 만든 사료',
    imageUrl:
      'https://t2.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/jG0XnAMBwRw6wS9v-YxfA9PkOnQ.jpg',
    brand: {
      name: '오리젠',
    },
    rating: 4.12,
  },
};
