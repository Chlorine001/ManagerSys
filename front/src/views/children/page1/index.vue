<template>
    <div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">
                这个架构里面，角色即等同于岗位，建议使用“部门_岗位”或“部门-岗位”这样的双重命名方式进行角色管理
            </span>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="display:inline-block;margin-right: 10px; white-space: nowrap;">
                <span style="display: inline-block;margin-right: 10px;">
                    <el-input placeholder="Filter keyword" />
                </span>
                <el-button style="vertical-align: top;" type="primary">查询</el-button>
            </span>
            <el-button style="vertical-align: top;"
                @click="showsetadd = true, type = 1, editobj = { name: '', en: '' }">新增</el-button>
            <el-button style="vertical-align: top;">批量禁用</el-button>
            <el-button style="vertical-align: top;">批量删除</el-button>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <!-- 角色 -->
            <div style="width:100%">
                <el-table v-if="flexshowright" style="width:100%" :data="userlist">
                    <el-table-column type="selection"></el-table-column>
                    <el-table-column label="角色名称" prop="name"></el-table-column>
                    <el-table-column label="英文匹配" prop="en"></el-table-column>
                    <el-table-column label="操作">
                        <template #default="scope">
                            <div style="padding-top:10px"></div>
                            <el-button style="margin:0 10px 10px 0"
                                @click="showsetadd = true, type = 2, editobj = { name: scope.row.name, en: scope.row.en }">编辑</el-button>
                            <el-button style="margin:0 10px 10px 0">禁用</el-button>
                            <el-button style="margin:0 10px 10px 0">删除</el-button>
                            <el-button style="margin:0 10px 10px 0" @click="showsetlist = 1">权限设置</el-button>
                            <el-button style="margin:0 10px 10px 0" @click="showdictionary = 1">字典设置</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- 分页 -->
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <el-pagination small background layout="prev, pager, next" :total="50" class="mt-4" />
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">
                字典查询：{{ $root.dictionarysuse }}
            </span>
        </div>
        <!-- 弹框 - 权限处理 -->
        <el-dialog v-model="showsetlist" align-center="true">
            <el-table :data="routes">
                <el-table-column label="页面导航">
                    <template #default="scope">
                        {{ scope.row.meta.title || '--' }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <div style="padding-top:10px"></div>
                        <el-button style="margin:0 10px 10px 0">禁止显示</el-button>
                        <el-button style="margin:0 10px 10px 0" v-if="!scope.row.children">禁止删除</el-button>
                        <el-button style="margin:0 10px 10px 0" v-if="!scope.row.children">禁止修改</el-button>
                        <el-button style="margin:0 10px 10px 0" v-if="!scope.row.children">禁止增加</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog v-model="showdictionary" align-center="true">
            <el-table :data="$root.dictionarys">
                <el-table-column label="字典参数">
                    <template #default="scope">
                        {{ scope.row.name || '--' }}
                    </template>
                </el-table-column>
                <el-table-column label="匹配页面">
                    <template #default="scope">
                        {{ scope.row.path || '--' }}
                    </template>
                </el-table-column>
                <el-table-column label="是否允许显示和操作">
                    <template #default="scope">
                        <div style="padding-top:10px"></div>
                        <el-button style="margin:0 10px 10px 0">是</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <!-- 弹框 - 新增，编辑 -->
        <el-dialog v-model="showsetadd" align-center="true" :title="type == 1 ? '新增角色' : '编辑角色'">
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">角色名称：</span> </div>
                <el-input v-model="editobj.name"></el-input>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">英文匹配：</span> </div>
                <el-input v-model="editobj.en"></el-input>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    /*
        与后端交互处理权限，
        交互完成后，再重新获取用户权限
        后端应该根据显示与否，在返回数组的时候是否返回对应页面对象到这个数组里面，比如：
        [{path:'xxxx',root:['set','add','del']}]，我打开了aaa的显示，那应该[{path:'xxxx',root:['set','add','del']},{path:'aaa',root:['set','add','del']}]这样格式返回
        然后其他权限处理，比如：
        [{path:'xxxx',root:['set','add','del']}]，我将'xxxx'的删除权限关闭，就应该[{path:'xxxx',root:['set','add']}]这样格式返回
    */
    data() {
        return {
            winheight: '',
            winwidth: '',
            showsetlist: 0,
            showdictionary: 0,
            flexshowright: 1,
            routes: [],
            editobj: {
                name: '',
                en: ''
            },
            //
            showsetadd: false,
            type: 1,
            userlist: [
                {
                    name: '角色1',
                    en: 'deptxxx_staff1',
                    id: 200
                },
                {
                    name: '角色2',
                    en: 'deptaaa_staff2',
                    id: 201
                }
            ]
        }
    },
    methods: {
        doms() {
            this.winheight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
            this.winwidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
            this.flexshowright = 0
            this.$nextTick(() => {
                this.flexshowright = 1
            })
        },
        //
        initroutes() {
            this.routes = []
            var routes = this.$router.getRoutes()
            var fn = (list) => {
                for (var i = 0; i < list.length; i++) {
                    if (list[i].meta.hide != true) {
                        this.routes.push(list[i])
                        if (list[i].children) {
                            fn(list[i].children)
                        }
                    }
                }
            }
            for (var i = 0; i < routes.length; i++) {
                if (routes[i].meta.title == 'base') {
                    fn(routes[i].children)
                }
            }
        },
    },
    watch: {
        '$root.openchange': function (news, olds) {
            this.doms()
        }
    },
    mounted() {
        this.initroutes()
        this.doms()
        window.addEventListener('resize', () => {
            this.doms()
        })
    }
}
</script>

<style scoped>
.tree {
    border-right: 1px solid #eee;
}
</style>