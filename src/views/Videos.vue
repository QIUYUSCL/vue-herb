<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <div class="container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-primary mb-6 text-center">视频教程</h2>
      <div v-if="loading" class="text-center text-gray-600">加载中...</div>
      <div v-else class="video-cards grid grid-cols-1 md:grid-cols-3 gap-6">
        <template v-for="video in filteredVideos" :key="video.video_id">
          <!-- 修改为 router-link 实现跳转 -->
          <router-link :to="`/video-detail/${video.video_id}`" class="video-card bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300">
            <img :src="video.cover_image" alt="video thumbnail" class="w-full h-48 object-cover rounded-lg mb-4">
            <h3 class="text-lg font-semibold text-gray-800">{{ video.title }}</h3>
            <p class="text-sm text-gray-600 mb-2">{{ video.description }}</p>
            <div class="flex items-center justify-between mt-2">
              <span class="text-sm text-gray-600">{{ formatDuration(video.duration) }}</span>
              <span class="text-sm text-gray-600">分类: {{ video.category }}</span>
            </div>
            <div class="flex items-center mt-2">
              <i class="fa fa-eye text-gray-600"></i>
              <span class="ml-1 text-sm text-gray-600">{{ video.views }} 次观看</span>
              <i class="fa fa-thumbs-up text-gray-600 ml-4"></i>
              <span class="ml-1 text-sm text-gray-600">{{ video.likes }} 点赞</span>
              <i class="fa fa-comment text-gray-600 ml-4"></i>
              <span class="ml-1 text-sm text-gray-600">{{ video.comments }} 评论</span>
            </div>
          </router-link>
        </template>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 模拟从 API 获取视频数据
const loading = ref(true);
const videos = ref([]);

// 过滤出状态正常的视频
const filteredVideos = computed(() => {
  return videos.value.filter(video => video.status === '0');
});

// 将时长从秒转换为 分:秒 格式
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

const fetchVideos = async () => {
  try {
    // 模拟 API 请求延迟
    await new Promise(resolve => setTimeout(resolve, 100));
    videos.value = [
      {
        video_id: 1,
        title: '中药材鉴别教程',
        description: '详细讲解中药材的鉴别方法',
        video_url: 'https://example.com/herb-identification-video.mp4',
        cover_image: 'https://picsum.photos/300/200?random=1&text=Herb+Identification',
        duration: 930,
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
        video_url: 'https://example.com/herb-processing-video.mp4',
        cover_image: 'https://picsum.photos/300/200?random=2&text=Herb+Processing',
        duration: 1215,
        category: '中药炮制',
        status: '0',
        views: 800,
        likes: 150,
        comments: 30,
        sort: 2,
        create_time: '2025-06-10 14:30:00'
      }
    ];
  } catch (error) {
    console.error('获取视频数据失败:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchVideos();
});
</script>

<style scoped>
/* 可以添加自定义样式 */
</style>