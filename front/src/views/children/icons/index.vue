<template>
    <div>
        <div v-for="(v, i) in fullicons" style="float: left;width: 25%;">
            <div style="padding: 20px;margin: 10px;background-color: white;text-align: center;"
                :style="{ boxShadow: $root.boxShadow }" :title="v">
                <el-icon size="50">
                    <component :is="fulliconsicon[i]" />
                </el-icon>
                <div style="margin-top:20px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">{{ v }}</div>
            </div>
        </div>
        <div style="clear: both;"></div>
    </div>
</template>

<script>
import * as svg from '@element-plus/icons-vue'//图标相关
export default {
    data() {
        return {
            fullicons: [],
            fulliconsicon: [],
        }
    },
    mounted() {
        const icons = svg;
        for (const i in icons) {
            this.fullicons.push(`ele-${icons[i].name}`);
            this.fulliconsicon.push(`${icons[i].name}`);
        }
    }
}
</script>