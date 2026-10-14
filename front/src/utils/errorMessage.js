export function getErrorMessage(e, defaultMsg = '操作失败') {
    if (!e) return defaultMsg
    switch (e.type) {
        case 'timeout':
            return '请求超时，请稍后重试'

        case 'network':
            return '网络异常，请检查网络连接'
        case 'business':
            // 业务错误：后端 message 一般是中文，直接用
            return e.message || defaultMsg

        case 'system':
        case 'http':
            console.log('Error', e)
            // 系统/HTTP 错误：优先用后端 message，没有再用兜底
            return e.body?.message || defaultMsg

        default:
            console.log('Error', e)
            // 未分类：可能是原生 Error，做一次中英文过滤
            return isUserFriendly(e.message) ? e.message : defaultMsg
    }
}

// 判断 message 是否是"给用户看"的（简单版：含中文就当友好文案）
function isUserFriendly(msg) {
    return typeof msg === 'string' && /[\u4e00-\u9fa5]/.test(msg)
}