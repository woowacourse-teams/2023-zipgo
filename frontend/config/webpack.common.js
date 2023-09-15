const paths = require('./paths');
const webpackUtils = require('./webpackUtils');
const { getClientEnvironment } = require('./env');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');

const { DefinePlugin } = require('webpack');

const PUBLIC_PATH = '/';

const libListToCopy = ['axios'];

const isDevelopment = process.env.NODE_ENV !== 'production';
const isProduction = process.env.NODE_ENV === 'production';

const env = getClientEnvironment(PUBLIC_PATH);

module.exports = {
  context: __dirname,
  entry: paths.appIndex,
  output: {
    filename: '[name].[chunkhash].js',
    path: paths.appOutput,
    publicPath: PUBLIC_PATH,
    clean: true,
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
        test: /\.(png|svg|jpg|jpeg|gif)$/i,
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
    new DefinePlugin({ ...env.stringified, isDevelopment, isProduction }),
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
