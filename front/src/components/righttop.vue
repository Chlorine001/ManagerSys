<template>
  <div style="line-height: 80px;">
        <span class="username" style="display: inline-block;" @click="showfades = 1">
            <svg t="1681450493936" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="3611" width="15" height="15" style="transform: scale(1.3);">
                <path
                    d="M465.334857 146.285714c176.201143 0 319.024762 145.091048 319.024762 324.096a325.973333 325.973333 0 0 1-65.560381 196.827429L877.714286 828.611048 829.366857 877.714286l-157.647238-160.182857a314.855619 314.855619 0 0 1-206.384762 76.921904C289.133714 794.453333 146.285714 649.386667 146.285714 470.381714S289.133714 146.285714 465.334857 146.285714z m0 69.436953c-138.459429 0-250.684952 114.029714-250.684952 254.659047 0 140.629333 112.225524 254.634667 250.684952 254.634667 138.435048 0 250.660571-114.005333 250.660572-254.634667 0-140.629333-112.225524-254.659048-250.660572-254.659047z"
                    p-id="3612"></path>
            </svg>
        </span>
    <span class="username" style="display: inline-block;">
            <svg t="1682079966770" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="3778" width="15" height="15" style="transform: scale(1.3);">
                <path
                    d="M782 912H242c-71.68 0-130-58.32-130-130V242c0-71.68 58.32-130 130-130h540c71.68 0 130 58.32 130 130v540c0 71.68-58.32 130-130 130zM242 172c-38.59 0-70 31.41-70 70v540c0 38.59 31.41 70 70 70h540c38.59 0 70-31.41 70-70V242c0-38.59-31.41-70-70-70H242z"
                    p-id="3779" fill="#2c2c2c"></path>
                <path
                    d="M455.49 621.78c-3.97-1.08-8.51-1.71-13.51-1.9-4.32-0.15-8.84-0.21-13.6-0.21h-100.5v-86.64h107.87c9.01 0 15.72-1.65 20.48-4.99 4.09-2.86 8.98-8.65 8.98-20.14 0-9.2-2.82-16.12-8.37-20.56-4.91-3.93-11.8-5.86-21.09-5.86H327.89V404.8h112.2c8.39 0 14.92-1.69 19.96-5.14 4.34-2.94 9.51-8.88 9.5-20.98-0.48-9.18-3.66-15.97-9.53-20.18-4.96-3.53-11.48-5.25-19.93-5.25H302.76c-10.47 0-18.01 2.31-23.07 7.09-5.17 4.93-7.69 12.25-7.69 22.36v259.06c0 11.11 3.15 19.06 9.36 23.65 5.28 3.91 12.28 5.82 21.4 5.82h139.93c9.38 0 16.37-1.54 21.44-4.74 4.45-2.83 9.75-8.84 9.75-21.26 0-8.19-3.11-13.24-5.7-16.01-3.26-3.53-7.52-6.03-12.69-7.44zM745.3 356.36c-4.74-5.25-11.96-7.91-21.46-7.91-9.85 0-17.18 3.02-21.79 8.99-3.95 5.1-5.94 11.4-5.94 18.74v188.1L559.8 364.97c-2.85-3.45-5.68-6.6-8.5-9.41-3.24-3.24-9.06-7.11-18.51-7.11-11.87 0-17.89 5.16-20.85 9.5-3.42 4.99-5.14 11.13-5.14 18.23v271.21c0 7.47 2.22 13.94 6.59 19.23 3.36 4.06 9.75 8.93 21.13 8.93 11.23 0 17.72-4.74 21.17-8.72 4.64-5.33 6.99-11.89 6.99-19.44v-184.6l135.35 195.08c3.18 4.61 6.73 8.5 10.59 11.63 5.01 4.02 10.86 6.05 17.39 6.05 11.9 0 17.92-5.18 20.85-9.5 3.42-4.99 5.14-11.13 5.14-18.23V376.19c0-8.19-2.24-14.85-6.7-19.83z"
                    p-id="3780" fill="#2c2c2c"></path>
            </svg>
            <span class="hoverwrap" :style="{ boxShadow: $root.boxShadow }">
                <span class="hoverwrap-item" :style="{ color: $root.langtype == 'default' ? 'blue' : '' }"
                      @click="$root.langtype = 'default', setlangs('default')">中文</span>
                <span class="hoverwrap-item" :style="{ color: $root.langtype == 'en' ? 'blue' : '' }"
                      @click="$root.langtype = 'en', setlangs('en')">英文</span>
            </span>
        </span>
    <span class="username" style="display: inline-block;cursor: pointer;" @click="tosetting">
            <svg t="1681451558355" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="4602" width="15" height="15" style="transform: scale(1.3);">
                <path
                    d="M924.8 625.7l-65.5-56c3.1-19 4.7-38.4 4.7-57.8s-1.6-38.8-4.7-57.8l65.5-56c10.1-8.6 13.8-22.6 9.3-35.2l-0.9-2.6c-18.1-50.5-44.9-96.9-79.7-137.9l-1.8-2.1c-8.6-10.1-22.5-13.9-35.1-9.5l-81.3 28.9c-30-24.6-63.5-44-99.7-57.6l-15.7-85c-2.4-13.1-12.7-23.3-25.8-25.7l-2.7-0.5c-52.1-9.4-106.9-9.4-159 0l-2.7 0.5c-13.1 2.4-23.4 12.6-25.8 25.7l-15.8 85.4c-35.9 13.6-69.2 32.9-99 57.4l-81.9-29.1c-12.5-4.4-26.5-0.7-35.1 9.5l-1.8 2.1c-34.8 41.1-61.6 87.5-79.7 137.9l-0.9 2.6c-4.5 12.5-0.8 26.5 9.3 35.2l66.3 56.6c-3.1 18.8-4.6 38-4.6 57.1 0 19.2 1.5 38.4 4.6 57.1L99 625.5c-10.1 8.6-13.8 22.6-9.3 35.2l0.9 2.6c18.1 50.4 44.9 96.9 79.7 137.9l1.8 2.1c8.6 10.1 22.5 13.9 35.1 9.5l81.9-29.1c29.8 24.5 63.1 43.9 99 57.4l15.8 85.4c2.4 13.1 12.7 23.3 25.8 25.7l2.7 0.5c26.1 4.7 52.8 7.1 79.5 7.1 26.7 0 53.5-2.4 79.5-7.1l2.7-0.5c13.1-2.4 23.4-12.6 25.8-25.7l15.7-85c36.2-13.6 69.7-32.9 99.7-57.6l81.3 28.9c12.5 4.4 26.5 0.7 35.1-9.5l1.8-2.1c34.8-41.1 61.6-87.5 79.7-137.9l0.9-2.6c4.5-12.3 0.8-26.3-9.3-35zM788.3 465.9c2.5 15.1 3.8 30.6 3.8 46.1s-1.3 31-3.8 46.1l-6.6 40.1 74.7 63.9c-11.3 26.1-25.6 50.7-42.6 73.6L721 702.8l-31.4 25.8c-23.9 19.6-50.5 35-79.3 45.8l-38.1 14.3-17.9 97c-28.1 3.2-56.8 3.2-85 0l-17.9-97.2-37.8-14.5c-28.5-10.8-55-26.2-78.7-45.7l-31.4-25.9-93.4 33.2c-17-22.9-31.2-47.6-42.6-73.6l75.5-64.5-6.5-40c-2.4-14.9-3.7-30.3-3.7-45.5 0-15.3 1.2-30.6 3.7-45.5l6.5-40-75.5-64.5c11.3-26.1 25.6-50.7 42.6-73.6l93.4 33.2 31.4-25.9c23.7-19.5 50.2-34.9 78.7-45.7l37.9-14.3 17.9-97.2c28.1-3.2 56.8-3.2 85 0l17.9 97 38.1 14.3c28.7 10.8 55.4 26.2 79.3 45.8l31.4 25.8 92.8-32.9c17 22.9 31.2 47.6 42.6 73.6L781.8 426l6.5 39.9z"
                    p-id="4603"></path>
                <path
                    d="M512 326c-97.2 0-176 78.8-176 176s78.8 176 176 176 176-78.8 176-176-78.8-176-176-176z m79.2 255.2C570 602.3 541.9 614 512 614c-29.9 0-58-11.7-79.2-32.8C411.7 560 400 531.9 400 502c0-29.9 11.7-58 32.8-79.2C454 401.6 482.1 390 512 390c29.9 0 58 11.6 79.2 32.8C612.3 444 624 472.1 624 502c0 29.9-11.7 58-32.8 79.2z"
                    p-id="4604"></path>
            </svg>
        </span>
    <img class="head" @click="topersonal" src="/favicon.ico" alt="">
    <span class="username" style="margin-left:10px;margin-right:10px;" @click="topersonal">Admin</span>
    <!-- 搜索层 -->
    <div v-if="showfades == 1">
      <div style="position: fixed;top:0;left:0;width:100%;height:100%;background-color: rgba(0, 0, 0, .7); ">
                <span
                    style="position: absolute;right: 0;top:0;padding: 10px;color: white;line-height: normal;font-size: 30px;cursor: pointer;"
                    @click="showfades = 0, resroutes = [], resroutespath = [], texts = ''">×</span>
        <div style="padding-top:100px;text-align: center;">
          <div
              style="display: inline-block;width:90%;background-color: white;line-height: normal;text-align: left; overflow: hidden;border-radius: 10px;"
              :style="{ height: winheight - 200 + 'px' }" @click.stop=" ">
            <div style="position: relative; padding:10px; background-color: white;z-index: 1;"
                 :style="{ boxShadow: $root.boxShadow }">
              <div
                  style="padding:10px;height: 20px; background-color: #eee;border-radius: 5px;overflow: hidden;">
                <input type="text" style="width: 100%;font-size: 16px;" placeholder="输入关键词查询页面"
                       v-model="texts" @input="search">
              </div>
            </div>
            <div style="overflow:hidden auto;" :style="{ height: winheight - 200 - 63 + 'px' }">
              <div v-for="v in resroutes">
                <div style="display: flex;border-bottom: 1px solid #ccc;"
                     v-if="!v.children || (v.children && !v.children.length)">
                  <div v-if="v.name">
                    <div style="padding:25px 10px 0">
                      <!-- <i :class="'fas ' + icons[v.name]"></i> -->
                      <elicon :name="v.name"></elicon>
                    </div>
                  </div>
                  <div style="width:100%">
                    <div style="padding:10px;">
                                            <span v-for="val in v.restitle"><span
                                                :style="{ color: val.find ? 'red' : '' }">{{
                                                val.str
                                              }}</span></span>
                    </div>
                    <div style="padding:0 10px 10px;color:#999">
                                            <span v-for="val in v.respath"><span
                                                :style="{ color: val.find ? 'red' : '' }">{{
                                                val.str
                                              }}</span></span>
                    </div>
                  </div>
                  <div>
                    <div style="padding-top: 20px; width:100px">
                      <el-button
                          @click="$router.replace(v.path), showfades = 0, resroutes = [], resroutespath = [], texts = ''">
                        进入
                      </el-button>
                    </div>
                  </div>
                </div>
              </div>
              <div v-if="resroutes.length == 0" style="padding:50px 0;text-align: center;">暂无信息显示</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import elicon from './fonticons.vue'

export default {
  data() {
    return {
      showfades: 0,
      winheight: '',
      texts: '',
      flag: null,
      routes: [],
      decplroutes: [],
      resroutes: [],
      resroutespath: []
    }
  },
  components: {
    elicon
  },
  methods: {
    topersonal() {
      this.$root.replaceto('/home/personal')
    },
    tosetting() {
      this.$root.replaceto('/home/setting')
    },
    search() {
      clearTimeout(this.flag)
      this.flag = setTimeout(() => {
        this.resroutes = []
        this.resroutespath = []
        setTimeout(() => {
          for (var i = 0; i < this.routes.length; i++) {
            for (var k = 0; k < this.texts.length; k++) {
              if (this.routes[i].meta && this.routes[i].meta.title && this.routes[i].meta.title.indexOf(this.texts[k]) != -1) {
                this.routes[i].meta.titles = this.$root.titlechange(this.routes[i].meta.title, this)
                var temp = this.routes[i].meta.titles.split('')
                var temp2 = this.routes[i].path.split('')
                if (this.resroutespath.indexOf(this.routes[i].path) == -1) {
                  this.resroutespath.push(this.routes[i].path)
                  this.routes[i].restitle = []
                  for (var m = 0; m < temp.length; m++) {
                    if (this.texts.indexOf(temp[m]) != -1) {
                      this.routes[i].restitle.push({str: temp[m], find: 1})
                    } else {
                      this.routes[i].restitle.push({str: temp[m], find: 0})
                    }
                  }
                  this.routes[i].respath = []
                  for (var m = 0; m < temp2.length; m++) {
                    if (this.texts.indexOf(temp2[m]) != -1) {
                      this.routes[i].respath.push({str: temp2[m], find: 1})
                    } else {
                      this.routes[i].respath.push({str: temp2[m], find: 0})
                    }
                  }
                  this.resroutes.push(this.routes[i])
                }
              } else if (this.routes[i].path && this.routes[i].path.indexOf(this.texts[k]) != -1) {
                var temp = this.routes[i].path.split('')
                this.routes[i].meta.titles = this.$root.titlechange(this.routes[i].meta.title, this)
                var temp2 = this.routes[i].meta.titles.split('')
                if (this.resroutespath.indexOf(this.routes[i].path) == -1) {
                  this.resroutespath.push(this.routes[i].path)
                  //
                  this.routes[i].restitle = []
                  for (var m = 0; m < temp2.length; m++) {
                    if (this.texts.indexOf(temp2[m]) != -1) {
                      this.routes[i].restitle.push({str: temp2[m], find: 1})
                    } else {
                      this.routes[i].restitle.push({str: temp2[m], find: 0})
                    }
                  }
                  this.routes[i].respath = []
                  for (var m = 0; m < temp.length; m++) {
                    if (this.texts.indexOf(temp[m]) != -1) {
                      this.routes[i].respath.push({str: temp[m], find: 1})
                    } else {
                      this.routes[i].respath.push({str: temp[m], find: 0})
                    }
                  }
                  //
                  this.resroutes.push(this.routes[i])
                }
              }
            }
          }
        })
      }, 2000)
    },
    initroutes() {
      var lists = JSON.parse(localStorage.getItem('navroot')) || []
      var templists = []
      for (var i = 0; i < lists.length; i++) {
        templists.push(lists[i].path)
      }
      this.routes = []
      var routes = this.$router.getRoutes()
      var fn = (list) => {
        for (var i = 0; i < list.length; i++) {
          if (list[i].path.split('/').length > 2 && templists.indexOf(list[i].path) != -1) {
            this.routes.push(list[i])
            if (list[i].children) {
              fn(list[i].children)
            }
          }
        }
      }
      fn(routes)
      //深克隆
      var temps = {}
      var depcl = (obj) => {
        var newobj = Array.isArray(obj) ? [] : {}
        if (obj && typeof obj === "object") {
          for (var i in obj) {
            if (obj.hasOwnProperty(i)) {
              typeof obj[i] == 'object' ? newobj[i] = depcl(obj[i]) : newobj[i] = obj[i]
            }
          }
        }
        return newobj
      }
      temps = depcl(this.routes)
      this.decplroutes = temps
    },
    setlangs(type) {
      localStorage.setItem('lang', type)
    },
    //
    doms() {
      setTimeout(() => {
        this.winheight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
      })
    }
  },
  computed: {
    'getglobalbtnroot'() {
      var that = this
      return function (name) {
        return that.$root.globalbtn.indexOf(name) != -1
      }
    }
  },
  mounted() {
    this.initroutes()
    this.doms()
    window.addEventListener('resize', () => {
      this.doms()
    })
  }
}
</script>

<style scoped>
.head {
  display: inline-block;
  margin-top: 27px;
  margin-left: 10px;
  width: 25px;
  height: 25px;
  /* background-color: #333; */
  /* border-radius: 50%; */
  vertical-align: top;
  cursor: pointer;
}

.username {
  position: relative;
  display: inline-block;
  padding: 0 10px;
  margin-top: 30px;
  height: 25px;
  vertical-align: top;
  cursor: pointer;
  line-height: normal;
}

.hoverwrap {
  display: none;
  position: absolute;
  top: 24px;
  right: 0;
  width: 60px;
  height: 70px;
  text-align: center;
  background-color: white;
  z-index: 3;
}

.hoverwrap-item {
  display: block;
  padding: 5px;
  line-height: normal;
  border-bottom: 1px solid #eee;
}

.username:hover .hoverwrap {
  display: block;
}

@media (max-width: 600px) {
  .head {
    margin-right: 20px;
  }

  .username {
    display: none;
  }
}
</style>