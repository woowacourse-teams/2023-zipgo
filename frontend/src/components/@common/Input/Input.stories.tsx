import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import Input from './Input';

const meta = {
  title: '@Common/Input',
  component: Input,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ width: '100%', height: '68px', padding: '2rem' }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof Input>;

export default meta;
type Story = StoryObj<typeof Input>;

export const DefaultInput: Story = {
  args: {},
};

export const InvalidDefaultInput: Story = {
  args: {
    isValid: false,
  },
};

export const DisabledDefaultInput: Story = {
  args: {
    isValid: false,
    disabled: true,
    value: 'Disabled Default Input',
  },
};

export const UnderlineInput: Story = {
  args: {
    design: 'underline',
  },
};

export const InvalidUnderlineInput: Story = {
  args: {
    design: 'underline',
    isValid: false,
  },
};
