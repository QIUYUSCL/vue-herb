<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <router-link to="/daily" class="hover:text-primary">每日一学</router-link>
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
        <p class="text-sm text-gray-600 mb-4">{{ article.content }}</p>
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
          <button @click="handleSubmitComment" class="mt-2 bg-primary text-white p-2 rounded">发表评论</button>
          <button @click="showCommentInput = false" class="mt-2 ml-2 bg-gray-300 p-2 rounded">取消</button>
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
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { Back } from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {handleInteraction, handleView, submitComment} from "@/utils/interactions.js";
import {commonRequest} from "@/utils/commonRequest.js";

const route = useRoute();
const router = useRouter();
const article = ref(null);
const showCommentInput = ref(false);
const commentContent = ref('');

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleDateString();
};

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
};

onMounted(() => {
  fetchArticleData();
  handleDailyView()
});

// 返回上一页的方法
const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>