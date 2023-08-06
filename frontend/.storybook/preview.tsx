import type { Preview } from '@storybook/react';
import GlobalStyle from '../src/components/@common/GlobalStyle';
import { ThemeProvider } from 'styled-components';
import theme from '../src/styles/theme';
import React from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { initialize, mswDecorator } from 'msw-storybook-addon';
import { withRouter } from 'storybook-addon-react-router-v6';
import handlers from '../src/mocks/handlers';

initialize(); // msw-storybook-addon

const queryClient = new QueryClient();

const customViewports = {
  defaultDevice: {
    name: 'default',
    styles: {
      width: '400px',
      height: '865px',
    },
  },

  iPhoneSE: {
    name: 'iPhone SE',
    styles: {
      width: '375px',
      height: '667px',
    },
  },

  iPhone12Pro: {
    name: 'iPhone 12 Pro',
    styles: {
      width: '390px',
      height: '844px',
    },
  },
};

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^[A-Z].*' },
    msw: {
      handlers: [...handlers],
    },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
    layout: 'fullScreen',
    viewport: {
      viewports: { ...customViewports },
      defaultViewport: 'defaultDevice',
    },
    options: {
      storySort: (a, b) =>
        a.title === b.title ? 0 : a.title.localeCompare(b.title, undefined, { alphabetical: true }),
    },
    docs: {
      autodocs: true,
      defaultName: 'Documentation',
    },
    parameters: {
      controls: { expanded: true },
    },
    presetColors: [{ color: '#ff4785', title: 'Coral' }, 'rgba(0, 159, 183, 1)', '#fe4a49'],
  },

  decorators: [
    Story => (
      <QueryClientProvider client={queryClient}>
        <ThemeProvider theme={theme}>
          <GlobalStyle />
          <Story />
        </ThemeProvider>
      </QueryClientProvider>
    ),
    mswDecorator, // msw-storybook-addon
    withRouter,
  ],
};

export default preview;
