/**
计划还要添加的内容：拖拽相关

项目整体权限管理路线图：部门-角色（绑定到对应部门）-(用户匹配一个或多个角色，就能对应到部门)

项目开发流程：
页面追加：路由添加-然后确定路由表是对应身份返回对应的权限路由表（带按钮权限和字典配置），admin默认返回全部
---路由表，存在对象即存在页面
---路由表里面每个对象包括：
------path：路径
------root：按钮权限，edit，add，del
------dicts：对应path设置允许访问的字典标识
---路由开发必须配备title字段，forrootset：1是用来标记为是否需要设置权限，也是必须，hide：true为标记左侧导航某项不显示
字典配置：dictionary文件夹里面有对应的字典配置示例
国际化：langs给对应导航和其他文本配置对应语言翻译
图标配置：fonticons.vue里面给对应路由配置对应图标

建议在主界面打开的内页路由，都放在title为base的路由的children数组里面（不涉及在外部打开的界面建议都这么做）

线上部署地址：https://xie-zhiyi-2.gitee.io/project_web_h5project_list/project_DEMO_element_self_system/docs/index.html?v_2#/
 */

# project_DEMO_element_self_system

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```
