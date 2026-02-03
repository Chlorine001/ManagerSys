<template></template>

<script setup>
import { getCurrentInstance } from 'vue'
const app = getCurrentInstance()
//改main.js全局数据，可以全局用（this.$vue3test = 4）
app.appContext.config.globalProperties.$vue3test = 4
//改App.vue全局数据，可以全局用（this.$root.vue3test = 7）
app.root.data.vue3test = 7
console.log(app)
</script>