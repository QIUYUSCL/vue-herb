<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">我的点赞</span>
        </div>
      </div>
    </div>
    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-8">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">我的点赞</h2>
      <div v-if="groupedLikes.length > 0">
        <div v-for="(group, index) in groupedLikes" :key="index">
          <!-- 根据 target_type 显示对应的标题 -->
          <h3 class="text-xl font-semibold text-gray-800 mb-2">
            {{ getTypeName(group[0].target_type) }}
          </h3>
          <!-- 使用 grid 布局，每行显示三个数据 -->
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div
                v-for="like in group"
                :key="like.interaction_id"
                class="bg-white rounded-xl overflow-hidden shadow-md card-hover p-4 cursor-pointer"
                @click="navigateToDetail(like.target_type, like.target_id)"
            >
              <div v-if="like.target_type === 'HERB' && likeHerbInfo[like.target_id]">
                <div class="relative">
                  <img :src="likeHerbInfo[like.target_id].image_url"
                       :alt="likeHerbInfo[like.target_id].herb_name"
                       class="w-full h-48 object-cover"
                  >
                  <div class="absolute top-2 left-2">
                    <span
                        :class="categoryColors[getCategoryName(likeHerbInfo[like.target_id].category_id)] + ' text-xs px-2 py-1 rounded-full'"
                    >{{ getCategoryName(likeHerbInfo[like.target_id].category_id) }}</span>
                  </div>
                  <div class="absolute top-2 right-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary">
                    <i class="fa fa-star text-yellow-400"></i> 0
                  </div>
                </div>
                <div class="p-4">
                  <p class="text-sm text-gray-600 mb-1">{{ likeHerbInfo[like.target_id].pinyin }}</p>
                  <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ likeHerbInfo[like.target_id].herb_name }}</h3>
                  <div class="flex items-center space-x-2">
                    <span
                        :class="efficacyColors[getEfficacyKey(likeHerbInfo[like.target_id].efficacy)] + ' text-xs px-2 py-1 rounded-full'"
                    >{{ likeHerbInfo[like.target_id].efficacy }}</span>
                  </div>
                </div>
              </div>
              <div v-else-if="like.target_type === 'HERB' && !likeHerbInfo[like.target_id]">
                <p class="text-gray-500">加载药材信息中...</p>
              </div>

              <div v-if="like.target_type === 'VIDEO' && likeVideoInfo[like.target_id]">
                <!-- 视频显示逻辑 -->
                <div class="relative">
                  <!-- 显示视频封面图 -->
                  <img :src="likeVideoInfo[like.target_id].cover_image"
                       :alt="likeVideoInfo[like.target_id].title"
                       class="w-full h-48 object-cover"
                  >
                  <div class="absolute bottom-2 left-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium">
                    <i class="fa fa-play-circle text-red-500"></i>
                  </div>
                </div>
                <div class="p-4">
                  <!-- 显示视频标题 -->
                  <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ likeVideoInfo[like.target_id].title }}</h3>
                  <!-- 显示视频描述 -->
                  <p class="text-sm text-gray-600">{{ likeVideoInfo[like.target_id].description }}</p>
                  <div class="flex items-center justify-between mt-2">
                    <!-- 显示视频时长 -->
                    <span class="text-sm text-gray-600">{{ formatDuration(likeVideoInfo[like.target_id].duration) }}</span>
                    <!-- 显示视频分类 -->
                    <span class="text-sm text-gray-600">分类: {{ likeVideoInfo[like.target_id].category }}</span>
                  </div>
                  <div class="flex items-center mt-2">
                    <i class="fa fa-eye text-gray-600"></i>
                    <!-- 显示视频观看次数 -->
                    <span class="ml-1 text-sm text-gray-600">{{ likeVideoInfo[like.target_id].views }} 次观看</span>
                    <i class="fa fa-thumbs-up text-gray-600 ml-4"></i>
                    <!-- 显示视频点赞数 -->
                    <span class="ml-1 text-sm text-gray-600">{{ likeVideoInfo[like.target_id].likes }} 点赞</span>
                    <i class="fa fa-comment text-gray-600 ml-4"></i>
                    <!-- 显示视频评论数 -->
                    <span class="ml-1 text-sm text-gray-600">{{ likeVideoInfo[like.target_id].comments }} 评论</span>
                  </div>
                </div>
              </div>
              <div v-else-if="like.target_type === 'VIDEO' && !likeVideoInfo[like.target_id]">
                <p class="text-gray-500">加载视频信息中...</p>
              </div>
              <div v-if="like.target_type === 'ARTICLE' && likeArticleInfo[like.target_id]">
                <!-- 每日一学显示逻辑 -->
                <div class="relative">
                  <!-- 显示文章封面图 -->
                  <img
                      v-if="likeArticleInfo[like.target_id].cover_image"
                      :src="likeArticleInfo[like.target_id].cover_image"
                      alt="cover image"
                      class="w-full h-48 object-cover rounded-lg mb-4"
                  >
                  <div
                      v-if="likeArticleInfo[like.target_id].category"
                      class="absolute top-2 left-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary"
                  >
                    {{ likeArticleInfo[like.target_id].category }}
                  </div>
                </div>
                <div class="p-4">
                  <!-- 显示文章标题 -->
                  <h2 class="text-xl font-semibold text-gray-800 mb-2">{{ likeArticleInfo[like.target_id].title }}</h2>
                  <!-- 显示文章部分内容，添加非空检查 -->
                  <p class="text-sm text-gray-600 mb-4">{{ likeArticleInfo[like.target_id].content ? likeArticleInfo[like.target_id].content.substring(0, 100) : '' }}...</p>
                  <div class="flex items-center justify-between text-sm text-gray-600">
                    <!-- 显示文章发布时间 -->
                    <span>{{ formatDate(likeArticleInfo[like.target_id].publish_time) }}</span>
                    <div class="flex items-center">
                      <i class="fa fa-eye mr-1"></i>
                      <!-- 显示文章观看次数 -->
                      <span>{{ likeArticleInfo[like.target_id].views }}</span>
                      <i class="fa fa-thumbs-up ml-3 mr-1"></i>
                      <!-- 显示文章点赞数 -->
                      <span>{{ likeArticleInfo[like.target_id].likes }}</span>
                    </div>
                  </div>
                </div>
              </div>
              <div v-else-if="like.target_type === 'ARTICLE' && !likeArticleInfo[like.target_id]">
                <p class="text-gray-500">加载文章信息中...</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center py-12">
        <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
        <p class="text-gray-500">暂无点赞记录</p>
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
const likes = ref([]);
const likeHerbInfo = ref({});
const likeVideoInfo = ref({});
const likeArticleInfo = ref({});

// 分类标签颜色映射
const categoryColors = {
  '解表药': 'bg-green-100 text-green-800',
  '清热药': 'bg-emerald-100 text-emerald-800',
  '祛风湿药': 'bg-pink-100 text-pink-800',
  '补虚药': 'bg-amber-100 text-amber-800',
};

// 功效标签颜色映射
const efficacyColors = {
  'tonify': 'bg-blue-100 text-blue-800',
  'clear': 'bg-red-100 text-red-800',
  'activate': 'bg-purple-100 text-purple-800',
  'dispel': 'bg-teal-100 text-teal-800',
  'resolve': 'bg-indigo-100 text-indigo-800'
};

// 获取分类名称
const getCategoryName = (categoryId) => {
  const category = categories.value.find(cat => cat.category_id === Number(categoryId));
  return category ? category.category_name : '';
};

// 根据功效描述获取功效 key，基于关键词匹配
const getEfficacyKey = (efficacyName) => {
  const keywordMap = {
    'tonify': ['补', '益', '养'],
    'clear': ['清热', '解毒', '凉血'],
    'activate': ['活血', '通经', '化瘀'],
    'dispel': ['祛湿', '祛风', '除湿', '湿'],
    'resolve': ['止痛', '痛']
  };

  for (const [key, keywords] of Object.entries(keywordMap)) {
    for (const keyword of keywords) {
      if (efficacyName.includes(keyword)) {
        return key;
      }
    }
  }
  return '';
};

// 药材类别数据
const categories = ref([]);

// 定义 loadCategories 函数，获取药材种类数据
const loadCategories = async () => {
  try {
    const response = await request.get('/herb/category/selectAll');
    if (response.code === "200") {
      categories.value = response.data;
    } else {
      ElMessage.error('药材种类数据加载失败，请稍后重试');
    }
  } catch (error) {
    console.error('请求出错:', error);
    ElMessage.error('药材种类数据加载失败');
  }
};

const fetchLikes = async () => {
  const user_id = localStorage.getItem('user_id');
  if (user_id) {
    try {
      const response = await request.get(`/user/likes/${user_id}`);
      console.log('获取点赞记录响应:', response);
      if (response.code === "200") {
        likes.value = response.data;
        for (const like of likes.value) {
          switch (like.target_type) {
            case 'HERB':
              try {
                const herbResponse = await request.get(`/herb/info/selectById/${like.target_id}`);
                if (herbResponse.code === "200") {
                  likeHerbInfo.value[like.target_id] = herbResponse.data;
                }
              } catch (error) {
                ElMessage.error(`获取药材 ${like.target_id} 信息失败，请稍后重试`);
                console.error(`获取药材 ${like.target_id} 信息出错:`, error);
              }
              break;
            case 'VIDEO':
              try {
                const videoResponse = await request.get(`/video/selectById/${like.target_id}`);
                if (videoResponse.code === "200") {
                  likeVideoInfo.value[like.target_id] = videoResponse.data;
                }
              } catch (error) {
                ElMessage.error(`获取视频 ${like.target_id} 信息失败，请稍后重试`);
                console.error(`获取视频 ${like.target_id} 信息出错:`, error);
              }
              break;
            case 'ARTICLE':
              try {
                const articleResponse = await request.get(`/daily/selectById/${like.target_id}`);
                if (articleResponse.code === "200") {
                  likeArticleInfo.value[like.target_id] = articleResponse.data;
                }
              } catch (error) {
                ElMessage.error(`获取文章 ${like.target_id} 信息失败，请稍后重试`);
                console.error(`获取文章 ${like.target_id} 信息出错:`, error);
              }
              break;
          }
        }
      }
    } catch (error) {
      ElMessage.error('获取点赞记录失败，请稍后重试');
      console.error('获取点赞记录出错:', error);
    }
  } else {
    ElMessage.warning('未检测到用户登录信息，请重新登录');
    router.push('/login');
  }
};

// 对点赞数据进行分组
const groupedLikes = computed(() => {
  const groups = {};
  likes.value.forEach(like => {
    if (!groups[like.target_type]) {
      groups[like.target_type] = [];
    }
    groups[like.target_type].push(like);
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

// 将时长从秒转换为 分:秒 格式
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};

onMounted(() => {
  loadCategories();
  fetchLikes();
});
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>