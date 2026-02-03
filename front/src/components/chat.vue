<template>
    <div v-show="load == 1">
        <!-- 运动顶部 -->
        <div style="position: fixed;top:0;left:0; width: 100%;height:80px;line-height: 80px;text-align: center;background-color: white;z-index: 999;transition: 200ms;"
            :style="{ top: !ifshow ? '-80px' : 0 }">
            <span style="float: left;padding: 0 20px;cursor: pointer;" @click="ifshow = 0">
                <img src="/favicon.ico" alt=""
                    style="display: inline-block;width: 40px;height:40px;vertical-align: middle;">
            </span>
            {{ titlechange('会话窗口') }}
            <span style="float:right;padding: 0 20px;font-size: 30px;cursor: pointer;" @click="ifshow = 0">×</span>
        </div>
        <!-- 运动窗体 -->
        <div style="position: fixed;top:80px;left:0; width: 100%;background-color: #cacaca;z-index: 99;transition: 500ms;"
            :style="{ height: winheight - 80 + 'px', top: !ifshow ? '-100%' : '80px' }">
            <!-- // nav -->
            <div style="float: left;overflow: hidden auto;" :style="{ height: winheight + 'px' }">
                <div style="padding: 20px 20px 0;">
                    <div style="height:50px;width:50px;background-color: white;cursor: pointer;"
                        :style="{ boxShadow: $root.boxShadow2, backgroundColor: changetype == 2 ? '#308df7' : '#fff' }"
                        @click="changetype = 2">
                        <ele-icon>
                            <User style="transform: scale(.5);" :style="{ color: changetype == 2 ? '#fff' : '' }" />
                        </ele-icon>
                    </div>
                </div>
                <div style="padding: 20px 20px 0;" v-if="showchat">
                    <div style="height:50px;width:50px;background-color: white;cursor: pointer;"
                        :style="{ boxShadow: $root.boxShadow2, backgroundColor: changetype == 1 ? '#308df7' : '#fff' }"
                        @click="changetype = 1">
                        <ele-icon>
                            <ChatDotRound style="transform: scale(.5);" :style="{ color: changetype == 1 ? '#fff' : '' }" />
                        </ele-icon>
                    </div>
                </div>
            </div>
            <!-- // cont -->
            <div style="margin-left: 90px;margin-right: 20px;padding-top:20px">
                <div style="background-color: white;overflow: hidden auto;"
                    :style="{ height: winheight - 120 + 'px', boxShadow: $root.boxShadow2 }">

                    <div v-show="changetype == 2">
                        <div style="min-height: 40px; padding: 20px 0;border-bottom: 1px solid #ccc;cursor: pointer;"
                            v-for="v in 5" @click="sel_else_init">
                            <span style="float: left;padding: 0 20px;cursor: pointer;">
                                <img src="/favicon.ico" alt=""
                                    style="display: inline-block;width: 40px;height:40px;vertical-align: middle;">
                            </span>
                            <div style="width:100%">
                                <div style="padding:0 30px 0 80px;">
                                    <div style="font-size: 16px;font-weight: 800;">Test</div>
                                    <div style="padding-top:0px; color: #969696;font-size: 16px;word-break: break-all;">
                                        Test文本！
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both;"></div>
                        </div>
                    </div>
                    <div v-show="changetype == 1">
                        <div style="padding: 20px 20px 0;">
                            <div style="overflow: hidden scroll;" id="chatwrap" :style="{ height: winheight - 220 + 'px' }">
                                <div v-for="v in list">
                                    <!-- 左边部分 -->
                                    <div class="chatcenteritem clearb" v-if="v.from != 'me'" :id="v.id">
                                        <!-- 左边内容 -->
                                        <div class="chatcenterleft">
                                            <!-- 头像 -->
                                            <div class="chatcenterleft-head"></div>
                                            <!-- 昵称 -->
                                            <div class="chatcenterleft-nick">
                                                <div class="chatcenterleft-nickbox">{{ v.nick }}</div>
<!--                                                &lt;!&ndash; sign &ndash;&gt;-->
<!--                                                <div class="chatcenterleft-usersign">::sign::</div>-->
                                            </div>
                                            <!-- 内容 -->
                                            <div class="chatcenterleft-cont">{{ v.cont }}</div>
                                        </div>
                                    </div>
                                    <!-- 右边部分 -->
                                    <div class="chatcenteritem clearb" v-if="v.from == 'me'" :id="v.id">
                                        <!-- 右边内容 -->
                                        <div class="chatcenterright">
                                            <!-- 头像 -->
                                            <div class="chatcenterright-head"></div>
                                            <!-- 昵称 -->
                                            <div class="chatcenterright-nick">
                                                <div class="chatcenterright-nickbox">{{ v.nick }}</div>
<!--                                                &lt;!&ndash; sign &ndash;&gt;-->
<!--                                                <div class="chatcenterright-usersign">::sign::</div>-->
                                            </div>
                                            <!-- 内容 -->
                                            <div class="chatcenterright-cont">{{ v.cont }}</div>
                                        </div>
                                    </div>
                                    <div style="clear: both;"></div>
                                </div>
                            </div>
                        </div>
                        <div style="padding:20px;">
                            <span
                                style="float:right;padding: 0 20px;line-height: 40px; font-size: 16px;cursor: pointer;background-color: #308df7;color: white;"
                                :style="{ boxShadow: $root.boxShadow }" @click="sendmsg">{{ titlechange('发送') }}</span>
                            <div style="margin-right: 100px;">
                                <div style="padding: 0 10px; height:40px;" :style="{ boxShadow: $root.boxShadow }">
                                    <input type="text" style="width:100%;line-height: 40px; font-size: 16px;" id="valuemsg"
                                        :placeholder="titlechange('输入要发送的内容')">
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div v-if="ifshow" v-html="'<style>body{overflow:hidden !important}</style>'"></div>
        </div>
        <!-- 浮动按钮 -->
        <div style="position: fixed;right:20px;bottom:30px;width: 60px;height: 60px;background-color: #308df7;border-radius: 50%;z-index: 2;cursor: pointer;"
            :style="{ boxShadow: $root.boxShadow }" @click="ifshow = 1">
            <ele-icon>
                <ChatDotRound style="color: white;transform: scale(.5);" />
            </ele-icon>
        </div>
    </div>
</template>

<script>
import { setchatdatas } from '../mock/getdata'
export default {
    data() {
        return {
            load: 0,
            winheight: '',
            ifshow: 0,
            showchat: 0,
            changetype: 2,//1是会话窗口，2是列表
            list: [],
            mscrollinit: {},
            canload: 1,
            page: 0,
        }
    },
    methods: {
        //发送数据
        sendmsg: function () {
            var ids = 'chat_me' + new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '-' + new Date().getHours() + '-' + new Date().getMinutes() + '-' + new Date().getSeconds() + '-' + new Date().getMilliseconds()
            this.list.push({
                id: ids,
                nick: 'xxx',
                from: 'me',
                cont: document.getElementById('valuemsg').value || '-'
            })
            setTimeout(() => {
                document.getElementById(ids).scrollIntoView(true)
            })
        },
        //获取数据
        ajaxgetchatres: function (fn) {
            //模拟拿到数据
            if (this.list.length == 0) {
                setTimeout(() => {
                    this.list = setchatdatas(new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '-' + new Date().getHours() + '-' + new Date().getMinutes() + '-' + new Date().getSeconds() + '-' + new Date().getMilliseconds())
                    setTimeout(() => {
                        document.getElementById(this.list[this.list.length - 1].id).scrollIntoView(true)
                    })
                    this.canload = 1
                    fn()
                }, 1000)
            } else {
                var tempid = this.list[0].id
                setTimeout(() => {
                    this.list = setchatdatas(new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '-' + new Date().getHours() + '-' + new Date().getMinutes() + '-' + new Date().getSeconds() + '-' + new Date().getMilliseconds()).concat(this.list)
                    setTimeout(() => {
                        document.getElementById(tempid).scrollIntoView(true)
                        document.getElementById('chatwrap').scrollTop = document.getElementById('chatwrap').scrollTop - 30
                    })
                    this.canload = 1
                    fn()
                }, 1000)
            }
        },
        //选择人物然后重新初始化窗口
        sel_else_init: function () {
            if (!this.showchat) {
                this.showchat = 1
                this.initmscroll()
            }
            this.changetype = 1
            this.page = 0
            this.list = []
            this.mscrollinit.triggerDownScroll()
        },
        //初始化窗口
        initmscroll: function () {
            var _this = this;
            this.mscrollinit = new MeScroll("chatwrap", {
                //第一个参数"mescroll"对应上面布局结构div的id (1.3.5版本支持传入dom对象)
                //如果您的下拉刷新是重置列表数据,那么down完全可以不用配置,具体用法参考第一个基础案例
                //解析: down.callback默认调用mescroll.resetUpScroll(),而resetUpScroll会将page.num=1,再触发up.callback

                down: {
                    callback: function () {
                        if (_this.canload == 0) return;
                        _this.canload = 0
                        //_this.mescroll.setPageNum(1)//商品模式时重置成第一页
                        _this.page++;
                        _this.mscrollinit.setPageNum(_this.page)
                        _this.ajaxgetchatres(function () {
                            _this.mscrollinit.endSuccess()
                        })
                        // _this.mscrollinit.endSuccess()//商品模式下这个才需要,下拉刷新的回调,别写成downCallback(),多了括号就自动执行方法了
                    }
                },

                /* up: {
                    offset:0,
                    callback: (page) => {
                        //this.mescroll.endSuccess()//这里代表加成功一页，但是商品模式才这样
                    }, //上拉加载的回调
                    //以下是一些常用的配置,当然不写也可以的.
                    page: {
                        num: 0, //当前页 默认0,回调之前会加1; 即callback(page)会从1开始
                        size: 10 //每页数据条数,默认10
                    },
                    htmlNodata: '<p class="upwarp-nodata">-- END --</p>',
                    noMoreSize: 5, //如果列表已无数据,可设置列表的总数量要大于5才显示无更多数据;
                            //避免列表数据过少(比如只有一条数据),显示无更多数据会不好看
                            //这就是为什么无更多数据有时候不显示的原因.
                    toTop: {
                        //回到顶部按钮
                        src: "../img/mescroll-totop.png", //图片路径,默认null,支持网络图
                        offset: 1000 //列表滚动1000px才显示回到顶部按钮	
                    },
                    empty: {
                        //列表第一页无任何数据时,显示的空提示布局; 需配置warpId才显示
                        warpId:	"xxid", //父布局的id (1.3.5版本支持传入dom元素)
                        icon: "../img/mescroll-empty.png", //图标,默认null,支持网络图
                        tip: "暂无相关数据~" //提示
                    },
                    lazyLoad: {
                            use: true ,// 是否开启懒加载,默认false
                            attr: 'imgurl' // 标签中网络图的属性名 : <img imgurl='网络图  src='占位图''/>
                        }
                } */
            });
        },
        //
        setdoms() {
            this.winheight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
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
    mounted() {
        setTimeout(() => {
            this.load = 1
            this.setdoms()
            window.addEventListener('resize', () => {
                this.setdoms()
            })
        }, 500)
    }
}
</script>

<style scoped>
.clearb:after {
    content: '';
    display: block;
    clear: both;
}

.chatcenteritem {
    /* 左边和右边内容public */
    padding-bottom: 30px
}

.chatcenterleft {
    /* 左边内容 */
    position: relative;
    float: left;
}

.chatcenterleft-head,
.chatcenterright-head,
.chatcenterleft-nick,
.chatcenterright-nick {
    /* 左右边内容：头像和昵称public */
    position: absolute;
    top: 0;
}

.chatcenterleft-nick,
.chatcenterright-nick {
    /* 左右边内容：头像和昵称public */
    position: absolute;
    top: 0;
    max-width: 50%;
}

.chatcenterleft-nickbox,
.chatcenterright-nickbox {
    /* 左右边内容：昵称public */
    max-width: 100%;
}

.chatcenterleft-usersign,
.chatcenterright-usersign {
    /* 左右边内容：sign public */
    position: absolute;
    top: 0;
    padding: 0 10px;
}

.chatcenterleft-cont,
.chatcenterright-cont {
    /* 左右边内容：内容public */
    padding: 10px;
    min-height: 20px;
    min-width: 120px;
    max-width: 50%;
    word-break: break-all;
    background-color: #F0AD4E;
}

.chatcenterleft-head {
    /* 左边内容：头像 */
    left: 20px;
    width: 60px;
    height: 60px;
    background-color: #F0AD4E;
    border-radius: 50%;
}

.chatcenterleft-nick {
    /* 左边内容：昵称 */
    left: 100px;
    height: 20px;
}

.chatcenterleft-nickbox {
    /* 左边内容：昵称 */
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}

.chatcenterleft-usersign {
    /* 左边内容：sign */
    left: 100%;
}

.chatcenterleft-cont {
    /* 左边内容：内容 */
    float: left;
    margin: 30px 0 0 100px;
}

.chatcenterright {
    /* 右边内容 */
    position: relative;
    float: right;
}


.chatcenterright-head {
    /* 左边内容：头像 */
    right: 20px;
    width: 60px;
    height: 60px;
    background-color: #F0AD4E;
    border-radius: 50%;
}

.chatcenterright-nick {
    /* 左边内容：昵称 */
    right: 100px;
    height: 20px;
}

.chatcenterright-usersign {
    /* 左边内容：sign */
    right: 100%;
}

.chatcenterright-nickbox {
    /* 左边内容：昵称 */
    text-align: right;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}

.chatcenterright-cont {
    /* 左边内容：内容 */
    float: right;
    margin: 30px 100px 0 0;
}
</style>