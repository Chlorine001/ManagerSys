// 全局按钮管理 - 使用 IIFE 封装避免污染全局作用域
const globalBtnManager = (() => {
    let globalbtn = [] // 全局架构上的页面跳转按钮显示配置（不算右下角打开聊天组件的按钮）--- 存储“按钮”路由的 name

    const setglobalbtn = (v) => {
        // 避免重复添加
        if (!globalbtn.includes(v.name)) {
            globalbtn.push(v.name)
        }
    }

    const getglobalbtn = () => {
        return globalbtn
    }

    const reset = () => {
        globalbtn = []
    }

    return {setglobalbtn, getglobalbtn, reset}
})()


export const getglobalbtn = () => {
    return globalBtnManager.getglobalbtn()
}

// 辅助函数：获取 base 路由的子节点
const getBaseChildren = (router) => {
    const routes = router.getRoutes()
    const baseRoute = routes.find(r => r.meta && r.meta.title === 'base')
    return baseRoute ? baseRoute.children : []
}
export const fns = (router, memorylist) => {
    // 参数验证
    if (!router || !memorylist || !Array.isArray(memorylist)) {
        console.warn('fns 函数参数无效:', {router, memorylist})
        return router
    }

    // 处理面包屑和左侧导航
    const addTittleList = getBaseChildren(router)

    const fn2 = (list, title, path) => {
        for (let ind = 0; ind < list.length; ind++) {
            const item = list[ind]
            if (!item) continue

            if (!item.meta) {
                item.meta = {}
            }

            item.meta.title = item.meta.title || 'none'

            // 构建面包屑信息
            // 层级化的标题，格式如 "父标题 - 子标题"
            if (title) {
                item.meta.subtitle = title + ' - ' + (item.meta.subtitle || item.meta.title)
                item.meta.subtitles = [...title.split('-')].concat(item.meta.subtitles || [item.meta.title])
            } else {
                item.meta.subtitle = item.meta.title
                item.meta.subtitles = [item.meta.title]
            }

            // 构建完整路径
            if (path) {
                item.meta.forusepath = path + '/' + item.path
            } else {
                item.meta.forusepath = '/' + item.path
            }

            // 递归处理子路由
            if (item.children && item.meta.forrootset) {
                fn2(item.children, item.meta.subtitle || item.meta.title, item.meta.forusepath || item.path)
            }
        }
    }
    fn2(addTittleList)

    // 适配左侧导航路由 - 添加 /home 前缀 -- 适应特定的路由前缀要求
    const addPrefixList = getBaseChildren(router)
    const fn2b = (list) => {
        for (let ind = 0; ind < list.length; ind++) {
            const item = list[ind]
            if (!item) continue
            if (!item.meta) {
                item.meta = {}
            }

            // 防止重复添加 /home 前缀
            if (item.meta.forusepath && !item.meta.forusepath.startsWith('/home')) {
                item.meta.forusepath = '/home' + item.meta.forusepath
            }

            if (item.children && item.meta.forrootset) {
                fn2b(item.children)
            }
        }
    }
    fn2b(addPrefixList)



    // 匹配权限 - 优化算法复杂度
    const temproute = router.getRoutes()

    // 使用 Map 加速查找，时间复杂度从 O(n²) 降到 O(n)
    const memoryMap = new Map()
    memorylist.forEach(item => {
        memoryMap.set(item.path, item)
    })

    const fn = (list) => {
        for (let ind = 0; ind < list.length; ind++) {
            const item = list[ind]
            if (!item) continue

            // 通过 path 快速查找对应的权限配置
            const memoryItem = memoryMap.get(item.path)
            if (memoryItem) {
                if (!item.meta) {
                    item.meta = {}
                }
                item.meta.root = memoryItem.root
                item.meta.dicts = memoryItem.dicts || undefined // 字典相关
                item.meta.userroot = 1

                // 记录到全局按钮
                // globalBtnManager.setglobalbtn(item)
            }

            // 递归处理子路由
            if (item.children) {
                fn(item.children)
            }
        }
    }
    fn(temproute)

    return router
}

