import axios from 'axios';

export default {
    async authenticate({ username, password }) {
        return await axios.post('/api/authentication', { username, password });
    },
};
