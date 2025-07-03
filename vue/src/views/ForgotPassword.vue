<template>
  <div class="bg-gray-50 min-h-screen">
    <!-- 顶部导航栏 -->
    <Header />
    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-16 md:py-24">
      <div class="max-w-md mx-auto">
        <div class="bg-white rounded-2xl shadow-lg overflow-hidden card-hover">
          <div class="bg-gradient-green p-6 text-white text-center">
            <h2 class="text-2xl font-bold mb-2">忘记密码</h2>
            <p class="opacity-80">请输入您的电话号码和邮箱来重置密码</p>
          </div>

          <div class="p-6">
            <form @submit.prevent="handleResetPassword" class="space-y-4">
              <div>
                <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">电话号码</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-phone"></i>
                  </span>
                  <input
                      type="text"
                      id="phone"
                      v-model="phone"
                      placeholder="请输入您的电话号码"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                </div>
              </div>

              <div>
                <label for="email" class="block text-sm font-medium text-gray-700 mb-1">邮箱</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-envelope"></i>
                  </span>
                  <input
                      type="email"
                      id="email"
                      v-model="email"
                      placeholder="请输入您的邮箱"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                </div>
              </div>

              <div>
                <label for="new-password" class="block text-sm font-medium text-gray-700 mb-1">新密码</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-lock"></i>
                  </span>
                  <input
                      :type="showNewPassword ? 'text' : 'password'"
                      id="new-password"
                      v-model="newPassword"
                      placeholder="请输入新密码"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                  <button
                      type="button"
                      @click="toggleNewPassword"
                      class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-400 hover:text-gray-600"
                  >
                    <i :class="showNewPassword ? 'fa fa-eye' : 'fa fa-eye-slash'"></i>
                  </button>
                </div>
              </div>

              <div>
                <label for="confirm-password" class="block text-sm font-medium text-gray-700 mb-1">确认新密码</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-lock"></i>
                  </span>
                  <input
                      :type="showConfirmPassword ? 'text' : 'password'"
                      id="confirm-password"
                      v-model="confirmPassword"
                      placeholder="请再次输入新密码"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                  <button
                      type="button"
                      @click="toggleConfirmPassword"
                      class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-400 hover:text-gray-600"
                  >
                    <i :class="showConfirmPassword ? 'fa fa-eye' : 'fa fa-eye-slash'"></i>
                  </button>
                </div>
              </div>

              <button
                  type="submit"
                  class="w-full bg-primary text-white py-2 px-4 rounded-lg font-medium hover:bg-secondary transition-colors"
              >
                重置密码
              </button>
            </form>

            <div class="mt-4 text-center text-sm text-gray-600">
              记得密码了？<router-link to="/login" class="text-primary hover:underline">返回登录</router-link>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- 页脚 -->
    <Footer />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import router from '@/router/index.js';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { ElMessage } from 'element-plus';
import request from '@/utils/request.js';

const phone = ref('');
const email = ref('');
const newPassword = ref('');
const confirmPassword = ref('');
const showNewPassword = ref(false);
const showConfirmPassword = ref(false);

const handleResetPassword = async () => {
  if (!phone.value || !email.value || !newPassword.value || !confirmPassword.value) {
    ElMessage.error('请填写所有必填字段');
    return;
  }

  if (newPassword.value !== confirmPassword.value) {
    ElMessage.error('两次输入的密码不一致');
    return;
  }

  try {
    const response = await request.post('/user/resetPassword', {
      phone: phone.value,
      email: email.value,
      newPassword: newPassword.value
    });
    if (response.code === "200") {
      ElMessage.success('密码重置成功，请登录');
      router.push('/login');
    } else {
      ElMessage.error(response.message);
    }
  } catch (error) {
    ElMessage.error('密码重置失败，请稍后重试');
    console.error('密码重置请求出错:', error);
  }
};

const toggleNewPassword = () => {
  showNewPassword.value = !showNewPassword.value;
};

const toggleConfirmPassword = () => {
  showConfirmPassword.value = !showConfirmPassword.value;
};
</script>

<style scoped>
@import "@/assets/login.css";
</style>