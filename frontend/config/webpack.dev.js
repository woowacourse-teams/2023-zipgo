const paths = require('./paths.js');
const { HTTPS } = require('./env.js');
const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const CopyPlugin = require('copy-webpack-plugin');

const fs = require('fs');
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');

module.exports = merge(common, {
  mode: 'development',
  devtool: 'eval-cheap-module-source-map',
  devServer: {
    historyApiFallback: true,
    port: 3000,
    ...(HTTPS
      ? {
          server: {
            type: 'https',
            options: {
              key: fs.readFileSync(paths.localhostKey),
              cert: fs.readFileSync(paths.cert),
            },
          },
        }
      : {}),
  },
  plugins: [
    new ReactRefreshWebpackPlugin(),
    new CopyPlugin({
      patterns: [
        {
          from: paths.msw,
          to: './',
        },
      ],
    }),
  ],
});
