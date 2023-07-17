import { expect } from '@storybook/jest';
import type { Meta, StoryObj } from '@storybook/react';
import { userEvent, within } from '@storybook/testing-library';
import React from 'react';

import { Button } from './Button';

/** @todo Global args */
/** @todo Mapping to complex arg values */

// More on how to set up stories at: https://storybook.js.org/docs/react/writing-stories/introduction
const meta = {
  title: 'Example/Atom/Button',
  component: Button,
  tags: ['autodocs'],
  argTypes: {
    label: {
      options: ['Normal', 'Bold', 'Italic'],
      mapping: {
        Bold: <b>Bold</b>,
        Italic: <i style={{ color: 'red' }}>이탤릭!</i>,
      },
      if: { arg: 'primary', truthy: false },
    },
    onClick: { action: 'clicked!' },
    primary: {
      options: [true, false],
    },
  },

  parameters: {
    controls: { sort: 'requiredFirst' },
    actions: {
      handles: ['mouseenter'],
    },
    backgrounds: {
      default: 'twitter',
      values: [
        { name: 'twitter', value: '#00aced' },
        { name: 'facebook', value: '#3b5998' },
      ],
    },
  },
  decorators: [
    (Story, { parameters }) => (
      <div style={{ margin: '3em' }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof Button>;

export default meta;
type Story = StoryObj<typeof Button>;

// More on writing stories with args: https://storybook.js.org/docs/react/writing-stories/args
export const Primary: Story = {
  args: {
    label: 'Hello',
    primary: true,
  },
  // name: 'I am the primary',
  render: () => <Button label="hu5" />,
  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    // 👇 Simulate interactions with the component
    await userEvent.type(canvas.getByTestId('email'), 'email@provider.com');

    // await userEvent.type(canvas.getByTestId('password'), 'a-random-password');

    // See https://storybook.js.org/docs/react/essentials/actions#automatically-matching-args to learn how to setup logging in the Actions panel
    // await userEvent.click(canvas.getByRole('button'));

    // 👇 Assert DOM structure
    await expect(canvas.getByTestId('email')).toHaveValue('email@provider.com');
  },
};

export const Secondary: Story = {
  args: {
    ...Primary.args,
    label: 'Secondary',
  },
  render: ({ label, ...props }) => <Button label={label} {...props} />,
};

export const Large: Story = {
  args: {
    size: 'large',
    label: 'Button',
  },
  decorators: [
    Story => (
      <div style={{ background: 'red' }}>
        <Story />
      </div>
    ),
  ],
};

export const Small: Story = {
  args: {
    size: 'small',
    label: 'Button',
    title: 'hihi',
  },
  loaders: [
    async () => ({
      todo: await (await fetch('https://jsonplaceholder.typicode.com/todos/1')).json(),
    }),
  ],
  parameters: {},
  render: (args, { loaded: { todo } }) => <Button {...args} {...todo} />,
};
