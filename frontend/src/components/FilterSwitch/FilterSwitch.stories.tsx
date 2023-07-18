import type { Meta, StoryObj } from '@storybook/react';
import React, { useState } from 'react';

import theme from '../../styles/theme';
import FilterSwitch from './FilterSwitch';

const meta: Meta<typeof FilterSwitch> = {
  title: 'Zipgo/FilterSwitch',
  component: FilterSwitch,
  tags: ['autodocs'],
  argTypes: {
    disabled: { control: 'boolean' },
    backgroundColor: {
      control: { type: 'color', presetColors: [theme.color.primary, theme.color.secondary] },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Small: Story = {
  args: {
    labelText: '다이어트 특화 식품 보기',
    filterSize: 'small',
    css: {},
  },

  render: args => {
    const [isActive, setIsActive] = useState(false);

    return <FilterSwitch {...args} isActive={isActive} setIsActive={setIsActive} />;
  },
};

export const Medium: Story = {
  args: {
    labelText: '다이어트 특화 식품 보기',
    filterSize: 'medium',
    css: {},
  },

  render: args => {
    const [isActive, setIsActive] = useState(false);

    return <FilterSwitch {...args} isActive={isActive} setIsActive={setIsActive} />;
  },
};

export const Large: Story = {
  args: {
    labelText: '다이어트 특화 식품 보기',
    filterSize: 'large',
    css: {},
  },

  render: args => {
    const [isActive, setIsActive] = useState(false);

    return <FilterSwitch {...args} isActive={isActive} setIsActive={setIsActive} />;
  },
};
