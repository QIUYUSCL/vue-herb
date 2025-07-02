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
      <h2 class="text-2xl font-bold text-gray-800 mb-6">我的收藏</h2>
      <div v-if="groupedCollections.length > 0">
        <div v-for="(group, index) in groupedCollections" :key="index">
          <!-- 根据 target_type 显示对应的标题 -->
          <h3 class="text-xl font-semibold text-gray-800 mb-2">
            {{ getTypeName(group[0].target_type) }} 收藏
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
                  <img :src="collectionHerbInfo[collection.target_id].image_url" :alt="collectionHerbInfo[collection.target_id].herb_name" class="w-full h-48 object-cover">
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
              <!-- 这里可以添加 VIDEO 和 ARTICLE 类型的显示逻辑 -->
              <div v-if="collection.target_type === 'VIDEO'">
                <!-- 视频显示逻辑，可根据实际需求完善 -->
                <p>视频收藏内容，ID: {{ collection.target_id }}</p>
              </div>
              <div v-if="collection.target_type === 'ARTICLE'">
                <!-- 每日一学显示逻辑，可根据实际需求完善 -->
                <p>每日一学收藏内容，ID: {{ collection.target_id }}</p>
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

const router = useRouter();
const collections = ref([]);
const collectionHerbInfo = ref({});

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
      if (response.code === "200") {
        collections.value = response.data;
        // 遍历收藏记录，获取药材信息
        for (const collection of collections.value) {
          if (collection.target_type === 'HERB') {
            try {
              const herbResponse = await request.get(`/herb/info/selectById/${collection.target_id}`);
              if (herbResponse.code === "200") {
                collectionHerbInfo.value[collection.target_id] = herbResponse.data;
              }
            } catch (error) {
              ElMessage.error(`获取药材 ${collection.target_id} 信息失败，请稍后重试`);
              console.error(`获取药材 ${collection.target_id} 信息出错:`, error);
            }
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
      return '每日一学';
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
  fetchCollections();
});
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>