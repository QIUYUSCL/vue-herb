<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">浏览历史</span>
        </div>
      </div>
    </div>
    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">浏览历史</h2>
      <div v-if="groupedHistory.length > 0">
        <div v-for="(group, index) in groupedHistory" :key="index">
          <!-- 根据 target_type 显示对应的标题 -->
          <h3 class="text-xl font-semibold text-gray-800 mb-2">
            {{ getTypeName(group[0].target_type) }} 浏览记录
          </h3>
          <!-- 使用 grid 布局，每行显示三个数据 -->
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div
              v-for="item in group"
              :key="item.createTime"
              class="bg-white rounded-xl overflow-hidden shadow-md card-hover p-4 cursor-pointer"
              @click="navigateToDetail(item.target_type, item.target_id)"
            >
              <div v-if="item.target_type === 'HERB'">
                <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ herbInfoMap[item.target_id]?.herb_name || '药材名称加载中...' }}</h3>
                <p class="text-sm text-gray-600">功效: {{ herbInfoMap[item.target_id]?.efficacy || '功效加载中...' }}</p>
                <!-- 修改为 createTime -->
                <p class="text-sm text-gray-600">浏览时间: {{ formatDate(item.createTime) }}</p>
              </div>
              <div v-if="item.target_type === 'VIDEO'">
                <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ videoInfoMap[item.target_id]?.title || '视频标题加载中...' }}</h3>
                <p class="text-sm text-gray-600">描述: {{ videoInfoMap[item.target_id]?.description || '描述加载中...' }}</p>
                <!-- 修改为 createTime -->
                <p class="text-sm text-gray-600">浏览时间: {{ formatDate(item.createTime) }}</p>
              </div>
              <div v-if="item.target_type === 'ARTICLE'">
                <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ articleInfoMap[item.target_id]?.title || '文章标题加载中...' }}</h3>
                <p class="text-sm text-gray-600">内容: {{ articleInfoMap[item.target_id]?.content?.substring(0, 100) || '内容加载中...' }}...</p>
                <!-- 修改为 createTime -->
                <p class="text-sm text-gray-600">浏览时间: {{ formatDate(item.createTime) }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center py-12">
        <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
        <p class="text-gray-500">暂无浏览记录</p>
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
const history = ref([]);
// 存储详细信息
const herbInfoMap = ref({});
const videoInfoMap = ref({});
const articleInfoMap = ref({});

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};


const fetchHistory = async () => {
  const user_id = localStorage.getItem('user_id');
  if (user_id) {
    try {
      const response = await request.get(`/user/history/${user_id}`);
      console.log('获取浏览历史响应数据:', response);
      if (response.code === '200') {
        history.value = response.data;
        // 获取每种类型的详细信息
        await Promise.all(history.value.map(async (item) => {
          switch (item.target_type) {
            case 'HERB':
              if (!herbInfoMap.value[item.target_id]) {
                const herbResponse = await request.get(`/herb/info/selectById/${item.target_id}`);
                if (herbResponse.code === '200') {
                  herbInfoMap.value[item.target_id] = herbResponse.data;
                }
              }
              break;
            case 'VIDEO':
              if (!videoInfoMap.value[item.target_id]) {
                const videoResponse = await request.get(`/video/selectById/${item.target_id}`);
                if (videoResponse.code === '200') {
                  videoInfoMap.value[item.target_id] = videoResponse.data;
                }
              }
              break;
            case 'ARTICLE':
              if (!articleInfoMap.value[item.target_id]) {
                const articleResponse = await request.get(`/daily/selectById/${item.target_id}`);
                if (articleResponse.code === '200') {
                  articleInfoMap.value[item.target_id] = articleResponse.data;
                }
              }
              break;
          }
        }));
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

// 对浏览历史数据进行分组
const groupedHistory = computed(() => {
  const groups = {};
  history.value.forEach(item => {
    if (!groups[item.target_type]) {
      groups[item.target_type] = [];
    }
    groups[item.target_type].push(item);
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
  fetchHistory();
});
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>