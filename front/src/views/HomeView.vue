<template>
  <div>
    <fadeshowVue/>
    <div class="top">
      <!-- in -->
      <span class="logoimg">
        <img src="/favicon.ico" alt="">
      </span>
      <span class="projtitle"
            :style="{ width: ifopen ? undefined : winwidth > 400 ? '120px' : undefined }">后台管理系统</span>
      <span class="navslide projtitle" @click="setleftnav" v-if="ifopen">
        <svg t="1682083887476" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="5621" width="17" height="17">
          <path
              d="M109.632 673.664h519.68c25.152 0 45.568-22.016 45.568-48.896 0-26.88-20.416-48.896-45.568-48.896h-519.68c-25.216 0-45.632 22.016-45.632 48.896 0 26.88 20.48 48.896 45.632 48.896z m0-228.096h519.68c25.152 0 45.568-21.952 45.568-48.896 0-26.88-20.416-48.896-45.568-48.896h-519.68c-25.216 0-45.632 22.016-45.632 48.896 0 26.88 20.48 48.896 45.632 48.896z m3.264-219.904h795.776c26.88 0 50.56-20.352 51.328-47.168A48.896 48.896 0 0 0 911.104 128H115.328c-26.88 0-50.56 20.416-51.328 47.168a48.896 48.896 0 0 0 48.896 50.56z m619.776 447.232V348.672L960 510.784l-227.328 162.112c0 0.768 0 0.768 0 0z m178.432 122.944H115.328c-26.88 0-50.56 20.48-51.328 47.232a48.896 48.896 0 0 0 48.896 50.496h795.776c26.88 0 50.56-20.416 51.328-47.232a48.896 48.896 0 0 0-48.896-50.496z"
              fill="#000000" p-id="5622"></path>
        </svg>
      </span>
      <span class="navslide projtitle" @click="setleftnav" v-else>
        <svg t="1682083749544" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="5241" width="17" height="17">
          <path
              d="M914.368 673.664h-519.68c-25.152 0-45.568-22.016-45.568-48.896 0-26.88 20.416-48.896 45.568-48.896h519.68c25.216 0 45.632 22.016 45.632 48.896 0 26.88-20.48 48.896-45.632 48.896z m0-228.096h-519.68c-25.152 0-45.568-21.952-45.568-48.896 0-26.88 20.416-48.896 45.568-48.896h519.68c25.216 0 45.632 22.016 45.632 48.896 0 26.88-20.48 48.896-45.632 48.896z m-3.264-219.904H115.328c-26.88 0-50.56-20.352-51.328-47.168A48.896 48.896 0 0 1 112.896 128h795.776c26.88 0 50.56 20.416 51.328 47.168a48.896 48.896 0 0 1-48.896 50.56z m-619.776 447.232V348.672L64 510.784l227.328 162.112c0 0.768 0 0.768 0 0z m-178.432 122.944h795.776c26.88 0 50.56 20.48 51.328 47.232a48.896 48.896 0 0 1-48.896 50.496H115.328c-26.88 0-50.56-20.416-51.328-47.232a48.896 48.896 0 0 1 48.896-50.496z"
              fill="#000000" p-id="5242"></path>
        </svg>
      </span>
      <span style="float:right">
        <righttop @title="settitle"/>
      </span>
    </div>

    <!-- out -->
    <chat></chat>

    <div class="left">
      <div class="leftin">
        <div class="theleftin">
          <!-- in -->
          <el-menu class="el-menu-vertical-demo" router
                   :collapse="!ifopen && allshow ? false : (!!(ifopen || (!ifopen && allhide)))"
                   :default-active="nowroute">
            <template v-for="val in $router.options.routes">
              <template v-if="val.meta && val.meta.title === 'base'">
                <template v-for="(v, i) in val.children">
                  <el-sub-menu v-if="v && v.children && v.children.length && v.meta.userroot"
                               :index="v.meta.forusepath || v.name">
                    <template #title>
                      <a style=" display: inline-block;margin-top:-20px; height:0;line-height: normal; vertical-align: top;">
                        <!--                         <i :class="'fas ' + icons[v.name]" style=" width:20px;font-size: 20px;"></i>-->
                        <!--                         <img class="navimg" src="../../public/favicon.ico" />-->
                        <elicon :name="v.meta.icon"></elicon>
                      </a>
                      <span v-if="!ifopen" style="margin-left: 10px;">{{ v && titlechange(v.meta.title) }}</span>
                    </template>

                    <!--                     <el-menu-item-group>-->
                    <menuitem :list="v" :ifopen="ifopen" @title="settitle">
                    </menuitem>
                    <!--                     </el-menu-item-group>-->
                  </el-sub-menu>

                  <el-menu-item v-else-if="!v.meta || v.meta && v.meta.ishide !== true && v.meta.userroot"
                                :index="v.meta.forusepath || v.name"
                                @click="$root.replaceto(v.meta.forusepath), title = v.meta.title, nowroute = v.path || v.name">
                    {{ console.log(v.meta.ishide,'child:', v, 'meta:', v.meta) }}

                    <!-- <i :class="'fas ' + icons[v.name]" style=" width:20px; font-size: 20px;"></i> -->
                    <!-- <img class="navimg" src="../../public/favicon.ico"> -->
                    <elicon :name="v.meta.icon"></elicon>

                    <template #title>
                      <span style="margin-left: 10px;">{{ v && titlechange(v.meta.title) }}</span>
                    </template>
                  </el-menu-item>

                </template>

              </template>
            </template>
          </el-menu>


          <!-- out -->
          <div style="height:56px"></div>
        </div>
        <!-- nav bottom -->
        <template v-for="val in $router.options.routes">
          <template v-if="val.meta && val.meta.title === 'base'">

            <template v-for="(v, i) in val.children">
              <div style="position: absolute;left:0;bottom: 0;width:100%;"
                   v-if="v.path === 'icons' && !v.meta || v.meta && v.meta.userroot">
                <div style="margin-right: 9px;border-top: 1px solid #ccc;">
                  <el-menu class="el-menu-vertical-demo"
                           :collapse="!ifopen && allshow ? false : (!!(ifopen || (!ifopen && allhide)))">
                    <el-menu-item style="_background-color: #fff;" @click="toicons">
                      <elicon name="icons"></elicon>
                      <template #title>
                        <span style="margin-left: 10px;">更多图标展示</span>
                      </template>
                    </el-menu-item>
                  </el-menu>
                </div>
              </div>
            </template>
          </template>
        </template>
      </div>
    </div>


    <!-- # cont -->
    <div style="padding-top: 80px;transition: 0.3s ease-out;" :style="{ paddingLeft: ifopen ? '65px' : '280px' }">
      <span style="display: inline-block;padding: 20px 20px 0;min-width: 260px;">
        {{ titlechange('当前位置') }}：
        <span v-if="titles && titles.length">
          <span v-for="(v, i) in titles">
            {{ titlechange(v) }}{{ i != titles.length - 1 ? ' / ' : '' }}
          </span>
        </span>
        <!-- 当前位置： {{ title || '标题' }} -->
      </span>
      <div style="padding:20px;min-width: 260px;">
        <router-view v-slot="{ Component }">
          <transition mode="out-in" name="fade-transform">
            <component :is="Component"/>
          </transition>
        </router-view>
        <!-- 权限：{{ $route.meta.root }} -->
      </div>
    </div>
    <!-- vue3test -->
    <vue3test></vue3test>
  </div>
</template>

<script>
import {getglobalbtn} from '@/utils/utils'
import loginShadowVue from '../views/login/loginshadow.vue'
import chat from '../components/chat.vue'
import menuitem from '../components/menuitem.vue'
import righttop from '../components/righttop.vue'
import vue3test from '../components/vue3test.vue'
import elicon from '../components/fonticons.vue'
import {ElMessage, ElMessageBox, ElLoading} from 'element-plus'

export default {
  data() {
    return {
      title: '',
      titles: '',
      winwidth: '',
      ifopen: false,
      //
      ifhide: 0,
      allhide: 0,
      allshow: 1,
      load: 0,
      //
      nowroute: ''
    }
  },
  components: {
    fadeshowVue: loginShadowVue,
    chat,
    menuitem,
    righttop,
    vue3test,
    elicon
  },
  methods: {
    doms() {
      this.winwidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
      if ((window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) < 758) {
        if (this.load == 0) {
          this.load = 1
          this.allhide = 1
          this.allshow = 0
        }
        if (this.allshow == 1) {
          return;
        }
        this.$root.openchange = true//全局
        this.ifopen = true
      } else {
        this.allshow = 0
        if (this.load == 0) {
          this.load = 1
          this.allhide = 0
          this.allshow = 1
        }
        if (this.allhide == 1) {
          return;
        }
        this.$root.openchange = false//全局
        this.ifopen = false
      }
    },
    setleftnav() {
      if (this.allhide == 0 && this.ifopen == false) {
        this.$root.openchange = true//全局
        this.ifopen = true
        this.allhide = 1
        this.allshow = 0
      } else {
        this.load = 1
        this.$root.openchange = false//全局
        this.ifopen = false
        this.allhide = 0
        this.allshow = 1
      }
    },
    //
    settitle(v) {
      this.title = v.meta.subtitle || '--'
      this.titles = v.meta.subtitles || '--'
      //
      this.nowroute = (v.path.split('/')[v.path.split('/').length - 1]) || v.name
    },
    gettitle() {
      this.title = this.$route.meta.subtitle || '--'
      this.titles = this.$route.meta.subtitles || '--'
      //
      this.nowroute = (this.$route.path.split('/')[this.$route.path.split('/').length - 1]) || this.$route.meta.name
      //
      window.scrollTo(0, 0)
    },
    //
    toicons() {
      this.$router.replace({path: '/home/icons'})
    },
    //
    showreport() {
      var loadings = ElLoading.service()
      // const loadings = ElLoading.service();

      // 设置加载动画显示时长（例如：3秒）
      // const loadingTimeout = setTimeout(() => {
      //   loadings.close();
      //   // 可选：在此处添加对超时情况的处理逻辑，如提示用户加载超时
      // }, 3000);


      $.ajax({
        url: '/api/back_notice/text',
        timeout: 3000,
        type: 'get',
        success: (res) => {
          loadings.close()
          if (res) {  //关于本站弹窗提示
            ElMessageBox.alert(res, '关于本站'
                , {
                  dangerouslyUseHTMLString: true,
                  // callback() {
                  //   console.log(res);
                  // }
                }
            )
            // console.log(res)
          }
        },
        fail: (error) => {
          // console.log(error)
          if (error === 'timeout') {
            // console.log('请求超时！');
            ElMessageBox.alert('加载超时！\n请检查网络', 'Timeout!')
            // 在此处处理超时后的逻辑，如提示用户、重试请求等
          } else {
            // console.log('请求失败！');
            ElMessageBox.alert('请求失败！\n请检查配置！', 'Error!')
            // 处理其他类型的错误
          }
          loadings.close()
          // ElMessageBox.alert('加载超时！\n请检查网络', 'Error!')
          // ElMessageBox.alert('网络错误', '关于本站')
        }
      })

    }
  },
  watch: {
    '$route.path'(news, olds) {
      this.gettitle()
      var k = 0;
      for (var i = 0; i < JSON.parse(localStorage.getItem('navroot')).length; i++) {
        if (this.$route.path == JSON.parse(localStorage.getItem('navroot'))[i].path) {
          k = 1
        }
      }
      if (k == 0) {
        ElMessage({
          type: 'error',
          message: '页面消失了，请尝试清理缓存'
        })
        this.$router.back()
      }
    }
  },
  computed: {
    'titlechange'() {
      var that = this
      return function (title) {
        return that.$root.titlechange(title, that)
      }
    },
    'getglobalbtnroot'() {
      var that = this
      return function (name) {
        return that.$root.globalbtn.indexOf(name) != -1
      }
    }
  },
  mounted() {
    this.gettitle()
    this.$root.globalbtn = getglobalbtn()
    this.showreport()
    //
    this.doms()
    window.addEventListener('resize', () => {
      this.doms()
    })
  }
}
</script>

<style scoped>
.top {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 80px;
  box-shadow: 0 0 10px 0 #333;
  z-index: 100;
  background-color: white;
  white-space: nowrap;
}

.logoimg {
  display: inline-block;
  margin-top: 20px;
  margin-left: 20px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  /* background-color: #333; */
}

.logoimg img {
  width: 100%;
  height: 100%;
}

.projtitle {
  display: inline-block;
  padding-left: 20px;
  vertical-align: top;
  line-height: 80px;
  font-size: 20px;
  font-weight: 800;
}

.navslide {
  cursor: pointer;
}

/* // */
.left {
  position: fixed;
  top: 0;
  left: 0;
  padding-top: 80px;
  /* width: 280px; */
  height: 100%;
  z-index: 10;
  box-sizing: border-box;
}

.leftin {
  position: relative;
  padding-right: 10px;
  width: 100%;
  height: 100%;
  overflow: hidden;
  box-sizing: border-box;
}

.theleftin {
  width: 100%;
  height: 100%;
  box-shadow: 0 0 10px 0 #333;
  background-color: white;
  overflow: hidden auto;
  scrollbar-width: thin;
  scrollbar-color: #f0f0f0 #c7c7c7;
}

.theleftin::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 5px;
  /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
}

.theleftin::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  background: #c1e2ff;
}

.theleftin::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  border-radius: 10px;
  background: #fafafa;
}

@media (max-width: 600px) {
  .projtitle {
    display: none;
  }

  .navslide {
    display: inline-block;
  }
}
</style>

<style>
body,
html {
  background-color: #f8f8f8;
  scrollbar-width: thin;
  scrollbar-color: #f0f0f0 #c7c7c7;
}

body::-webkit-scrollbar,
html::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 5px;
  /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
}

body::-webkit-scrollbar-thumb,
html::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  background: #c7c7c7;
}

body::-webkit-scrollbar-track,
html::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  border-radius: 10px;
  background: #f0f0f0;
}

.navimg {
  display: inline-block;
  width: 20px;
  height: 20px;
  /* border-radius: 50%; */
  /* background-color: #333; */
  vertical-align: middle !important;
  overflow: hidden;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 280px;
}

.el-calendar-table .el-calendar-day {
  height: 50px !important
}

.el-calendar-table th {
  height: 20px;
}

.el-calendar__header {
  height: 25px;
}

.fade-transform-enter-active {
  position: relative;
  animation: scale 500ms;
}

.fade-transform-leave-active {
  position: relative;
  animation: scale2 500ms;
}

@keyframes scale {
  0% {
    opacity: 0;
    left: -100px;
  }

  100% {
    opacity: 1;
    left: 0px;
  }
}

@keyframes scale2 {
  0% {
    opacity: 1;
    left: 0px;
  }

  100% {
    opacity: 0;
    left: -100px;
  }
}
</style>