<template>
  <div class="flex justify-center mt-6">
    <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        active-color="#409eff"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

// 定义组件接收的属性
const props = defineProps({
  total: {
    type: Number,
    required: true
  },
  pageSize: {
    type: Number,
    required: true
  }
});

// 定义组件触发的事件
const emits = defineEmits(['page-change']);

// 当前页码变化时触发事件
const handleCurrentChange = (newPage) => {
  emits('page-change', newPage);
};
</script>

<style scoped>
/* 可按需添加样式 */
</style>