var roles = ['view', 'edit', 'add', 'del']

//字典格式：路由name+增删改查其中一种+数据类型，比如home:view:nums

export const dictionarys = [
    { name: 'home:' + roles[0] + ':nums', path: '/home/home' },//顶部数字 - 首页
    { name: 'home:' + roles[0] + ':radar', path: '/home/home' },//雷达图 - 首页
    { name: 'home:' + roles[0] + ':calendar', path: '/home/home' },//日历 - 首页
    { name: 'home:' + roles[0] + ':bar', path: '/home/home' },//树状图 - 首页
    { name: 'home:' + roles[0] + ':pie', path: '/home/home' },//饼状图 - 首页
    { name: 'page1:' + roles[1] + ':ban', path: '/home/systemSet/page1' },//禁用 - 权限配置
    { name: 'page1:' + roles[1] + ':userroot', path: '/home/systemSet/page1' },//权限设置 - 权限配置
    { name: 'page1:' + roles[1] + ':userdictionarys', path: '/home/systemSet/page1' },//字设配置 - 权限配置
    { name: 'pagerootsettip:' + roles[0] + ':test', path: '/home/systemSet/pagerootsettip' },//测试页面
    { name: 'pagerootsettip:' + roles[1] + ':test', path: '/home/systemSet/pagerootsettip' },//测试页面
    { name: 'pagerootsettip:' + roles[2] + ':test', path: '/home/systemSet/pagerootsettip' },//测试页面
    { name: 'pagerootsettip:' + roles[3] + ':test', path: '/home/systemSet/pagerootsettip' },//测试页面
]