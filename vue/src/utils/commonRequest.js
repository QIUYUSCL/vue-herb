import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';

/**
 * 通用请求函数，用于调用药材、视频、文章相关接口
 * @param {string} type 目标类型，如 'herb', 'video', 'article'
 * @param {string} action 操作类型，如 'selectAll', 'selectById', 'search'
 * @param {object} params 请求参数
 * @returns {Promise} 请求的 Promise 对象
 */
export const commonRequest = async (type, action, params = {}) => {
    let url = '';
    let method = 'get';
    let config = {};

    // 根据不同的 type 和 action 构建请求 URL
    switch (type) {
        case 'herb':
            if (action === 'selectAll') {
                url = '/herb/info/selectAll';
            } else if (action === 'selectById') {
                url = `/herb/info/selectById/${params.id}`;
            } else if (action === 'search') {
                url = '/herb/info/search';
                config.params = { keyword: params.keyword };
            }
            break;
        case 'video':
            if (action === 'selectAll') {
                url = '/video/selectAll';
            } else if (action === 'selectById') {
                url = `/video/selectById/${params.id}`;
            } else if (action === 'search') {
                url = '/video/search';
                config.params = { keyword: params.keyword };
            }
            break;
        case 'article':
            if (action === 'selectAll') {
                url = '/daily/selectAll';
            } else if (action === 'selectById') {
                url = `/daily/selectById/${params.id}`;
            } else if (action === 'search') {
                url = '/daily/search';
                config.params = { keyword: params.keyword };
            }
            break;
        default:
            ElMessage.error('不支持的目标类型');
            return Promise.reject(new Error('不支持的目标类型'));
    }

    try {
        const response = await request[method](url, config);
        if (response.code === "200") {
            return response.data;
        } else {
            ElMessage.error(response.msg || '请求失败');
            return Promise.reject(new Error(response.msg || '请求失败'));
        }
    } catch (error) {
        console.error('通用请求出错:', error);
        ElMessage.error('请求出错，请稍后重试');
        return Promise.reject(error);
    }
};