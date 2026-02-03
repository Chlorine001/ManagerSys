<template>
    <div>
        <div style="position: relative;  border-top: 1px solid #eee;background-color: white;overflow: auto;"
            :style="{ height: winheight - 220 + 'px', boxShadow: $root.boxShadow }">
            <div style="display: flex;min-width: 650px;">
                <!-- 部门 -->
                <div style="position:relative;z-index: 3;">
                    <div class="tree" style="overflow: hidden auto;"
                        :style="{ height: winheight - 220 + 'px', width: winwidth < 600 ? ' 200px' : '210px' }">
                        <div
                            style="padding:0 12px; height:39px;line-height: 39px; border-bottom: 1px solid #eee;font-size: 14px;color: #666;">
                            部门</div>
                        <div style="padding:12px">
                            <el-input v-model="filterText" placeholder="Filter keyword" @input="setvalue" />
                            <el-tree ref="tree" :props="props" node-key="id" :default-expanded-keys="expandedkeys"
                                :data="datas" @node-click="nodeClick" show-checkbox :render-content="renderContent"
                                :filter-node-method="filterNode" />
                        </div>
                    </div>
                </div>
                <!-- 角色 -->
                <div style="width:100%">
                    <div style="overflow: hidden auto;" :style="{ height: winheight - 220 + 'px' }">
                        <div
                            style="padding:0 12px; height:39px;line-height: 39px; border-bottom: 1px solid #eee;font-size: 14px;color: #666;">
                            角色</div>
                        <div>
                            <span style="display:inline-block;white-space: nowrap;">
                                <span style="display: inline-block;margin: 12px 0px 0 12px;">
                                    <el-input placeholder="Filter keyword" />
                                </span>
                                <el-button style="margin:12px 0px 0px 12px;vertical-align: top;"
                                    type="primary">查询</el-button>
                            </span>
                            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;"
                                @click="showsetadd = true">新增</el-button>
                            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;">批量禁用</el-button>
                            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;">批量删除</el-button>
                        </div>
                        <div style="padding-bottom: 50px;">
                            <el-table v-if="flexshowright" style="width:100%" :data="userlist">
                                <el-table-column type="selection"></el-table-column>
                                <el-table-column label="昵称" prop="name"></el-table-column>
                                <el-table-column label="操作">
                                    <template #default="scope">
                                        <div style="padding-top:10px"></div>
                                        <el-button style="margin:0 10px 10px 0">禁用</el-button>
                                        <el-button style="margin:0 10px 10px 0">删除</el-button>
                                        <el-button style="margin:0 10px 10px 0" @click="showsetdata = 1">编辑</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </div>
                </div>
                <!-- 分页 -->
                <div style="position: absolute;left:0;bottom:0; width: 100%;z-index: 1;">
                    <div style="padding:10px;border-top: 1px solid #eee;background-color: white;"
                        :style="{ marginLeft: winwidth < 600 ? ' 200px' : '210px' }">
                        <el-pagination small background layout="prev, pager, next" :total="50" class="mt-4" />
                    </div>
                </div>
            </div>
        </div>
        <!-- 弹框 - 权限处理 -->
        <el-dialog v-model="showsetdata" align-center="true" title="编辑用户">
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">用户名：</span> </div>
                <el-input value="xxx"></el-input>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">身份：</span> </div>
                <el-select style="width:100%" v-model="userrootselect">
                    <el-option :value="1" label="管理员">管理员</el-option>
                </el-select>
            </div>
        </el-dialog>
        <!-- 弹框 - 新增 -->
        <el-dialog v-model="showsetadd" align-center="true" title="新增用户">
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">用户名：</span> </div>
                <el-input></el-input>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">身份：</span> </div>
                <el-select style="width:100%" v-model="userrootselect">
                    <el-option :value="1" label="管理员">管理员</el-option>
                </el-select>
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
            showsetdata: 0,
            flexshowright: 1,
            //
            filterText: '',
            props: {
                label: 'name',//
                children: 'zones',
                isLeaf: 'leaf',
            },
            datas: [
                {
                    name: '部门1',
                    id: 100
                },
                {
                    name: '部门2',
                    id: 102,
                    zones: [
                        {
                            name: '部门2二级',
                            id: 103,
                        }
                    ]
                }
            ],
            expandedkeys: [103],
            //
            showsetadd: false,
            userrootselect: 1,
            userlist: [
                {
                    name: '角色1',
                    id: 200
                },
                {
                    name: '角色2',
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
        nodeClick: function (data, node, item) {
            console.log(data)
            if (data.id == 100 || data.name == '部门1二级') {
                var date = new Date().getTime()
                for (var i = 0; i <= node.childNodes.length; i++) {
                    this.$refs.tree.remove(node.childNodes[i])
                }
            }

            setTimeout(() => {
                //第一级
                if (node.level == 1 && data.id == 100) {
                    this.$refs.tree.append({ name: '部门1二级', id: 200 + '-' + date }, data)
                    this.expandedkeys = [data.id]
                }
                //第二级(虽然用了name，但一般情况是用id的)
                if (node.level == 2 && data.name == '部门1二级') {
                    this.$refs.tree.append({ name: '部门1三级', id: 200 + '-' + date }, data)
                    this.expandedkeys = [data.id]
                }
            }, 300)
        },
        setvalue() {
            this.$refs.tree.filter(this.filterText)
        },
        filterNode(value, data) {
            if (!value) return true
            return data.name.includes(value)
        }
    },
    watch: {
        '$root.openchange': function (news, olds) {
            this.doms()
        }
    },
    mounted() {
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