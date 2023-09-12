import { expect } from '@storybook/jest';
import type { Meta, StoryObj } from '@storybook/react';
import { screen, waitFor, within } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';
import React from 'react';

import { COMMENT_LIMIT } from '../../../constants/review';
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

export const InvalidForm: Story = {
  args: {
    petFoodId: 1,
    rating: 5,
  },

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const textBox = screen.getByRole('textbox');
      expect(textBox).toBeInTheDocument();
    });

    const commentTextarea = canvas.getByRole('textbox');
    const longText = new Array(COMMENT_LIMIT + 1).fill(1).join('');
    await userEvent.type(commentTextarea, longText, { delay: 5 });

    const completeButton = canvas.getByText('작성 완료');

    expect(completeButton).toBeDisabled();
  },
};
