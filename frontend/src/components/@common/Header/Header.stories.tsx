import type { Meta, StoryObj } from '@storybook/react';

import Header from './Header';

const meta: Meta<typeof Header> = {
  title: 'Zipgo/@common/Header',
  component: Header,
  tags: ['autodocs'],
  argTypes: {},
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {},
};
