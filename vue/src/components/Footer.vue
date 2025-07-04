<template>
  <footer class="bg-gray-800 text-white py-8">
    <div class="container mx-auto px-4">
      <div class="flex flex-wrap justify-between">
        <div class="w-full md:w-1/3 mb-6 md:mb-0">
          <h3 class="text-xl font-bold mb-4">中药材学习平台</h3>
          <p class="text-sm text-gray-400">探索中医药文化，了解自然草药的奥秘。</p>
        </div>
        <div class="w-full md:w-1/3 mb-6 md:mb-0">
          <h3 class="text-xl font-bold mb-4">快速链接</h3>
          <ul class="text-sm">
            <li class="mb-2">
              <router-link to="/" class="hover:text-primary transition-colors">首页</router-link>
            </li>
            <li class="mb-2">
              <router-link to="/herbs" class="hover:text-primary transition-colors">药材大全</router-link>
            </li>
            <li class="mb-2">
              <router-link to="/videos" class="hover:text-primary transition-colors">视频教程</router-link>
            </li>
            <li class="mb-2">
              <router-link to="/daily" class="hover:text-primary transition-colors">偏方秘方</router-link>
            </li>
            <li class="mb-2">
              <router-link to="/daily-herb-knowledge" class="hover:text-primary transition-colors">每日一学</router-link>
            </li>
          </ul>
        </div>
        <div class="w-full md:w-1/3">
          <h3 class="text-xl font-bold mb-4">联系我们</h3>
          <p class="text-sm text-gray-400 mb-2">邮箱: info@herblearning.com</p>
          <p class="text-sm text-gray-400">电话: +86 123 456 7890</p>
        </div>
      </div>
      <div class="mt-8 border-t border-gray-700 pt-6 text-center text-sm text-gray-400">
        &copy; 2025 中药材学习平台. 保留所有权利.
      </div>
    </div>
  </footer>
</template>

<script setup>
// 页脚组件逻辑
</script>

<style scoped>
/* 可添加自定义样式 */
</style>