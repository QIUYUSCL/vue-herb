<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <router-link to="/herbs" class="hover:text-primary">药材大全</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span v-if="herb" class="text-gray-800">{{ herb.herb_name }}</span>
          <span v-else class="text-gray-800">药材详情</span>
        </div>
      </div>
    </div>
    <div class="container mx-auto px-4 py-8">
      <el-button type="success" round :icon="Back" @click="goBack" class="mb-4" />
      <div v-if="loading" class="text-center text-gray-600">加载中...</div>
      <div v-else-if="herb" class="bg-white p-6 rounded-lg shadow-md">
        <img :src="herb.image_url" :alt="herb.herb_name" class="w-full h-64 object-cover rounded-lg mb-4">
        <h2 class="text-2xl font-bold text-gray-800 mb-2">{{ herb.herb_name }}</h2>
        <p class="text-sm text-gray-600 mb-4">{{ herb.description }}</p>
        <div class="flex items-center space-x-2 mb-4">
          <!-- 使用计算属性获取分类名称 -->
          <span
            :class="categoryColors[categoryName] + ' text-xs px-2 py-1 rounded-full'"
          >{{ categoryName }}</span>
          <span
            :class="efficacyColors[getEfficacyKey(herb.efficacy)] + ' text-xs px-2 py-1 rounded-full'"
          >{{ getEfficacyName(herb.efficacy) }}</span>
        </div>
        <div class="flex items-center mb-4">
          <i class="fa fa-star text-yellow-400"></i>
          <span class="ml-1 text-sm text-gray-600">{{ herb.rating }}</span>
        </div>
        <div class="mb-4 herb-info">
          <div class="info-item">
            <span class="info-label">性味：</span>
            <span class="info-content">{{ herb.property }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">归经：</span>
            <span class="info-content">{{ herb.meridian }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">功效：</span>
            <span class="info-content">{{ herb.efficacy }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">主治：</span>
            <span class="info-content">{{ herb.indications }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">用法用量：</span>
            <span class="info-content">{{ herb.usage }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">使用禁忌：</span>
            <span class="info-content">{{ herb.contraindication }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">产地：</span>
            <span class="info-content">{{ herb.production_area }}</span>
          </div>
        </div>
        <div class="flex items-center space-x-4">
          <div class="flex items-center">
            <i class="fa fa-eye text-gray-600"></i>
            <span class="ml-1 text-sm text-gray-600">{{ herb.views }} 次浏览</span>
          </div>
          <div class="flex items-center">
            <i class="fa fa-thumbs-up text-gray-600"></i>
            <span class="ml-1 text-sm text-gray-600">{{ herb.likes }} 点赞</span>
          </div>
          <div class="flex items-center">
            <i class="fa fa-bookmark text-gray-600"></i>
            <span class="ml-1 text-sm text-gray-600">{{ herb.collections }} 收藏</span>
          </div>
        </div>
      </div>
      <div v-else class="text-center py-12">
        <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
        <p class="text-gray-500">未找到该药材信息</p>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { Back } from "@element-plus/icons-vue";
import request from '@/utils/request';
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();
const herb = ref(null);
const loading = ref(true);
const categories = ref([]);
const categoryId = ref(null);

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

// 根据功效描述获取功效 key，基于关键词匹配
const getEfficacyKey = (efficacyName) => {
  const keywordMap = {
    'tonify': ['补', '益', '养'],
    'clear': ['清热', '解毒', '凉血'],
    'activate': ['活血', '通经', '化瘀'],
    'dispel': ['祛湿', '祛风', '除湿'],
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

// 获取功效名称
const getEfficacyName = (efficacy) => {
  const efficacyMap = {
    'tonify': '补虚',
    'clear': '清热',
    'activate': '活血',
    'dispel': '祛湿',
    'resolve': '化痰'
  };
  const key = getEfficacyKey(efficacy);
  return efficacyMap[key] || '';
};

// 计算属性，根据 categoryId 获取分类名称
const categoryName = computed(() => {
  const category = categories.value.find(cat => cat.category_id === categoryId.value);
  return category ? category.category_name : '';
});

// 从 API 获取药材数据
const fetchHerbData = async () => {
  try {
    const herbId = parseInt(route.params.id);
    const response = await request.get(`/herb/info/selectById/${herbId}`);
    if (response.code === "200") {
      herb.value = response.data;
      categoryId.value = response.data.category_id;
      // 调用获取分类信息的接口
      await fetchCategories();
    } else {
      ElMessage.error('获取药材数据失败');
    }
  } catch (error) {
    console.error('获取药材数据失败:', error);
    ElMessage.error('获取药材数据失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

// 获取所有分类信息
const fetchCategories = async () => {
  try {
    const response = await request.get('/herb/category/selectAll');
    if (response.code === "200") {
      categories.value = response.data;
    } else {
      ElMessage.error('获取分类信息失败');
    }
  } catch (error) {
    console.error('获取分类信息失败:', error);
    ElMessage.error('获取分类信息失败，请稍后重试');
  }
};

onMounted(() => {
  fetchHerbData();
});

// 返回上一页的方法
const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
@import '@/assets/herbs.css';

.herb-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem; /* 项之间的间距 */
}

.info-item {
  display: flex;
  align-items: center;
}

.info-label {
  width: 80px; /* 标签固定宽度 */
  font-size: 0.875rem; /* 14px */
  font-weight: 500;
  color: #374151; /* 深灰色 */
}

.info-content {
  flex: 1;
  font-size: 0.875rem; /* 14px */
  color: #6b7280; /* 中灰色 */
}
</style>