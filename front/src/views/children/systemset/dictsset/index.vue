<template>
    <div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">该表格用于维护存储到后端的字典表，拿了几个简单来举例放到表格中</span>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">字典用于控制对应页面所能看到的数据的权限，前后端要及时匹配，添加了之后，默认是允许操作和显示的</span>
        </div>
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: white;"
            :style="{ boxShadow: $root.boxShadow }">
            <span style="display:inline-block;white-space: nowrap;">
                <span style="display: inline-block;margin: 12px 0px 0 12px;">
                    <el-input placeholder="Filter keyword" />
                </span>
                <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="primary">查询</el-button>
            </span>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" @click="showsetadd = true">新增</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;">批量删除</el-button>
        </div>
        <div id="tables" style="margin-bottom: 20px;" :style="{ boxShadow: $root.boxShadow }">
            <el-table :data="list">
                <el-table-column type="selection"></el-table-column>
                <el-table-column label="字典名称">
                    <template #default="scope">
                        {{ scope.row.dicts }}
                    </template>
                </el-table-column>
                <el-table-column label="路径">
                    <template #default="scope">
                        {{ scope.row.path }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <div style="padding-top:10px"></div>
                        <el-button style="margin:0 10px 10px 0" @click="viewdetail2">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <el-pagination small background layout="prev, pager, next" :total="50" class="mt-4" />
        </div>
        <el-dialog v-model="showsetadd" align-center="true" title="新增条目">
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">字典名称：</span> </div>
                <el-input v-model="dicts"></el-input>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">路径：</span> </div>
                <div title="建议前端根据字典名称来本地自动读取" style="width:100%"><el-input placeholder="建议前端根据字典名称来本地自动读取"></el-input>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            showsetadd: 0,
            dicts: '',
            list: [
                // {
                //     dicts: 'home:view:nums',
                //     path: '/home/home',
                //     id: '1'
                // },
                // {
                //     dicts: 'home:view:radar',
                //     path: '/home/home',
                //     id: '2'
                // },
                // {
                //     dicts: 'home:view:calendar',
                //     path: '/home/home',
                //     id: '3'
                // },
            ]

        }
    },
    methods: {
        viewdetail2() {
            ElMessage({
                message: '删除操作',
                type: 'success'
            })
        },
    }
}
</script>