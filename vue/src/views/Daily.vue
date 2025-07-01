<template>
  <Header />
  <!-- 面包屑导航 -->
  <div class="bg-gray-100 py-3">
    <div class="container mx-auto px-4">
      <div class="flex items-center text-sm text-gray-600">
        <router-link to="/" class="hover:text-primary">首页</router-link>
        <i class="fa fa-angle-right mx-2 text-gray-400"></i>
        <span class="text-gray-800">每日一学</span>
      </div>
    </div>
  </div>
  <div class="daily-page bg-gray-50 min-h-screen p-8">
    <div  class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
          v-for="article in dailyArticles"
          :key="article.article_id"
          class="bg-white p-6 rounded-lg shadow-md hover:scale-105 hover:shadow-xl transition-transform duration-300 cursor-pointer"
          @click="viewArticle(article.article_id)"
      >
        <div class="relative">
          <img
              v-if="article.cover_image"
              :src="article.cover_image"
              alt="cover image"
              class="w-full h-48 object-cover rounded-lg mb-4"
          >
          <div
              v-if="article.category"
              class="absolute top-2 left-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary"
          >
            {{ article.category }}
          </div>
        </div>
        <h2 class="text-xl font-semibold text-gray-800 mb-2">{{ article.title }}</h2>
        <p class="text-sm text-gray-600 mb-4">{{ article.content.substring(0, 100) }}...</p>
        <div class="flex items-center justify-between text-sm text-gray-600">
          <span>{{ formatDate(article.publish_time) }}</span>
          <div class="flex items-center">
            <i class="fa fa-eye mr-1"></i>
            <span>{{ article.views }}</span>
            <i class="fa fa-thumbs-up ml-3 mr-1"></i>
            <span>{{ article.likes }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useRouter } from 'vue-router';
import Request  from "@/utils/request.js";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";

const router = useRouter();


const loading = ref(true);
const dailyArticles = ref([]);

const load = async () => {
  try{
    const response = await request.get('/daily/selectAll');
    console.log('返回的数据:', response.data); // 打印返回的数据
    if(response.code === "200")
    {
      dailyArticles.value = response.data;
    }else{
      ElMessage.error('加载失败,请重试');
    }
  }catch{
    console.error('请求出错:', error);
    ElMessage.error('加载失败');
  }finally {
    loading.value = false;
  }
}

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleDateString();
};

// 查看文章详情
const viewArticle = (articleId) => {
  router.push({ name: 'DailyDetail', params: { id: articleId } });
};

onMounted(() => {
  load();
});
</script>

<style scoped>
/* 若有需要的自定义样式可继续添加 */
</style>