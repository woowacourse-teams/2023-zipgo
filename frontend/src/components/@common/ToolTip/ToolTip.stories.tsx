import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import ToolTip from './ToolTip';

const meta = {
  title: '@Common/ToolTip',
  component: ToolTip,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof ToolTip>;

export default meta;
type Story = StoryObj<typeof ToolTip>;

export const Basic: Story = {
  args: {
    title: '이 기준들은 뭐죠?',
    content:
      '미국의 AAFCO와 유럽의 FEDIAF의 기준을 이야기해요. 이 두 단체는 일정 주기마다 반려동물이 하루에 필요로 하는 최소 영양소 요구치를 정해서 업계에 알려주는 중요한 역할을 하고 있습니다. 이 기준을 만족했다면 ‘장기 급여가 가능한 사료’라고 볼 수 있습니다.',
    width: '30.3rem',
    direction: 'top',
  },
};

export const ShowBubbleOnly: Story = {
  args: {
    title: '이 기준들은 뭐죠?',
    content:
      '미국의 AAFCO와 유럽의 FEDIAF의 기준을 이야기해요. 이 두 단체는 일정 주기마다 반려동물이 하루에 필요로 하는 최소 영양소 요구치를 정해서 업계에 알려주는 중요한 역할을 하고 있습니다. 이 기준을 만족했다면 ‘장기 급여가 가능한 사료’라고 볼 수 있습니다.',
    width: '30.3rem',
    showBubbleOnly: true,
  },
};
