<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <div class="container mx-auto px-4 py-8">
      <!-- 返回按钮 -->
      <el-button type="success" round :icon="Back" @click="goBack" class="mb-4" />
      <div v-if="loading" class="text-center text-gray-600">加载中...</div>
      <div v-else-if="article" class="bg-white p-6 rounded-lg shadow-md">
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
        <div v-if="article.category" class="mb-4">
          <span class="text-sm font-medium">分类：</span>
          <span class="text-sm text-gray-600">{{ article.category }}</span>
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

const route = useRoute();
const router = useRouter();
const article = ref(null);
const loading = ref(true);

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleDateString();
};

// 模拟从 API 获取文章数据
const fetchArticleData = async () => {
  try {
    // 模拟 API 请求延迟
    await new Promise(resolve => setTimeout(resolve, 100));
    const articleId = parseInt(route.params.id);
    const allArticles = [
      {
        article_id: 1,
        title: '中药材的保存方法',
        content: '中药材的保存对于保证其药效至关重要。不同的药材有不同的保存要求...',
        cover_image: 'https://picsum.photos/300/200?random=1&text=Herb+Storage',
        category: '药材保存',
        status: '0',
        publish_time: '2025-06-30 08:00:00',
        views: 120,
        likes: 20,
        comments: 5,
        sort: 1,
        create_time: '2025-06-30 07:00:00'
      },
      {
        article_id: 2,
        title: '常见中药材的鉴别',
        content: '在购买中药材时，如何鉴别其真伪和品质是非常重要的。下面为你介绍几种常见中药材的鉴别方法...',
        cover_image: 'https://picsum.photos/300/200?random=2&text=Herb+Identification',
        category: '药材鉴别',
        status: '0',
        publish_time: '2025-06-29 09:30:00',
        views: 180,
        likes: 30,
        comments: 8,
        sort: 2,
        create_time: '2025-06-29 08:30:00'
      }
    ];
    article.value = allArticles.find(item => item.article_id === articleId);
  } catch (error) {
    console.error('获取文章数据失败:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchArticleData();
});

// 返回上一页的方法
const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>