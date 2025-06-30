<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <router-link to="/videos" class="hover:text-primary">视频教程</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span v-if="video" class="text-gray-800">{{ video.title }}</span>
          <span v-else class="text-gray-800">视频详情</span>
        </div>
      </div>
    </div>
    <div class="container mx-auto px-4 py-8">
      <el-button type="success" round :icon="Back" @click="goBack" class="mb-4" />
      <div v-if="loading" class="text-center text-gray-600">加载中...</div>
      <div v-else-if="video" class="video-detail bg-white p-6 rounded-lg shadow-md">
        <!-- 视频播放器 -->
        <video
            :src="video.video_url"
            controls
            class="w-full h-auto mb-4 rounded-lg"
            :poster="video.cover_image"
        ></video>
        <h2 class="text-2xl font-bold text-gray-800 mb-2">{{ video.title }}</h2>
        <p class="text-sm text-gray-600 mb-4">{{ video.description }}</p>
        <div class="flex items-center justify-between mb-4">
          <span class="text-sm text-gray-600">时长: {{ formatDuration(video.duration) }}</span>
          <span class="text-sm text-gray-600">分类: {{ video.category }}</span>
        </div>
        <div class="flex items-center mb-4">
          <i class="fa fa-eye text-gray-600"></i>
          <span class="ml-1 text-sm text-gray-600">{{ video.views }} 次观看</span>
          <i class="fa fa-thumbs-up text-gray-600 ml-4"></i>
          <span class="ml-1 text-sm text-gray-600">{{ video.likes }} 点赞</span>
          <i class="fa fa-comment text-gray-600 ml-4"></i>
          <span class="ml-1 text-sm text-gray-600">{{ video.comments }} 评论</span>
        </div>
        <p class="text-sm text-gray-600">创建时间: {{ video.create_time }}</p>
      </div>
      <div v-else class="text-center text-gray-600">未找到该视频信息</div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import {Back} from "@element-plus/icons-vue";
import router from "@/router";

const route = useRoute();
const video = ref(null);
const loading = ref(true);

// 将时长从秒转换为 分:秒 格式
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

// 模拟从 API 获取视频详情数据
const fetchVideoDetail = async () => {
  try {
    // 模拟 API 请求延迟
    await new Promise(resolve => setTimeout(resolve, 100));
    const videoId = parseInt(route.params.id);
    const allVideos = [
      {
        video_id: 1,
        title: '中药材鉴别教程',
        description: '详细讲解中药材的鉴别方法',
        // 使用网络视频链接
        video_url: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
        cover_image: 'https://picsum.photos/300/200?random=1&text=Herb+Identification',
        duration: 596,
        category: '中药材鉴别',
        status: '0',
        views: 1200,
        likes: 200,
        comments: 50,
        sort: 1,
        create_time: '2025-06-01 09:00:00'
      },
      {
        video_id: 2,
        title: '中药炮制方法',
        description: '介绍常见中药的炮制流程',
        // 使用网络视频链接
        video_url: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4',
        cover_image: 'https://picsum.photos/300/200?random=2&text=Herb+Processing',
        duration: 674,
        category: '中药炮制',
        status: '0',
        views: 800,
        likes: 150,
        comments: 30,
        sort: 2,
        create_time: '2025-06-10 14:30:00'
      }
    ];
    video.value = allVideos.find(item => item.video_id === videoId);
  } catch (error) {
    console.error('获取视频详情数据失败:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchVideoDetail();
});

const goBack = () => {
  router.go(-1);
};
</script>