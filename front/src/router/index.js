import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { http } from '@/api/http'

// 动态菜单路由
let dynamicRoutes = []

/**
 * 将数据库返回的扁平菜单数据转换为树形结构
 * @param {Array} items - 菜单数组
 * @returns {Object} - 以 pid 为 key 的分组对象
 */
function groupByPid(items) {
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
 * @param {Array} allRoutes - 所有路由用于查找子路由
 * @returns {Array} - 路由配置数组
 */
function buildRoutes(pid, grouped, allRoutes) {
  const children = grouped[pid] || []
  return children.map(menu => {
    // 生成路由 path（去掉 /home 前缀）
    const path = menu.url.replace(/^\/home\/?/, '')

    const route = {
      path: path,
      name: menu.name,
      meta: {
        title: menu.name,
        icon: menu.icon,
        forrootset: 1
      }
    }

    // 递归构建子路由
    const subRoutes = buildRoutes(menu.id, grouped, allRoutes)
    if (subRoutes.length > 0) {
      route.children = subRoutes
    }

    // 添加组件加载（只有叶子节点或没有子路由的节点）
    if (!route.children || route.children.length === 0) {
      // 根据 URL 动态匹配组件路径
      const componentPath = menu.url.replace(/^\/home/, '') || '/home'
      route.component = () => import(`../views/children${componentPath}/index.vue`)
        .catch(() => import('../views/children/404.vue'))
    }

    return route
  })
}

/**
 * 从数据库加载菜单并生成路由
 * @returns {Promise<Array>} - 生成的路由数组
 */
export async function loadMenuRoutes() {
  try {
    const res = await http.get('/menu')
    if (res.ok && res.data) {
      const grouped = groupByPid(res.data)
      dynamicRoutes = buildRoutes(0, grouped, res.data)
      return dynamicRoutes
    }
  } catch (e) {
    console.error('加载菜单路由失败:', e)
  }
  return []
}

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('../views/login.vue'),
    },
    {
      path: '/home',
      name: 'home',
      meta: {
        title: 'base'
      },
      redirect: '/home/home',
      component: HomeView,
      children: [
        // 首页 - 固定路由
        {
          path: 'home',
          name: 'home',
          meta: {
            title: '首页',
            forrootset: 1
          },
          component: () => import('../views/children/home/index.vue')
        },
        // 404 页面 - 固定路由
        {
          path: '/:path(.*)*',
          name: '404',
          meta: {
            title: '404',
            forrootset: 1,
            hide: true
          },
          component: () => import('../views/children/404.vue'),
        }
      ]
    },
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
      const exists = homeRoute.children.some(child => child.name === route.name)
      if (!exists) {
        homeRoute.children.push(route)
      }
    })
    // 重新添加路由以确保生效
    router.clearRoutes()
    router.options.routes.forEach(r => {
      router.addRoute(r)
    })
  }
}

export { dynamicRoutes }

export default router
