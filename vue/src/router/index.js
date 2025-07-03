// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue';
import Test from "@/views/test.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/register.vue";
import Herbs from "@/views/Herbs.vue";
import HerbDetail from "@/views/HerbDetail.vue";
import UserCenter from "@/views/UserCenter.vue";
import Videos from "@/views/Videos.vue";
import VideoDetail from "@/views/VideoDetail.vue";
import Daily from "@/views/Daily.vue";
import DailyDetail from "@/views/DailyDetail.vue";
import SearchResult from "@/views/SearchResult.vue";
import MyCollections from "@/views/my-collections.vue";
import BrowsingHistory from "@/views/browsing-history.vue";
import MyComments from "@/views/my-comments.vue";
import MyLikes from "@/views/my-likes.vue";
import ForgotPassword from "@/views/ForgotPassword.vue";

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'Home',
        component: Home
    },
    {
        path:'/test',
        name:'test',
        component: Test
    },
    {
        path:'/login',
        name:'login',
        component: Login
    },
    //引入register路由
    {
        path:'/register',
        name:'register',
        component: Register
    },
    //引入herbs路由
    {
        path:'/herbs',
        name:'Herbs',
        component: Herbs
    },
    //引入herbdetail路由
    {
        path:'/herbs/:id',
        name:'HerbDetail',
        component:HerbDetail
    },
    //引入usercenter路由
    {
        path: '/user-center',
        name: 'UserCenter',
        component: UserCenter,
        meta: {requiresAuth: true} // 标记需要登录才能访问
    },
    {
        path: '/videos',
        name: 'Videos',
        component: Videos,
    },
    {
        path: '/video-detail/:id',
        name: 'VideoDetail',
        component: VideoDetail
    },
    {
        path:'/daily',
        name:'Daily',
        component: Daily,
    },
    {
        path: '/daily/:id',
        name: 'DailyDetail',
        component: DailyDetail
    },
    {
        path: '/search-result',
        name: 'SearchResult',
        component: SearchResult
    },
    {
        path: '/my-collections',
        name: 'MyCollections',
        component: MyCollections,
        meta: { requiresAuth: true }
    },
    {
        path: '/browsing-history',
        name: 'BrowsingHistory',
        component: BrowsingHistory,
        meta: { requiresAuth: true }
    },
    {
        path: '/my-comments',
        name: 'MyComments',
        component: MyComments,
        meta: { requiresAuth: true }
    },
    {
        path: '/my-likes',
        name: 'MyLikes',
        component: MyLikes,
        meta: { requiresAuth: true }
    },
    {
        path: '/forgot-password',
        name: 'ForgotPassword',
        component: ForgotPassword
    },

];

const router = createRouter({
    history: createWebHistory(),
    routes
});




export default router;