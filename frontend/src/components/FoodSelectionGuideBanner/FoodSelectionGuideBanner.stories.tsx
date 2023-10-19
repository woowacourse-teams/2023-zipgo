import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import FoodSelectionGuideBanner from './FoodSelectionGuideBanner';

const meta = {
  title: 'Food/FoodSelectionGuideBanner',
  component: FoodSelectionGuideBanner,
} satisfies Meta<typeof FoodSelectionGuideBanner>;

export default meta;
type Story = StoryObj<typeof FoodSelectionGuideBanner>;

export const Basic: Story = {
  args: {},
};
