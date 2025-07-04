<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <router-link to="/daily" class="hover:text-primary">偏方秘方</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span v-if="article" class="text-gray-800">{{ article.title }}</span>
          <span v-else class="text-gray-800">文章详情</span>
        </div>
      </div>
    </div>
    <div class="container mx-auto px-4 py-8">
      <!-- 返回按钮 -->
      <el-button type="success" round :icon="Back" @click="goBack" class="mb-4" />
      <div v-if="article" class="bg-white p-6 rounded-lg shadow-md">
        <img v-if="article.cover_image" :src="article.cover_image" :alt="article.title" class="w-full h-64 object-cover rounded-lg mb-4">
        <h2 class="text-2xl font-bold text-gray-800 mb-2">{{ article.title }}</h2>
        <div class="flex items-center justify-between text-sm text-gray-600 mb-4">
          <span>{{ formatDate(article.publish_time) }}</span>
          <div class="flex items-center">
            <i class="fa fa-eye mr-1"></i>
            <span>{{ article.views }}</span>
            <i class="fa fa-thumbs-up ml-3 mr-1"></i>
            <span>{{ article.likes }}</span>
          </div>
        </div>
        <!-- 拆分显示文章内容 -->
        <div v-html="formattedContent"></div>
        <div class="flex items-center mb-4">
          <i class="fa fa-eye text-gray-600"></i>
          <span class="ml-1 text-sm text-gray-600">{{ article.views }} 次观看</span>
          <i class="fa fa-thumbs-up text-gray-600 ml-4" @click="handleLike(article.article_id)"></i>
          <span class="ml-1 text-sm text-gray-600">{{ article.likes }} 点赞</span>
          <i class="fa fa-bookmark text-gray-600 ml-4" @click="handleCollect(article.article_id)"></i>
          <span class="ml-1 text-sm text-gray-600">{{ article.collections }} 收藏</span>
          <i class="fa fa-comment text-gray-600 ml-4" @click="showCommentInput = true"></i>
          <span class="ml-1 text-sm text-gray-600">{{ article.comments }} 评论</span>
        </div>
        <div v-if="showCommentInput" class="mt-4">
          <textarea v-model="commentContent" rows="3" class="w-full border border-gray-300 p-2 rounded"></textarea>
          <!-- 使用 flex 布局 -->
          <div class="mt-2 flex justify-between items-center">
            <div>
              <button @click="handleSubmitComment" class="bg-primary text-white p-2 rounded">发表评论</button>
              <button @click="showCommentInput = false" class="ml-2 bg-gray-300 p-2 rounded">取消</button>
            </div>
            <button @click="toggleShowAllComments" class="bg-gray-300 p-2 rounded">
              {{ showAllComments ? '隐藏评论' : '查看所有评论' }}
            </button>
          </div>
        </div>
        <div v-if="showAllComments" class="mt-4">
          <h3 class="text-lg font-bold text-gray-800 mb-2">所有评论</h3>
          <div v-if="comments.length > 0">
            <div v-for="comment in rootComments" :key="comment.comment_id" class="bg-white p-4 rounded-lg shadow-sm mb-4">
              <!-- 父评论 -->
              <div>
                <img
                    :src="comment.avatar"
                    alt="用户头像"
                    class="w-8 h-8 rounded-full mb-2 cursor-pointer"
                    @click="navigateToUserProfile(comment.user_id)"
                />
                <p class="font-semibold">{{ comment.username }}</p>
                <p class="text-gray-600">{{ comment.content }}</p>
                <div class="flex justify-between items-center">
                  <p class="text-sm text-gray-500">{{ formatDate(comment.create_time) }}</p>
                  <button
                    @click="toggleReplyOrCollapse(comment.comment_id)"
                    class="text-blue-500 hover:underline hover:text-blue-600 transition-colors"
                  >
                    {{ isReplyExpanded[comment.comment_id] ? '收起回复' : '回复' }}
                  </button>
                </div>
              </div>
              <!-- 子评论 -->
              <div v-if="isReplyExpanded[comment.comment_id] && comment.children && comment.children.length > 0" class="ml-8 mt-2">
                <div v-for="childComment in comment.children" :key="childComment.comment_id" class="bg-gray-100 p-4 rounded-lg shadow-sm mb-2">
                  <img
                      :src="childComment.avatar"
                      alt="用户头像"
                      class="w-8 h-8 rounded-full mb-2 cursor-pointer"
                      @click="navigateToUserProfile(childComment.user_id)"
                  />
                  <p class="font-semibold">{{ childComment.username }}</p>
                  <p class="text-gray-600">{{ childComment.content }}</p>
                  <p class="text-sm text-gray-500">{{ formatDate(childComment.create_time) }}</p>
                </div>
              </div>
              <!-- 回复输入框 -->
              <div v-if="showReplyInput === comment.comment_id" class="mt-2">
                <textarea
                  v-model="replyContent"
                  rows="3"
                  class="w-full border border-gray-300 p-2 rounded"
                ></textarea>
                <div class="mt-2">
                  <button
                    @click="submitReply(comment.comment_id)"
                    class="bg-primary text-white p-2 rounded mr-2 hover:bg-primary-dark transition-colors"
                  >
                    发表回复
                  </button>
                  <button
                    @click="toggleReplyInput(null)"
                    class="bg-gray-300 p-2 rounded hover:bg-gray-400 transition-colors"
                  >
                    取消
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="text-center text-gray-500">暂无评论记录</div>
        </div>
      </div>
      <div v-else class="text-center py-12">
        <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
        <p class="text-gray-500">未找到该文章信息</p>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { Back } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import {
  handleInteraction,
  handleView,
  submitComment,
  fetchComments,
  submitReplyComment,
  navigateToUserProfile
} from "@/utils/interactions.js";
import { commonRequest } from "@/utils/commonRequest.js";

const route = useRoute();
const router = useRouter();
const article = ref(null);
const showCommentInput = ref(false);
const commentContent = ref('');
const showAllComments = ref(false);
const showReplyInput = ref(null);
const replyContent = ref('');
const comments = ref([]);

// 用于记录每个父评论的子评论展开状态
const isReplyExpanded = reactive({});

// 计算属性，格式化文章内容
const formattedContent = computed(() => {
  if (!article.value) return '';
  const content = article.value.content;
  // 按模块拆分内容
  const sections = content.split(/【([^】]+)】/).filter(Boolean);
  let formatted = '';
  for (let i = 0; i < sections.length; i += 2) {
    const title = sections[i];
    const text = sections[i + 1];
    formatted += `<h3 class="text-lg font-semibold text-gray-800 mb-2">【${title}】</h3>`;
    formatted += `<p class="text-sm text-gray-600 mb-4">${text}</p>`;
  }
  return formatted;
});

// 计算属性，获取根评论（一级评论）
const rootComments = computed(() => {
  const commentMap = new Map();
  const rootComments = [];

  comments.value.forEach(comment => {
    comment.children = [];
    commentMap.set(comment.comment_id, comment);
    // 初始化子评论展开状态
    if (!isReplyExpanded[comment.comment_id]) {
      isReplyExpanded[comment.comment_id] = false;
    }

    if (comment.parent_id === 0) {
      rootComments.push(comment);
    } else {
      const parent = commentMap.get(comment.parent_id);
      if (parent) {
        parent.children.push(comment);
      }
    }
  });
  return rootComments;
});

const fetchArticleData = async () => {
  try {
    const articleId = parseInt(route.params.id);
    const data = await commonRequest('article', 'selectById', { id: articleId });
    article.value = data;
  } catch (error) {
    ElMessage.error('获取文章信息失败，请稍后重试');
  }
};

const handleDailyView = async () => {
  const articleId = parseInt(route.params.id);
  await handleView('ARTICLE', articleId);
};

const handleLike = async () => {
  await handleInteraction(article.value.article_id, 'ARTICLE', 'LIKE', article.value);
};

const handleCollect = async () => {
  await handleInteraction(article.value.article_id, 'ARTICLE', 'COLLECT', article.value);
};

const handleSubmitComment = async () => {
  await submitComment(article, 'ARTICLE', commentContent, showCommentInput);
  await fetchComments('ARTICLE', article.value.article_id, comments);
};

// 切换显示所有评论
const toggleShowAllComments = async () => {
  if (!showAllComments.value) {
    try {
      await fetchComments('ARTICLE', article.value.article_id, comments);
    } catch (error) {
      ElMessage.error('获取评论失败，请稍后重试');
    }
  }
  showAllComments.value = !showAllComments.value;
};

// 切换回复输入框
const toggleReplyInput = (commentId) => {
  showReplyInput.value = commentId === showReplyInput.value ? null : commentId;
  replyContent.value = '';
};

// 切换回复输入框或子评论展开状态
const toggleReplyOrCollapse = (commentId) => {
  isReplyExpanded[commentId] = !isReplyExpanded[commentId];
  if (isReplyExpanded[commentId]) {
    // 若子评论展开，同时展开回复输入框
    toggleReplyInput(commentId);
  } else {
    // 若子评论收起，关闭回复输入框
    toggleReplyInput(null);
  }
};

// 提交回复
const submitReply = async (parentId) => {
  if (!replyContent.value.trim()) {
    ElMessage.warning('请输入回复内容');
    return;
  }

  try {
    const targetType = 'ARTICLE';
    const targetId = article.value.article_id;
    await submitReplyComment(targetType, targetId, parentId, replyContent.value);
    // 清空回复内容，但不关闭回复输入框
    replyContent.value = '';
    // 重新获取评论
    await fetchComments(targetType, targetId, comments);
  } catch (error) {
    ElMessage.error('回复发表失败，请稍后重试');
  }
};

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};

onMounted(() => {
  fetchArticleData();
  handleDailyView();
});

// 返回上一页的方法
const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
@import '@/assets/herbs.css';
/* 若使用了自定义颜色，需要定义这些颜色变量 */
:root {
  --primary: #2563eb;
  --primary-dark: #1d4ed8;
}

/* 可根据需要添加更多自定义样式 */
</style>