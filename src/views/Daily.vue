<template>
  <Header />
  <!-- 面包屑导航 -->
  <div class="bg-gray-100 py-3">
    <div class="container mx-auto px-4">
      <div class="flex items-center text-sm text-gray-600">
        <router-link to="/" class="hover:text-primary">首页</router-link>
        <i class="fa fa-angle-right mx-2 text-gray-400"></i>
        <span class="text-gray-800">每日一学</span>
      </div>
    </div>
  </div>
  <div class="daily-page bg-gray-50 min-h-screen p-8">
    <div v-if="loading" class="text-center text-xl">
      <i class="fa fa-spinner fa-spin text-primary text-3xl"></i>
      <p class="mt-2">加载中...</p>
    </div>
    <div v-else-if="error" class="text-center text-red-500 text-xl">
      <i class="fa fa-exclamation-triangle text-red-500 text-3xl"></i>
      <p class="mt-2">{{ error }}</p>
    </div>
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
          v-for="article in dailyArticles"
          :key="article.article_id"
          class="bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300 cursor-pointer"
          @click="viewArticle(article.article_id)"
      >
        <div class="relative">
          <img
              v-if="article.cover_image"
              :src="article.cover_image"
              alt="cover image"
              class="w-full h-48 object-cover rounded-lg mb-4"
          >
          <div
              v-if="article.category"
              class="absolute top-2 left-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary"
          >
            {{ article.category }}
          </div>
        </div>
        <h2 class="text-xl font-semibold text-gray-800 mb-2">{{ article.title }}</h2>
        <p class="text-sm text-gray-600 mb-4">{{ article.content.substring(0, 100) }}...</p>
        <div class="flex items-center justify-between text-sm text-gray-600">
          <span>{{ formatDate(article.publish_time) }}</span>
          <div class="flex items-center">
            <i class="fa fa-eye mr-1"></i>
            <span>{{ article.views }}</span>
            <i class="fa fa-thumbs-up ml-3 mr-1"></i>
            <span>{{ article.likes }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useRouter } from 'vue-router';

const router = useRouter();

// 模拟 API 接口调用
const loading = ref(true);
const error = ref(null);
const dailyArticles = ref([]);

const fetchDailyArticles = async () => {
  try {
    // 模拟 API 响应
    const response = await new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          data: [
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
          ]
        });
      }, 100);
    });
    dailyArticles.value = response.data.filter(article => article.status === '0');
  } catch (err) {
    error.value = '加载每日一学文章失败，请稍后重试。';
  } finally {
    loading.value = false;
  }
};

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleDateString();
};

// 查看文章详情
const viewArticle = (articleId) => {
  router.push({ name: 'DailyDetail', params: { id: articleId } });
};

onMounted(() => {
  fetchDailyArticles();
});
</script>

<style scoped>
/* 若有需要的自定义样式可继续添加 */
</style>