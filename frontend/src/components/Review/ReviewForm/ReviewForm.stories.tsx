import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import ReviewForm from './ReviewForm';

const meta = {
  title: 'Review/ReviewForm',
  component: ReviewForm,
  tags: ['autodocs'],
  decorators: [Story => <Story />],
} satisfies Meta<typeof ReviewForm>;

export default meta;
type Story = StoryObj<typeof ReviewForm>;

export const Basic: Story = {
  args: {
    petFoodId: 1,
    rating: 5,
  },
};
