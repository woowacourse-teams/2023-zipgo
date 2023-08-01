import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import NavigationBar from './NavigationBar';

const meta = {
  title: '@Common/NavigationBar',
  component: NavigationBar,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ backgroundColor: '#f2f2f2', padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof NavigationBar>;

export default meta;
type Story = StoryObj<typeof NavigationBar>;

export const Basic: Story = {
  args: {
    data: [{ title: '상세정보' }, { title: '리뷰 (12)' }],
    index: 0,
  },
};

export const ManyMenus: Story = {
  args: {
    data: [{ title: '상세정보' }, { title: '리뷰 (12)' }, { title: '브랜드' }],
    index: 0,
  },
};

export const NotFixedItems: Story = {
  args: {
    data: [{ title: '영양기준' }, { title: '주원료' }, { title: '브랜드' }, { title: '기능성' }],
    fixedWidth: false,
    index: 0,
    indicatorColor: '#333D4B',
    style: {
      height: 60,
      backgroundColor: '#fff',
      borderTopLeftRadius: 20,
      borderTopRightRadius: 20,
    },
  },
};
