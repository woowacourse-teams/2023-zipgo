import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import Button from './Button';

const meta = {
  title: '@Common/Button',
  component: Button,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ width: '100vw', height: '100vh' }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof Button>;

export default meta;
type Story = StoryObj<typeof Button>;

export const FixedButton: Story = {
  args: {
    text: '여기를 눌러봐요',
    fixed: true,
  },
};

export const DisabledButton: Story = {
  args: {
    text: '품절',
    fixed: true,
    disabled: true,
  },
};

export const NotStatic: Story = {
  args: {
    text: '버튼',
  },
};

export const SecondButton: Story = {
  args: {
    text: '버튼',
    kind: 'secondary',
  },
};
