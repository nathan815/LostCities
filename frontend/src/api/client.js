import axios from 'axios';
import auth from '@/store/modules/auth';

const client = axios.create({
    baseURL: '/api/',
    timeout: 1000,
});

client.interceptors.request.use(function(config) {
    config.headers['Authorization'] = 'Bearer ' + auth.state.auth.token;
    console.log(config.headers['Authorization'])
    return config;
});

export default client;
