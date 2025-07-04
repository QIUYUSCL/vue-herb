<template>
  <div class="home-page bg-gray-50 min-h-screen">
    <!-- 引入导航栏组件 -->
    <Header />

    <!-- 搜索区域 -->
    <div class="search-area bg-gradient-to-r from-primary to-blue-600 py-12 px-4">
      <div class="max-w-4xl mx-auto text-center">
        <h1 class="text-[clamp(2rem,5vw,3.5rem)] font-bold text-white mb-4 text-shadow">中药材学习平台</h1>
        <p class="text-white/90 text-lg mb-8">探索中医药文化，了解自然草药的奥秘</p>
        <div class="relative max-w-2xl mx-auto">
          <input
            v-model="searchQuery"
            id="searchQuery"
            type="text"
            placeholder="搜索药材、功效、偏方..."
            class="w-full py-4 px-6 rounded-full text-lg focus:outline-none shadow-lg transition-all duration-300 focus:shadow-xl"
            @keyup.enter="handleSearch"
          />
          <button
            id="searchButton"
            class="absolute right-3 top-1/2 transform -translate-y-1/2 bg-primary hover:bg-primary/90 text-white rounded-full w-12 h-12 flex items-center justify-center transition-all duration-300"
            @click="handleSearch"
          >
            <i class="fa fa-search text-xl"></i>
          </button>
        </div>
      </div>
    </div>

    <!-- 快速导航 -->
    <div class="quick-nav bg-white py-6 shadow-md">
      <div class="max-w-6xl mx-auto px-4">
        <div class="flex flex-wrap justify-center gap-4">
        </div>
      </div>
    </div>

    <!-- 功能模块 -->
    <div class="featured-modules container mx-auto px-4 py-8 grid grid-cols-5 gap-6">
      <router-link to="/" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-home text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">首页</h3>
      </router-link>
      <router-link to="/herbs" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-leaf text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">药材大全</h3>
      </router-link>
      <router-link to="/videos" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-play-circle text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">视频教程</h3>
      </router-link>
      <router-link to="/daily" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-flask text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">偏方秘方</h3>
      </router-link>
      <router-link to="/daily-herb-knowledge" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-calendar text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">每日一学</h3>
      </router-link>
    </div>

    <!-- 热门药材区域 -->
    <div class="popular-herbs container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">热门药材</h2>
      <!-- 药材卡片列表 -->
      <div class="herb-cards grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <template v-for="herb in popularHerbs" :key="herb.herb_id">
          <div
            class="herb-card bg-white p-6 rounded-lg hover:scale-105 hover:shadow-xl transition-transform duration-300 cursor-pointer"
            @click="navigateToDetail('HERB', herb.herb_id)"
          >
            <img :src="herb.image_url" alt="herb image" class="w-full h-48 object-cover rounded-lg mb-4" />
            <h3 class="text-lg font-semibold text-gray-800">{{ herb.herb_name }}</h3>
            <p class="text-sm text-gray-600">{{ herb.efficacy }}</p>
          </div>
        </template>
      </div>
    </div>

    <!-- 推荐药材区域 -->
    <div class="recommended-herbs container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">推荐药材</h2>
      <div class="herb-cards grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <template v-for="herb in recommendedHerbs" :key="herb.herb_id">
          <div
            class="herb-card bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300 cursor-pointer"
            @click="navigateToDetail('HERB', herb.herb_id)"
          >
            <img :src="herb.image_url" alt="herb image" class="w-full h-48 object-cover rounded-lg mb-4" />
            <h3 class="text-lg font-semibold text-gray-800">{{ herb.herb_name }}</h3>
            <p class="text-sm text-gray-600">{{ herb.efficacy }}</p>
          </div>
        </template>
      </div>
    </div>

    <!-- 热门文章区域 -->
    <div class="efficacies container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">热门文章</h2>
      <div class="flex flex-wrap gap-4 justify-center">
        <template v-for="article in popularArticles" :key="article.article_id">
          <div
            class="article-card bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300 cursor-pointer"
            @click="navigateToDetail('ARTICLE', article.article_id)"
          >
            <img :src="article.cover_image" alt="article cover" class="w-full h-48 object-cover rounded-lg mb-4" />
            <h3 class="text-lg font-semibold text-gray-800">{{ article.title }}</h3>
            <p class="text-sm text-gray-600">{{ article.content.substring(0, 100) }}...</p>
            <div class="flex items-center justify-between mt-2">
              <span class="text-sm text-gray-600">{{ formatDate(article.publish_time) }}</span>
              <span class="text-sm text-gray-600">{{ article.views }} 次阅读</span>
            </div>
          </div>
        </template>
      </div>
    </div>


    <div class="videos container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">热门视频</h2>
      <div class="video-cards grid grid-cols-1 md:grid-cols-3 gap-6">
        <!-- 使用 v-for 遍历视频列表 -->
        <template v-for="video in popularVideo" :key="video.video_id">
          <div
            class="video-card bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300 cursor-pointer"
            @click="navigateToDetail('VIDEO', video.video_id)"
          >
            <!-- 使用 cover_image 作为视频封面 -->
            <img :src="video.cover_image" alt="video thumbnail" class="w-full h-48 object-cover rounded-lg mb-4" />
            <h3 class="text-lg font-semibold text-gray-800">{{ video.title }}</h3>
            <div class="flex items-center justify-between mt-2">
              <span class="text-sm text-gray-600">{{ formatDuration(video.duration) }}</span>
              <span class="text-sm text-gray-600">{{ video.views }} 次观看</span>
            </div>
            <div class="flex items-center mt-2">
              <i class="fa fa-eye text-gray-600"></i>
              <span class="ml-1 text-sm text-gray-600">{{ video.views }} 次观看</span>
            </div>
          </div>
        </template>
      </div>
    </div>

    <!-- 引入页脚组件 -->
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { commonRequest } from '@/utils/commonRequest.js';
import request from '@/utils/request.js';

const searchQuery = ref('');
const router = useRouter();
const popularHerbs = ref([]);
const recommendedHerbs = ref([]);
const popularArticles = ref([]);
const popularVideo = ref(null);

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({
      name: 'SearchResult',
      query: { keyword: searchQuery.value.trim() },
    });
    searchQuery.value = '';
  }
};

// 获取热门药材（点赞数最多的四个）
const fetchPopularHerbs = async () => {
  try {
    const response = await request.get('herb/info/selectByLikes', {
      params: { limit: 4 },
    });
    popularHerbs.value = response.data;
  } catch (error) {
    console.error('获取热门药材失败:', error);
  }
};

// 获取推荐药材（收藏数最多的四个）
const fetchRecommendedHerbs = async () => {
  try {
    const data = await commonRequest('herb', 'selectByCollections', {
      limit: 4,
    });
    recommendedHerbs.value = data;
  } catch (error) {
    console.error('获取推荐药材失败:', error);
  }
};

// 获取热门文章（点赞数最多的四个）
const fetchPopularArticles = async () => {
  try {
    const data = await commonRequest('article', 'selectByLikes', { limit: 4 });
    popularArticles.value = data;
  } catch (error) {
    console.error('获取热门文章失败:', error);
  }
};

// 获取点赞数最多的视频
const fetchPopularVideo = async () => {
  try {
    const data = await commonRequest('video', 'selectByLikes', { limit: 4 });
    // 直接将返回的视频列表赋值给 popularVideo
    popularVideo.value = data;
  } catch (error) {
    console.error('获取热门视频失败:', error);
  }
};

// 跳转详情页方法
const navigateToDetail = (targetType, targetId) => {
  switch (targetType) {
    case 'HERB':
      router.push({ name: 'HerbDetail', params: { id: targetId } });
      break;
    case 'VIDEO':
      router.push({ name: 'VideoDetail', params: { id: targetId } });
      break;
    case 'ARTICLE':
      router.push({ name: 'DailyDetail', params: { id: targetId } });
      break;
    default:
      break;
  }
};

onMounted(() => {
  fetchPopularHerbs();
  fetchRecommendedHerbs();
  fetchPopularArticles();
  fetchPopularVideo();
});

// 格式化视频时长函数
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};
</script>

<style scoped>


</style>



