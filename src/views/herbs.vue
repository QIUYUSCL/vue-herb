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
              <el-option label="化痰" value="resolve" />
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
                <div class="absolute top-2 right-2 bg-white/90 rounded-full px-2 py-1 text-sm font-medium text-primary">
                  <i class="fa fa-star text-yellow-400"></i> {{ herb.rating }}
                </div>
              </div>
              <div class="p-4">
                <p class="text-sm text-gray-600 mb-1">{{ herb.pinyin }}</p>
                <h3 class="text-xl font-semibold text-gray-800 mb-2">{{ herb.herb_name }}</h3>
                <p class="text-sm text-gray-600 mb-4">{{ herb.description }}</p>
                <div class="flex items-center space-x-2">
                  <span
                      :class="efficacyColors[herb.efficacy] + ' text-xs px-2 py-1 rounded-full'"
                  >{{ getEfficacyName(herb.efficacy) }}</span>
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
import { ref, computed, onMounted } from 'vue';
import Footer from "@/components/Footer.vue";
import Header from "@/components/Header.vue";

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
  const category = categories.value.find(cat => cat.category_id === Number(categoryId));
  return category ? category.category_name : '';
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
  return efficacyMap[efficacy] || '';
};

// 计算属性，用于过滤药材列表
const filteredHerbs = computed(() => {
  return allHerbs.value.filter(herb => {
    const matchesSearch = herb.herb_name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        herb.description.toLowerCase().includes(searchQuery.value.toLowerCase());

    const matchesCategory = categoryFilter.value === 'all' || herb.category_id.toString() === categoryFilter.value;

    // 直接比较 efficacyFilter 的值
    const matchesEfficacy = efficacyFilter.value === 'all' || herb.efficacy === efficacyFilter.value;

    return matchesSearch && matchesCategory && matchesEfficacy && herb.status === '0';
  });
});

// 根据功效名称获取功效 key
const getEfficacyKey = (efficacyName) => {
  const efficacyMap = {
    '补虚': 'tonify',
    '清热': 'clear',
    '活血': 'activate',
    '祛湿': 'dispel',
    '化痰': 'resolve'
  };
  return efficacyMap[efficacyName] || '';
};

// 计算属性，用于获取当前页的药材列表
const paginatedHerbs = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredHerbs.value.slice(start, end);
});

// 当前页码变化时的处理函数
const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
  console.log('Current page changed to:', newPage); // 添加日志，检查页码是否正确更新
};

// 模拟从 API 获取数据
const fetchData = async () => {
  try {
    // 模拟 API 请求延迟
    await new Promise(resolve => setTimeout(resolve, 100));
    allHerbs.value = [
      { herb_id: 1, herb_name: '黄芪', category_id: 1, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/ginseng/300/200', rating: 4.9, description: '大补元气，复脉固脱，补脾益肺，生津养血，安神益智。', pinyin: 'rén shēn', status: '0' },
      { herb_id: 2, herb_name: '当归', category_id: 1, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/astragalus/300/200', rating: 4.7, description: '补气升阳，固表止汗，利水消肿，生津养血，行滞通痹，托毒排脓，敛疮生肌。', pinyin: 'huáng qí', status: '0' },
      { herb_id: 3, herb_name: '枸杞', category_id: 1, efficacy: 'activate', image_url: 'https://picsum.photos/seed/angelica/300/200', rating: 4.8, description: '补血活血，调经止痛，润肠通便。', pinyin: 'dāng guī', status: '0' },
      { herb_id: 4, herb_name: '人参', category_id: 4, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/wolfberry/300/200', rating: 4.6, description: '滋补肝肾，益精明目。', pinyin: 'gǒu qǐ', status: '0' },
      { herb_id: 5, herb_name: '金银花', category_id: 3, efficacy: 'clear', image_url: 'https://picsum.photos/seed/honeysuckle/300/200', rating: 4.5, description: '清热解毒，疏散风热。', pinyin: 'jīn yín huā', status: '0' },
      { herb_id: 6, herb_name: '菊花', category_id: 3, efficacy: 'clear', image_url: 'https://picsum.photos/seed/chrysanthemum/300/200', rating: 4.4, description: '散风清热，平肝明目，清热解毒。', pinyin: 'jú huā', status: '0' },
      { herb_id: 7, herb_name: '茯苓', category_id: 1, efficacy: 'dispel', image_url: 'https://picsum.photos/seed/poria/300/200', rating: 4.3, description: '利水渗湿，健脾，宁心。', pinyin: 'fú líng', status: '0' },
      { herb_id: 8, herb_name: '白术', category_id: 1, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/atractylodes/300/200', rating: 4.2, description: '健脾益气，燥湿利水，止汗，安胎。', pinyin: 'bái zhú', status: '0' },
      { herb_id: 9, herb_name: '甘草', category_id: 1, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/licorice/300/200', rating: 4.1, description: '补脾益气，清热解毒，祛痰止咳，缓急止痛，调和诸药。', pinyin: 'gān cǎo', status: '0' },
      { herb_id: 10, herb_name: '大黄', category_id: 1, efficacy: 'clear', image_url: 'https://picsum.photos/seed/rhubarb/300/200', rating: 4.0, description: '泻下攻积，清热泻火，凉血解毒，逐瘀通经，利湿退黄。', pinyin: 'dà huáng', status: '0' },
      { herb_id: 11, herb_name: '陈皮', category_id: 4, efficacy: 'resolve', image_url: 'https://picsum.photos/seed/tangerine/300/200', rating: 4.7, description: '理气健脾，燥湿化痰。', pinyin: 'chén pí', status: '0' },
      { herb_id: 12, herb_name: '麻黄', category_id: 2, efficacy: 'clear', image_url: 'https://picsum.photos/seed/ephedra/300/200', rating: 4.3, description: '发汗散寒，宣肺平喘，利水消肿。', pinyin: 'má huáng', status: '0' },
      { herb_id: 13, herb_name: '薄荷', category_id: 2, efficacy: 'clear', image_url: 'https://picsum.photos/seed/mint/300/200', rating: 4.5, description: '疏散风热，清利头目，利咽透疹，疏肝行气。', pinyin: 'bò he', status: '0' },
      { herb_id: 14, herb_name: '三七', category_id: 1, efficacy: 'activate', image_url: 'https://picsum.photos/seed/panax/300/200', rating: 4.9, description: '散瘀止血，消肿定痛。', pinyin: 'sān qī', status: '0' },
      { herb_id: 15, herb_name: '天麻', category_id: 1, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/gastrodia/300/200', rating: 4.6, description: '息风止痉，平抑肝阳，祛风通络。', pinyin: 'tiān má', status: '0' },
      { herb_id: 16, herb_name: '川贝母', category_id: 1, efficacy: 'resolve', image_url: 'https://picsum.photos/seed/fritillaria/300/200', rating: 4.8, description: '清热润肺，化痰止咳，散结消痈。', pinyin: 'chuān bèi mǔ', status: '0' },
      { herb_id: 17, herb_name: '黄连', category_id: 1, efficacy: 'clear', image_url: 'https://picsum.photos/seed/coptis/300/200', rating: 4.4, description: '清热燥湿，泻火解毒。', pinyin: 'huáng lián', status: '0' },
      { herb_id: 18, herb_name: '杜仲', category_id: 2, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/eucommia/300/200', rating: 4.5, description: '补肝肾，强筋骨，安胎。', pinyin: 'dù zhòng', status: '0' },
      { herb_id: 19, herb_name: '麦冬', category_id: 1, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/ophiopogon/300/200', rating: 4.3, description: '养阴生津，润肺清心。', pinyin: 'mài dōng', status: '0' },
      { herb_id: 20, herb_name: '龙骨', category_id: 5, efficacy: 'tonify', image_url: 'https://picsum.photos/seed/dragonbone/300/200', rating: 4.2, description: '镇惊安神，平肝潜阳，收敛固涩。', pinyin: 'lóng gǔ', status: '0' }
    ];
    categories.value = [
      { category_id: 1, category_name: '根茎类', parent_id: 0, description: '', status: '0' },
      { category_id: 2, category_name: '叶类', parent_id: 0, description: '', status: '0' },
      { category_id: 3, category_name: '花类', parent_id: 0, description: '', status: '0' },
      { category_id: 4, category_name: '果实种子类', parent_id: 0, description: '', status: '0' },
      { category_id: 5, category_name: '矿物类', parent_id: 0, description: '', status: '0' }
    ];
  } catch (error) {
    console.error('获取数据失败:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchData();
});
</script>

<style scoped>
@import '@/assets/herbs.css';
</style>