const paths = require('./paths.js');
const {merge} = require('webpack-merge');
const common = require('./webpack.common.js');
const CopyPlugin = require('copy-webpack-plugin');

const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');

module.exports = merge(common, {
  mode: 'development',
  devtool: 'eval-cheap-module-source-map',
  devServer: {
    open: true,
    historyApiFallback: true,
    port: 3000,
  },
  plugins: [
    new ReactRefreshWebpackPlugin(),    
    new CopyPlugin({
      patterns: [
        {
          from: paths.msw, // 복사할 원본 디렉토리
          to: './', // 복사할 대상 디렉토리 (output.path 기준)
        },
      ]
    })
  ],
});
