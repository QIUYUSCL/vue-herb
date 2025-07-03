<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">视频教程</span>
        </div>
      </div>
    </div>

    <div class="container mx-4 px-4 mb-4 d-flex justify-end">
      <el-select
          v-model="selectedCategory"
          placeholder="请选择分类"
          @change="filterVideos"
          clearable
          style="width: 200px;"
      >
        <el-option label="全部" value="" />
        <el-option
            v-for="category in uniqueCategories"
            :key="category"
            :label="category"
            :value="category"
        />
      </el-select>
    </div>

    <div class="container mx-auto px-4 py-8">
      <div v-if="loading" class="text-center text-gray-600">加载中...</div>
      <div v-else class="video-cards grid grid-cols-1 md:grid-cols-3 gap-6">
        <template v-for="video in paginatedVideos" :key="video.video_id">
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
      <!-- 使用分页组件 -->
      <Pagination
        :total="filteredVideos.length"
        :page-size="pageSize"
        @page-change="handleCurrentChange"
      />
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { useRouter } from 'vue-router';
import Request  from "@/utils/request.js";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import {commonRequest} from "@/utils/commonRequest.js";
// 引入分页组件
import Pagination from "@/components/Pagination.vue";

const router = useRouter();

const videos = ref([]);


// 当前页码
const currentPage = ref(1);
// 每页显示条目数
const pageSize = ref(9);
// 当前选中的分类
const selectedCategory = ref('');

// 过滤出状态正常且符合分类条件的视频
const filteredVideos = computed(() => {
  let filtered = videos.value.filter(video => video.status === '0');
  if (selectedCategory.value) {
    filtered = filtered.filter(video => video.category === selectedCategory.value);
  }
  return filtered;
});

// 获取所有不重复的分类
const uniqueCategories = computed(() => {
  const categories = new Set();
  videos.value.forEach(video => {
    if (video.status === '0') {
      categories.add(video.category);
    }
  });
  return Array.from(categories).sort();
});

// 计算当前页要显示的视频列表
const paginatedVideos = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredVideos.value.slice(start, end);
});

// 将时长从秒转换为 分:秒 格式
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

// 当前页码变化时的处理函数
const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
};

const fetchVideos = async () => {
  try {
    const data = await commonRequest('video', 'selectAll');
    videos.value = data;
  } catch (error) {
    ElMessage.error('获取视频信息失败，请稍后重试');
  }
};

// 分类选择变化时重置页码
const filterVideos = () => {
  currentPage.value = 1;
};

onMounted(() => {
  fetchVideos();
});
</script>

<style scoped>
.d-flex {
  display: flex;
}

.justify-end {
  justify-content: flex-end;
}
</style>