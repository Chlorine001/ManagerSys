<template>
    <div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">
                系统代码获取：
            </span>
            <div style="padding-top: 20px;">
                <div style="padding: 20px;">
                    <a href="https://gitee.com/xie-zhiyi-2/aappii_file/blob/master/project_DEMO_element_self_system.zip"
                        download="系统代码" target="_blank">点击获取系统代码</a>
                </div>
            </div>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">
                配置白名单：
            </span>
            <div style="padding-top: 20px;">
                <div style="padding: 20px;background-color: #eee;">
                    <textarea style="width: 100%;height:100px;resize: none;" name="" id="" cols="30" rows="10"
                        v-model="texts"></textarea>
                </div>
            </div>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">
                赞助：
            </span>
            <div style="padding-top:20px;text-align: center;">
                <div style="display: inline-block; margin:0 20px 20px">
                    <div
                        style="width: 200px;height:200px;box-shadow: 0 5px 10px 0 #05a558 inset;border-radius: 10px;background-color: #04c66a;overflow: hidden;">
                        <div style="margin: 20px 0 0 20px; width:160px;height: 160px;background-color: white;">
                            <img src="../../../assets/1.jpg" style="width: 100%;height: 100%;" alt="">
                        </div>
                    </div>
                    <div
                        style="margin:20px 0 0 0;padding: 10px;text-align: center;box-shadow: 0 5px 10px 0 #05a558  inset;background-color: #04c66a;border-radius: 40px;color: white;">
                        微信</div>
                </div>
                <div style="display: inline-block; margin:0 20px 20px">
                    <div
                        style="width: 200px;height:200px;box-shadow: 0 5px 10px 0 #3944bd inset;border-radius: 10px;background-color: #3368fc;overflow: hidden;">
                        <div style="margin: 20px 0 0 20px; width:160px;height: 160px;background-color: white;">
                            <img src="../../../assets/2.jpg" style="width: 100%;height: 100%;" alt="">
                        </div>
                    </div>
                    <div
                        style="margin:20px 0 0 0;padding: 10px;text-align: center;box-shadow: 0 5px 10px 0 #3944bd inset;background-color: #3368fc;border-radius: 40px;color: white;">
                        支付宝</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            texts: '192.183.1.5;192.183.1.5;192.183.1.5;192.183.1.5;192.183.1.5;192.183.1.5;192.183.1.5;'
        }
    }
}
</script>