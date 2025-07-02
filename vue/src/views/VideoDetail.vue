<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />
    <!-- 面包屑导航 -->
    <div class="bg-gray-100 py-3">
      <div class="container mx-auto px-4">
        <div class="flex items-center text-sm text-gray-600">
          <router-link to="/" class="hover:text-primary">首页</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <router-link to="/videos" class="hover:text-primary">视频教程</router-link>
          <i class="fa fa-angle-right mx-2 text-gray-400"></i>
          <span v-if="video" class="text-gray-800">{{ video.title }}</span>
          <span v-else class="text-gray-800">视频详情</span>
        </div>
      </div>
    </div>
    <div class="container mx-auto px-4 py-8">
      <el-button type="success" round :icon="Back" @click="goBack" class="mb-4" />
      <div v-if="video" class="video-detail bg-white p-6 rounded-lg shadow-md">
        <!-- 视频播放器 -->
        <video
            :src="video.video_url"
            controls
            class="w-full h-auto mb-4 rounded-lg"
            :poster="video.cover_image"
        ></video>
        <h2 class="text-2xl font-bold text-gray-800 mb-2">{{ video.title }}</h2>
        <p class="text-sm text-gray-600 mb-4">{{ video.description }}</p>
        <div class="flex items-center justify-between mb-4">
          <span class="text-sm text-gray-600">时长: {{ formatDuration(video.duration) }}</span>
          <span class="text-sm text-gray-600">分类: {{ video.category }}</span>
        </div>
        <div class="flex items-center mt-2">
          <i class="fa fa-eye text-gray-600"></i>
          <span class="ml-1 text-sm text-gray-600">{{ video.views }} 次观看</span>
          <i class="fa fa-thumbs-up text-gray-600 ml-4" @click="handleLike(video.video_id)"></i>
          <span class="ml-1 text-sm text-gray-600">{{ video.likes }} 点赞</span>
          <i class="fa fa-bookmark text-gray-600 ml-4" @click="handleCollect(video.video_id)"></i>
          <span class="ml-1 text-sm text-gray-600">{{ video.collections}} 收藏</span>
          <i class="fa fa-comment text-gray-600 ml-4" @click="showCommentInput = true"></i>
          <span class="ml-1 text-sm text-gray-600">{{ video.comments }} 评论</span>
        </div>
        <div v-if="showCommentInput" class="mt-4 relative">
          <textarea v-model="commentContent" rows="3" class="w-full border border-gray-300 p-2 rounded"></textarea>
          <button @click="handleSubmitComment" class="mt-2 bg-primary text-white p-2 rounded">发表评论</button>
          <button @click="toggleShowAllComments" class="mt-2 ml-2 bg-gray-300 p-2 rounded absolute right-0 bottom-0">
            {{ showAllComments ? '隐藏评论' : '查看所有评论' }}
          </button>
          <button @click="showCommentInput = false" class="mt-2 ml-2 bg-gray-300 p-2 rounded">取消</button>
        </div>
        <div v-if="showAllComments" class="mt-4">
          <h3 class="text-lg font-bold text-gray-800 mb-2">所有评论</h3>
          <div v-if="comments.length > 0">
            <div v-for="comment in comments" :key="comment.comment_id" class="bg-white p-4 rounded-lg shadow-sm mb-4">
              <img :src="comment.avatar" alt="用户头像" class="w-8 h-8 rounded-full mb-2">
              <p class="font-semibold">{{ comment.username }}</p>
              <p class="text-gray-600">{{ comment.content }}</p>
              <p class="text-sm text-gray-500">{{ formatDate(comment.create_time) }}</p>
            </div>
          </div>
          <div v-else class="text-center text-gray-500">暂无评论记录</div>
        </div>
      </div>
      <div v-else class="text-center text-gray-600">未找到该视频信息</div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import {Back} from "@element-plus/icons-vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import {fetchComments, handleInteraction, handleView, submitComment} from "@/utils/interactions.js";
import {commonRequest} from "@/utils/commonRequest.js";

const route = useRoute();
const video = ref(null);
const showCommentInput = ref(false);
const commentContent = ref('');
const showAllComments = ref(false);
const comments = ref([]);


// 将时长从秒转换为 分:秒 格式
const formatDuration = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`;
};

// 格式化日期函数
const formatDate = (dateString) => {
  return new Date(dateString).toLocaleString();
};

const toggleShowAllComments = async () => {
  if (!showAllComments.value) {
    try {
      await fetchComments('VIDEO', video.value.video_id, comments);
      console.log('获取到的评论:', comments.value);
    } catch (error) {
      ElMessage.error('获取评论失败，请稍后重试');
    }
  }
  showAllComments.value = !showAllComments.value;
};


const fetchVideoDetail = async () => {
  try {
    const videoId = parseInt(route.params.id);
    const data = await commonRequest('video', 'selectById', { id: videoId });
    video.value = data;
  } catch (error) {
    ElMessage.error('获取视频信息失败，请稍后重试');
  }
};

const handleVideoView = async () => {
  const videoId = parseInt(route.params.id);
  await handleView('VIDEO', videoId);
};

const handleLike = async () => {
  await handleInteraction(video.value.video_id, 'VIDEO', 'LIKE', video.value);
};

const handleCollect = async () => {
  await handleInteraction(video.value.video_id, 'VIDEO', 'COLLECT', video.value);
};

const handleSubmitComment = async () => {
  await submitComment(video, 'VIDEO', commentContent, showCommentInput);
};

onMounted(() => {
  fetchVideoDetail();
  handleVideoView()
});

const goBack = () => {
  router.go(-1);
};
</script>