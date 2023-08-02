import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import BackBtnIcon from '../../../assets/svg/back_btn.svg';
import Label from './Label';

const meta = {
  title: '@Common/Label',
  component: Label,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof Label>;

export default meta;
type Story = StoryObj<typeof Label>;

export const Basic: Story = {
  args: {
    text: '라벨',
  },
};

export const LongText: Story = {
  args: {
    text: '집사의고민팀최고에요',
  },
};

export const WithIcon: Story = {
  args: {
    text: '뒤로가기',
    textColor: '#1C1D20',
    borderColor: '#1C1D20',
    icon: BackBtnIcon,
  },
};

export const Satisfied: Story = {
  args: {
    text: '충족',
    backgroundColor: '#D0E6F9',
    hasBorder: false,
    fontSize: 1.3,
    fontWeight: 700,
    width: 6,
  },
};

export const NotSatisfied: Story = {
  args: {
    text: '불충족',
    backgroundColor: '#FFDBDE',
    textColor: '#E73846',
    hasBorder: false,
    fontSize: 1.3,
    fontWeight: 700,
    width: 6,
  },
};

export const LabelButton: Story = {
  args: {
    text: '버튼',
    onClick: () => {},
  },
};

export const ClickedLabelButton: Story = {
  args: {
    text: '눌린 버튼',
    clicked: true,
    onClick: () => {},
  },
};
