<template>
  <Header />

  <main class="container mx-auto px-4 py-8">
    <div class="flex flex-col lg:flex-row gap-6">
      <!-- 左侧边栏 -->
      <div class="lg:w-1/5">
        <div class="bg-white rounded-2xl shadow-sm overflow-hidden">
          <div class="bg-primary p-5 text-white">
            <!-- 使用 flex 布局让头像和用户名并排显示 -->
            <div class="flex items-center">
              <button @click="showAvatarSelection = true" class="p-0 border-none bg-transparent">
                <img
                  :src="userInfo.avatar"
                  alt="用户头像"
                  class="w-16 h-16 rounded-full border-2 border-white object-cover"
                />
              </button>
              <!-- 将用户名显示在头像旁边 -->
              <div class="ml-3">
                <h3 class="font-semibold text-lg">{{ userInfo.username }}</h3>
              </div>
            </div>
            <!-- 头像选择弹窗 -->
            <div v-if="showAvatarSelection" class="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white p-4 rounded-lg shadow-lg z-50">
              <h3 class="text-lg font-semibold mb-4">选择头像</h3>
              <div class="grid grid-cols-3 gap-2">
                <button
                    v-for="(avatar, index) in availableAvatars"
                    :key="index"
                    @click="changeAvatar(avatar)"
                    class="p-0 border-none bg-transparent"
                >
                  <img
                      :src="avatar"
                      alt="可选头像"
                      class="w-16 h-16 rounded-full border-2 border-gray-300 object-cover"
                  />
                </button>
              </div>
              <button @click="showAvatarSelection = false" class="mt-4 bg-gray-300 p-2 rounded">取消</button>
            </div>
            <div class="mt-4 grid grid-cols-3 gap-2 text-center">
              <div>
                <div class="text-xl font-semibold">{{ userStats.likeCount }}</div>
                <div class="text-xs opacity-80">点赞</div>
              </div>
              <div>
                <div class="text-xl font-semibold">{{ userStats.collectCount }}</div>
                <div class="text-xs opacity-80">收藏</div>
              </div>
              <div>
                <div class="text-xl font-semibold">{{ userStats.commentCount }}</div>
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
                to="/my-likes"
                class="flex items-center px-5 py-3 nav-inactive"
            >
              <i class="fa fa-thumbs-up w-6"></i>
              <span class="ml-3">我的点赞</span>
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


        <el-card class="change-password-card relative">
          <template #header>
            <div class="flex justify-between items-center">
              <span>修改密码</span>
            </div>
          </template>
          <el-form
              ref="passwordFormRef"
              :model="passwordForm"
              :rules="passwordRules"
              label-width="120px"
          >
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input
                  v-model="passwordForm.oldPassword"
                  type="password"
                  show-password
              ></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input
                  v-model="passwordForm.newPassword"
                  type="password"
                  show-password
              ></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                  v-model="passwordForm.confirmPassword"
                  type="password"
                  show-password
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitPasswordChange">
                提交修改
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <div class="logout-container  mt-8 ml-auto max-w-fit">
          <div class="logout-button  bottom-5 right-5">
            <el-button type="danger" @click="logout">退出登录</el-button>
          </div>
        </div>

      </div>
    </div>
  </main>

  <Footer />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { ElMessage } from 'element-plus';
import request from '@/utils/request.js';
import availableAvatars from '@/assets/avatars.js';

// 用户信息
const userInfo = reactive({
  avatar: "",
  username: "",
  memberType: "",
  email: "",
  phone: "",
  gender: "",
});


// 控制头像选择弹窗显示
const showAvatarSelection = ref(false);


// 更换头像方法
const changeAvatar = async (avatar) => {
  try {
    const user_id = localStorage.getItem('user_id');
    const response = await request.post('/user/updateAvatar', {
      user_id,
      avatar
    });
    if (response.code === "200") {
      userInfo.avatar = avatar;
      ElMessage.success('头像修改成功');
    } else {
      ElMessage.error(response.message);
    }
  } catch (error) {
    ElMessage.error('修改头像失败，请稍后重试');
    console.error('修改头像出错:', error);
  }
  showAvatarSelection.value = false;
};


// 用户统计信息
const userStats = reactive({
  favorites: 0,
  views: 0,
  comments: 0,
});



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

const fetchUserRelatedData = async () => {
  const user_id = localStorage.getItem('user_id');
  if (user_id) {
    try {
      // 获取用户信息
      const userResponse = await request.get(`/user/selectById/${user_id}`);
      if (userResponse.code === "200") {
        const userData = userResponse.data;
        userInfo.avatar = userData.avatar;
        userInfo.username = userData.username;
        userInfo.email = userData.email;
        userInfo.phone = userData.phone;
        userInfo.gender = userData.gender === 1 ? '1' : '0';
      } else {
        ElMessage.error(userResponse.message);
      }

      // 获取用户互动数量
      const interactionResponse = await request.get(`/user/interaction-count/${user_id}`);
      if (interactionResponse.code === "200") {
        const interactionData = interactionResponse.data;
        // 判断数据是否有效
        if (interactionData) {
          userStats.collectCount = interactionData.collectCount || 0;
          userStats.commentCount = interactionData.commentCount || 0;
          userStats.likeCount = interactionData.likeCount || 0;
        } else {
          // 若数据无效，将统计信息设为 0
          userStats.collectCount = 0;
          userStats.commentCount = 0;
          userStats.likeCount = 0;
        }
      } else {
        ElMessage.error(interactionResponse.message);
      }

      // 获取用户浏览数量，假设已有对应接口
      const viewResponse = await request.get(`/user/history/${user_id}`);
      if (viewResponse.code === "200") {
        userStats.viewCount = viewResponse.data;
      } else {
        ElMessage.error(viewResponse.message);
      }
    } catch (error) {
      ElMessage.error('获取数据失败，请稍后重试');
      console.error('获取数据出错:', error);
    }
  } else {
    ElMessage.warning('未检测到用户登录信息，请重新登录');
    router.push('/login');
  }
};

// 组件加载时获取用户信息
onMounted(async () => {
  fetchUserRelatedData()
});

// 提交个人资料修改
const submitProfile = async () => {
  try {
    const userInfoData = {
      user_id: parseInt(localStorage.getItem('user_id')),
      username: userInfo.username,
      phone: userInfo.phone,
      email: userInfo.email,
      gender: parseInt(userInfo.gender)
    };

    const response = await request.post('/user/updateInfo', userInfoData);
    if (response.code === "200") {
      ElMessage.success("个人资料修改成功");
      // 重新获取用户信息
      await fetchUserRelatedData();
    } else {
      ElMessage.error(response.message);
    }
  } catch (error) {
    ElMessage.error('修改失败，请稍后重试');
    console.error('修改用户信息出错:', error);
  }
};

const submitPasswordChange = async () => {
  if (!passwordFormRef.value) return;
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const user_id = localStorage.getItem('user_id');
        const requestData = {
          user_id,
          oldPassword: passwordForm.oldPassword,
          newPassword: passwordForm.newPassword
        };
        console.log('准备发送请求，请求参数:', requestData); // 打印请求参数
        const response = await request.post('/user/changePassword', requestData);
        console.log('收到响应:', response); // 打印响应数据
        if (response.code === '200') {
          ElMessage.success('密码修改成功，请重新登录');
          logout();
        } else {
          ElMessage.error(response.message);
        }
      } catch (error) {
        ElMessage.error('修改失败，请稍后重试');
        console.error('修改密码出错:', error);
      }
    }
  });
};
// 退出登录
const logout = () => {
  // 清除用户登录状态，
  localStorage.removeItem("token");
  localStorage.removeItem("user_id");
  router.push("/login");
  ElMessage.success('退出成功');
};
</script>

<style scoped>

</style>