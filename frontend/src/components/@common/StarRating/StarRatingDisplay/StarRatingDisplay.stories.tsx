import type { Meta, StoryObj } from '@storybook/react';

import StarRatingDisplay from './StartRatingDisplay';

const meta: Meta<typeof StarRatingDisplay> = {
  title: '@common/StarRating/StartRatingDisplay',
  component: StarRatingDisplay,
  tags: ['autodocs'],
  argTypes: {
    rating: {
      control: {
        type: 'number',
        max: 5,
        min: 0,
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Large: Story = {
  args: {
    rating: 5,
    size: 'large',
  },
};

export const Medium: Story = {
  args: {
    rating: 3,
    size: 'medium',
  },
};

export const Small: Story = {
  args: {
    rating: 0,
    size: 'small',
  },
};
