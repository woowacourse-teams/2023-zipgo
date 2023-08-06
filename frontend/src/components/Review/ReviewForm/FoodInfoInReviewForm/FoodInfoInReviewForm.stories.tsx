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
    petFoodId: 0,
    rating: 5,
  },
};

export const Score4: Story = {
  args: {
    petFoodId: 0,
    rating: 4,
  },
};

export const Score3: Story = {
  args: {
    petFoodId: 0,
    rating: 3,
  },
};

export const Score2: Story = {
  args: {
    petFoodId: 0,
    rating: 2,
  },
};

export const Score1: Story = {
  args: {
    petFoodId: 0,
    rating: 1,
  },
};
