import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import InfoBlock from './InfoBlock';

const meta = {
  title: '@Common/InfoBlock',
  component: InfoBlock,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ paddingTop: 36, paddingLeft: 20 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof InfoBlock>;

export default meta;
type Story = StoryObj<typeof InfoBlock>;

export const Basic: Story = {
  args: {
    headText: '주원료',
    children: (
      <div>
        <p
          style={{
            fontSize: 17,
            fontWeight: 500,
            color: '#8b95a1',
            letterSpacing: -0.5,
            margin: 0,
          }}
        >
          닭고기, 쌀, 귀리, 보리
        </p>
      </div>
    ),
  },
};
