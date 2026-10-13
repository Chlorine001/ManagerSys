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

//路由处理层级导航

//路由守卫

router.beforeEach((to, from, next) => {
    //一个模拟的令牌token，实际情况请自行处理
    if (localStorage.getItem('token')) {
        if (to.path == '/') {
            next({path: '/home/home'});
        } else {
            next()
        }
    } else {
        if (to.path == '/') {
            next();
        } else {
            // if (to.meta.ifnologin != 1) {
            next({path: '/'})
            // } else {
            // 	next()
            // }
        }
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
