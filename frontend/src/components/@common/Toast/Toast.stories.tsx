import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import Toast from './Toast';

const meta = {
  title: '@Common/Toast',
  component: Toast,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof Toast>;

export default meta;
type Story = StoryObj<typeof Toast>;

export const Info: Story = {
  args: {
    type: 'info',
    content: '히히',
  },
};

export const Warning: Story = {
  args: {
    type: 'warning',
    content: '경고!',
  },
};

export const Success: Story = {
  args: {
    type: 'success',
    content: '성공!',
  },
};
