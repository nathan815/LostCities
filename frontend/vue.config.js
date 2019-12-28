const path = require('path');

const FRONTEND_PORT = 8088;
const BACKEND_PORT = 8089;
const BACKEND_URL = `http://localhost:${BACKEND_PORT}/`;

module.exports = {
    lintOnSave: false,
    devServer: {
        port: FRONTEND_PORT,
        proxy: {
            // proxy all requests starting with /api to backend api
            '/api': {
                target: BACKEND_URL,
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
