<template>
  <RouterView/>
</template>

<script>
import {dictionarys} from './utils/dictionaryMap.js' // 字典相关
import {langarr} from './utils/langs.js'//国际化相关
export default {
  data() {
    return {
      /**
       * 架构页面跳转按钮的显示标识
       */
      globalbtn: [],
      /*
        '$root.openchange'(newa,old){...}
      */
      openchange: false,//内页如果有resize处理，通过全局修改并内页监听这个字段，来实现点击左侧导航收起或展开去处理内页的resize响应
      /*
        '$root.dictionarysuse'字段直接在内页获取里面的属性作为内容的权限管理,字典相关
      */
      dictionarysarr: [],//字典相关
      dictionarys,//字典相关
      dictionarysuse: null,//字典相关
      //
      boxShadow: '2px 2px 5px 0px #ccc',
      boxShadow2: '2px 2px 5px 0px #888',
      //国际化
      langtype: 'default',//目前分了default和en两种
      //vue3test
      vue3test: 5
    }
  },
  watch: {
    '$route.path': function (news) {
      this.dictionaryset(news)//字典相关
    }
  },
  methods: {
    dictionaryset(news) {
      //字典相关
      this.dictionarysuse = null
      this.dictionarysarr = []
      for (var i = 0; i < dictionarys.length; i++) {
        if (dictionarys[i].path == news) {
          this.dictionarysarr.push(dictionarys[i].name)
        }
      }
      if (this.$route.meta && this.$route.meta.dicts && this.$route.meta.dicts.length != 0) {
        for (var i = 0; i < this.$route.meta.dicts.length; i++) {
          this.dictionarysarr.indexOf(this.$route.meta.dicts[i]) != -1 ? (!this.dictionarysuse ? this.dictionarysuse = {[this.$route.meta.dicts[i]]: 1} : this.dictionarysuse[this.$route.meta.dicts[i]] = 1) : ''
        }
      } else {
        for (var i = 0; i < dictionarys.length; i++) {
          (!this.dictionarysuse ? this.dictionarysuse = {[dictionarys[i].name]: 1} : this.dictionarysuse[dictionarys[i].name] = 1)
        }
      }
    },
    titlechange(title, that) {
      //国际化相关
      var temp = ''
      for (var i = 0; i < langarr.length; i++) {
        if (title.trim() == langarr[i].name) {
          temp = langarr[i][that.$root.langtype]
        }
      }
      return temp || title
    },
    replaceto(url) {
      this.$router.replace(url)
    },
    navto(url) {
      this.$router.push(url)
    },
  },
  mounted() {
    setTimeout(() => {
      this.dictionaryset(this.$route.path)//字典相关
      if (localStorage.getItem('lang')) {
        this.langtype = localStorage.getItem('lang')
      }
    })
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  list-style: none;
  text-decoration: none;
  border: none;
  outline: none;
  appearance: none;
  -webkit-appearance: none;
  font-family: 'Microsoft weiruanyahei';
  background-color: transparent;
}
</style>
