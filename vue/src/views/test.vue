<template>
  <div style="margin:40px ">
    <router-link to="/home">
      跳转到首页
    </router-link>
  </div>

  <div>
    <el-button type="primary" @click="router.push('/home')">
      跳转到首页
    </el-button>
    <!--    <el-button type="primary" @click="router.replace('/test')">-->
    <!--      跳转到test页面-->
    <!--    </el-button>-->
  </div>

  <div style="margin: 300px">
    <el-icon :size="size" :color="color">
      <Edit />
    </el-icon>
    <el-icon>
      <Plus />
    </el-icon>
    <el-button type="primary" :icon="Delete" circle />
    <el-input  clearable v-model="data.a" style="width: 240px" placeholder="Please input" />
    <el-input
        v-model="data.c"
        style="width: 240px"
        :rows="2"
        type="textarea"
        placeholder="Please input"
    />
    <el-select
        clearable
        multiple

        v-model="data.value"
        placeholder="Select"
        size="large"
        style="width: 240px"
    >
      <el-option
          v-for="item in data.options"
          :key="item.id"
          :label="item.name"
          :value="item.name"
          :disabled="item.disabled"
      />
    </el-select>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import {Delete} from "@element-plus/icons-vue";
import router from "@/router/index.js";

const data = reactive({
  a:123,
  b:456,
  c:789,
  size: 20,
  color: "#409EFF",
  input: "",
  textarea: "",
  value: "",
  options: [
    {
      id: 1,
      name:'11'
    },
    {
      id:2,
      name:'22',
      disabled:true,
    },
    {
      id:3,
      name:'33'
    },
    {
      id:4,
      name:'44'
    },{
      id:5,
      name:'55'
    },
    {
      id:6,
      name:'66'
    },
    {
      id:7,
      name:'77'
    }
  ]
  // options: ['1','2','3','4','5','6','7','8','9'],
})
</script>
