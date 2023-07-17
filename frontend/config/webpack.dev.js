const path = require('path');
const {merge} = require('webpack-merge');
const common = require('./webpack.common.js');

const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');

module.exports = merge(common, {
  mode: 'development',
  devtool: 'eval-cheap-module-source-map',
  devServer: {
    open: true,
    historyApiFallback: true,
    port: 3000,
  },
  plugins: [new ReactRefreshWebpackPlugin()],
});
