import {createRouter, createWebHistory, createWebHashHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import {http} from '@/utils/http'

// 动态菜单路由
let dynamicRoutes = []

/**
 * 将数据库返回的扁平菜单数据转换为树形结构
 * @param {Array} items - 菜单数组
 * @returns {Object} - 以 pid 为 key 的分组对象
 */
export function groupByPid(items) {
    return items.reduce((acc, item) => {
        const pid = item.pid || 0
        if (!acc[pid]) acc[pid] = []
        acc[pid].push(item)
        return acc
    }, {})
}

/**
 * 递归构建路由树
 * @param {number} pid - 父级菜单 ID
 * @param {Object} grouped - 按 pid 分组的菜单数据
 * @param {string} parentPath - 父级路径前缀
 * @returns {Array} - 路由配置数组
 */
export function buildRoutes(pid, grouped, parentPath = '') {
    const children = grouped[pid] || []

    return children.map(menu => {
        // 生成路由 path（去掉开头的 /）
        const menuPath = menu.url.replace(/^\//, '')
        // 递归构建子路由（传入当前路径作为父级路径）
        const subRoutes = buildRoutes(menu.id, grouped, menuPath)
        const hasChildren = subRoutes.length > 0

        // 路径安全验证：只允许字母、数字、下划线、中划线和斜杠
        const isValidPath = /^[a-zA-Z0-9_\-/]+$/.test(menuPath)
        if (!isValidPath) {
            console.error('非法的路径格式:', menuPath, '菜单 ID:', menu.id)
            return null // 跳过非法路径
        }

        // 构建路由配置（path 保持原始值）

        const route = {
            path: menuPath,
            name: `menu_${menu.id}`, // 使用菜单 ID 作为 name 保证唯一性
            meta: {
                title: menu.name,
                icon: menu.icon,
                ishide: menu.ishide,
                userroot: 1
            }
        }
        const componentPath = parentPath ? `${parentPath}/${menuPath}` : menuPath
        const modules = import.meta.glob('../views/children/**/index.vue')

        // 在路由守卫或组件中动态加载
        const fullPath = `../views/children/${componentPath}/index.vue`

        function loadComponent(fullPath) {
            const loader = modules[fullPath]
            // console.log('组件路径:', componentPath)
            // console.log(loader)
            if (!loader) {
                console.error(`Loader not found for path: ${fullPath}`)
                // 开发环境下可以返回一个提示组件，生产环境建议跳转404
                return () => import('@/views/error/404.vue')
            }
            return loader
        }

        // 如果有子路由，添加子路由配置和中间页面组件
        if (hasChildren) {
            route.children = subRoutes
        } else {
            // 叶子节点加载对应组件

            route.component = loadComponent(fullPath)
        }

        return route
    })
}

/**
 * 从数据库加载菜单并生成路由
 * @param {Object} user - 用户对象（包含 name、id 等属性）
 * @returns {Promise<Array>} - 生成的路由数组
 */
export async function loadMenuRoutes(user = {}) {
    //fixme 登录时候会返回路由数组，这个是在管理平台展示管理用---待修复

    // const menuRoutes = await loadMenuRoutes(res.user || {})
    // try {
    //   const res = await http.get('/menu/user', user)
    //   console.log('菜单数据:', res.data)
    //   if (res.ok && Array.isArray(res.data)) {
    //     const grouped = groupByPid(res.data)
    //     console.log('菜单数据:', grouped)
    //     dynamicRoutes = buildRoutes(0, grouped)
    //     return dynamicRoutes
    //   } else if (res.ok && res.data) {
    //     console.warn('菜单数据格式不正确:', res.data)
    //     return []
    //   }
    // } catch (e) {
    //   console.error('加载菜单路由失败:', e.message || e)
    //   return []
    // }
    return []
}

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            name: 'login',
            component: () => import('../views/login/login.vue'),
        },
        // 首页 - 默认路由
        {
            path: '/home',
            name: 'home',
            meta: {
                title: 'base',
            },
            redirect: '/home/home',
            component: HomeView,
            children: []
        },
        // 404 页面 - 固定路由
        {
            path: '/:path(.*)*',
            name: '404',
            meta: {
                title: '404',
                userroot: 1,
                hide: true
            },
            component: () => import('../views/error/404.vue'),
        }
    ]
})

/**
 * 动态添加菜单路由到 home 路由下
 * 需要在登录成功后调用
 * @param {Array} routes - 路由配置数组
 */
export function addMenuRoutes(routes) {
    const homeRoute = router.options.routes.find(r => r.path === '/home')
    if (homeRoute && routes && routes.length > 0) {
        routes.forEach(route => {
            // 避免重复添加
            // const exists = homeRoute.children.some(child => child.name === route.name)
            // if (!exists) {
            // // 在 path 前添加 'home/' 前缀（如果没有）
            // if (!route.path.startsWith('home/')) {
            //     route.path = 'home/' + route.path
            // }
            homeRoute.children.push(route)
            // }
        })
        // 重新添加路由以确保生效
        router.options.routes.forEach(r => {
            router.addRoute(r)
        })
        dynamicRoutes = router.options.routes
    }
    return dynamicRoutes
}

export {dynamicRoutes}

export default router
