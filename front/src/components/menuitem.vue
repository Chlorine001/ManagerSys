<template>
    <template v-for="(v, i) in list.children">
        <el-sub-menu v-if="v && v.children && v.children.length && v.meta.userroot" :index="v.path || v.name">
            <template #title>
                <a style=" display: inline-block;margin-top:-20px;height:0;line-height: normal; vertical-align: top;">
                    <!-- <i :class="'fas ' + icons[v.name]" style=" width:20px;font-size: 20px;"></i> -->
                    <!-- <img class="navimg" src="../../public/favicon.ico" /> -->
                    <elicon :name="v.meta.icon"></elicon>
                </a>
                <span style="margin-left: 10px;">{{ v && titlechange(v.meta.title) }}</span>
            </template>
            <!-- <el-menu-item-group> -->
            <Menuitem :list="v" :ifopen="ifopen" @title="settitle">
            </Menuitem>
            <!-- </el-menu-item-group> -->
        </el-sub-menu>
        <el-menu-item v-else-if="!v.meta || v.meta && v.meta.hide !== 0 && v.meta.userroot" :index="v.path || v.name"
            @click="$root.replaceto(v.meta.forusepath), settitle(v)">
            <!-- <i :class="'fas ' + icons[v.name]" style=" width:20px;font-size: 20px;"></i> -->
            <!-- <img class="navimg" src="../../public/favicon.ico"> -->
            <elicon :name="v.meta.icon"></elicon>
            <template #title>
                <span style="margin-left: 10px;">{{ v && titlechange(v.meta.title) }}</span>
            </template>
        </el-menu-item>
    </template>
</template>

<script>
import elicon from '../components/fonticons.vue'
export default {
    name: 'Menuitem', props: ['list', 'ifopen'],
    data() {
        return {

        }
    },
    components: {
        elicon
    },
    methods: {
        settitle(v) {
            this.$emit('title', v)
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
}
</script>