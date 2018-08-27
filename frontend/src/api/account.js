import axios from 'axios';

export default {
    async register(data) {
        return await axios.post('/api/register', data);
    }
};