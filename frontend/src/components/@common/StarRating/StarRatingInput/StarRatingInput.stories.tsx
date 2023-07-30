import type { Meta, StoryObj } from '@storybook/react';
import React, { useState } from 'react';

import StarRatingInput from './StarRatingInput';

const meta: Meta<typeof StarRatingInput> = {
  title: '@common/StarRating/StartRatingInput',
  component: StarRatingInput,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Basic: Story = {
  args: {
    size: 'extra-large',
  },

  render: args => {
    const [rating, setRating] = useState(0);
    const onClickStar = (selectedRating: number) => {
      setRating(selectedRating);
    };

    return <StarRatingInput rating={rating} onClickStar={onClickStar} size={args.size} />;
  },
};
