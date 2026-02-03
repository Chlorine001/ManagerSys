<template>
    <div>
        <div style="margin-bottom: 20px;padding: 0 0 20px; background-color: white;"
            :style="{ boxShadow: $root.boxShadow }">
            <personalHeadvue />
        </div>
        <div style="margin-bottom: 20px;padding:30px 20px; background-color: white;"
            :style="{ boxShadow: $root.boxShadow }">
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">用户名称：</span> </div>
                <el-input v-if="type == 1" value="Admin"></el-input>
                <div v-else>Admin</div>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">生日：</span> </div>
                <el-input v-if="type == 1" value="2020-10-20"></el-input>
                <div v-else>2020-10-20</div>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">用户类型：</span> </div>
                <el-select v-if="type == 1" style="width:100%" v-model="userrootselect">
                  <el-option :value="0" label="超级管理员">超级管理员</el-option>
                  <el-option :value="1" label="学生用户">学生用户</el-option>
                  <el-option :value="2" label="教师用户">教师用户</el-option>
                </el-select>
                <div v-else>学生用户</div>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">单项：</span> </div>
                <div v-if="type == 1">
                    <el-radio-group v-model="radio1" class="ml-4">
                        <el-radio label="1">是</el-radio>
                        <el-radio label="2">否</el-radio>
                    </el-radio-group>
                </div>
                <div v-else>是</div>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">多项：</span> </div>
                <div v-if="type == 1">
                    <el-checkbox v-model="checked1" label="Option 1" size="large" />
                    <el-checkbox v-model="checked2" label="Option 2" size="large" />
                    <el-checkbox v-model="checked3[0]" label="Option 3" size="large" />
                </div>
                <div v-else>选项1，选项2</div>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px"></span> </div>
                <div>
                    <el-button v-if="type == 0" @click="type = 1">修改</el-button>
                    <el-button v-if="type == 1" @click="type = 0">完成</el-button>
                </div>
            </div>
        </div>
        <el-button @click="loginout">退出登录</el-button>
    </div>
</template>

<script>
import personalHeadvue from '../components/personal_headview.vue'
export default {
    data() {
        return {
            type: 0,//0为查看，1为编辑
            //
            userrootselect: 1,
            radio1: '1',
            checked1: true,
            checked2: false,
            checked3: [true],
        }
    },
    methods: {
        loginout() {
            var confirms = confirm('确定退出登录？')
            if (confirms) {
                //token过期也按如下方式处理清除信息，不建议直接clear全部本地缓存清除
                localStorage.removeItem('token')
                localStorage.removeItem('navroot')
                //这一步非常重要
                setTimeout(() => {
                    location.reload(location.href.split('#')[0] + '#/')
                    // location.reload('/')//非hash模式
                })
            }
        }
    },
    components: {
        personalHeadvue
    }
}
</script>