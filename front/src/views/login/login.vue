<template>
  <div>
    <div class="wrapout">
      <div class="wrap">
        <div style="max-width: 520px;height: 100%;">
          <div class="wrapin">
            <h1 style="padding-top: 70px; text-align: center;">
                            <span
                                style="display: inline-block;padding: 20px; width:40px;height: 40px;border-radius: 50%;background-color: white;overflow: hidden;">
                                <img src="/favicon.ico" style="width:40px;height: 40px;" alt="">
                            </span>
            </h1>
            <h1 style="padding-top: 20px; text-align: center;">后台管理系统</h1>
            <div style="padding:40px 66px 30px;">
              <div style="margin: 0 0 25px;">
                <el-input placeholder="登录账号" v-model="account"
                          style="background-color: rgba(255, 255, 255, .7);border-radius: 40px;">
                  <template #prepend><span class="sign">账号：</span></template>
                </el-input>
              </div>
              <div style="margin: 0 0 25px;">
                <el-input placeholder="登录密码" v-model="pass" type="password"
                          style="background-color: rgba(255, 255, 255, .7);border-radius: 40px;">
                  <template #prepend><span class="sign">密码：</span></template>
                </el-input>
              </div>
<!--              <div>-->
<!--                <el-input placeholder="输入验证码" value="dragon"-->
<!--                          style="background-color: rgba(255, 255, 255, .7);border-radius: 40px;">-->
<!--                  <template #prepend><span class="sign">验证码：</span></template>-->
<!--                </el-input>-->
<!--              </div>-->
            </div>
            <div style="padding:20px 40px 0; text-align: center;">
              <span style="display: inline-block;width: 70%;max-width: 150px;">
                <el-button
                    style="width: 100%; border-radius: 40px;_background-color: #41b883;_border: none;box-shadow: 0 5px 30px 0 #777;"
                    type="primary" :loading="loading" size="large" @click="tologin">登录</el-button>
              </span>
            </div>
<!--            <div style="padding: 20px 30px;">-->
<!--              <div-->
<!--                  style="position: relative; padding-top:20px; text-align: center;color: black;overflow: hidden;">-->
<!--                教师身份：除admin外，任意登录<br>管理员：账号admin，密码123456-->
                <!--                <div-->
                <!--                    style="position: absolute;top:-1px;left:-1px; padding-top:20px; width: 100%; text-align: center;color: white;">-->
                <!--                  管理员：admin账号，123456密码，游客身份：除了admin之外，随便输-->
<!--                                </div>-->
<!--              </div>-->
<!--            </div>-->
          </div>
        </div>
      </div>
    </div>
    <div style="position: fixed;top:-100%;left:0;width: 100%;height: 100%;background-color: #333;z-index: 3;"
         :class="{ logined: logined }"></div>
  </div>
</template>

<script>
import {fns, getglobalbtn} from '../../utils.js'
import {ElMessage} from 'element-plus'
import { http } from '@/api/http'
import { loadMenuRoutes, addMenuRoutes } from '@/router'

export default {
  data() {
    return {
      logined: false,
      account: 'admin',
      pass: 'admin',
      loading: false,
    }
  },
  methods: {
    async tologin() {
      this.loading = true
      try {
        const res = await http.postJson('/auth/login', { account: this.account, pass: this.pass }, { timeoutMs: 5000 })
        if (!res || res.ok !== true) {
          ElMessage({ type: 'error', message: (res && res.message) ? res.message : '登录失败' })
          return
        }
        this.logined = true
        localStorage.setItem('token', res.token || 'token')

        const navroot = Array.isArray(res.navroot) ? res.navroot : []
        localStorage.setItem('navroot', JSON.stringify(navroot))
        this.$router.options.routes = fns(this.$router, navroot).getRoutes()

        setTimeout(() => {
          if ((localStorage.getItem('navroot') || []).length === 0) {
            ElMessage({ type: 'error', message: '没有获取到页面地址组' })
          } else {
            this.$router.replace('/home/home')
          }
        }, 200)
      } catch (e) {
        ElMessage({ type: 'error', message: e?.message === 'timeout' ? '登录超时，请检查后端服务！' : '登录失败，请检查后端服务！' })
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
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