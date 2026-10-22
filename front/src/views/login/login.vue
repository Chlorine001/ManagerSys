<template>
  <div>
    <div class="wrapout">
      <div
        style="position: fixed; top: 20px; right: 30px; z-index: 10; cursor: pointer; font-size: 24px; user-select: none;"
        @click="$root.toggleDark()" title="切换主题">
        {{ $root.isDark ? '🌙' : '☀️' }}
      </div>
      <div class="wrap">
        <div style="max-width: 520px;height: 100%;">
          <div class="wrapin">
            <h1 style="padding-top: 70px; text-align: center;">
              <span
                style="display: inline-block;padding: 20px; width:40px;height: 40px;border-radius: 50%;background-color: var(--app-panel);overflow: hidden;">
                <img :src="$root.isDark ? '/favicon-dark.ico' : '/favicon.ico'" style="width:40px;height: 40px;"
                  alt=""></span>
            </h1>
            <h1 style="padding-top: 20px; text-align: center;">后台管理系统</h1>
            <div style="padding:40px 66px 30px;">
              <div style="margin: 0 0 25px;">
                <el-input placeholder="请输入登录账号" v-model="name"
                  style="background-color:var(--app-login-input);border-radius: 40px;">
                  <template #prepend><span class="sign">账号：</span></template>
                </el-input>
              </div>
              <div style="margin: 0 0 25px;">
                <el-input placeholder="请输入登录密码" v-model="password" type="password"
                  style="background-color:var(--app-login-input);border-radius: 40px;">
                  <template #prepend><span class="sign">密码：</span></template>
                </el-input>
              </div>
              <div style="margin: 0 0 25px; display: flex; align-items: center; justify-content: space-between;">
                <el-input placeholder="请输入验证码" v-model="code" maxlength="4" @keyup.enter="tologin"
                  style="background-color:var(--app-login-input);border-radius: 40px; flex: 1;">
                  <template #prepend><span class="sign">验证码：</span></template>
                </el-input>
                <!-- 验证码图片/画布，点击可刷新 -->
                <div
                  style="width: 100px; height: 40px; margin-left: 10px; cursor: pointer; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 6px rgba(0,0,0,0.1);"
                  @click="refreshCode" title="点击刷新验证码">
                  <img v-if="codeImg" :src="codeImg" style="width: 100%; height: 100%; object-fit: cover;" />
                  <canvas v-else ref="codeCanvas" width="100" height="40" style="width: 100%; height: 100%;"></canvas>
                </div>
              </div>
            </div>
            <div style="padding:20px 40px 0; text-align: center;">
              <span style="display: inline-block;width: 70%;max-width: 150px;">
                <el-button
                  style="width: 100%; border-radius: 40px;_background-color: #41b883;_border: none;box-shadow: 0 5px 30px 0 #777;"
                  type="primary" :loading="loading" size="large" @click="tologin">登录</el-button>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="position: fixed;top:-100%;left:0;width: 100%;height: 100%;background-color: var(--app-text);z-index: 3;"
      :class="{ logined: logined }"></div>
  </div>
</template>

<script>
import { fns } from '@/utils/utils.js'
import { ElMessage } from 'element-plus'
import { http } from '@/utils/http'
import { loadMenuRoutes, addMenuRoutes, dynamicRoutes, groupByPid, buildRoutes, goBase } from '@/router/router'

export default {
  data() {
    return {
      logined: false,
      name: 'admin',
      password: 'admin',
      code: '',          // ✅ 绑定的用户输入的验证码
      codeValue: '',     // ✅ 当前的真实验证码文本（仅前端演示用）
      codeImg: '',       // ✅ 后端返回的验证码 Base64（如果是后端生成）
      loading: false,
    }
  },
  mounted() {
    this.refreshCode()
  },
  methods: {
    async tologin() {
      if (!this.code) {
        ElMessage({ type: 'warning', message: '请输入验证码' })
        return
      }
      if (this.code.toUpperCase() !== this.codeValue.toUpperCase()) {
        ElMessage({ type: 'error', message: '验证码错误，请重试' })
        this.code = ''
        this.refreshCode() // 错误后自动刷新
        return
      }
      this.loading = true
      try {
        const res = await http.post('/auth/login', { name: this.name, password: this.password, code: this.code }, { timeoutMs: 5000 })
        this.logined = true
        // 保存 token 和用户信息
        const { token, user, navroot } = res.data
        if (!token) throw new Error('登录响应缺少 token')
        if (!user) throw new Error('登录响应缺少 user')

        localStorage.setItem('token', token)
        localStorage.setItem('currentUser', JSON.stringify(user))

        // 验证并处理菜单路由数据
        if (navroot.length === 0) {
          console.warn('未获取到菜单数据')
          ElMessage({ type: 'warning', message: '暂无可用菜单权限' })
        } else {
          try {
            // 将扁平菜单转换为树形结构
            const grouped = groupByPid(navroot)
            // 构建动态路由树（从 pid=0 开始）
            const menuRoutes = buildRoutes(0, grouped)
            // 保存原始菜单数据到 localStorage（供侧边栏使用）
            localStorage.setItem('navroot', JSON.stringify(navroot))
            // 保存构建好的路由配置到 localStorage（可选，用于调试）
            // localStorage.setItem('menuRoutes', JSON.stringify(menuRoutes))
            // 动态添加路由到 router 实例
            addMenuRoutes(menuRoutes)
            // console.log('menuRoutes:', menuRoutes)
            // console.log('router:', this.$router.options.routes)
            // 处理路由元信息（面包屑、权限等）- 传入原始的 navroot
            fns(this.$router, navroot)
          } catch (e) {
            console.error('处理菜单路由失败:', e.message || e)
            ElMessage({ type: 'error', message: '路由配置异常' })
          }
        }

        setTimeout(() => {
          const navroot = JSON.parse(localStorage.getItem('navroot') || '[]')
          if (navroot.length === 0) { ElMessage({ type: 'error', message: '没有获取到页面地址组' }) } else { goBase('/home') }
        }, 200)
      } catch (e) {
        this.refreshCode()
        this.code = ''
        ElMessage({
          type: 'error',
          message: e?.message === 'timeout' ? '登录超时，请检查后端服务！' : e.message
        })
      } finally {
        this.loading = false
      }
    },
    refreshCode() {
      // 【方案 A：纯前端Canvas生成（演示用）】
      const canvas = this.$refs.codeCanvas
      if (!canvas) return
      const ctx = canvas.getContext('2d')
      const width = canvas.width
      const height = canvas.height

      // 清空画布，铺背景
      ctx.clearRect(0, 0, width, height)
      ctx.fillStyle = '#f0f0f0'
      ctx.fillRect(0, 0, width, height)

      // 生成随机 4 位字符
      const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789' // 去掉了容易混淆的 O0 I1
      let code = ''
      for (let i = 0; i < 4; i++) {
        code += chars.charAt(Math.floor(Math.random() * chars.length))
      }
      this.codeValue = code // 保存真实验证码用于比对

      // 画出字符
      for (let i = 0; i < 4; i++) {
        ctx.font = `${Math.floor(Math.random() * 6 + 20)}px Arial`
        ctx.fillStyle = `rgb(${Math.random() * 100}, ${Math.random() * 100}, ${Math.random() * 100})`
        ctx.save()
        const x = 15 + i * 20
        const y = 25 + Math.random() * 5
        ctx.translate(x, y)
        ctx.rotate((Math.random() - 0.5) * 0.5) // 随机旋转
        ctx.fillText(code[i], 0, 0)
        ctx.restore()
      }

      // 画几条干扰线
      for (let i = 0; i < 4; i++) {
        ctx.strokeStyle = `rgba(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255}, 0.5)`
        ctx.beginPath()
        ctx.moveTo(Math.random() * width, Math.random() * height)
        ctx.lineTo(Math.random() * width, Math.random() * height)
        ctx.stroke()
      }
    }
  }
}
</script>

<style scoped>
/* ✅ 暗黑模式下，用伪元素盖一层半透明黑布 */
html.dark .wrapout::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  /* 调整 0.6 控制暗度 */
  pointer-events: none;
  /* 让鼠标点击可以穿透这层黑布 */
  z-index: 0;
}
.wrapout {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: brown;
  background: url(../../assets/back.jpg) center no-repeat;
  background-size: cover;
}

.wrap {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.wrapin {
  margin: auto;
  height: 100%;
  background-color: rgba(255, 255, 255, .5);
  /* border-radius: 20px; */
}

.sign {
  display: inline-block;
  width: 50px;
}

.logined {
  transition: 200ms top;
  top: 0px !important
}
</style>

<style>
body,
html {
  height: 100%;
}

.wrapin .el-input__wrapper {
  background-color: unset;
  box-shadow: unset;
}

.wrapin .el-input-group__prepend {
  border-radius: 40px 0 0 40px;
}
</style>