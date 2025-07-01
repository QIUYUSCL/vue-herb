<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />

    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">搜索结果</span>
        </div>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">搜索结果：{{ keyword }}</h2>

      <!-- 药材部分 -->
      <section class="mb-12">
        <h3 class="text-xl font-semibold text-gray-800 mb-4">药材</h3>
        <div id="herb-search-results" class="herb-list grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <template v-if="loadingHerbs">
            <div class="col-span-full text-center py-12">
              <p class="text-gray-500">加载中...</p>
            </div>
          </template>
          <template v-else-if="errorHerbs">
            <div class="col-span-full text-center py-12">
              <p class="text-red-500">搜索药材出错：{{ errorHerbs }}</p>
            </div>
          </template>
          <template v-else-if="herbs.length === 0">
            <div class="col-span-full text-center py-12">
              <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
              <p class="text-gray-500">没有找到匹配的药材</p>
            </div>
          </template>
          <template v-else>
            <router-link
                v-for="herb in herbs"
                :key="herb.herb_id"
                :to="`/herbs/${herb.herb_id}`"
                class="herb-card bg-white rounded-xl overflow-hidden shadow-md card-hover"
                :data-id="herb.herb_id"
            >
              <div class="relative">
                <img :src="herb.image_url" :alt="herb.herb_name" class="w-full h-48 object-cover">
                <div class="absolute top-2 left-2">
                  <span
                      :class="categoryColors[getCategoryName(herb.category_id)] + ' text-xs px-2 py-1 rounded-full'"
                  >{{ getCategoryName(herb.category_id) }}</span>
                </div>
                <div class="absolute top-2 right-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary">
                  <i class="fa fa-star text-yellow-400"></i> 0
                </div>
              </div>
              <div class="p-4">
                <p class="text-sm text-gray-600 mb-1">{{ herb.pinyin }}</p>
                <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ herb.herb_name }}</h3>
                <div class="flex items-center space-x-2">
                  <span
                      :class="efficacyColors[getEfficacyKey(herb.efficacy)] + ' text-xs px-2 py-1 rounded-full'"
                  >{{ herb.efficacy }}</span>
                </div>
              </div>
            </router-link>
          </template>
        </div>
      </section>

      <!-- 视频部分 -->
      <section class="mb-12">
        <h3 class="text-xl font-semibold text-gray-800 mb-4">视频</h3>
        <div id="video-search-results" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <template v-if="loadingVideos">
            <div class="col-span-full text-center py-12">
              <p class="text-gray-500">加载中...</p>
            </div>
          </template>
          <template v-else-if="errorVideos">
            <div class="col-span-full text-center py-12">
              <p class="text-red-500">搜索视频出错：{{ errorVideos }}</p>
            </div>
          </template>
          <template v-else-if="videos.length === 0">
            <div class="col-span-full text-center py-12">
              <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
              <p class="text-gray-500">没有找到匹配的视频</p>
            </div>
          </template>
          <template v-else>
            <!-- 修改为使用 video.video_id 作为跳转参数 -->
            <router-link
                v-for="video in videos"
                :key="video.video_id"
                :to="`/video-detail/${video.video_id}`"
                class="bg-white rounded-xl overflow-hidden shadow-md card-hover"
            >
              <div class="relative">
                <!-- 修改为使用 video.cover_image -->
                <img :src="video.cover_image" :alt="video.title" class="w-full h-48 object-cover">
                <div class="absolute bottom-2 left-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium">
                  <i class="fa fa-play-circle text-red-500"></i>
                </div>
              </div>
              <div class="p-4">
                <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ video.title }}</h3>
                <p class="text-sm text-gray-600">{{ video.description }}</p>
              </div>
            </router-link>
          </template>
        </div>
      </section>

      <!-- 文章部分 -->
      <section>
        <h3 class="text-xl font-semibold text-gray-800 mb-4">文章</h3>
        <div id="article-search-results">
          <template v-if="loadingArticles">
            <div class="text-center py-12">
              <p class="text-gray-500">加载中...</p>
            </div>
          </template>
          <template v-else-if="errorArticles">
            <div class="text-center py-12">
              <p class="text-red-500">搜索文章出错：{{ errorArticles }}</p>
            </div>
          </template>
          <template v-else-if="articles.length === 0">
            <div class="text-center py-12">
              <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
              <p class="text-gray-500">没有找到匹配的文章</p>
            </div>
          </template>
          <template v-else>
            <!-- 修改为使用 article.article_id 作为跳转参数 -->
            <router-link
                v-for="article in articles"
                :key="article.article_id"
                :to="`/daily/${article.article_id}`"
                class="bg-white rounded-xl overflow-hidden shadow-md card-hover p-4 mb-4"
            >
              <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ article.title }}</h3>
              <!-- 修改为显示文章内容前 100 字符 -->
              <p class="text-sm text-gray-600">{{ article.content.substring(0, 100) }}...</p>
              <span class="text-primary hover:underline mt-2 inline-block">
                阅读更多
              </span>
            </router-link>
          </template>
        </div>
      </section>
    </main>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import request from '@/utils/request.js';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { ElMessage } from 'element-plus';

const route = useRoute();
const keyword = ref(route.query.keyword || '');

// 药材相关状态
const herbs = ref([]);
const loadingHerbs = ref(true);
const errorHerbs = ref(null);

// 视频相关状态
const videos = ref([]);
const loadingVideos = ref(true);
const errorVideos = ref(null);

// 文章相关状态
const articles = ref([]);
const loadingArticles = ref(true);
const errorArticles = ref(null);

// 分类标签颜色映射
const categoryColors = {
  '解表药': 'bg-green-100 text-green-800',
  '清热药': 'bg-emerald-100 text-emerald-800',
  '祛风湿药': 'bg-pink-100 text-pink-800',
  '补虚药': 'bg-amber-100 text-amber-800',
};

// 功效标签颜色映射
const efficacyColors = {
  'tonify': 'bg-blue-100 text-blue-800',
  'clear': 'bg-red-100 text-red-800',
  'activate': 'bg-purple-100 text-purple-800',
  'dispel': 'bg-teal-100 text-teal-800',
  'resolve': 'bg-indigo-100 text-indigo-800'
};

// 获取分类名称
const getCategoryName = (categoryId) => {
  const category = categories.value.find(cat => cat.category_id === Number(categoryId));
  return category ? category.category_name : '';
};

// 根据功效描述获取功效 key，基于关键词匹配
const getEfficacyKey = (efficacyName) => {
  const keywordMap = {
    'tonify': ['补', '益', '养'],
    'clear': ['清热', '解毒', '凉血'],
    'activate': ['活血', '通经', '化瘀'],
    'dispel': ['祛湿', '祛风', '除湿', '湿'],
    'resolve': ['止痛', '痛']
  };

  for (const [key, keywords] of Object.entries(keywordMap)) {
    for (const keyword of keywords) {
      if (efficacyName.includes(keyword)) {
        return key;
      }
    }
  }
  return '';
};

// 药材类别数据
const categories = ref([]);

// 定义 loadCategories 函数，获取药材种类数据
const loadCategories = async () => {
  try {
    const response = await request.get('/herb/category/selectAll');
    if (response.code === '200') {
      categories.value = response.data;
    } else {
      ElMessage.error('药材种类数据加载失败，请稍后重试');
    }
  } catch (error) {
    console.error('请求出错:', error);
    ElMessage.error('药材种类数据加载失败');
  }
};

// 封装搜索药材的方法
const searchHerbs = async () => {
  try {
    const herbResponse = await request.get('/herb/info/search', {
      params: { keyword: keyword.value }
    });
    if (herbResponse.code === '200') {
      herbs.value = herbResponse.data;
    } else {
      ElMessage.error('搜索药材失败，请重试');
    }
  } catch (err) {
    errorHerbs.value = err.message;
    ElMessage.error('搜索药材出错，请稍后重试');
  } finally {
    loadingHerbs.value = false;
  }
};

// 封装搜索视频的方法
const searchVideos = async () => {
  try {
    const videoResponse = await request.get('/video/search', {
      params: { keyword: keyword.value }
    });
    if (videoResponse.code === '200') {
      videos.value = videoResponse.data;
    } else {
      ElMessage.error('搜索视频失败，请重试');
    }
  } catch (err) {
    errorVideos.value = err.message;
    ElMessage.error('搜索视频出错，请稍后重试');
  } finally {
    loadingVideos.value = false;
  }
};

// 封装搜索文章的方法
const searchArticles = async () => {
  try {
    const articleResponse = await request.get('/daily/search', {
      params: { keyword: keyword.value }
    });
    if (articleResponse.code === '200') {
      articles.value = articleResponse.data;
    } else {
      ElMessage.error('搜索文章失败，请重试');
    }
  } catch (err) {
    errorArticles.value = err.message;
    ElMessage.error('搜索文章出错，请稍后重试');
  } finally {
    loadingArticles.value = false;
  }
};

onMounted(async () => {
  await loadCategories();
  if (keyword.value) {
    await searchHerbs();
    await searchVideos();
    await searchArticles();
  }
});
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>