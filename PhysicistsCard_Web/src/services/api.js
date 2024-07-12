import axios from 'axios';

// 创建 axios 实例
const api = axios.create({
    baseURL: 'http://localhost:8080', // 根据你的后端实际 URL 配置
    timeout: 10000,
});

// 添加请求拦截器
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// 添加响应拦截器
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

// 注册用户
export const registerUser = async (data) => {
    const response = await api.post('/register', data);
    return response.data;
};

// 登录用户
export const loginUser = async (data) => {
    const response = await api.post('/login', data);
    return response.data;
};

// 获取用户信息
export const getUserInfo = async () => {
    const response = await api.get('/user');
    return response.data;
};

// 获取悬赏列表
export const getBounties = async () => {
    const response = await api.get('/bounties');
    return response.data;
};

// 创建悬赏
export const createBounty = async (data) => {
    const response = await api.post('/bounties', data);
    return response.data;
};

// 获取悬赏详情
export const getBountyDetail = async (id) => {
    const response = await api.get(`/bounties/${id}`);
    return response.data;
};

// 更新悬赏
export const updateBounty = async (id, data) => {
    const response = await api.put(`/bounties/${id}`, data);
    return response.data;
};

// 删除悬赏
export const deleteBounty = async (id) => {
    const response = await api.delete(`/bounties/${id}`);
    return response.data;
};
