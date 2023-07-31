import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import FoodDetail from './FoodDetail';

const meta = {
  title: 'pages/FoodDetail',
  component: FoodDetail,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof FoodDetail>;

export default meta;
type Story = StoryObj<typeof FoodDetail>;

export const Basic: Story = {
  args: {},
};
