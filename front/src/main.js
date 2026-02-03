import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { fns } from './utils.js'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'//图标相关

const app = createApp(App)

//路由处理层级导航

var memoryRoute = [//假设这是缓存本地的数据
    ...(localStorage.getItem('navroot') ? JSON.parse(localStorage.getItem('navroot')) : [])
]
if (memoryRoute.length) {
    router.options.routes = fns(router, memoryRoute).getRoutes()
}

//路由守卫

router.beforeEach((to, from, next) => {
    //一个模拟的令牌token，实际情况请自行处理
    if (localStorage.getItem('token')) {
        if (to.path == '/') {
            next({ path: '/home/home' });
        } else {
            next()
        }
    } else {
        if (to.path == '/') {
            next();
        } else {
            // if (to.meta.ifnologin != 1) {
            next({ path: '/' })
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
