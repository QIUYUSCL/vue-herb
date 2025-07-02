import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';

/**
 * 处理浏览操作
 * @param {string} targetType - 目标类型，如 'HERB', 'VIDEO', 'ARTICLE'
 * @param {number} targetId - 目标 ID
 * @returns {Promise<void>}
 */
export const handleView = async (targetType, targetId) => {
    const userId = parseInt(localStorage.getItem('user_id'));
    console.log('handleView - userId:', userId); // 打印 userId

    if (isNaN(userId)) {
        ElMessage.warning('请先登录');
        console.log('handleView - 用户未登录，操作终止'); // 打印未登录提示
        return;
    }

    try {
        const params = new URLSearchParams();
        params.append('targetType', targetType);
        params.append('targetId', targetId);
        params.append('userId', userId);

        const response = await request.get('/interaction/view', { params });

        if (response.code === "200") {

        }
    } catch (error) {
        ElMessage.error('记录浏览信息失败，请稍后重试');
    }
};

/*/**
 * 处理点赞或收藏操作
 * @param {number} targetId - 目标 ID，如药材 ID、视频 ID 等
 * @param {string} targetType - 目标类型，如 'HERB'、'VIDEO' 等
 * @param {string} actionType - 操作类型，'LIKE' 或 'COLLECT'
 * @param {Object} targetData - 目标数据对象，用于更新点赞或收藏数
 * @returns {Promise<void>}
 */
export const handleInteraction = async (targetId, targetType, actionType, targetData) => {
    const userId = parseInt(localStorage.getItem('user_id'));
    if (isNaN(userId)) {
        ElMessage.warning('请先登录');
        return;
    }

    try {
        const url = targetType === 'HERB' ? '/interaction/likeOrCollect' :
            targetType === 'VIDEO' ? '/interaction/likeOrCollect' :
                targetType === 'ARTICLE' ? '/interaction/likeOrCollect' :
                    null;

        if (!url) {
            throw new Error('不支持的目标类型');
        }

        const params = new URLSearchParams();
        params.append('targetType', targetType);
        params.append('targetId', targetId);
        params.append('userId', userId);
        params.append('actionType', actionType);

        const response = await request.post(url, params);
        if (response.code === "200") {
            if (actionType === 'LIKE') {
                targetData.likes += 1;
                ElMessage.success('点赞成功');
            } else if (actionType === 'COLLECT') {
                targetData.collections += 1;
                ElMessage.success('收藏成功');
            }
        } else {
            ElMessage.error(actionType === 'LIKE' ? '请勿重复点赞' : '请勿重复收藏');
        }
    } catch (error) {
        console.error(`${actionType === 'LIKE' ? '点赞' : '收藏'}请求出错:`, error);
        ElMessage.error(`${actionType === 'LIKE' ? '点赞' : '收藏'}失败，请稍后重试，错误信息：` + error.message);
    }
};

/**
 * 提交评论
 * @param {Object} target - 目标对象，包含视频或文章数据
 * @param {string} targetType - 目标类型，如 'VIDEO', 'ARTICLE'
 * @param {ref} commentContent - 评论内容的响应式引用
 * @param {ref} showCommentInput - 控制评论输入框显示状态的响应式引用
 * @returns {Promise<void>}
 */
export const submitComment = async (target, targetType, commentContent, showCommentInput) => {
    const userId = parseInt(localStorage.getItem('user_id'));
    if (isNaN(userId)) {
        ElMessage.warning('请先登录');
        return;
    }
    if (!commentContent.value.trim()) {
        ElMessage.warning('请输入评论内容');
        return;
    }

    try {
        const commentData = {
            user_id: userId,
            target_type: targetType,
            target_id: target.value[targetType === 'VIDEO' ? 'video_id' : 'article_id'],
            parent_id: 0,
            content: commentContent.value.trim()
        };

        const response = await request.post('/interaction/addComment', commentData);
        if (response.code === '200') {
            ElMessage.success('评论发表成功');
            target.value.comments += 1;
            showCommentInput.value = false;
            commentContent.value = '';
        } else {
            ElMessage.error('评论发表失败');
        }
    } catch (error) {
        ElMessage.error('评论发表失败，请稍后重试');
    }
};

/**
 * 获取评论
 * @param {string} targetType - 目标类型，如 'HERB', 'VIDEO', 'ARTICLE'
 * @param {number} targetId - 目标 ID
 * @param {ref} comments - 用于存储评论的响应式引用
 * @returns {Promise<void>}
 */
export const fetchComments = async (targetType, targetId, comments) => {
    try {
        const response = await request.get('/user/comments/withUserInfo', {
            params: {
                targetType,
                targetId
            }
        });
        if (response.code === "200") {
            comments.value = response.data;
        } else {
            ElMessage.error(response.message);
        }
    } catch (error) {
        ElMessage.error('获取评论记录失败，请稍后重试');
        console.error('获取评论记录出错:', error);
    }
};