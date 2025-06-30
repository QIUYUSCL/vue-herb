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
              id="searchQuery"
              type="text"
              placeholder="搜索药材、功效、偏方..."
              class="w-full py-4 px-6 rounded-full text-lg focus:outline-none shadow-lg transition-all duration-300 focus:shadow-xl"
          >
          <button
              id="searchButton"
              class="absolute right-3 top-1/2 transform -translate-y-1/2 bg-primary hover:bg-primary/90 text-white rounded-full w-12 h-12 flex items-center justify-center transition-all duration-300"
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
          <button class="quick-nav-item" data-section="common">
            <i class="fa fa-leaf text-primary text-2xl mb-2"></i>
            <span class="text-sm">常用药材</span>
          </button>
          <button class="quick-nav-item" data-section="new">
            <i class="fa fa-star text-yellow-500 text-2xl mb-2"></i>
            <span class="text-sm">新品药材</span>
          </button>
          <button class="quick-nav-item" data-section="efficacy">
            <i class="fa fa-heartbeat text-red-500 text-2xl mb-2"></i>
            <span class="text-sm">按功效</span>
          </button>
        </div>
      </div>
    </div>

    <!-- 五个功能模块 -->
    <div class="featured-modules container mx-auto px-4 py-8 grid grid-cols-1 md:grid-cols-3 lg:grid-cols-5 gap-6">
      <router-link to="/" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-home text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">首页</h3>
      </router-link>
      <router-link to="/herbs" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-leaf text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">药材大全</h3>
      </router-link>
      <router-link to="/remedies" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-book text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">偏方秘方</h3>
      </router-link>
      <router-link to="/videos" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-play-circle text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">视频教程</h3>
      </router-link>
      <router-link to="/daily" class="module-card card-hover bg-white p-6 rounded-lg">
        <i class="fa fa-calendar text-primary text-4xl mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-800">每日一学</h3>
      </router-link>
    </div>



    <!-- 轮播图区域 -->
    <div class="carousel container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">最新动态</h2>
      <div class="carousel-items grid grid-cols-1 md:grid-cols-3 gap-6">
        <template v-for="item in carouselItems" :key="item.id">
          <div class="carousel-item bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300">
            <img :src="item.image" alt="carousel image" class="w-full h-48 object-cover rounded-lg mb-4">
            <h3 class="text-lg font-semibold text-gray-800">{{ item.title }}</h3>
            <p class="text-sm text-gray-600">{{ item.description }}</p>
          </div>
        </template>
      </div>
    </div>

    <!-- 热门药材区域 -->
    <div class="popular-herbs container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">热门药材</h2>
      <!-- 药材卡片列表 -->
      <div class="herb-cards grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <template v-for="herb in popularHerbs" :key="herb.id">
          <div class="herb-card bg-white p-6 rounded-lg hover:scale-105 hover:shadow-xl transition-transform duration-300">
            <img :src="herb.image" alt="herb image" class="w-full h-48 object-cover rounded-lg mb-4">
            <h3 class="text-lg font-semibold text-gray-800">{{ herb.name }}</h3>
            <p class="text-sm text-gray-600">{{ herb.description }}</p>
          </div>
        </template>
      </div>
    </div>

    <!-- 推荐药材区域 -->
    <div class="recommended-herbs container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">推荐药材</h2>
      <div class="herb-cards grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <template v-for="herb in recommendedHerbs" :key="herb.id">
          <div class="herb-card bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300">
            <img :src="herb.image" alt="herb image" class="w-full h-48 object-cover rounded-lg mb-4">
            <h3 class="text-lg font-semibold text-gray-800">{{ herb.name }}</h3>
            <p class="text-sm text-gray-600">{{ herb.efficacy }}</p>
            <div class="flex items-center mt-2">
              <i class="fa fa-star text-yellow-500"></i>
              <span class="ml-1 text-sm text-gray-700">{{ herb.rating }}</span>
            </div>
          </div>
        </template>
      </div>
    </div>

    <!-- 功效分类区域 -->
    <div class="efficacies container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">常见功效</h2>
      <div class="flex flex-wrap gap-4 justify-center">
        <template v-for="efficacy in efficacies" :key="efficacy.id">
          <div class="bg-white p-3 rounded-lg shadow-md flex items-center gap-2 hover:scale-105 hover:shadow-xl transition-transform duration-300 ">
            <span class="text-sm font-medium">{{ efficacy.name }}</span>
            <span class="text-xs text-gray-600">({{ efficacy.count }}种药材)</span>
          </div>
        </template>
      </div>
    </div>


    <!-- 偏方秘方区域 -->
    <div class="recipes container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">偏方秘方</h2>
      <div class="recipe-cards grid grid-cols-1 md:grid-cols-2 gap-6">
        <template v-for="recipe in recipes" :key="recipe.id">
          <div class="recipe-card bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300">
            <img :src="recipe.image" alt="recipe image" class="w-full h-48 object-cover rounded-lg mb-4">
            <h3 class="text-lg font-semibold text-gray-800">{{ recipe.title }}</h3>
            <p class="text-sm text-gray-600">{{ recipe.description }}</p>
            <div class="mt-4">
              <span class="text-sm font-medium">主要药材：</span>
              <span class="text-sm text-gray-600">{{ recipe.herbs.join('、') }}</span>
            </div>
          </div>
        </template>
      </div>
    </div>

    <!-- 视频教程区域 -->
    <div class="videos container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">视频教程</h2>
      <div class="video-cards grid grid-cols-1 md:grid-cols-3 gap-6">
        <template v-for="video in videos" :key="video.id">
          <div class="video-card bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300">
            <img :src="video.thumbnail" alt="video thumbnail" class="w-full h-48 object-cover rounded-lg mb-4">
            <h3 class="text-lg font-semibold text-gray-800">{{ video.title }}</h3>
            <div class="flex items-center justify-between mt-2">
              <span class="text-sm text-gray-600">{{ video.uploader }}</span>
              <span class="text-sm text-gray-600">{{ video.duration }}</span>
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
// 首页逻辑（如数据加载）
import { ref } from 'vue';
import Header from '@/components/Header.vue';
import Footer from "@/components/Footer.vue";

// 功能模块示例数据
const featuredModules = ref([
  {
    name: '首页',
    path: '/',
    icon: 'fa fa-home',
    description: '了解平台最新资讯和动态'
  },
  {
    name: '药材大全',
    path: '/herbs',
    icon: 'fa fa-leaf',
    description: '查看各种中药材的详细信息'
  },
  {
    name: '偏方秘方',
    path: '/remedies',
    icon: 'fa fa-book',
    description: '探索传统中医药的经典偏方'
  },
  {
    name: '视频教程',
    path: '/videos',
    icon: 'fa fa-play-circle',
    description: '观看专业的中医药教学视频'
  },
  {
    name: '每日一学',
    path: '/daily',
    icon: 'fa fa-calendar',
    description: '每天学习一点中医药小知识'
  }
]);

// 轮播图示例数据
const carouselItems = ref([
  {
    id: 1,
    title: '新药材上线',
    description: '平台新增多种珍稀中药材，快来了解一下',
    image: 'https://picsum.photos/300/200?random=1&text=New+Herbs'
  },
  {
    id: 2,
    title: '视频更新',
    description: '最新的中医药教学视频已发布，立即观看',
    image: 'https://picsum.photos/300/200?random=1&text=New+Herbs'
  },
  {
    id: 3,
    title: '每日一学',
    description: '今天学习如何辨别常见中药材的真伪',
    image: 'https://picsum.photos/300/200?random=1&text=New+Herbs'
  }
]);

// 热门药材示例数据
const popularHerbs = ref([
  {
    id: 1,
    name: '人参',
    description: '大补元气，复脉固脱，补脾益肺',
    image: 'https://picsum.photos/seed/ginseng/300/200'
  },
  {
    id: 2,
    name: '黄芪',
    description: '补气升阳，固表止汗，利水消肿',
    image: 'https://picsum.photos/seed/astragalus/300/200'
  },
  {
    id: 3,
    name: '当归',
    description: '补血活血，调经止痛，润肠通便',
    image: 'https://picsum.photos/seed/angelica/300/200'
  },
  {
    id: 4,
    name: '枸杞',
    description: '滋补肝肾，益精明目',
    image: 'https://picsum.photos/seed/wolfberry/300/200'
  }
]);

// 模拟数据
const recommendedHerbs = ref([
  { id: 1, name: '人参', image: 'https://picsum.photos/seed/ginseng/300/200', efficacy: '大补元气，复脉固脱', rating: 4.9 },
  { id: 2, name: '黄芪', image: 'https://picsum.photos/seed/astragalus/300/200', efficacy: '补气升阳，固表止汗', rating: 4.7 },
  { id: 3, name: '当归', image: 'https://picsum.photos/seed/angelica/300/200', efficacy: '补血活血，调经止痛', rating: 4.8 },
  { id: 4, name: '枸杞', image: 'https://picsum.photos/seed/wolfberry/300/200', efficacy: '滋补肝肾，益精明目', rating: 4.6 }
]);

const efficacies = ref([
  { id: 1, name: '补气', count: 24 },
  { id: 2, name: '补血', count: 18 },
  { id: 3, name: '滋阴', count: 16 },
  { id: 4, name: '壮阳', count: 12 },
  { id: 5, name: '清热', count: 32 },
  { id: 6, name: '解毒', count: 20 },
  { id: 7, name: '祛湿', count: 25 },
  { id: 8, name: '活血', count: 19 },
  { id: 9, name: '安神', count: 15 },
  { id: 10, name: '止咳', count: 17 },
  { id: 11, name: '化痰', count: 14 },
  { id: 12, name: '止痛', count: 22 }
]);

const recipes = ref([
  {
    id: 1,
    title: '四物汤',
    herbs: ['当归', '川芎', '白芍', '熟地黄'],
    description: '补血活血，调理月经不调，养血安神',
    image: 'https://picsum.photos/seed/sigowu/600/300'
  },
  {
    id: 2,
    title: '四君子汤',
    herbs: ['人参', '白术', '茯苓', '甘草'],
    description: '益气健脾，用于脾胃气虚，面色萎黄',
    image: 'https://picsum.photos/seed/sigunzi/600/300'
  }
]);

// 新增视频教程模拟数据
const videos = ref([
  {
    video_id: 1,
    title: '中药材鉴别：如何区分真假人参',
    description: '详细讲解真假人参的鉴别方法',
    video_url: 'https://example.com/ginseng-video.mp4',
    cover_image: 'https://picsum.photos/seed/ginseng-video/400/225',
    duration: 754,
    category: '药材鉴别',
    status: '0',
    views: 12456,
    likes: 800,
    comments: 120,
    sort: 1,
    create_time: '2025-06-20 09:00:00'
  },
  {
    video_id: 2,
    title: '黄芪的功效与使用方法详解',
    description: '了解黄芪的多种功效及正确使用方式',
    video_url: 'https://example.com/astragalus-video.mp4',
    cover_image: 'https://picsum.photos/seed/astragalus-video/400/225',
    duration: 1125,
    category: '药材功效',
    status: '0',
    views: 9876,
    likes: 600,
    comments: 90,
    sort: 2,
    create_time: '2025-06-22 14:30:00'
  },
  {
    video_id: 3,
    title: '当归的炮制工艺与药用价值',
    description: '深入探究当归的炮制过程和药用价值',
    video_url: 'https://example.com/angelica-video.mp4',
    cover_image: 'https://picsum.photos/seed/angelica-video/400/225',
    duration: 1452,
    category: '药材炮制',
    status: '0',
    views: 7654,
    likes: 500,
    comments: 70,
    sort: 3,
    create_time: '2025-06-25 16:15:00'
  }
]);

// 格式化视频时长函数
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

</script>

<style scoped>
/* 若有需要的自定义样式可继续添加 */
</style>



