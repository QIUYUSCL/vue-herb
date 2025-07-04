<template>
  <!-- 添加 flex、flex-col 和 min-h-screen 类，使根元素成为 Flex 容器 -->
  <div class="bg-gray-50 flex flex-col min-h-screen">
    <Header />
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">每日一学</span>
        </div>
      </div>
    </div>
    <main class="container mx-auto px-4 py-8 grid grid-cols-12 gap-6 flex-1">
      <!-- 左侧知识点表格 -->
      <div class="col-span-8 bg-white p-6 py-8 rounded-lg shadow-md">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">每日一学 - 药材知识点</h2>
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-300">
            <thead class="bg-gray-50">
            <tr>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                发布日期
              </th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                药材名称
              </th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                知识点内容
              </th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="knowledge in filteredKnowledgeList" :key="knowledge.id" class="hover:bg-gray-50 transition-colors">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ formatDate(knowledge.publish_date) }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ knowledge.herb_name }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-900">
                {{ knowledge.knowledge_content }}
              </td>
            </tr>
            <tr v-if="filteredKnowledgeList.length === 0">
              <td colspan="3" class="px-6 py-4 text-center text-gray-500">该日期暂无知识点</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 右侧日历 -->
      <div class="col-span-4 bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">选择日期</h2>
        <el-date-picker
            v-model="selectedDate"
            type="date"
            placeholder="选择日期"
            @change="handleDateChange"
            class="w-full"
        />
      </div>
    </main>
    <Footer />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import request from '@/utils/request.js';
import { ElMessage, ElDatePicker } from 'element-plus';

const dailyKnowledgeList = ref([]);
const selectedDate = ref(null);

// 计算属性，根据选择的日期过滤知识点列表
const filteredKnowledgeList = computed(() => {
  if (!selectedDate.value) {
    return dailyKnowledgeList.value;
  }
  const selectedDateStr = new Date(selectedDate.value).toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' }).replace(/\//g, '-');
  return dailyKnowledgeList.value.filter(knowledge => {
    const publishDate = new Date(knowledge.publish_date).toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' }).replace(/\//g, '-');
    return publishDate === selectedDateStr;
  });
});

const fetchDailyKnowledge = async () => {
  try {
    const response = await request.get('/dailyHerbKnowledge/selectAll');
    console.log('响应数据:', response);
    if (Array.isArray(response) && response.length > 0) {
      dailyKnowledgeList.value = response;
    } else {
      ElMessage.error('获取每日药材知识点失败，请稍后重试');
    }
  } catch (error) {
    console.error('请求出错:', error);
    ElMessage.error('获取每日药材知识点失败');
  }
};

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};

const handleDateChange = (date) => {
  selectedDate.value = date;
};

onMounted(() => {
  // 设置默认日期为当天
  selectedDate.value = new Date();
  fetchDailyKnowledge();
});
</script>

<style scoped>
/* 可添加自定义样式 */
</style>