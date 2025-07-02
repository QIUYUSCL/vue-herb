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
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
const route = useRoute();
const video = ref(null);


// 将时长从秒转换为 分:秒 格式
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};


const fetchVideoDetail = async () => {
  try {
    const videoId = parseInt(route.params.id);
    const response = await request.get(`/video/selectById/${videoId}`);
    if(response.code==="200")
    {
      video.value=response.data;
    }else{
      ElMessage.error("code错误");
    }
  } catch (error) {
    console.error('获取视频详情数据失败:', error);
  }
};

onMounted(() => {
  fetchVideoDetail();
});

const goBack = () => {
  router.go(-1);
};
</script>