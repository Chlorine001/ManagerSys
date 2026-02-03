<template>
    <div>
        <div id="pie_echarts" style="height:449px"></div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            options: {
                title: {
                    // text: 'Referer of a Website',
                    text: '',
                    subtext: 'Fake Data',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: '50%',
                        data: [
                            { value: 1048, name: 'Search Engine' },
                            { value: 735, name: 'Direct' },
                            { value: 580, name: 'Email' },
                            { value: 484, name: 'Union Ads' },
                            { value: 300, name: 'Video Ads' }
                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            }
        }
    },
    methods: {
        initEchart() {
            echarts.dispose(document.getElementById("pie_echarts"));
            window.pie_echarts = echarts.init(
                document.getElementById("pie_echarts")
            );
            window.pie_echarts.setOption(this.options)
        },
        resizeEchart() {
            if (document.getElementById("pie_echarts"))
                window.pie_echarts.resize()
        }
    },
    mounted() {
        this.initEchart()
    }
}
</script>