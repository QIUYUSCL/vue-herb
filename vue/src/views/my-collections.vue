<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">我的收藏</span>
        </div>
      </div>
    </div>
    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-8">
      <el-button type="success" round :icon="Back" @click="goBack" class="mb-4" />
      <h2 class="text-2xl font-bold text-gray-800 mb-6">我的收藏</h2>
      <div v-if="groupedCollections.length > 0">
        <div v-for="(group, index) in groupedCollections" :key="index">
          <!-- 根据 target_type 显示对应的标题 -->
          <h3 class="text-xl font-semibold text-gray-800 mb-2">
            {{ getTypeName(group[0].target_type) }}
          </h3>
          <!-- 使用 grid 布局，每行显示三个数据 -->
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div
              v-for="collection in group"
              :key="collection.interaction_id"
              class="bg-white rounded-xl overflow-hidden shadow-md card-hover p-4 cursor-pointer"
              @click="navigateToDetail(collection.target_type, collection.target_id)"
            >
              <div v-if="collection.target_type === 'HERB' && collectionHerbInfo[collection.target_id]">
                <div class="relative">
                  <img :src="collectionHerbInfo[collection.target_id].image_url"
                       :alt="collectionHerbInfo[collection.target_id].herb_name"
                       class="w-full h-48 object-cover"
                  >
                  <div class="absolute top-2 left-2">
                    <span
                      :class="categoryColors[getCategoryName(collectionHerbInfo[collection.target_id].category_id)] + ' text-xs px-2 py-1 rounded-full'"
                    >{{ getCategoryName(collectionHerbInfo[collection.target_id].category_id) }}</span>
                  </div>
                  <div class="absolute top-2 right-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary">
                    <i class="fa fa-star text-yellow-400"></i> 0
                  </div>
                </div>
                <div class="p-4">
                  <p class="text-sm text-gray-600 mb-1">{{ collectionHerbInfo[collection.target_id].pinyin }}</p>
                  <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ collectionHerbInfo[collection.target_id].herb_name }}</h3>
                  <div class="flex items-center space-x-2">
                    <span
                      :class="efficacyColors[getEfficacyKey(collectionHerbInfo[collection.target_id].efficacy)] + ' text-xs px-2 py-1 rounded-full'"
                    >{{ collectionHerbInfo[collection.target_id].efficacy }}</span>
                  </div>
                </div>
              </div>
              <div v-else-if="collection.target_type === 'HERB' && !collectionHerbInfo[collection.target_id]">
                <p class="text-gray-500">加载药材信息中...</p>
              </div>

              <div v-if="collection.target_type === 'VIDEO' && collectionVideoInfo[collection.target_id]">
                <!-- 视频显示逻辑 -->
                <div class="relative">
                  <!-- 显示视频封面图 -->
                  <img :src="collectionVideoInfo[collection.target_id].cover_image"
                       :alt="collectionVideoInfo[collection.target_id].title"
                       class="w-full h-48 object-cover"
                  >
                  <div class="absolute bottom-2 left-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium">
                    <i class="fa fa-play-circle text-red-500"></i>
                  </div>
                </div>
                <div class="p-4">
                  <!-- 显示视频标题 -->
                  <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ collectionVideoInfo[collection.target_id].title }}</h3>
                  <!-- 显示视频描述 -->
                  <p class="text-sm text-gray-600">{{ collectionVideoInfo[collection.target_id].description }}</p>
                  <div class="flex items-center justify-between mt-2">
                    <!-- 显示视频时长 -->
                    <span class="text-sm text-gray-600">{{ formatDuration(collectionVideoInfo[collection.target_id].duration) }}</span>
                    <!-- 显示视频分类 -->
                    <span class="text-sm text-gray-600">分类: {{ collectionVideoInfo[collection.target_id].category }}</span>
                  </div>
                  <div class="flex items-center mt-2">
                    <i class="fa fa-eye text-gray-600"></i>
                    <!-- 显示视频观看次数 -->
                    <span class="ml-1 text-sm text-gray-600">{{ collectionVideoInfo[collection.target_id].views }} 次观看</span>
                    <i class="fa fa-thumbs-up text-gray-600 ml-4"></i>
                    <!-- 显示视频点赞数 -->
                    <span class="ml-1 text-sm text-gray-600">{{ collectionVideoInfo[collection.target_id].likes }} 点赞</span>
                    <i class="fa fa-comment text-gray-600 ml-4"></i>
                    <!-- 显示视频评论数 -->
                    <span class="ml-1 text-sm text-gray-600">{{ collectionVideoInfo[collection.target_id].comments }} 评论</span>
                  </div>
                </div>
              </div>
              <div v-else-if="collection.target_type === 'VIDEO' && !collectionVideoInfo[collection.target_id]">
                <p class="text-gray-500">加载视频信息中...</p>
              </div>
              <div v-if="collection.target_type === 'ARTICLE' && collectionArticleInfo[collection.target_id]">
                <!-- 每日一学显示逻辑 -->
                <div class="relative">
                  <!-- 显示文章封面图 -->
                  <img
                    v-if="collectionArticleInfo[collection.target_id].cover_image"
                    :src="collectionArticleInfo[collection.target_id].cover_image"
                    alt="cover image"
                    class="w-full h-48 object-cover rounded-lg mb-4"
                  >
                  <div
                    v-if="collectionArticleInfo[collection.target_id].category"
                    class="absolute top-2 left-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary"
                  >
                    {{ collectionArticleInfo[collection.target_id].category }}
                  </div>
                </div>
                <div class="p-4">
                  <!-- 显示文章标题 -->
                  <h2 class="text-xl font-semibold text-gray-800 mb-2">{{ collectionArticleInfo[collection.target_id].title }}</h2>
                  <!-- 显示文章部分内容，添加非空检查 -->
                  <p class="text-sm text-gray-600 mb-4">{{ collectionArticleInfo[collection.target_id].content ? collectionArticleInfo[collection.target_id].content.substring(0, 100) : '' }}...</p>
                  <div class="flex items-center justify-between text-sm text-gray-600">
                    <!-- 显示文章发布时间 -->
                    <span>{{ formatDate(collectionArticleInfo[collection.target_id].publish_time) }}</span>
                    <div class="flex items-center">
                      <i class="fa fa-eye mr-1"></i>
                      <!-- 显示文章观看次数 -->
                      <span>{{ collectionArticleInfo[collection.target_id].views }}</span>
                      <i class="fa fa-thumbs-up ml-3 mr-1"></i>
                      <!-- 显示文章点赞数 -->
                      <span>{{ collectionArticleInfo[collection.target_id].likes }}</span>
                    </div>
                  </div>
                </div>
              </div>
              <div v-else-if="collection.target_type === 'ARTICLE' && !collectionArticleInfo[collection.target_id]">
                <p class="text-gray-500">加载文章信息中...</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center py-12">
        <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
        <p class="text-gray-500">暂无收藏记录</p>
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
import {Back} from "@element-plus/icons-vue";

const router = useRouter();
const collections = ref([]);
const collectionHerbInfo = ref({});
const collectionVideoInfo = ref({});
const collectionArticleInfo = ref({});

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

const fetchCollections = async () => {
  const user_id = localStorage.getItem('user_id');
  if (user_id) {
    try {
      const response = await request.get(`/user/collections/${user_id}`);
      console.log('获取收藏记录响应:', response); // 打印收藏记录响应
      if (response.code === "200") {
        collections.value = response.data;
        for (const collection of collections.value) {
          switch (collection.target_type) {
            case 'HERB':
              try {
                const herbResponse = await request.get(`/herb/info/selectById/${collection.target_id}`);
                if (herbResponse.code === "200") {
                  collectionHerbInfo.value[collection.target_id] = herbResponse.data;
                }
              } catch (error) {
                ElMessage.error(`获取药材 ${collection.target_id} 信息失败，请稍后重试`);
                console.error(`获取药材 ${collection.target_id} 信息出错:`, error);
              }
              break;
            case 'VIDEO':
              try {
                const videoResponse = await request.get(`/video/selectById/${collection.target_id}`);
                if (videoResponse.code === "200") {
                  collectionVideoInfo.value[collection.target_id] = videoResponse.data;
                }
              } catch (error) {
                ElMessage.error(`获取视频 ${collection.target_id} 信息失败，请稍后重试`);
                console.error(`获取视频 ${collection.target_id} 信息出错:`, error);
              }
              break;
            case 'ARTICLE':
              try {
                const articleResponse = await request.get(`/daily/selectById/${collection.target_id}`);
                if (articleResponse.code === "200") {
                 collectionArticleInfo.value[collection.target_id] = articleResponse.data;
                }
              } catch (error) {
                ElMessage.error(`获取文章 ${collection.target_id} 信息失败，请稍后重试`);
                console.error(`获取文章 ${collection.target_id} 信息出错:`, error);
              }
              break;
          }
        }
      } else {
        ElMessage.error(response.message);
      }
    } catch (error) {
      ElMessage.error('获取收藏记录失败，请稍后重试');
      console.error('获取收藏记录出错:', error);
    }
  } else {
    ElMessage.warning('未检测到用户登录信息，请重新登录');
    router.push('/login');
  }
};

// 对收藏数据进行分组
const groupedCollections = computed(() => {
  const groups = {};
  collections.value.forEach(collection => {
    if (!groups[collection.target_type]) {
      groups[collection.target_type] = [];
    }
    groups[collection.target_type].push(collection);
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
      return '偏方秘方';
    default:
      return type;
  }
};

// 跳转详情页方法
const navigateToDetail = (targetType, targetId) => {
  loadCategories();
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

onMounted(async () => {
  await loadCategories();
  await fetchCollections();
});

// 将时长从秒转换为 分:秒 格式
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleDateString();
};

// 返回上一页的方法
const goBack = () => {
  router.go(-1);
};

</script>

<style scoped>
@import '@/assets/herbs.css';
</style>