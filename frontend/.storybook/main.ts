import type { StorybookConfig } from '@storybook/react-webpack5';

import path from 'path';

const devConfig = require('../config/webpack.dev.js');
const prodConfig = require('../config/webpack.prod.js');

const wrapForPnp = <T extends string>(packageName: T): T =>
  path.dirname(require.resolve(path.join(packageName, 'package.json'))) as T;

const config: StorybookConfig = {
  stories: ['../src/**/*.mdx', '../src/**/*.stories.@(js|jsx|ts|tsx)'],
  addons: [
    '@storybook/addon-links',
    '@storybook/addon-essentials',
    '@storybook/preset-create-react-app',
    '@storybook/addon-interactions',
    '@storybook/addon-actions',
    wrapForPnp('@storybook/addon-essentials'),
  ],
  framework: {
    name: wrapForPnp('@storybook/react-webpack5'),
    options: {},
  },
  docs: {
    autodocs: 'tag',
  },
  staticDirs: ['../public'],
  webpackFinal: (config, { configType }) => {
    const custom = configType === 'DEVELOPMENT' ? devConfig : prodConfig;

    return {
      ...config,
      module: {
        rules: custom.module.rules,
      },
      resolve: {
        ...config.resolve,
        ...custom.resolve,
      },
    };
  },
};
export default config;
