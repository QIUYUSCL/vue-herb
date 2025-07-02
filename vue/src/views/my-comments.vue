<template>
  <Header />
  <main class="container mx-auto px-4 py-8">
    <h2 class="text-xl font-bold text-gray-800 mb-4">我的评论</h2>
    <div v-if="comments.length > 0">
      <div v-for="comment in comments" :key="comment.comment_id" class="bg-white p-4 rounded-lg shadow-sm mb-4">
        <p>评论类型: {{ comment.target_type }}</p>
        <p>评论目标 ID: {{ comment.target_id }}</p>
        <p>评论内容: {{ comment.content }}</p>
        <p>评论时间: {{ formatDate(comment.create_time) }}</p>
      </div>
    </div>
    <div v-else class="text-center text-gray-500">暂无评论记录</div>
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

onMounted(() => {
  fetchComments();
});
</script>

<style scoped>

</style>