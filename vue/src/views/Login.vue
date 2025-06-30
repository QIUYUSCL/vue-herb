<template>
  <div class="bg-gray-50 min-h-screen">
<!--   顶部导航栏-->
    <Header />
    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-16 md:py-24">
      <div class="max-w-md mx-auto">
        <div class="bg-white rounded-2xl shadow-lg overflow-hidden card-hover">
          <div class="bg-gradient-green p-6 text-white text-center">
            <h2 class="text-2xl font-bold mb-2">欢迎回来</h2>
            <p class="opacity-80">登录您的账户，探索更多中药材知识</p>
          </div>

          <div class="p-6">
            <form @submit.prevent="handleLogin" class="space-y-4">
              <div>
                <label for="login-email" class="block text-sm font-medium text-gray-700 mb-1">邮箱</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-envelope-o"></i>
                  </span>
                  <input
                      type="email"
                      id="login-email"
                      v-model="email"
                      placeholder="请输入您的邮箱"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                </div>
              </div>

              <div>
                <label for="login-password" class="block text-sm font-medium text-gray-700 mb-1">密码</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-lock"></i>
                  </span>
                  <input
                      :type="showPassword ? 'text' : 'password'"
                      id="login-password"
                      v-model="password"
                      placeholder="请输入您的密码"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                  <button
                      type="button"
                      @click="togglePassword"
                      class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-400 hover:text-gray-600"
                  >
                    <i :class="showPassword ? 'fa fa-eye' : 'fa fa-eye-slash'"></i>
                  </button>
                </div>
              </div>

              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <input
                      type="checkbox"
                      id="remember-me"
                      class="form-checkbox h-4 w-4 text-primary focus:ring-primary"
                  />
                  <label for="remember-me" class="ml-2 block text-sm text-gray-700">记住我</label>
                </div>
                <router-link to="/forgot-password" class="text-sm text-primary hover:underline">忘记密码？</router-link>
              </div>

              <button
                  type="submit"
                  class="w-full bg-primary text-white py-2 px-4 rounded-lg font-medium hover:bg-secondary transition-colors"
              >
                登录
              </button>
            </form>

            <div class="mt-4 text-center text-sm text-gray-600">
              还没有账户？<router-link to="/register" class="text-primary hover:underline">立即注册</router-link>
            </div>
          </div>
        </div>
      </div>
    </main>

<!--    页脚-->
    <Footer/>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import router from '@/router/index.js';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { ElMessage } from 'element-plus';

const email = ref('');
const password = ref('');
const showPassword = ref(false);

const handleLogin = async () => {
  if (!email.value || !password.value) {
    ElMessage.error('邮箱和密码不能为空');
    return;
  }
  try {
    // 模拟登录请求
    await new Promise(resolve => setTimeout(resolve, 1500));
    console.log('邮箱:', email.value);
    console.log('密码:', password.value);
    // 登录成功后跳转到首页
    router.push('/user-center');
    ElMessage.success('登录成功');
  } catch (error) {
    ElMessage.error('登录失败，请稍后重试');
  }
};

const togglePassword = () => {
  showPassword.value = !showPassword.value;
};
</script>

<style scoped>
@import "@/assets/login.css";
</style>