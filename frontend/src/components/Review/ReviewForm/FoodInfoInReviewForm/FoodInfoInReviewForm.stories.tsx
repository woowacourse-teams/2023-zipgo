import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import FoodInfoInReviewForm from './FoodInfoInReviewForm';

const meta = {
  title: 'Review/ReviewForm/FoodInfoInReviewForm',
  component: FoodInfoInReviewForm,
  tags: ['autodocs'],
  decorators: [
    Story => (
      <div style={{ width: 300, padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof FoodInfoInReviewForm>;

export default meta;
type Story = StoryObj<typeof FoodInfoInReviewForm>;

export const Score5: Story = {
  args: {
    name: '인스팅트 로 부스트 시니어 독',
    rating: 5,
    imageUrl: 'http://www.instinctpetfood.co.kr/nvk-product/pro_img/rbk_sndog_2.png',
  },
};

export const Score4: Story = {
  args: {
    name: '인스팅트 로 부스트 시니어 독',
    rating: 4,
    imageUrl: 'http://www.instinctpetfood.co.kr/nvk-product/pro_img/rbk_sndog_2.png',
  },
};

export const Score3: Story = {
  args: {
    name: '퓨리나 프로플랜 민감한 장건강 12kg',
    rating: 3,
    imageUrl: 'https://www.purinapetcare.co.kr/data/files/e3469d6a8936e6416891b97bdbd7c708.png',
  },
};

export const Score2: Story = {
  args: {
    name: '퓨리나 프로플랜 스타터 1kg',
    rating: 2,
    imageUrl: 'https://www.purinapetcare.co.kr/data/files/ac7fa46e3ae9db5a46a3f14623cdf826.png',
  },
};

export const Score1: Story = {
  args: {
    name: '인스팅트 로 부스트 시니어 독',
    rating: 1,
    imageUrl: 'http://www.instinctpetfood.co.kr/nvk-product/pro_img/rbk_sndog_2.png',
  },
};
