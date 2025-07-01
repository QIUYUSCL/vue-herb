<template>
  <Header />

  <main class="container mx-auto px-4 py-8">
    <div class="flex flex-col lg:flex-row gap-6">
      <!-- 左侧边栏 -->
      <div class="lg:w-1/5">
        <div class="bg-white rounded-2xl shadow-sm overflow-hidden">
          <div class="bg-primary p-5 text-white">
            <div class="flex items-center">
              <img
                  :src="userInfo.avatar"
                  alt="用户头像"
                  class="w-16 h-16 rounded-full border-2 border-white object-cover"
              />
              <div class="ml-4">
                <h3 class="font-semibold text-lg">{{ userInfo.username }}</h3>
                <p class="text-sm opacity-80">{{ userInfo.memberType || '普通会员' }}</p>
              </div>
            </div>
            <div class="mt-4 grid grid-cols-3 gap-2 text-center">
              <div>
                <div class="text-xl font-semibold">{{ userStats.favorites }}</div>
                <div class="text-xs opacity-80">收藏</div>
              </div>
              <div>
                <div class="text-xl font-semibold">{{ userStats.views }}</div>
                <div class="text-xs opacity-80">浏览</div>
              </div>
              <div>
                <div class="text-xl font-semibold">{{ userStats.comments }}</div>
                <div class="text-xs opacity-80">评论</div>
              </div>
            </div>
          </div>

          <!-- 侧边栏导航 -->
          <nav class="py-2">
            <router-link
                to="/user-center"
                class="flex items-center px-5 py-3 nav-active"
            >
              <i class="fa fa-user-circle w-6"></i>
              <span class="ml-3">个人资料</span>
            </router-link>
            <router-link
                to="/my-collections"
                class="flex items-center px-5 py-3 nav-inactive"
            >
              <i class="fa fa-bookmark w-6"></i>
              <span class="ml-3">我的收藏</span>
            </router-link>
            <router-link
                to="/browsing-history"
                class="flex items-center px-5 py-3 nav-inactive"
            >
              <i class="fa fa-history w-6"></i>
              <span class="ml-3">浏览历史</span>
            </router-link>
            <router-link
                to="/my-comments"
                class="flex items-center px-5 py-3 nav-inactive"
            >
              <i class="fa fa-comment w-6"></i>
              <span class="ml-3">我的评论</span>
            </router-link>
            <router-link
                to="/notifications"
                class="flex items-center px-5 py-3 nav-inactive"
            >
              <i class="fa fa-bell w-6"></i>
              <span class="ml-3">消息通知</span>
              <span
                  class="ml-auto bg-red-500 text-white text-xs px-2 py-0.5 rounded-full"
              >{{ notificationCount }}</span
              >
            </router-link>
            <router-link
                to="/account-settings"
                class="flex items-center px-5 py-3 nav-inactive"
            >
              <i class="fa fa-cog w-6"></i>
              <span class="ml-3">账户设置</span>
            </router-link>
            <router-link
                to="/help-center"
                class="flex items-center px-5 py-3 nav-inactive"
            >
              <i class="fa fa-question-circle w-6"></i>
              <span class="ml-3">帮助中心</span>
            </router-link>
          </nav>
        </div>
      </div>

      <!-- 右侧内容 -->
      <div class="lg:w-4/5">
        <div class="bg-white rounded-2xl shadow-sm p-6 mb-6">
          <h2 class="text-xl font-bold text-gray-800 mb-4">个人资料</h2>
          <el-form :model="userInfo" label-width="120px">
            <el-form-item label="用户名">
              <el-input v-model="userInfo.username"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="userInfo.email"></el-input>
            </el-form-item>
            <el-form-item label="手机号码">
              <el-input v-model="userInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="userInfo.gender">
                <el-radio label="1">男</el-radio>
                <el-radio label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitProfile">保存修改</el-button>
            </el-form-item>
          </el-form>
        </div>

        <el-card class="change-password-card">
          <!-- ... 已有代码 ... -->
        </el-card>
      </div>
    </div>
  </main>

  <div class="logout-button">
    <el-button type="danger" @click="logout">退出登录</el-button>
  </div>

  <Footer />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { ElMessage } from 'element-plus';
import request from '@/utils/request.js';

// 用户信息
const userInfo = reactive({
  avatar: "",
  username: "",
  memberType: "",
  email: "",
  phone: "",
  gender: "",
});

// 用户统计信息
const userStats = reactive({
  favorites: 12,
  views: 24,
  comments: 3,
});

// 消息通知数量
const notificationCount = ref(5);

// 密码修改表单数据
const passwordForm = reactive({
  oldPassword: "",
  newPassword: "",
  confirmPassword: "",
});

// 密码修改表单验证规则
const passwordRules = {
  oldPassword: [
    { required: true, message: "请输入旧密码", trigger: "blur" },
  ],
  newPassword: [
    { required: true, message: "请输入新密码", trigger: "blur" },
    { min: 6, message: "密码长度不能少于 6 个字符", trigger: "blur" },
  ],
  confirmPassword: [
    { required: true, message: "请确认密码", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
};

const passwordFormRef = ref(null);
const router = useRouter();

// 组件加载时获取用户信息
onMounted(async () => {
  const user_id = localStorage.getItem('user_id');
  if (user_id) {
    try {
      const response = await request.get(`/user/selectById/${user_id}`);
      if (response.code === "200") {
        const userData = response.data;
        userInfo.avatar = userData.avatar;
        userInfo.username = userData.username;
        userInfo.email = userData.email;
        userInfo.phone = userData.phone;
        userInfo.gender = userData.gender === 1 ? '1' : '0';
      } else {
        ElMessage.error(response.message);
      }
    } catch (error) {
      ElMessage.error('获取用户信息失败，请稍后重试');
      console.error('获取用户信息出错:', error);
    }
  } else {
    ElMessage.warning('未检测到用户登录信息，请重新登录');
    router.push('/login');
  }
});

// 提交个人资料修改
const submitProfile = () => {
  // 模拟保存成功
  ElMessage.success("个人资料修改成功");
};

// 提交密码修改
const submitPasswordChange = async () => {
  if (!passwordFormRef.value) return;
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      // 模拟密码修改成功
      ElMessage.success("密码修改成功，请重新登录");
      logout();
    }
  });
};

// 退出登录
const logout = () => {
  // 清除用户登录状态，实际项目中应清除 token 等信息
  localStorage.removeItem("token");
  localStorage.removeItem("user_id");
  router.push("/login");
};
</script>

<style scoped>
/* ... 已有代码 ... */
</style>