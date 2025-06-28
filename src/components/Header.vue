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
        <button class="hidden md:block text-gray-700 hover:text-primary transition-colors">
          <i class="fa fa-search text-lg"></i>
        </button>
        <router-link to="/login" class="hidden md:block text-gray-700 hover:text-primary transition-colors">
          <button class="flex items-center justify-center w-10 h-10 rounded-full hover:bg-gray-100 transition-colors">
            <i class="fa fa-user-circle text-lg"></i>
          </button>
        </router-link>
        <button class="md:hidden text-gray-700 hover:text-primary transition-colors">
          <i class="fa fa-bars text-xl"></i>
        </button>
      </div>
    </div>
  </header>
</template>

<script setup>
// 导航栏组件逻辑
</script>

<style scoped>
/* 可添加自定义样式 */
</style>