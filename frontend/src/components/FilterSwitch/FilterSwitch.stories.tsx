import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';
import { styled } from 'styled-components';

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
    filterSize: 'small',
  },

  render: args => (
    <Label filterSize={args.filterSize}>
      <FilterSwitch {...args} />
      다이어트 특화 식품 보기
    </Label>
  ),
};

export const Medium: Story = {
  args: {
    filterSize: 'medium',
    backgroundColor: theme.color.secondary,
  },

  render: args => (
    <Label filterSize={args.filterSize}>
      <FilterSwitch {...args} />
      다이어트 특화 식품 보기
    </Label>
  ),
};

export const Large: Story = {
  args: {
    filterSize: 'large',
    backgroundColor: theme.color.success,
  },

  render: args => (
    <Label filterSize={args.filterSize}>
      <FilterSwitch {...args} />
      다이어트 특화 식품 보기
    </Label>
  ),
};

const Label = styled.label<{ filterSize?: 'small' | 'medium' | 'large' }>`
  cursor: pointer;

  display: flex;
  gap: 1rem;
  align-items: center;

  font-size: ${({ filterSize }) => {
    if (filterSize === 'small') return '1.2rem';
    if (filterSize === 'large') return '1.8rem';
    return '1.6rem';
  }};
  font-weight: 700;
  color: ${({ color }) => color || '#333d4b'};
`;
