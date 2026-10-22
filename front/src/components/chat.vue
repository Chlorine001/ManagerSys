<template>
  <div v-show="load == 1">
    <!-- 运动顶部 -->
    <div
      style="position: fixed;top:0;left:0; width: 100%;height:80px;line-height: 80px;text-align: center;background-color: var(--app-panel);z-index: 999;transition: 200ms; box-shadow: 0 2px 10px rgba(0,0,0,0.1);"
      :style="{ top: !ifshow ? '-80px' : 0, color: 'var(--app-text)' }">
      <span style="float: left;padding: 0 20px;cursor: pointer;" @click="ifshow = 0">
        <img :src="$root.isDark ? '/favicon-dark.ico' : '/favicon.ico'" alt=""
          style="display: inline-block;width: 40px;height:40px;vertical-align: middle;">
      </span>
      {{ titlechange('会话窗口') }}
      <span style="float:right;padding: 0 20px;font-size: 30px;cursor: pointer;" @click="ifshow = 0">×</span>
    </div>

    <!-- 运动窗体 -->
    <div
      style="position: fixed;top:80px;left:0; width: 100%;background-color: var(--app-panel);z-index: 99;transition: 500ms; box-shadow: 0 10px 30px rgba(0,0,0,0.2);"
      :style="{ height: winheight - 80 + 'px', top: !ifshow ? '-100%' : '80px' }">

      <!-- // nav -->
      <div style="float: left;overflow: hidden auto;" :style="{ height: winheight + 'px' }">
        <div style="padding: 20px 20px 0;">
          <!-- 替换了硬编码的 #fff 和 #308df7，改用 CSS 变量 -->
          <div class="nav-btn" :class="{ active: changetype == 2 }" @click="changetype = 2">
            <el-icon>
              <User />
            </el-icon>
          </div>
        </div>
        <div style="padding: 20px 20px 0;" v-if="showchat">
          <div class="nav-btn" :class="{ active: changetype == 1 }" @click="changetype = 1">
            <el-icon>
              <ChatDotRound />
            </el-icon>
          </div>
        </div>
      </div>

      <!-- // cont -->
      <div style="margin-left: 90px;margin-right: 20px;padding-top:20px">
        <div style="background-color: var(--app-bg);overflow: hidden auto;"
          :style="{ height: winheight - 120 + 'px', boxShadow: $root.boxShadow2 }">

          <!-- 通讯录列表 -->
          <div v-show="changetype == 2">
            <div class="list-item" v-for="v in 5" :key="v" @click="sel_else_init">
              <span style="float: left;padding: 0 20px;cursor: pointer;">
                <img :src="$root.isDark ? '/favicon-dark.ico' : '/favicon.ico'" alt=""
                  style="display: inline-block;width: 40px;height:40px;vertical-align: middle;border-radius: 50%;">
              </span>
              <div style="width:100%">
                <div style="padding:0 30px 0 80px;">
                  <div style="font-size: 16px;font-weight: 800; color: var(--app-text);">Test</div>
                  <div style="padding-top:0px; color: var(--app-text-secondary);font-size: 14px;word-break: break-all;">
                    Test文本！
                  </div>
                </div>
              </div>
              <div style="clear: both;"></div>
            </div>
          </div>

          <!-- 聊天窗口 -->
          <div v-show="changetype == 1">
            <div style="padding: 20px 20px 0;">
              <div style="overflow: hidden scroll;" id="chatwrap" :style="{ height: winheight - 220 + 'px' }">
                <div v-for="v in list" :key="v.id">
                  <!-- 左边部分 (对方) -->
                  <div class="chatcenteritem clearb" v-if="v.from != 'me'" :id="v.id">
                    <div class="chatcenterleft">
                      <div class="chatcenterleft-head">
                        <img :src="$root.isDark ? '/favicon-dark.ico' : '/favicon.ico'"
                          style="width:100%; height:100%; border-radius:50%;" />
                      </div>
                      <div class="chatcenterleft-nick">
                        <div class="chatcenterleft-nickbox" style="color: var(--app-text-secondary);">{{ v.nick }}</div>
                      </div>
                      <div class="chatcenterleft-cont">{{ v.cont }}</div>
                    </div>
                  </div>
                  <!-- 右边部分 (自己) -->
                  <div class="chatcenteritem clearb" v-if="v.from == 'me'" :id="v.id">
                    <div class="chatcenterright">
                      <div class="chatcenterright-head">
                        <img :src="$root.isDark ? '/favicon-dark.ico' : '/favicon.ico'"
                          style="width:100%; height:100%; border-radius:50%;" />
                      </div>
                      <div class="chatcenterright-nick">
                        <div class="chatcenterright-nickbox" style="color: var(--app-text-secondary);">{{ v.nick }}
                        </div>
                      </div>
                      <div class="chatcenterright-cont">{{ v.cont }}</div>
                    </div>
                  </div>
                  <div style="clear: both;"></div>
                </div>
              </div>
            </div>

            <!-- 输入区 -->
            <div style="padding:20px; display: flex; align-items: center;">
              <div style="flex: 1; margin-right: 20px;">
                <div class="input-wrap">
                  <input type="text" id="valuemsg" :placeholder="titlechange('输入要发送的内容')" @keyup.enter="sendmsg">
                </div>
              </div>
              <span class="send-btn" @click="sendmsg">
                {{ titlechange('发送') }}
              </span>
            </div>
          </div>

        </div>
      </div>
    </div>

    <!-- 浮动按钮 -->
    <div class="float-btn" @click="ifshow = 1">
      <el-icon>
        <ChatDotRound style="color: white;" />
      </el-icon>
    </div>
  </div>
</template>

<script>
import { setchatdatas } from '../utils/chatdata'

export default {
  data() {
    return {
      load: 0,
      winheight: '',
      ifshow: 0,
      showchat: 0,
      changetype: 2, // 1 是会话窗口，2 是列表
      list: [],
      mscrollinit: {},
      canload: 1,
      page: 0,
    }
  },
  watch: {
    ifshow(newVal) {
      document.body.style.overflow = newVal ? 'hidden' : ''
    }
  },
  methods: {
    sendmsg() {
      const input = document.getElementById('valuemsg')
      if (!input.value.trim()) return

      const ids = 'chat_me' + new Date().getTime()
      this.list.push({
        id: ids,
        nick: 'xxx',
        from: 'me',
        cont: input.value
      })
      input.value = '' // 发送后清空输入框

      setTimeout(() => {
        const el = document.getElementById(ids)
        if (el) el.scrollIntoView({ behavior: 'smooth' })
      }, 50)
    },
    ajaxgetchatres(fn) {
      if (this.list.length == 0) {
        setTimeout(() => {
          this.list = setchatdatas(new Date().getTime())
          setTimeout(() => {
            const el = document.getElementById(this.list[this.list.length - 1].id)
            if (el) el.scrollIntoView(true)
          })
          this.canload = 1
          fn()
        }, 1000)
      } else {
        var tempid = this.list[0].id
        setTimeout(() => {
          this.list = setchatdatas(new Date().getTime()).concat(this.list)
          setTimeout(() => {
            const el = document.getElementById(tempid)
            if (el) el.scrollIntoView(true)
            const wrap = document.getElementById('chatwrap')
            if (wrap) wrap.scrollTop = wrap.scrollTop - 30
          })
          this.canload = 1
          fn()
        }, 1000)
      }
    },
    sel_else_init() {
      if (!this.showchat) {
        this.showchat = 1
        this.initmscroll()
      }
      this.changetype = 1
      this.page = 0
      this.list = []
      if (this.mscrollinit && this.mscrollinit.triggerDownScroll) {
        this.mscrollinit.triggerDownScroll()
      }
    },
    initmscroll() {
      var _this = this;
      if (typeof MeScroll === 'undefined') {
        console.warn('MeScroll 插件未引入')
        return
      }
      this.mscrollinit = new MeScroll("chatwrap", {
        down: {
          callback: function () {
            if (_this.canload == 0) return;
            _this.canload = 0
            _this.page++;
            _this.mscrollinit.setPageNum(_this.page)
            _this.ajaxgetchatres(function () {
              _this.mscrollinit.endSuccess()
            })
          }
        }
      });
    },
    setdoms() {
      this.winheight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
    }
  },
  computed: {
    'titlechange'() {
      var that = this
      return function (title) {
        return that.$root.titlechange(title, that)
      }
    }
  },
  mounted() {
    setTimeout(() => {
      this.load = 1
      this.setdoms()
      // ✅ 修复内存泄漏：使用具名函数，并在卸载时移除
      window.addEventListener('resize', this.setdoms)
    }, 500)
  },
  beforeUnmount() {
    document.body.style.overflow = ''
    // ✅ 移除监听
    window.removeEventListener('resize', this.setdoms)
  }
}
</script>

<style scoped>
/* ======= 导航按钮 ======= */
.nav-btn {
  height: 50px;
  width: 50px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background-color: var(--app-panel);
  color: var(--app-text);
  transition: all 0.3s;
  border: 1px solid var(--app-border);
}

.nav-btn.active {
  background-color: #308df7 !important;
  color: #fff !important;
  border-color: #308df7;
}

/* ======= 列表项 ======= */
.list-item {
  min-height: 40px;
  padding: 20px 0;
  border-bottom: 1px solid var(--app-border);
  cursor: pointer;
  transition: background-color 0.2s;
}

.list-item:hover {
  background-color: rgba(128, 128, 128, 0.1);
}

/* ======= 聊天输入框 ======= */
.input-wrap {
  height: 40px;
  padding: 0 15px;
  border-radius: 20px;
  background-color: var(--app-input-bg);
  display: flex;
  align-items: center;
  border: 1px solid var(--app-border);
}

.input-wrap input {
  width: 100%;
  height: 100%;
  font-size: 15px;
  color: var(--app-text);
  background: transparent;
  border: none;
  outline: none;
}

.input-wrap input::placeholder {
  color: var(--app-text-secondary);
}

.send-btn {
  display: inline-block;
  padding: 0 25px;
  line-height: 40px;
  font-size: 15px;
  border-radius: 20px;
  cursor: pointer;
  background-color: #308df7;
  color: white;
  box-shadow: 0 4px 10px rgba(48, 141, 247, 0.3);
  transition: opacity 0.2s;
}

.send-btn:hover {
  opacity: 0.8;
}

/* ======= 浮动按钮 ======= */
.float-btn {
  position: fixed;
  right: 30px;
  bottom: 40px;
  width: 60px;
  height: 60px;
  background-color: #308df7;
  border-radius: 50%;
  z-index: 999;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 5px 15px rgba(48, 141, 247, 0.4);
  transition: transform 0.2s;
}

.float-btn:hover {
  transform: scale(1.05);
}

/* ======= 聊天气泡布局 ======= */
.clearb:after {
  content: '';
  display: block;
  clear: both;
}

.chatcenteritem {
  padding-bottom: 30px;
}

.chatcenterleft {
  position: relative;
  float: left;
  max-width: 80%;
}

.chatcenterright {
  position: relative;
  float: right;
  max-width: 80%;
}

.chatcenterleft-head,
.chatcenterright-head {
  position: absolute;
  top: 0;
  width: 40px;
  height: 40px;
  background-color: var(--app-bg);
  border-radius: 50%;
  overflow: hidden;
}

.chatcenterleft-head {
  left: 0;
}

.chatcenterright-head {
  right: 0;
}

.chatcenterleft-nick,
.chatcenterright-nick {
  position: absolute;
  top: 0;
  font-size: 13px;
}

.chatcenterleft-nick {
  left: 50px;
}

.chatcenterright-nick {
  right: 50px;
}

.chatcenterleft-nickbox,
.chatcenterright-nickbox {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

/* 聊天气泡主题适配 */
.chatcenterleft-cont {
  float: left;
  margin: 25px 0 0 50px;
  padding: 12px 16px;
  border-radius: 0 12px 12px 12px;
  background-color: var(--app-panel);
  color: var(--app-text);
  border: 1px solid var(--app-border);
  word-break: break-all;
  font-size: 15px;
  line-height: 1.5;
}

.chatcenterright-cont {
  float: right;
  margin: 25px 50px 0 0;
  padding: 12px 16px;
  border-radius: 12px 0 12px 12px;
  background-color: #308df7;
  color: #ffffff;
  word-break: break-all;
  font-size: 15px;
  line-height: 1.5;
}
</style>