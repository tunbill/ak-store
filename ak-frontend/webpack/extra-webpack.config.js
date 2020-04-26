const path = require('path');
const webpack = require('webpack');

const CopyWebpackPlugin = require('copy-webpack-plugin');
const MergeJsonWebpackPlugin = require('merge-jsons-webpack-plugin');

const projectDir = path.join(__dirname, '../../ak');

module.exports = async config => {
  console.log('Project working dir: ' + projectDir);
  const copyPlugin = new CopyWebpackPlugin([
    {
      from: projectDir + '/node_modules/swagger-ui/dist/css',
      to: 'swagger-ui/dist/css'
    },
    {
      from: projectDir + '/node_modules/swagger-ui/dist/lib',
      to: 'swagger-ui/dist/lib'
    },
    {
      from: projectDir + '/node_modules/swagger-ui/dist/swagger-ui.min.js',
      to: 'swagger-ui/dist/swagger-ui.min.js'
    },
    { from: projectDir + '/src/main/webapp/swagger-ui/', to: 'swagger-ui' },
    { from: projectDir + '/src/main/webapp/content/', to: 'content' },
    { from: projectDir + '/src/main/webapp/favicon.ico', to: 'favicon.ico' },
    {
      from: projectDir + '/src/main/webapp/manifest.webapp',
      to: 'manifest.webapp'
    }
  ]);
  const i18nJSON = new MergeJsonWebpackPlugin({
    debug: true,
    output: {
      groupBy: [
        { pattern: './src/i18n/vi/*.json', fileName: '.src/assets/i18n/vi.json' },
        { pattern: './src/i18n/en/*.json', fileName: '.src/assets/i18n/en.json' }
      ]
    }
  });
  config.plugins = config.plugins.concat([copyPlugin, i18nJSON]);

  return config;
};
