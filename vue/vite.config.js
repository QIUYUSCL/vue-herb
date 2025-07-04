import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    host: '0.0.0.0', // 监听所有网络接口
    port: 5173, // 端口号
    proxy: {
      // 配置代理
      '/api': {
        // target: 'http://192.168.212.29:9090',
        target: 'http://127.0.0.1:9090',
        changeOrigin: true,
        secure: false, // 忽略证书验证
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
