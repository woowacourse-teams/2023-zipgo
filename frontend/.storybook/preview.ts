import type { Preview } from '@storybook/react';

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
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
};

export default preview;
