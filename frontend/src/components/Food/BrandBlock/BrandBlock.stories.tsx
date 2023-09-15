import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import BrandBlock from './BrandBlock';

const meta = {
  title: 'Food/BrandBlock',
  component: BrandBlock,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof BrandBlock>;

export default meta;
type Story = StoryObj<typeof BrandBlock>;

export const Basic: Story = {
  args: {
    name: '오리젠',
    imageUrl:
      'https://www.doowonpet.co.kr/wp/wp-content/uploads/2022/08/%EC%98%A4%EB%A6%AC%EC%A0%A0-tm%EB%A1%9C%EA%B3%A0-ex2-4.jpg',
    state: '미국',
    foundedYear: 1920,
    hasResidentVet: true,
    hasResearchCenter: true,
  },
};
