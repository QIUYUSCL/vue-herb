import axios from 'axios';

// 创建 axios 实例
const service = axios.create({
    // 使用代理路径
    baseURL: '/api',
    timeout: 5000 // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
    config => {
        // 可在此添加请求头，如 token
        return config;
    },
    error => {
        console.error('请求出错:', error);
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    response => {
        // 可在此处理响应数据
        return response.data;
    },
    error => {
        console.error('响应出错:', error);
        return Promise.reject(error);
    }
);

export default service;