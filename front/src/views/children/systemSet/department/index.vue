<template>
    <div>
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: white;"
            :style="{ boxShadow: $root.boxShadow }">
            <span style="display:inline-block;white-space: nowrap;">
                <span style="display: inline-block;margin: 12px 0px 0 12px;">
                    <el-input placeholder="Filter keyword" />
                </span>
                <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="primary">查询</el-button>
            </span>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;"
                @click="showsetadd = true, type = 1, editobj = {}, parentobj = {}">新增</el-button>
        </div>
        <div id="tables" style="margin-bottom: 20px;" :style="{ boxShadow: $root.boxShadow }">
            <el-table :data="list" row-key="id" default-expand-all>
                <el-table-column label="表列1标题">
                    <template #default="scope">
                        {{ scope.row.name }}
                    </template>
                </el-table-column>
                <el-table-column label="创建时间">
                    <template #default="scope">
                        {{ scope.row.desc }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <div style="padding-top:10px"></div>
                        <el-button style="margin:0 10px 10px 0"
                            @click="showsetadd = true, type = 3, editobj = scope.row, parentobj = {}, getparent()">编辑</el-button>
                        <el-button style="margin:0 10px 10px 0"
                            @click="showsetadd = true, type = 2, editobj = {}, parentobj = scope.row">新增</el-button>
                        <el-button style="margin:0 10px 10px 0" @click="viewdetail2">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 弹框 - 新增 -->
        <el-dialog v-model="showsetadd" align-center="true" title="部门">
            <div style="display: flex;margin-bottom: 20px;" v-if="type == 2">
                <div><span style="display: inline-block;width:100px">上级部门：</span> </div>
                <el-input disabled v-model="parentobj.name"></el-input>
            </div>
            <div style="display: flex;margin-bottom: 20px;" v-else-if="type == 3 && editobj.parent != '-1'">
                <div><span style="display: inline-block;width:100px">上级部门：</span> </div>
                <span style="display:inline-block;width: 100%;cursor: pointer;" @click="showselectdept = true">
                    <el-input disabled v-model="parentobj.name" style="pointer-events: none;"></el-input>
                </span>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">部门名称：</span> </div>
                <el-input v-model="editobj.name"></el-input>
            </div>
        </el-dialog>
        <!-- 弹框 - 选部门 -->
        <el-dialog v-model="showselectdept" align-center="true" title="部门">
            <el-table :data="list" row-key="id" default-expand-all>
                <el-table-column label="表列1标题">
                    <template #default="scope">
                        {{ scope.row.name }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <div style="padding-top:10px"></div>
                        <el-button style="margin:0 10px 10px 0" @click="setparent(scope.row)">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            type: 1,
            showsetadd: false,
            showselectdept: false,
            editobj: {},
            parentobj: {},
            list: [//后端返回的结构，直接拿到渲染
                {
                    name: '表格数据1',
                    desc: '2020-02-03',
                    parent: '-1',
                    id: '1',
                    children: [
                        {
                            name: '表格数据1a',
                            desc: '2020-02-03',
                            parent: '1',
                            id: '5'
                        },
                    ]
                },
                {
                    name: '表格数据2',
                    desc: '2020-02-03',
                    parent: '-1',
                    id: '2'
                },
                {
                    name: '表格数据3',
                    desc: '2020-02-03',
                    parent: '-1',
                    id: '3'
                },
                {
                    name: '表格数据4',
                    desc: '2020-02-03',
                    parent: '-1',
                    id: '4'
                },
            ]
        }
    },
    methods: {
        viewdetail2() {
            //这个函数请求后端提交删除操作
            ElMessage({
                message: '删除操作',
                type: 'success'
            })
        },
        getparent() {
            var temp = []
            var fn = (list) => {
                for (var i = 0; i < list.length; i++) {
                    temp.push({
                        name: list[i].name,
                        desc: list[i].desc,
                        parent: list[i].parent,
                        id: list[i].id
                    })
                    if (list[i].children) {
                        fn(list[i].children)
                    }
                }
            }
            fn(this.list)
            //
            for (var i = 0; i < temp.length; i++) {
                if (this.editobj.parent == temp[i].id) {
                    this.parentobj = {
                        name: temp[i].name,
                        desc: temp[i].desc,
                        parent: temp[i].parent,
                        id: temp[i].id
                    }
                }
            }
        },
        setparent(v) {
            var temp = []
            var fn = (list, index) => {
                index = index + 1
                for (var i = 0; i < list.length; i++) {
                    temp.push({
                        name: list[i].name,
                        desc: list[i].desc,
                        parent: list[i].parent,
                        id: list[i].id,
                        index,
                    })
                    if (list[i].children) {
                        fn(list[i].children, index)
                    }
                }
            }
            fn(this.list, -1)
            //
            var tempeditobj = {}
            var chooseobj = {}
            for (var i = 0; i < temp.length; i++) {
                if (temp[i].id == this.editobj.id) {
                    tempeditobj = temp[i]
                }
                if (temp[i].id == v.id) {
                    chooseobj = temp[i]
                }
            }
            //
            if (chooseobj.index >= tempeditobj.index) {
                ElMessage({
                    type: 'error',
                    message: '不能选择当前或大于当前层级的部门'
                })
                return
            }
            this.parentobj = v
            this.showselectdept = false
        },
        submitadd() {
            //在这里请求后端提交新增部门以及在某部门下面新增的部门
            //新增部门，直接提交名称即可，某部门下面新增部门，提交名称的同时还要提交上级部门的id
        },
        submitsedit() {
            //在这里请求后端提交编辑后的父级id，当前的部门id和部门名称
        }
    },
    mounted() {

    }
}
</script>