import { expect } from '@storybook/jest';
import type { Meta, StoryObj } from '@storybook/react';
import { screen, waitFor, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import { reactRouterParameters } from 'storybook-addon-react-router-v6';
import { styled } from 'styled-components';

import PetProfileProvider from '../../../context/petProfile/PetProfileContext';
import PetProfileEditionForm from './PetProfileEditionForm';

const meta = {
  title: 'PetProfile/EditionForm',
  component: PetProfileEditionForm,
  decorators: [
    Story => (
      <PetProfileProvider>
        <Story />
      </PetProfileProvider>
    ),
  ],

  parameters: {
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { petId: '2' },
      },
      routing: { path: '/pets/:petId/edit' },
    }),
  },
} satisfies Meta<typeof PetProfileEditionForm>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: [],
};

export const InvalidPetName: Story = {
  args: [],

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByLabelText('이름 입력');
      expect(element).toBeInTheDocument();
    });

    const petNameInput = canvas.getByLabelText('이름 입력');
    await userEvent.type(petNameInput, '{backspace}{backspace}', { delay: 100 });
    await userEvent.type(petNameInput, '@@', { delay: 100 });

    const nameErrorMessage = canvas.getByText(
      '아이의 이름은 1~10글자 사이의 한글, 영어, 숫자만 입력 가능합니다.',
    );

    expect(nameErrorMessage).toBeVisible();
  },
};

export const InvalidWeight: Story = {
  args: [],

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByLabelText('몸무게 입력');
      expect(element).toBeInTheDocument();
    });

    const petWeightInput = canvas.getByLabelText('몸무게 입력');
    await userEvent.type(petWeightInput, '{backspace}', { delay: 100 });
    await userEvent.type(petWeightInput, '200', { delay: 100 });

    const weightErrorMessage = canvas.getByText(
      '몸무게는 0kg초과, 100kg이하 소수점 첫째짜리까지 입력이 가능합니다.',
    );

    expect(weightErrorMessage).toBeVisible();
  },
};

export const ValidForm: Story = {
  args: [],

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByLabelText('이름 입력');
      expect(element).toBeInTheDocument();
    });

    const petNameInput = canvas.getByLabelText('이름 입력');
    await userEvent.type(petNameInput, '{backspace}{backspace}', { delay: 100 });
    await userEvent.type(petNameInput, '멍멍이', { delay: 100 });

    const petWeightInput = canvas.getByLabelText('몸무게 입력');
    await userEvent.type(petWeightInput, '{backspace}{backspace}{backspace}', { delay: 100 });
    await userEvent.type(petWeightInput, '35.5', { delay: 100 });

    const editButton = canvas.getByText('수정');

    expect(editButton).toBeEnabled();
  },
};

export const InvalidForm: Story = {
  args: [],

  play: async ({ canvasElement }) => {
    const canvas = within(canvasElement);

    await waitFor(() => {
      const element = screen.getByLabelText('이름 입력');
      expect(element).toBeInTheDocument();
    });

    const petNameInput = canvas.getByLabelText('이름 입력');
    await userEvent.type(petNameInput, '{backspace}{backspace}', { delay: 100 });
    await userEvent.type(petNameInput, '멍멍이🐾', { delay: 100 });

    const petWeightInput = canvas.getByLabelText('몸무게 입력');
    await userEvent.type(petWeightInput, '{backspace}{backspace}{backspace}', { delay: 100 });
    await userEvent.type(petWeightInput, '107', { delay: 100 });

    const editButton = canvas.getByText('수정');

    expect(editButton).toBeDisabled();
  },
};
