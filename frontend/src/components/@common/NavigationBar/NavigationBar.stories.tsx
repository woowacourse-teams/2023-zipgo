import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import NavigationBar from './NavigationBar';

const meta = {
  title: '@Common/NavigationBar',
  component: NavigationBar,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ backgroundColor: '#fff', paddingTop: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof NavigationBar>;

export default meta;
type Story = StoryObj<typeof NavigationBar>;

export const Basic: Story = {
  args: {
    navData: [{ title: '상세정보' }, { title: '리뷰 (12)' }],
    navIndex: 0,
    onChangeNav: () => {},
    style: {
      backgroundColor: '#fff',
    },
  },
};

export const ManyMenus: Story = {
  args: {
    navData: [{ title: '상세정보' }, { title: '리뷰 (12)' }, { title: '브랜드' }],
    navIndex: 0,
    onChangeNav: () => {},
    style: {
      backgroundColor: '#fff',
    },
  },
};

export const NotFixedItems: Story = {
  args: {
    navData: [{ title: '영양기준' }, { title: '주원료' }, { title: '브랜드' }, { title: '기능성' }],
    fixedWidth: false,
    navIndex: 0,
    onChangeNav: () => {},
    indicatorColor: '#333D4B',
    style: {
      height: 60,
      backgroundColor: '#fff',
      borderTopLeftRadius: 20,
      borderTopRightRadius: 20,
      border: '1px solid #AFB8C1',
    },
  },
};
