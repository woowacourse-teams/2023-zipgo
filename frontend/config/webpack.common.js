const paths = require('./paths');
const webpackUtils = require('./webpackUtils');
const { getClientEnvironment, isDevelopment, isProduction, isLocal } = require('./env');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');

const { DefinePlugin } = require('webpack');

const PUBLIC_PATH = '/';

const libListToCopy = ['axios'];

const env = getClientEnvironment(PUBLIC_PATH);

/** @type {import('webpack').Configuration} */
module.exports = {
  context: __dirname,
  entry: paths.appIndex,
  output: {
    filename: '[name].[chunkhash].js',
    path: paths.appOutput,
    publicPath: PUBLIC_PATH,
    clean: true,
    assetModuleFilename: 'static/[name].[contenthash][ext]',
  },
  resolve: {
    extensions: paths.moduleFileExtensions.map(e => `.${e}`),
    alias: {
      '@': paths['alias@'],
    },
  },
  externals: webpackUtils.getExternals(libListToCopy),
  module: {
    rules: [
      {
        test: /\.(ts|js)x?$/,
        exclude: /node_modules/,
        use: [
          {
            loader: 'babel-loader',
          },
        ],
      },
      {
        test: /\.(png|gif|svg|jpg|jpeg|webp)$/i,
        type: 'asset/resource',
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)$/i,
        type: 'asset/resource',
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: paths.appHtml,
      hash: true,
      favicon: paths.appFavicon,
    }),
    new DefinePlugin({ ...env.stringified, isDevelopment, isProduction, isLocal }),
    new ForkTsCheckerWebpackPlugin({
      typescript: {
        configFile: paths.appTsConfig,
      },
    }),
    new CopyPlugin({
      patterns: webpackUtils.getLibsToCopyPattern(libListToCopy),
    }),
  ],
  watchOptions: {
    ignored: ['.yarn', '**/node_modules'],
  },
};
