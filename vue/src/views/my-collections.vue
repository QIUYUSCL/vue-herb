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
      <div v-if="collections.length > 0">
        <div v-for="collection in collections" :key="collection.interaction_id" class="bg-white rounded-xl overflow-hidden shadow-md card-hover p-4 mb-4">
          <p class="text-sm text-gray-600 mb-1">收藏类型: {{ collection.target_type }}</p>
          <p class="text-sm text-gray-600 mb-1">收藏目标 ID: {{ collection.target_id }}</p>
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
import { ref, onMounted } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

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
    if (response.code === '200') {
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
      if (response.code === '200') {
        collections.value = response.data;
        // 遍历收藏记录，获取药材信息
        for (const collection of collections.value) {
          if (collection.target_type === 'HERB') {
            try {
              const herbResponse = await request.get(`/herb/info/selectById/${collection.target_id}`);
              if (herbResponse.code === '200') {
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

onMounted(async () => {
  await loadCategories();
  fetchCollections();
});
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>