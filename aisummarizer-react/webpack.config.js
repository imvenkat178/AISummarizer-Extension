import path from 'path';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import CopyPlugin from 'copy-webpack-plugin';
import { fileURLToPath } from 'url';
import { dirname } from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

export default {
  entry: './src/index.js',
  output: {
    filename: 'popup.js',
    path: path.resolve(__dirname, 'dist'),
    clean: true
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-react']
          }
        }
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/popup.html',
      filename: 'popup.html',
      inject: 'body'
    }),
    new CopyPlugin({
      patterns: [
        { from: 'public/manifest.json', to: 'manifest.json' }
        // no icon files referenced here since you don't have them
      ]
    })
  ],
  resolve: {
    extensions: ['.js', '.jsx']
  },
  mode: 'development'
};