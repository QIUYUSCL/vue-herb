<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />

    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span class="text-gray-800">药材大全</span>
        </div>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-8">
      <!-- 药材大全内容区域 -->
      <section id="herbs-section" class="mb-12">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-2xl font-bold text-gray-800">药材大全</h2>
          <div class="flex items-center space-x-3" >
            <el-select
                v-model="categoryFilter"
                id="herb-category-filter"
                placeholder="全部分类"
                class="w-full"
                size="large"
                style="width: 150px"
            >
              <el-option label="全部分类" value="all" />
              <el-option
                  v-for="category in categories"
                  :key="category.category_id"
                  :label="category.category_name"
                  :value="category.category_id.toString()"
              />
            </el-select>
            <el-select
                v-model="efficacyFilter"
                id="herb-efficacy-filter"
                placeholder="全部功效"
                class="w-full"
                size="large"
                style="width: 150px"
            >
              <el-option label="全部功效" value="all" />
              <el-option label="补虚" value="tonify" />
              <el-option label="清热" value="clear" />
              <el-option label="活血" value="activate" />
              <el-option label="祛湿" value="dispel" />
              <el-option label="止痛" value="resolve" />
            </el-select>
            <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
              <i class="fa fa-chevron-down text-xs"></i>
            </div>
          </div>
        </div>

        <div id="herbs-list" class="herb-list grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <template v-if="paginatedHerbs.length === 0">
            <div class="col-span-full text-center py-12">
              <i class="fa fa-search text-4xl text-gray-300 mb-4"></i>
              <p class="text-gray-500">没有找到匹配的药材</p>
            </div>
          </template>
          <template v-else>
            <router-link
                v-for="herb in paginatedHerbs"
                :key="herb.herb_id"
                :to="`/herbs/${herb.herb_id}`"
                class="herb-card bg-white rounded-xl overflow-hidden shadow-md card-hover"
                :data-id="herb.herb_id"
            >
              <div class="relative">
                <img :src="herb.image_url" :alt="herb.herb_name" class="w-full h-48 object-cover">
                <div class="absolute top-2 left-2">
                  <span
                      :class="categoryColors[getCategoryName(herb.category_id)] + ' text-xs px-2 py-1 rounded-full'"
                  >{{ getCategoryName(herb.category_id) }}</span>
                </div>
                <!-- 由于数据中没有 rating 字段，暂时用 0 替代 -->
                <div class="absolute top-2 right-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary">
                  <i class="fa fa-star text-yellow-400"></i> 0
                </div>
              </div>
              <div class="p-4">
                <p class="text-sm text-gray-600 mb-1">{{ herb.pinyin }}</p>
                <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ herb.herb_name }}</h3>
                <div class="flex items-center space-x-2">
                  <span
                      :class="efficacyColors[getEfficacyKey(herb.efficacy)] + ' text-xs px-2 py-1 rounded-full'"
                  >{{ herb.efficacy }}</span>
                </div>
              </div>
            </router-link>
          </template>
        </div>
        <!-- 分页组件 -->
        <div class="flex justify-center mt-6">
          <el-pagination
              background
              layout="prev, pager, next"
              :total="filteredHerbs.length"
              :page-size="pageSize"
              active-color="#409eff"
              @current-change="handleCurrentChange"
          />
        </div>
      </section>
    </main>
    <!-- 底部导航栏 -->
    <Footer />
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from 'vue';
import Footer from "@/components/Footer.vue";
import Header from "@/components/Header.vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";


// 加载状态
const loading = ref(true);
// 药材数据
const allHerbs = ref([]);
// 药材类别数据
const categories = ref([]);

// 搜索关键词
const searchQuery = ref('');
// 分类筛选
const categoryFilter = ref('all');
// 功效筛选
const efficacyFilter = ref('all');

// 当前页码
const currentPage = ref(1);
// 每页显示条目数
const pageSize = ref(9);

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

// 动态生成功效映射
const generateEfficacyMap = () => {
  const efficacyMap = {};
  const uniqueEfficacies = [...new Set(allHerbs.value.map(herb => herb.efficacy))];
  uniqueEfficacies.forEach((efficacy, index) => {

    efficacyMap[efficacy] = ['tonify', 'clear', 'activate', 'dispel', 'resolve'][index % 5];
  });
  return efficacyMap;
};

// 获取分类名称
const getCategoryName = (categoryId) => {
  const category = categories.value.find(cat => cat.category_id === Number(categoryId));
  return category ? category.category_name : '';
};

// 获取功效名称
const getEfficacyName = (efficacy) => {
  const efficacyMap = generateEfficacyMap();
  const key = efficacyMap[efficacy];
  const nameMap = {
    'tonify': '补虚',
    'clear': '清热',
    'activate': '活血',
    'dispel': '祛湿',
    'resolve': '化痰'
  };
  return nameMap[key] || '';
};

// 根据功效描述获取功效 key，基于关键词匹配
const getEfficacyKey = (efficacyName) => {
  const keywordMap = {
    'tonify': ['补', '益', '养'],
    'clear': ['清热', '解毒', '凉血'],
    'activate': ['活血', '通经', '化瘀'],
    'dispel': ['祛湿', '祛风', '除湿','湿'],
    'resolve': ['止痛','痛']
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

// 计算属性，用于过滤药材列表
const filteredHerbs = computed(() => {
  return allHerbs.value.filter(herb => {
    const matchesSearch = herb.herb_name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        herb.efficacy.toLowerCase().includes(searchQuery.value.toLowerCase());

    const matchesCategory = categoryFilter.value === 'all' || herb.category_id.toString() === categoryFilter.value;

    const matchesEfficacy = efficacyFilter.value === 'all' || getEfficacyKey(herb.efficacy) === efficacyFilter.value;

    return matchesSearch && matchesCategory && matchesEfficacy && herb.status === '0';
  });
});

// 计算属性，用于获取当前页的药材列表
const paginatedHerbs = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredHerbs.value.slice(start, end);
});

// 当前页码变化时的处理函数
const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
};


// 定义 load 函数
const load = async () => {
  try {
    const response = await request.get('/herb/info/selectAll'); // 确认请求路径

    if(response.code === "200") {
      allHerbs.value = response.data;
    }
  } catch (error) {
    console.error('请求出错:', error);
    ElMessage.error('加载失败');
  }
};

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

onMounted(() => {
  load();
  loadCategories();
});


</script>

<style scoped>
@import '@/assets/herbs.css';
</style>