import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import NutritionStandardBlock from './NutritionStandardBlock';

const meta = {
  title: 'Food/NutritionStandardBlock',
  component: NutritionStandardBlock,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof NutritionStandardBlock>;

export default meta;
type Story = StoryObj<typeof NutritionStandardBlock>;

export const EU: Story = {
  args: {
    state: '유럽',
    satisfied: false,
  },
};

export const US: Story = {
  args: {
    state: '미국',
    satisfied: false,
  },
};
