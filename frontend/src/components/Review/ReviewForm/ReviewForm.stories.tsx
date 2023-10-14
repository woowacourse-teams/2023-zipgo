import { expect } from '@storybook/jest';
import type { Meta, StoryObj } from '@storybook/react';
import { screen, userEvent, waitFor, within } from '@storybook/testing-library';
import React from 'react';

import { COMMENT_LIMIT } from '../../../constants/review';
import { useReviewForm } from '../../../hooks/review/useReviewForm';
import ReviewForm from './ReviewForm';

const meta = {
  title: 'Review/ReviewForm',
  component: ReviewForm,
  decorators: [Story => <Story />],
} satisfies Meta<typeof ReviewForm>;

export default meta;
type Story = StoryObj<typeof ReviewForm>;

export const Basic: Story = {
  args: {},

  render: args => {
    const reviewData = useReviewForm({
      petFoodId: 1,
      rating: 5,
      isEditMode: false,
      reviewId: 1,
    });

    return <ReviewForm reviewData={reviewData} />;
  },
};

export const ValidForm: Story = {
  args: {},

  render: args => {
    const reviewData = useReviewForm({
      petFoodId: 1,
      rating: 5,
      isEditMode: false,
      reviewId: 1,
    });

    return <ReviewForm reviewData={reviewData} />;
  },

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const textBox = screen.getByRole('textbox');
      expect(textBox).toBeInTheDocument();
    });

    const commentTextarea = canvas.getByRole('textbox');
    const validText = '저희집 에디가 너무 잘먹어요^^';
    await userEvent.type(commentTextarea, validText, { delay: 100 });

    const completeButton = canvas.getByText('작성 완료');

    expect(completeButton).toBeEnabled();
  },
};

export const InvalidForm: Story = {
  args: {},

  render: args => {
    const reviewData = useReviewForm({
      petFoodId: 1,
      rating: 5,
      isEditMode: false,
      reviewId: 1,
    });

    return <ReviewForm reviewData={reviewData} />;
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

export const SingleSelectionTestForTastePreference: Story = {
  args: {},

  render: args => {
    const reviewData = useReviewForm({
      petFoodId: 1,
      rating: 5,
      isEditMode: false,
      reviewId: 1,
    });

    return <ReviewForm reviewData={reviewData} />;
  },

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByText('기호성');
      expect(element).toBeInTheDocument();
    });

    const tastePreferences = canvas.getAllByTestId('tastePreference');
    const checkedTastePreference = tastePreferences[1];

    await userEvent.click(checkedTastePreference, { delay: 100 });

    expect(checkedTastePreference).toHaveAttribute('aria-checked', 'true');

    tastePreferences.forEach(tastePreference => {
      if (tastePreference !== checkedTastePreference) {
        expect(tastePreference).toHaveAttribute('aria-checked', 'false');
      }
    });
  },
};

export const SingleSelectionTestForStoolCondition: Story = {
  args: {},

  render: args => {
    const reviewData = useReviewForm({
      petFoodId: 1,
      rating: 5,
      isEditMode: false,
      reviewId: 1,
    });

    return <ReviewForm reviewData={reviewData} />;
  },

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByText('대변 상태');
      expect(element).toBeInTheDocument();
    });

    const stoolConditions = canvas.getAllByTestId('stoolCondition');
    const checkedStoolCondition = stoolConditions[1];

    await userEvent.click(checkedStoolCondition, { delay: 100 });

    expect(checkedStoolCondition).toHaveAttribute('aria-checked', 'true');

    stoolConditions.forEach(stoolCondition => {
      if (stoolCondition !== checkedStoolCondition) {
        expect(stoolCondition).toHaveAttribute('aria-checked', 'false');
      }
    });
  },
};

export const MultipleSelectionTestForAdverseReaction: Story = {
  args: {},

  render: args => {
    const reviewData = useReviewForm({
      petFoodId: 1,
      rating: 5,
      isEditMode: false,
      reviewId: 1,
    });

    return <ReviewForm reviewData={reviewData} />;
  },

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByText('이상 반응');
      expect(element).toBeInTheDocument();
    });

    const adverseReactions = canvas.getAllByTestId('adverseReaction');

    await userEvent.click(adverseReactions[1], { delay: 100 });
    await userEvent.click(adverseReactions[2], { delay: 100 });

    const checkedAdverseReactions = [adverseReactions[1], adverseReactions[2]];

    adverseReactions.forEach(adverseReaction => {
      if (checkedAdverseReactions.includes(adverseReaction)) {
        expect(adverseReaction).toHaveAttribute('aria-checked', 'true');
      }

      if (!checkedAdverseReactions.includes(adverseReaction)) {
        expect(adverseReaction).toHaveAttribute('aria-checked', 'false');
      }
    });
  },
};

export const NoneButtonDeselectOthersTestForAdverseReaction: Story = {
  args: {},

  render: args => {
    const reviewData = useReviewForm({
      petFoodId: 1,
      rating: 5,
      isEditMode: false,
      reviewId: 1,
    });

    return <ReviewForm reviewData={reviewData} />;
  },

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByText('이상 반응');
      expect(element).toBeInTheDocument();
    });

    const adverseReactions = canvas.getAllByTestId('adverseReaction');
    const noneAdverseReaction = adverseReactions[0];

    await userEvent.click(adverseReactions[1], { delay: 100 });
    await userEvent.click(noneAdverseReaction, { delay: 100 });

    adverseReactions.forEach(adverseReaction => {
      if (noneAdverseReaction === adverseReaction) {
        expect(adverseReaction).toHaveAttribute('aria-checked', 'true');
      }

      if (noneAdverseReaction !== adverseReaction) {
        expect(adverseReaction).toHaveAttribute('aria-checked', 'false');
      }
    });
  },
};
