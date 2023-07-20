import type { Meta, StoryObj } from '@storybook/react';
import React from 'react';

import FoodItem from './FoodItem';

const meta = {
  title: 'Food/FoodItem',
  component: FoodItem,
  tags: ['autodocs'],
  decorators: [
    (Story, { parameters }) => (
      <div style={{ width: 170, backgroundColor: '#F2F4F6', padding: 10 }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof FoodItem>;

export default meta;
type Story = StoryObj<typeof FoodItem>;

export const Basic: Story = {
  args: {
    id: 0,
    name: '나우프레시 강아지용 프레시 사료',
    imageUrl:
      'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
    purchaseUrl:
      'https://www.coupang.com/vp/products/6080929259?itemId=12642998513&vendorItemId=4116327396&q=%EB%82%98%EC%9A%B0%ED%94%84%EB%A0%88%EC%8B%9C+%EA%B0%95%EC%95%84%EC%A7%80%EC%9A%A9+%EC%82%AC%EB%A3%8C&itemsCount=36&searchId=2d6ab623c99242e388be97d7302d6cfe&rank=1&isAddedCart=',
  },
  decorators: [Story => <Story />],
};

export const LongName: Story = {
  args: {
    id: 0,
    name: '나우프레시 강아지용 익스트림 프레시 프리미엄 S+ 참치로 만든 사료',
    imageUrl:
      'https://t2.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/jG0XnAMBwRw6wS9v-YxfA9PkOnQ.jpg',
    purchaseUrl:
      'https://www.coupang.com/vp/products/6080929259?itemId=12642998513&vendorItemId=4116327396&q=%EB%82%98%EC%9A%B0%ED%94%84%EB%A0%88%EC%8B%9C+%EA%B0%95%EC%95%84%EC%A7%80%EC%9A%A9+%EC%82%AC%EB%A3%8C&itemsCount=36&searchId=2d6ab623c99242e388be97d7302d6cfe&rank=1&isAddedCart=',
  },
  decorators: [Story => <Story />],
};
