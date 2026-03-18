import {createApp} from 'vue'
import App from './App.vue'
import router from '@/utils/router'
import {fns} from '@/utils/utils.js'
import {groupByPid, buildRoutes, addMenuRoutes} from '@/utils/router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'//图标相关

const app = createApp(App)

// 从 localStorage 恢复动态路由
const navrootStr = localStorage.getItem('navroot')
const memoryRoute = navrootStr ? JSON.parse(navrootStr) : []

if (memoryRoute.length) {
    // 将扁平菜单转换为树形结构
    const grouped = groupByPid(memoryRoute)
    // 构建动态路由树
    const menuRoutes = buildRoutes(0, grouped)
    // 添加到 router 实例
    addMenuRoutes(menuRoutes)
    // 处理路由元信息
    fns(router, memoryRoute)
    // router.options.routes = fns(router, memoryRoute).getRoutes()
}

//路由守卫
// 定义无需登录的白名单路由
const whiteList = ['/', '/404'] // 登录页和 404 页无需登录

router.beforeEach((to, from, next) => {
    const hasToken = localStorage.getItem('token')

    // 在白名单中，直接放行
    if (whiteList.includes(to.path)) {
        // 已登录用户访问登录页，重定向到首页
        if (hasToken && to.path === '/') {
            next({path: '/tup/home'})
        } else {
            next()
        }
        return
    }

    // 不在白名单中，需要登录
    if (hasToken) {
        // TODO: 可在此处添加 token 有效性验证逻辑
        next()
    } else {
        // 未登录，重定向到登录页
        next({
            path: '/',
            query: {redirect: to.fullPath} // 保存原始目标路径，登录后可跳转回去
        })
    }
})

//图标相关

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//

app.use(router)
app.use(ElementPlus)

app.config.globalProperties.$vue3test = 2

app.mount('#app')
