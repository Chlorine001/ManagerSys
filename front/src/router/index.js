import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

//login.vue的mock路由数据（实际是后端数据）要和这里路由导航的层级要匹配
/*
  login.vue里面获取到的：/page/page1/page1a
  这里的格式就是：
  /page:
  --page1
  ----page1a
*/

const router = createRouter({
  // history: createWebHistory(import.meta.env.BASE_URL),
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('../views/login.vue'),
    },
    // 框架部分（主体）
    {
      path: '/home',
      name: 'home',
      meta: {
        title: 'base'//必须
      },
      redirect: '/home/home',
      component: HomeView,
      children: [
        //框架内页，如果不需要显示在左侧导航，看如下个人资料设置，设置hide为true
        //forrootset用于权限管理页面的渲染，目的是将左侧导航全部层级的路由区分出来，为1则为左侧导航栏路由
        //左侧导航栏中，title和forrootset为必须字段
        {
          path: 'home',
          name: 'home',
          meta: {
            title: '首页',
            forrootset: 1
          },
          component: () => import('../views/children/home/index.vue')
        },
        {
          path: 'systemSet',
          name: 'systemSet',
          meta: {
            title: '系统管理',
            forrootset: 1
          },
          children: [
            {
              path: 'routes',
              name: 'routes',
              meta: {
                title: '路由存储管理',
                forrootset: 1
              },
              component: () => import('../views/children/routesset/index.vue')
            },
            {
              path: 'dictsset',
              name: 'dictsset',
              meta: {
                title: '字典存储管理',
                forrootset: 1
              },
              component: () => import('../views/children/dictsset/index.vue')
            },
            {
              path: 'department',
              name: 'department',
              meta: {
                title: '部门管理',
                forrootset: 1
              },
              component: () => import('../views/children/department/index.vue')
            },
            {
              path: 'page1',
              name: 'page1',
              meta: {
                title: '角色配置',
                forrootset: 1
              },
              component: () => import('../views/children/page1/index.vue')
            },
            {
              path: 'userset',
              name: 'userset',
              meta: {
                title: '用户管理',
                forrootset: 1
              },
              component: () => import('../views/children/userset/index.vue')
            },
            {
              path: 'pagerootsettip',
              name: 'pagerootsettip',
              meta: {
                title: '权限页面分配',
                forrootset: 1
              },
              component: () => import('../views/children/pagerootsettip/index.vue')
            },
          ]
        },
        {
          path: 'page1b',
          name: 'page1b',
          meta: {
            title: '数据查看',
            forrootset: 1
          },
          children: [
            {
              path: 'subpage1b',
              name: 'subpage1b',
              meta: {
                title: '关于本站弹窗管理',
                forrootset: 1
              },
              component: () => import('../views/children/formdata/index.vue')
            },
            {
              path: 'subpage1b2',
              name: 'subpage1b2',
              meta: {
                title: '网页通知管理',
                forrootset: 1
              },
              component: () => import('../views/children/formdata/index2.vue')
            },
            {
              path: 'subpage1b3',
              name: 'subpage1b3',
              meta: {
                title: '数据查看',
                forrootset: 1
              },
              component: () => import('../views/children/formdata/index3.vue')
            },
          ]
        },
        {
          path: 'subpag',
          name: 'subpag',
          meta: {
            title: '导航三级222',
            forrootset: 1
          },
          children: [
              {
                  path: 'subpage1',
                  name: 'subpage1',
                  meta: {
                      title: '导航二级',
                      forrootset: 1
                  },
              },
          ],
          component: () => import('../views/children/signature/index.vue')
        },
        {
          path: 'page1b2',
          name: 'page1b2',
          meta: {
            title: '导航分级',
            forrootset: 1
          },
          children: [
            {
              path: 'subpage1b2',
              name: 'subpage1b2',
              meta: {
                title: '导航二级',
                forrootset: 1
              },
              children: [
                {
                  path: 'subpage1b2s',
                  name: 'subpage1b2s',
                  meta: {
                    title: '导航三级',
                    forrootset: 1
                  },
                  component: () => import('../views/children/signature/index.vue')
                },
              ]
            },
          ]
        },
        {
          path: 'icons',
          name: 'icons',
          meta: {
            title: '更多图标展示',
            forrootset: 1,
            hide: true//true为隐藏
          },
          component: () => import('../views/children/icons/index.vue'),
        },
        {
          path: 'personal',
          name: 'personal',
          meta: {
            title: '个人资料',
            forrootset: 1,
            hide: true//true为隐藏
          },
          component: () => import('../views/children/personal/index.vue'),
        },
        {
          path: 'setting',
          name: 'setting',
          meta: {
            title: '设置',
            forrootset: 1,
            hide: true//true为隐藏
          },
          component: () => import('../views/children/setting/index.vue'),
        },
        {
          path: '/:path(.*)*',
          name: '404',
          meta: {
            title: '404',
            forrootset: 1,
            hide: true//true为隐藏
          },
          component: () => import('../views/children/404.vue'),
        }
      ]
    },
  ]
})

export default router
