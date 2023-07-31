import type { Preview } from '@storybook/react';
import GlobalStyle from '../src/components/@common/GlobalStyle';
import { ThemeProvider } from 'styled-components';
import theme from '../src/styles/theme';
import React from 'react';

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
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <Story />
      </ThemeProvider>
    ),
  ],
};

export default preview;
