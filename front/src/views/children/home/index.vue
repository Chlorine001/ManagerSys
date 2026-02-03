<template>
    <div>
<!--        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">-->
<!--            <span style="color:#999">-->
<!--                这个项目目前全程都是以localStorage本地缓存为主要的缓存形式，实际开发请酌情处理-->
<!--            </span>-->
<!--        </div>-->
        <div class="leftshow1 a">
            <div class="leftshow1in" :style="{ boxShadow: $root.boxShadow }">
                <homeCount />
            </div>
        </div>
        <div class="leftshow2">
            <div class="leftshow2in" :style="{ boxShadow: $root.boxShadow }">
                <homeHeadvue />
            </div>
        </div>
        <div class="leftshow1 b">
            <div class="leftshow1in" :style="{ boxShadow: $root.boxShadow }">
                <homeCount />
            </div>
        </div>
        <!--  -->
        <div class="leftshow1">
            <div class="leftshow1in" style="min-height: 449px;" :style="{ boxShadow: $root.boxShadow }">
                <el-calendar v-model="value" />
            </div>
        </div>
        <div class="leftshow2">
            <div class="leftshow2in" :style="{ boxShadow: $root.boxShadow }">
                <radarEchart ref="radarEchart" />
            </div>
        </div>
        <div class="leftshow1">
            <div class="leftshow1in" :style="{ boxShadow: $root.boxShadow }">
                <barEchart ref="barEchart" />
            </div>
        </div>
        <div class="leftshow2">
            <div class="leftshow2in" :style="{ boxShadow: $root.boxShadow }">
                <pieEchart ref="pieEchart" />
            </div>
        </div>
        <div style="clear: both;"></div>
    </div>
</template>

<script>
import homeCount from '../components/home_count.vue'
import homeHeadvue from '../components/home_headview.vue'
import radarEchart from '../components/echarts_radar.vue'
import barEchart from '../components/echarts_bar.vue'
import pieEchart from '../components/echarts_pie.vue'
export default {
    data() {
        return {
            value: new Date()
        }
    },
    components: {
        homeCount,
        homeHeadvue,
        radarEchart,
        barEchart,
        pieEchart
    },
    methods: {
        setresize() {
            this.$refs.radarEchart && this.$refs.radarEchart.resizeEchart()
            this.$refs.barEchart && this.$refs.barEchart.resizeEchart()
            this.$refs.pieEchart && this.$refs.pieEchart.resizeEchart()
        }
    },
    watch: {
        '$root.openchange': function (news, olds) {
            this.$nextTick(() => {
                this.setresize()
            })
        }
    },
    mounted() {
        window.addEventListener('resize', () => {
            this.setresize()
        })
    }
}
</script>

<style scoped>
.leftshow1,
.leftshow2 {
    float: left;
    margin-bottom: 20px;
}

.leftshow1in,
.leftshow2in {
    background: white;
}

@media (min-width:1066px) {
    .b {
        display: none;
    }

    .leftshow1 {
        width: 60%;
    }

    .leftshow2 {
        width: 40%;
    }

    .leftshow1in {
        margin-right: 20px;
    }

    .leftshow1in,
    .leftshow2in {
        min-height: 100px;
    }
}

@media (max-width:1065px) {
    .a {
        display: none;
    }

    .leftshow1 {
        width: 100%;
    }

    .leftshow2 {
        width: 100%;
    }

    .leftshow1in {
        margin-right: 0px;
    }

    .leftshow1in,
    .leftshow2in {
        min-height: 100px;
    }
}
</style>