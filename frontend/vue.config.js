const path = require('path');

module.exports = {
    lintOnSave: false,
    devServer: {
        port: 8088,
        proxy: {
            // proxy all requests starting with /api to backend api
            '/api': {
                target: 'http://localhost:8080/',
                changeOrigin: true
            }
        }
    },
    configureWebpack: {
        resolve: {
            alias: {
                '~': path.resolve(__dirname, 'src/'),
            },
        },
    },
};