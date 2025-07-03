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
            <div v-for="comment in rootComments" :key="comment.comment_id" class="bg-white p-4 rounded-lg shadow-sm mb-4">
              <!-- 父评论 -->
              <div>
                <img
                    :src="comment.avatar"
                    alt="用户头像"
                    class="w-8 h-8 rounded-full mb-2 cursor-pointer"
                    @click="navigateToUserProfile(comment.user_id)"
                />
                <p class="font-semibold">{{ comment.username }}</p>
                <p class="text-gray-600">{{ comment.content }}</p>
                <div class="flex justify-between items-center">
                  <p class="text-sm text-gray-500">{{ formatDate(comment.create_time) }}</p>
                  <button
                      @click="toggleReplyOrCollapse(comment.comment_id)"
                      class="text-blue-500 hover:underline hover:text-blue-600 transition-colors"
                  >
                    {{ isReplyExpanded[comment.comment_id] ? '收起回复' : '回复' }}
                  </button>
                </div>
              </div>
              <!-- 子评论 -->
              <div v-if="isReplyExpanded[comment.comment_id] && comment.children && comment.children.length > 0" class="ml-8 mt-2">
                <div v-for="childComment in comment.children" :key="childComment.comment_id" class="bg-gray-100 p-4 rounded-lg shadow-sm mb-2">
                  <img
                      :src="childComment.avatar"
                      alt="用户头像"
                      class="w-8 h-8 rounded-full mb-2 cursor-pointer"
                      @click="navigateToUserProfile(childComment.user_id)"
                  />
                  <p class="font-semibold">{{ childComment.username }}</p>
                  <p class="text-gray-600">{{ childComment.content }}</p>
                  <p class="text-sm text-gray-500">{{ formatDate(childComment.create_time) }}</p>
                </div>
              </div>
              <!-- 回复输入框 -->
              <div v-if="showReplyInput === comment.comment_id" class="mt-2">
                <textarea
                  v-model="replyContent"
                  rows="3"
                  class="w-full border border-gray-300 p-2 rounded"
                ></textarea>
                <div class="mt-2">
                  <button
                    @click="submitReply(comment.comment_id)"
                    class="bg-primary text-white p-2 rounded mr-2 hover:bg-primary-dark transition-colors"
                  >
                    发表回复
                  </button>
                  <button
                    @click="toggleReplyInput(null)"
                    class="bg-gray-300 p-2 rounded hover:bg-gray-400 transition-colors"
                  >
                    取消
                  </button>
                </div>
              </div>
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
import {ref, onMounted, computed, reactive} from 'vue';
import { useRoute } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import {Back} from "@element-plus/icons-vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import {fetchComments,
  handleInteraction,
  handleView,
  submitComment,
  submitReplyComment,
  navigateToUserProfile,
} from "@/utils/interactions.js";
import {commonRequest} from "@/utils/commonRequest.js";


const route = useRoute();
const video = ref(null);
const showCommentInput = ref(false);
const commentContent = ref('');
const showAllComments = ref(false);
const showReplyInput = ref(null);
const replyContent = ref('');
const comments = ref([]);


const fetchVideoDetail = async () => {
  try {
    const videoId = parseInt(route.params.id);
    const data = await commonRequest('video', 'selectById', {id: videoId});
    video.value = data;
  } catch (error) {
    ElMessage.error('获取视频信息失败，请稍后重试');
  }
};

// 用于记录每个父评论的子评论展开状态
const isReplyExpanded = reactive({});


// 计算属性，获取根评论（一级评论）
const rootComments = computed(() => {
  const commentMap = new Map();
  const rootComments = [];

  comments.value.forEach(comment => {
    comment.children = [];
    commentMap.set(comment.comment_id, comment);
    // 初始化子评论展开状态
    if (!isReplyExpanded[comment.comment_id]) {
      isReplyExpanded[comment.comment_id] = false;
    }

    if (comment.parent_id === 0) {
      rootComments.push(comment);
    } else {
      const parent = commentMap.get(comment.parent_id);
      if (parent) {
        parent.children.push(comment);
      }
    }
  });
  return rootComments;
});

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
    } catch (error) {
      ElMessage.error('获取评论失败，请稍后重试');
    }
  }
  showAllComments.value = !showAllComments.value;
};





const toggleReplyInput = (commentId) => {
  showReplyInput.value = commentId === showReplyInput.value ? null : commentId;
  replyContent.value = '';
};

// 切换回复输入框或子评论展开状态
const toggleReplyOrCollapse = (commentId) => {
  // 切换子评论展开状态
  isReplyExpanded[commentId] = !isReplyExpanded[commentId];
  if (isReplyExpanded[commentId]) {
    // 若子评论展开，同时展开回复输入框
    toggleReplyInput(commentId);
  } else {
    // 若子评论收起，关闭回复输入框
    toggleReplyInput(null);
  }
};

const submitReply = async (parentId) => {
  if (!replyContent.value.trim()) {
    ElMessage.warning('请输入回复内容');
    return;
  }

  try {
    const targetType = 'VIDEO';
    const targetId = video.value.video_id;
    await submitReplyComment(targetType, targetId, parentId, replyContent.value);
    // 清空回复内容，但不关闭回复输入框
    replyContent.value = '';
    // 重新获取评论
    await fetchComments(targetType, targetId, comments);
  } catch (error) {
    ElMessage.error('回复发表失败，请稍后重试');
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
}
</script>

<style scoped>
/* 若使用了自定义颜色，需要定义这些颜色变量 */
:root {
  --primary: #2563eb;
  --primary-dark: #1d4ed8;
}

/* 可根据需要添加更多自定义样式 */
</style>};
