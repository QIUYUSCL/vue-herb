<template>
  <header class="bg-white shadow-md sticky top-0 z-50">
    <div class="container mx-auto px-4 py-4 flex justify-between items-center">
      <div class="flex items-center space-x-2">
        <i class="fa fa-leaf text-primary text-2xl"></i>
        <span class="text-xl font-bold text-gray-800">中药材学习平台</span>
      </div>
      <nav class="hidden md:flex space-x-8">
        <router-link to="/" class="text-gray-700 hover:text-primary transition-colors">首页</router-link>
        <router-link to="/herbs" class="text-gray-700 hover:text-primary transition-colors">药材大全</router-link>
        <router-link to="/remedies" class="text-gray-700 hover:text-primary transition-colors">偏方秘方</router-link>
        <router-link to="/videos" class="text-gray-700 hover:text-primary transition-colors">视频教程</router-link>
        <router-link to="/daily" class="text-gray-700 hover:text-primary transition-colors">每日一学</router-link>
      </nav>
      <div class="flex items-center space-x-4">
        <div v-if="showSearchIcon">
          <el-button
              class="hidden md:block search-icon-btn"
              @click="showSearchIcon = false"
          >
            <i class="fa fa-search search-icon"></i>
          </el-button>
        </div>
        <div v-else class="flex items-center">
          <el-input
              v-model="searchQuery"
              placeholder="请输入搜索内容"
              @keyup.enter="handleSearch"
              class="w-48"
          >
            <template #append>
              <el-button @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
          <el-button
              class="ml-2 text-gray-700 hover:text-primary transition-colors"
              @click="cancelSearch"
          >
            <i class="fa fa-times text-lg"></i>
          </el-button>
        </div>
        <router-link to="/login" class="hidden md:block user-icon-link">
          <button class="user-icon-btn">
            <i class="fa fa-user-circle user-icon"></i>
          </button>
        </router-link>

      </div>
    </div>
  </header>
</template>

<script setup>

import { ref } from 'vue';
import request from "@/utils/request.js";
import router from "@/router/index.js";

// 控制显示搜索图标还是搜索框
const showSearchIcon = ref(true);
// 存储搜索关键词
const searchQuery = ref('');

// 处理搜索逻辑
const handleSearch = async () => {
  if (searchQuery.value.trim()) {
    console.log('搜索关键词:', searchQuery.value.trim());
    // 跳转到搜索结果页面并携带关键词
    router.push({
      name: 'SearchResult',
      query: {keyword: searchQuery.value.trim()}
    });
    // 搜索完成后恢复显示搜索图标
    showSearchIcon.value = true;
  }
};

// 取消搜索，恢复显示图标
const cancelSearch = () => {
  searchQuery.value = '';
  showSearchIcon.value = true;
};

</script>

<style scoped>
.search-icon-btn {
  padding: 0;
  border: none;
  background: transparent;
  transition: transform 0.3s ease;
}

.search-icon-btn:hover {
  transform: scale(1.1);
}

.search-icon {
  font-size: 1.25rem; /* 增大图标尺寸 */
  color: #6b7280; /* 初始颜色 */
  transition: color 0.3s ease;
}

.search-icon-btn:hover .search-icon {
  color: #409eff; /* 悬停时颜色 */
}

.user-icon-link {
  text-decoration: none;
}

.user-icon-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  background-color: transparent;
  border: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.user-icon-btn:hover {
  background-color: #f3f4f6;
  transform: scale(1.1);
}

.user-icon {
  font-size: 1.25rem;
  color: #6b7280;
  transition: color 0.3s ease;
}

.user-icon-btn:hover .user-icon {
  color: #409eff;
}

.mobile-menu-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: transparent;
  border: none;
  padding: 0;
  transition: transform 0.3s ease;
}

.mobile-menu-btn:hover {
  transform: scale(1.1);
}

.mobile-menu-icon {
  font-size: 1.5rem;
  color: #6b7280;
  transition: color 0.3s ease;
}

.mobile-menu-btn:hover .mobile-menu-icon {
  color: #409eff;
}
</style>