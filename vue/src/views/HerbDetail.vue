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
          <span
              :class="categoryColors[getCategoryName(herb.category_id)] + ' text-xs px-2 py-1 rounded-full'"
          >{{ getCategoryName(herb.category_id) }}</span>
          <span
              :class="efficacyColors[herb.efficacy] + ' text-xs px-2 py-1 rounded-full'"
          >{{ getEfficacyName(herb.efficacy) }}</span>
        </div>
        <div class="flex items-center mb-4">
          <i class="fa fa-star text-yellow-400"></i>
          <span class="ml-1 text-sm text-gray-600">{{ herb.rating }}</span>
        </div>
        <div class="mb-4">
          <p class="text-sm font-medium mb-1">性味：{{ herb.property }}</p>
          <p class="text-sm font-medium mb-1">归经：{{ herb.meridian }}</p>
          <p class="text-sm font-medium mb-1">功效：{{ herb.efficacy }}</p>
          <p class="text-sm font-medium mb-1">主治：{{ herb.indications }}</p>
          <p class="text-sm font-medium mb-1">用法用量：{{ herb.usage }}</p>
          <p class="text-sm font-medium mb-1">使用禁忌：{{ herb.contraindication }}</p>
          <p class="text-sm font-medium mb-1">产地：{{ herb.production_area }}</p>
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
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { Back } from "@element-plus/icons-vue";

const route = useRoute();
const router = useRouter();
const herb = ref(null);
const loading = ref(true);

// 分类标签颜色映射
const categoryColors = {
  '根茎类': 'bg-green-100 text-green-800',
  '叶类': 'bg-emerald-100 text-emerald-800',
  '花类': 'bg-pink-100 text-pink-800',
  '果实种子类': 'bg-amber-100 text-amber-800',
  '矿物类': 'bg-gray-100 text-gray-800'
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
  const categoryMap = {
    1: '根茎类',
    2: '叶类',
    3: '果实种子类'
  };
  return categoryMap[categoryId] || '';
};

// 获取功效名称
const getEfficacyName = (efficacy) => {
  const efficacyMap = {
    '补气固表、利尿托毒': '补虚',
    '补血活血、调经止痛': '活血',
    '滋补肝肾、益精明目': '补虚'
  };
  return efficacyMap[efficacy] || '';
};

// 模拟从 API 获取药材数据
const fetchHerbData = async () => {
  try {
    // 模拟 API 请求延迟
    await new Promise(resolve => setTimeout(resolve, 100));
    const herbId = parseInt(route.params.id);
    const allHerbs = [
      {
        herb_id: 1,
        herb_name: '黄芪',
        category_id: 1,
        pinyin: 'huangqi',
        alias: '棉芪、黄耆',
        property: '甘，微温',
        meridian: '脾、肺经',
        efficacy: '补气固表、利尿托毒',
        indications: '气虚乏力、食少便溏、自汗',
        usage: '煎服，9 - 30g；蜜炙可增强补气作用',
        contraindication: '阴虚阳亢者忌用',
        production_area: '内蒙古',
        image_url: 'https://picsum.photos/seed/ginseng/300/200',
        views: 120,
        likes: 35,
        collections: 28,
        status: '0',
        sort: 5,
        create_time: '2025-01-15 08:30:00',
        rating: 4.7
      },
      {
        herb_id: 2,
        herb_name: '当归',
        category_id: 2,
        pinyin: 'danggui',
        alias: '干归、文无',
        property: '甘、辛，温',
        meridian: '肝、心、脾经',
        efficacy: '补血活血、调经止痛',
        indications: '血虚萎黄、月经不调、痛经',
        usage: '煎服，6 - 12g；酒当归活血作用增强',
        contraindication: '湿盛中满、大便溏泄者忌用',
        production_area: '甘肃',
        image_url: 'https://picsum.photos/seed/astragalus/300/200',
        views: 98,
        likes: 27,
        collections: 22,
        status: '0',
        sort: 3,
        create_time: '2025-02-20 10:15:00',
        rating: 4.8
      },
      {
        herb_id: 3,
        herb_name: '枸杞',
        category_id: 3,
        pinyin: 'gouqi',
        alias: '枸杞子、地骨皮',
        property: '甘，平',
        meridian: '肝、肾经',
        efficacy: '滋补肝肾、益精明目',
        indications: '虚劳精亏、腰膝酸痛、眩晕耳鸣',
        usage: '煎服，6 - 12g；亦可泡水、泡酒',
        contraindication: '外邪实热、脾虚有湿者忌用',
        production_area: '宁夏',
        image_url: 'https://picsum.photos/seed/angelica/300/200',
        views: 156,
        likes: 42,
        collections: 35,
        status: '0',
        sort: 8,
        create_time: '2025-03-10 14:20:00',
        rating: 4.6
      }
    ];
    herb.value = allHerbs.find(item => item.herb_id === herbId);
  } catch (error) {
    console.error('获取药材数据失败:', error);
  } finally {
    loading.value = false;
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
</style>