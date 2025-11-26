<template>
  <Header />
  <!-- 面包屑导航 -->
  <div class="bg-gray-100 py-3">
    <div class="container mx-auto px-4">
      <div class="flex items-center text-sm text-gray-600">
        <router-link to="/" class="hover:text-primary">首页</router-link>
        <i class="fa fa-angle-right mx-2 text-gray-400"></i>
        <span class="text-gray-800">偏方秘方</span>
      </div>
    </div>
  </div>
  <div class="daily-page bg-gray-50 min-h-screen p-8">
    <div  class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
          v-for="article in paginatedArticles"
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
    <Pagination
        :total="dailyArticles.length"
        :page-size="pageSize"
        @page-change="handleCurrentChange"
    />
  </div>
  <Footer />
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useRouter } from 'vue-router';
import {ElMessage} from "element-plus";
import {commonRequest} from "@/utils/commonRequest.js";
import Pagination from "@/components/Pagination.vue";

const router = useRouter();

const loading = ref(true);
const dailyArticles = ref([]);

// 当前页码
const currentPage = ref(1);
// 每页显示条目数
const pageSize = ref(9);

// 计算当前页要显示的文章列表
const paginatedArticles = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return dailyArticles.value.slice(start, end);
});

const fetchArticle = async () => {
  try {
    const data = await commonRequest('article', 'selectAll');
    dailyArticles.value = data;
  } catch (error) {
    ElMessage.error('获取文章信息失败，请稍后重试');
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

// 页码变化时的处理函数
const handleCurrentChange = (page) => {
  currentPage.value = page;
};

onMounted(() => {
  fetchArticle()
});
</script>

<style scoped>
/* 若有需要的自定义样式可继续添加 */
</style>