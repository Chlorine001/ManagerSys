<template>
    <div>
        <div id="bar_echarts" style="height:449px"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            options: {
                title: {
                    // text: 'Accumulated Waterfall Chart'
                    text: ''
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function (params) {
                        let tar;
                        if (params[1] && params[1].value !== '-') {
                            tar = params[1];
                        } else {
                            tar = params[2];
                        }
                        return tar && tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
                    }
                },
                legend: {
                    data: ['Expenses', 'Income']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: (function () {
                        let list = [];
                        for (let i = 1; i <= 11; i++) {
                            list.push('Nov ' + i);
                        }
                        return list;
                    })()
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: 'Placeholder',
                        type: 'bar',
                        stack: 'Total',
                        silent: true,
                        itemStyle: {
                            borderColor: 'transparent',
                            color: 'transparent'
                        },
                        emphasis: {
                            itemStyle: {
                                borderColor: 'transparent',
                                color: 'transparent'
                            }
                        },
                        data: [0, 900, 1245, 1530, 1376, 1376, 1511, 1689, 1856, 1495, 1292]
                    },
                    {
                        name: 'Income',
                        type: 'bar',
                        stack: 'Total',
                        label: {
                            show: true,
                            position: 'top'
                        },
                        data: [900, 345, 393, '-', '-', 135, 178, 286, '-', '-', '-']
                    },
                    {
                        name: 'Expenses',
                        type: 'bar',
                        stack: 'Total',
                        label: {
                            show: true,
                            position: 'bottom'
                        },
                        data: ['-', '-', '-', 108, 154, '-', '-', '-', 119, 361, 203]
                    }
                ]
            }
        }
    },
    methods: {
        initEchart() {
            echarts.dispose(document.getElementById("bar_echarts"));
            window.bar_echarts = echarts.init(
                document.getElementById("bar_echarts")
            );
            window.bar_echarts.setOption(this.options)
        },
        resizeEchart() {
            if (document.getElementById("bar_echarts"))
                window.bar_echarts.resize()
        }
    },
    mounted() {
        this.initEchart()
    }
}
</script>