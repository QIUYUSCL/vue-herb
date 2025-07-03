<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">用户主页</span>
        </div>
      </div>
    </div>
    <main class="container mx-auto px-4 py-8">
      <!-- 返回按钮 -->
      <el-button type="success" round :icon="Back" @click="goBack" class="mb-4" />
      <div v-if="userInfo">
        <div class="bg-white rounded-2xl shadow-sm p-6">
          <div class="flex items-center">
            <img
                :src="userInfo.avatar"
                alt="用户头像"
                class="w-24 h-24 rounded-full border-2 border-gray-300 object-cover"
            />
            <div class="ml-6">
              <h2 class="text-2xl font-bold text-gray-800">{{ userInfo.username }}</h2>
            </div>
          </div>
          <div class="mt-8">
            <h3 class="text-xl font-semibold text-gray-800 mb-4">互动统计</h3>
            <div class="grid grid-cols-3 gap-4">
              <div class="bg-gray-100 p-4 rounded-lg">
                <p class="text-lg font-semibold text-gray-800">{{ userStats.likeCount }}</p>
                <p class="text-sm text-gray-600">点赞数</p>
              </div>
              <div class="bg-gray-100 p-4 rounded-lg">
                <p class="text-lg font-semibold text-gray-800">{{ userStats.collectCount }}</p>
                <p class="text-sm text-gray-600">收藏数</p>
              </div>
              <div class="bg-gray-100 p-4 rounded-lg">
                <p class="text-lg font-semibold text-gray-800">{{ userStats.commentCount }}</p>
                <p class="text-sm text-gray-600">评论数</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center py-12">
        <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
        <p class="text-gray-500">未找到用户信息</p>
      </div>
    </main>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Back } from "@element-plus/icons-vue"; // 引入返回图标
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter(); // 获取路由实例
const userInfo = ref(null);
const userStats = ref({
  likeCount: 0,
  collectCount: 0,
  commentCount: 0
});

// 返回上一页的方法
const goBack = () => {
  router.go(-1);
};

const fetchUserInfo = async () => {
  const userId = route.params.id;
  if (userId) {
    try {
      // 获取用户基本信息
      const userInfoResponse = await request.get(`/user/selectById/${userId}`);
      if (userInfoResponse.code === '200') {
        userInfo.value = userInfoResponse.data;
      } else {
        ElMessage.error(userInfoResponse.message);
      }

      // 获取用户互动统计信息
      const userStatsResponse = await request.get(`/user/interaction-count/${userId}`);
      if (userStatsResponse.code === '200') {
        userStats.value = userStatsResponse.data;
      } else {
        ElMessage.error(userStatsResponse.message);
      }
    } catch (error) {
      ElMessage.error('获取用户信息失败，请稍后重试');
      console.error('获取用户信息出错:', error);
    }
  }
};


onMounted(() => {
  fetchUserInfo();
});
</script>