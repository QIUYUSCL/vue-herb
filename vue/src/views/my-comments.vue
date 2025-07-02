<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">我的评论</span>
        </div>
      </div>
    </div>
    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">我的评论</h2>
      <div v-if="groupedComments.length > 0">
        <div v-for="(group, index) in groupedComments" :key="index">
          <!-- 根据 target_type 显示对应的标题 -->
          <h3 class="text-xl font-semibold text-gray-800 mb-2">
            {{ getTypeName(group[0].target_type) }} 评论记录
          </h3>
          <!-- 使用 grid 布局，每行显示三个数据 -->
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div
                v-for="comment in group"
                :key="comment.comment_id"
                class="bg-white rounded-xl overflow-hidden shadow-md card-hover p-4 cursor-pointer"
                @click="navigateToDetail(comment.target_type, comment.target_id)"
            >
              <p class="text-sm text-gray-600 mb-1">评论内容</p>
              <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ comment.content }}</h3>
              <p class="text-sm text-gray-600">评论时间: {{ formatDate(comment.create_time) }}</p>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center py-12">
        <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
        <p class="text-gray-500">暂无评论记录</p>
      </div>
    </main>
    <Footer />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';

const router = useRouter();
const comments = ref([]);

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};

const fetchComments = async () => {
  const user_id = localStorage.getItem('user_id');
  if (user_id) {
    try {
      const response = await request.get(`/user/comments/${user_id}`);
      if (response.code === '200') {
        comments.value = response.data;
      } else {
        ElMessage.error(response.message);
      }
    } catch (error) {
      ElMessage.error('获取评论记录失败，请稍后重试');
      console.error('获取评论记录出错:', error);
    }
  } else {
    ElMessage.warning('未检测到用户登录信息，请重新登录');
    router.push('/login');
  }
};

// 对评论数据进行分组
const groupedComments = computed(() => {
  const groups = {};
  comments.value.forEach(comment => {
    if (!groups[comment.target_type]) {
      groups[comment.target_type] = [];
    }
    groups[comment.target_type].push(comment);
  });
  return Object.values(groups);
});

// 根据 target_type 获取显示名称
const getTypeName = (type) => {
  switch (type) {
    case 'HERB':
      return '药材';
    case 'VIDEO':
      return '视频';
    case 'ARTICLE':
      return '每日一学';
    default:
      return type;
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
  fetchComments();
});
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>