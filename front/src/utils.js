var globalbtn = []//全局架构上的页面跳转按钮显示配置（不算右下角打开聊天组件的按钮）

var setglobalbtn = (v) => {
    globalbtn.push(v.name)
}

export const getglobalbtn = () => {
    return globalbtn
}

export const fns = (router, memorylist) => {
    //处理面包屑和左侧导航
    var tempnavlist = []
    for (var i = 0; i < router.getRoutes().length; i++) {
        if (router.getRoutes()[i].meta.title == 'base') {
            tempnavlist = router.getRoutes()[i].children
        }
    }
    var fn2 = (list, title, path) => {
        title = title
        path = path
        for (var ind = 0; ind < list.length; ind++) {
            if (!list[ind].meta) {
                list[ind].meta = {}
            }
            list[ind].meta.title = list[ind].meta.title || 'none'
            if (title) {
                list[ind].meta.subtitle = title + ' - ' + (list[ind].meta.subtitle || list[ind].meta.title)
                list[ind].meta.subtitles = [...title.split('-')].concat(list[ind].meta.subtitles || [list[ind].meta.title])
            } else if (list[ind]) {
                list[ind].meta.subtitle = list[ind].meta.title
                list[ind].meta.subtitles = [list[ind].meta.title]
            }
            if (path) {
                list[ind].meta.forusepath = path + '/' + list[ind].path
            } else if (list[ind]) {
                list[ind].meta.forusepath = '/' + list[ind].path
            }
            if (list[ind] && list[ind].children && list[ind].meta.forrootset) {
                fn2(list[ind].children, (list[ind].meta.subtitle || list[ind].meta.title), (list[ind].meta.forusepath || list[ind].path));
            }
        }
    }
    fn2(tempnavlist)

    //适配左侧导航路由
    var tempnavlist2b = []
    for (var i = 0; i < router.getRoutes().length; i++) {
        if (router.getRoutes()[i].meta.title == 'base') {
            tempnavlist2b = router.getRoutes()[i].children
        }
    }
    var fn2b = (list) => {
        for (var ind = 0; ind < list.length; ind++) {
            if (!list[ind].meta) {
                list[ind].meta = {}
            }
            if (list[ind].meta.forusepath) {
                list[ind].meta.forusepath = '/home' + list[ind].meta.forusepath
            }
            if (list[ind].children && list[ind].meta.forrootset) {
                fn2b(list[ind].children);
            }
        }
    }
    fn2b(tempnavlist2b)

    //匹配权限

    var temproute = router.getRoutes()
    var fn = list => {
        for (var i = 0; i < memorylist.length; i++) {
            var items = list.filter(item => item.path == memorylist[i].path)
            list.map((v, ind) => {
                if (v.path == (items[0] && items[0].path)) {
                    if (!list[ind].meta) {
                        list[ind].meta = {}
                    }
                    list[ind].meta.root = memorylist[i].root
                    list[ind].meta.dicts = memorylist[i].dicts ? memorylist[i].dicts : undefined//字典相关
                    list[ind].meta.userroot = 1
                    if (list[ind].children) {
                        fn(list[ind].children)
                    }
                }
                if (list[ind].meta.userroot == 1) {
                    setglobalbtn(list[ind])
                }
            })
        }
    }
    fn(temproute)
    return router
}

/**
 * 获取当前登录用户信息
 * @returns {Object|null} - 用户对象，如果未登录则返回 null
 */
export const getCurrentUser = () => {
    const userStr = localStorage.getItem('currentUser')
    if (userStr) {
        try {
            return JSON.parse(userStr)
        } catch (e) {
            console.error('解析用户信息失败:', e)
            return null
        }
    }
    return null
}
