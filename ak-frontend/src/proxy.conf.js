const PROXY_CONFIG = [
  {
    context: [
      '/api',
      '/services',
      '/management',
      '/swagger-resources',
      '/v2/api-docs',
      '/h2-console',
      '/auth'
    ],
    target: `http://localhost:8080`,
    secure: false,
    changeOrigin: false
  }
];

module.exports = PROXY_CONFIG;
