<template>
    <div>
        <!-- 用 ref 替代 id，Vue 中更规范 -->
        <div ref="chartRef" style="height: 449px; width: 100%;"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            chartInstance: null,
            resizeObserver: null,
        }
    },
    watch: {
        // 监听全局暗黑模式切换，实时重绘
        '$root.isDark'() {
            this.initEchart()
        }
    },
    mounted() {
        this.initEchart()
        // 监听窗口变化
        window.addEventListener('resize', this.resizeEchart)
        // 监听容器尺寸变化（解决左侧菜单展开/收起时图表不变宽的问题）
        if (window.ResizeObserver) {
            this.resizeObserver = new ResizeObserver(() => {
                this.resizeEchart()
            })
            this.resizeObserver.observe(this.$refs.chartRef)
        }
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.resizeEchart)
        if (this.resizeObserver) {
            this.resizeObserver.disconnect()
        }
        if (this.chartInstance) {
            this.chartInstance.dispose()
            this.chartInstance = null
        }
    },
    methods: {
        initEchart() {
            if (!this.$refs.chartRef) return

            // 如果已有实例，先销毁
            if (this.chartInstance) {
                this.chartInstance.dispose()
            }

            // 初始化 ECharts
            this.chartInstance = echarts.init(this.$refs.chartRef)

            // ✅ 动态获取颜色，完美适配暗黑模式
            const isDark = document.documentElement.classList.contains('dark')
            const textColor = isDark ? '#e5eaf3' : '#333333' // 文字颜色
            const lineColor = isDark ? '#414243' : '#e5e5e5' // 网格线/轴线颜色

            const options = {
                backgroundColor: 'transparent', // 背景透明
                title: {
                    text: ''
                },
                // ✅ 修复图例变灰：给图例设置文字颜色
                legend: {
                    data: ['预算分配', '实际开销'],
                    inactiveColor: '#999', // 未选中时的文字颜色（灰色）
                    textStyle: { color: textColor },
                    top: 10
                },
                radar: {
                    // 稍微调大一点半径
                    radius: '65%',
                    indicator: [
                        { name: '销售', max: 6500 },
                        { name: '管理', max: 16000 },
                        { name: '信息技术', max: 30000 },
                        { name: '客服', max: 38000 },
                        { name: '研发', max: 52000 },
                        { name: '市场', max: 25000 }
                    ],
                    // ✅ 适配暗黑模式的雷达图文字、网格线、轴线
                    axisName: {
                        color: textColor
                    },
                    splitLine: {
                        lineStyle: { color: lineColor }
                    },
                    axisLine: {
                        lineStyle: { color: lineColor }
                    },
                    splitArea: {
                        show: false // 去掉一圈一圈的背景色
                    }
                },
                // ✅ 核心修复：给两条数据分别赋予颜色
                series: [
                    {
                        name: '预算 vs 开销',
                        type: 'radar',
                        data: [
                            {
                                value: [4200, 3000, 20000, 35000, 50000, 18000],
                                name: '预算分配',
                                itemStyle: { color: '#409EFF' }, // 蓝线
                                areaStyle: { color: 'rgba(64, 158, 255, 0.2)' }
                            },
                            {
                                value: [5000, 14000, 28000, 26000, 42000, 21000],
                                name: '实际开销',
                                itemStyle: { color: '#71b900' }, // 绿线
                                areaStyle: { color: 'rgba(113, 185, 0, 0.2)' }
                            }
                        ]
                    }
                ]
            }

            this.chartInstance.setOption(options)
            // ✅ 核心修复：只替换 series 组件，强制清除旧线条
            this.chartInstance.on('legendselectchanged', () => {
                // 直接基于原始的 options 重新设置，但只替换 series
                this.chartInstance.setOption(options, {
                    replaceMerge: ['series'] // 仅替换 series，其他如 legend 状态保持不变
                })
            })
        },

        resizeEchart() {
            if (this.chartInstance) {
                this.chartInstance.resize()
            }
        }
    }
}
</script>