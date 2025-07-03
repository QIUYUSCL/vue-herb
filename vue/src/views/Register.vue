<template>
  <div class="bg-gray-50 min-h-screen">
    <Header/>

    <!-- 主要内容区域 -->
    <main class="container mx-auto px-4 py-16 md:py-24">
      <div class="max-w-md mx-auto">
        <div class="bg-white rounded-2xl shadow-lg overflow-hidden card-hover">
          <div class="bg-gradient-green p-6 text-white text-center">
            <h2 class="text-2xl font-bold mb-2">创建账户</h2>
            <p class="opacity-80">注册成为会员，获取更多专业中医药知识</p>
          </div>

          <div class="p-6">
            <form @submit.prevent="handleRegister" class="space-y-4">
              <div>
                <label for="register-username" class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-user-o"></i>
                  </span>
                  <input
                      type="text"
                      id="register-username"
                      v-model="username"
                      placeholder="请输入您的用户名"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                </div>
              </div>

              <div>
                <label for="register-email" class="block text-sm font-medium text-gray-700 mb-1">邮箱</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-envelope-o"></i>
                  </span>
                  <input
                      type="email"
                      id="register-email"
                      v-model="email"
                      placeholder="请输入您的邮箱"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                  />
                </div>
              </div>

              <div>
                <label for="register-phone" class="block text-sm font-medium text-gray-700 mb-1">电话号码</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-phone"></i>
                  </span>
                  <input
                      type="tel"
                      id="register-phone"
                      v-model="phone"
                      placeholder="请输入 11 位电话号码"
                      class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg input-focus outline-none transition-all duration-300"
                      maxlength="11"
                      @input="validatePhone"
                  />
                </div>
              </div>

              <div>
                <label for="register-password" class="block text-sm font-medium text-gray-700 mb-1">密码</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-lock"></i>
                  </span>
                  <input
                      :type="showPassword ? 'text' : 'password'"
                      id="register-password"
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

              <div>
                <label for="register-confirm-password" class="block text-sm font-medium text-gray-700 mb-1">确认密码</label>
                <div class="relative">
                  <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                    <i class="fa fa-lock"></i>
                  </span>
                  <input
                      :type="showConfirmPassword ? 'text' : 'password'"
                      id="register-confirm-password"
                      v-model="confirmPassword"
                      placeholder="请再次输入您的密码"
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

              <div class="flex items-center">
                <input
                    type="checkbox"
                    id="agree-terms"
                    v-model="agreeTerms"
                    class="form-checkbox h-4 w-4 text-primary focus:ring-primary"
                />
                <label for="agree-terms" class="ml-2 block text-sm text-gray-700">
                  我已阅读并同意
                  <a href="#" class="text-primary hover:underline">用户协议</a> 和 <a href="#" class="text-primary hover:underline">隐私政策</a>
                </label>
              </div>

              <button
                  type="submit"
                  :disabled="!agreeTerms"
                  class="w-full bg-primary text-white py-2 px-4 rounded-lg font-medium hover:bg-secondary transition-colors"
              >
                注册
              </button>
            </form>

            <div class="mt-4 text-center text-sm text-gray-600">
              已有账户？<router-link to="/login" class="text-primary hover:underline">立即登录</router-link>
            </div>
          </div>
        </div>
      </div>
    </main>

    <Footer />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import router from '@/router/index.js';
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';

const username = ref('');
const email = ref('');
const password = ref('');
const phone = ref('');
const confirmPassword = ref('');
const showPassword = ref(false);
const showConfirmPassword = ref(false);
const agreeTerms = ref(false);

const handleRegister = async () => {
  if (!username.value || !email.value || !password.value || !confirmPassword.value) {
    ElMessage.error('请填写所有必填字段');
    return;
  }

  // 验证电话号码
  const validatePhone = () => {
    const phoneValue = phone.value;
    if (phoneValue && !/^\d{11}$/.test(phoneValue)) {
      ElMessage.warning('请输入 11 位数字的电话号码');
    }
  };

  if (password.value !== confirmPassword.value) {
    ElMessage.error('两次输入的密码不一致');
    return;
  }

  if (!/^\d{11}$/.test(phone.value)) {
    ElMessage.error('请输入有效的 11 位电话号码');
    return;
  }

  if (!agreeTerms.value) {
    ElMessage.error('请同意用户协议和隐私政策');
    return;
  }



  try {
    const userInfo = {
      username: username.value,
      password: password.value,
      email: email.value,
      phone: phone.value
    };
    const response = await request.post('/user/register', userInfo);
    if (response.code === "200") {
      ElMessage.success('注册成功，请登录');
      router.push('/login');
    } else {
      ElMessage.error(response.message);
    }
  } catch (error) {
    ElMessage.error('注册失败，请稍后重试');
  }
};

const togglePassword = () => {
  showPassword.value = !showPassword.value;
};

const toggleConfirmPassword = () => {
  showConfirmPassword.value = !showConfirmPassword.value;
};
</script>

<style scoped>
@tailwind base;
@tailwind components;
@tailwind utilities;

@layer utilities {
  .content-auto {
    content-visibility: auto;
  }
  .bg-gradient-green {
    background: linear-gradient(135deg, #16A34A 0%, #0F766E 100%);
  }
  .text-shadow {
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  .card-hover {
    @apply transition-all duration-300 hover:shadow-xl hover:-translate-y-1;
  }
  .input-focus {
    @apply focus:ring-2 focus:ring-primary/50 focus:border-primary;
  }
}
</style>