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

/**
 * 获取当前登录用户名
 * @returns {string|null} - 用户名，如果未登录则返回 null
 */
export const getCurrentUserName = () => {
    const user = getCurrentUser()
    return user ? user.name : null
}