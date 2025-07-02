<template>
  <Header />
  <main class="container mx-auto px-4 py-8">
    <h2 class="text-xl font-bold text-gray-800 mb-4">浏览历史</h2>
    <div v-if="history.length > 0">
      <div v-for="item in history" :key="item.create_time" class="bg-white p-4 rounded-lg shadow-sm mb-4">
        <p>浏览类型: {{ item.target_type }}</p>
        <p>浏览目标 ID: {{ item.target_id }}</p>
        <p>浏览时间: {{ formatDate(item.create_time) }}</p>
      </div>
    </div>
    <div v-else class="text-center text-gray-500">暂无浏览记录</div>
  </main>
  <Footer />
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter();
const history = ref([]);

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};

const fetchHistory = async () => {
  const user_id = localStorage.getItem('user_id');
  if (user_id) {
    try {
      const response = await request.get(`/user/history/${user_id}`);
      if (response.code === '200') {
        history.value = response.data;
      } else {
        ElMessage.error(response.message);
      }
    } catch (error) {
      ElMessage.error('获取浏览历史失败，请稍后重试');
      console.error('获取浏览历史出错:', error);
    }
  } else {
    ElMessage.warning('未检测到用户登录信息，请重新登录');
    router.push('/login');
  }
};

onMounted(() => {
  fetchHistory();
});
</script>

<style scoped>

</style>