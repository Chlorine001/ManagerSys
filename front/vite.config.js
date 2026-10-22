import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    // chainWebpack(config) {
    //     config.resolve.alias
    //         .set('@', resolve('http://localhost:3000'));
    // },
    server: {
        proxy: {
            '/api': {
                target: 'http://localhost:8090',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '')
            }
        },
        // open: true, // 启动后自动打开浏览器
        // host: '0.0.0.0', // 本地开发环境通过代理实现跨域
    }
})

//当前vue3版本