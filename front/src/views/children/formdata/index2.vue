<template>
    <div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999"> ! 表格每一项可以进行拖拽排序操作</span>
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
                <el-table-column label="表列1标题">
                    <template #default="scope">
                        {{ scope.row.name }}
                    </template>
                </el-table-column>
                <el-table-column label="表列2标题">
                    <template #default="scope">
                        {{ scope.row.desc }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <div style="padding-top:10px"></div>
                        <el-button style="margin:0 10px 10px 0" @click="viewdetail">查看详情</el-button>
                        <el-button style="margin:0 10px 10px 0" @click="viewdetail2">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <el-pagination small background layout="prev, pager, next" :total="50" class="mt-4" />
        </div>
        <!-- 弹框 - 新增 -->
        <el-dialog v-model="showsetadd" align-center="true" title="新增条目">
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">项目名：</span> </div>
                <el-input></el-input>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">类型：</span> </div>
                <el-select style="width:100%" v-model="userrootselect">
                    <el-option :value="1" label="主项">主项</el-option>
                    <el-option :value="2" label="分项">分项</el-option>
                </el-select>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">单项：</span> </div>
                <div>
                    <el-radio-group v-model="radio1" class="ml-4">
                        <el-radio label="1">是</el-radio>
                        <el-radio label="2">否</el-radio>
                    </el-radio-group>
                </div>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">多项：</span> </div>
                <div>
                    <el-checkbox v-model="checked1" label="Option 1" size="large" />
                    <el-checkbox v-model="checked2" label="Option 2" size="large" />
                    <el-checkbox v-model="checked3[0]" label="Option 3" size="large" />
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
            showsetadd: false,
            userrootselect: 1,
            radio1: '1',
            checked1: true,
            checked2: false,
            checked3: [true],
            list: [
                {
                    name: '表格数据1',
                    desc: '表格数据1',
                    id: '1'
                },
                {
                    name: '表格数据2',
                    desc: '表格数据2',
                    id: '2'
                },
                {
                    name: '表格数据3',
                    desc: '表格数据3',
                    id: '3'
                },
                {
                    name: '表格数据4',
                    desc: '表格数据4',
                    id: '4'
                },
            ]
        }
    },
    methods: {
        viewdetail() {
            ElMessage({
                message: '表格详情查看',
                type: 'success'
            })
        },
        viewdetail2() {
            ElMessage({
                message: '删除操作',
                type: 'success'
            })
        },
        //
        onDrops() {
            const tbody = document.querySelector("#tables .el-table__body-wrapper tbody");
            const _this = this;
            Sortable.create(tbody, {
                // 官网上的配置项,加到这里面来,可以实现各种效果和功能
                animation: 150,
                // ghostClass: "blue-background-class",
                onEnd({ newIndex, oldIndex }) {
                    _this.list.splice(
                        newIndex,
                        0,
                        _this.list.splice(oldIndex, 1)[0]
                    );
                    var newArray = _this.list.slice(0);
                    _this.list = [];
                    _this.$nextTick(function () {
                        _this.list = newArray;
                    });
                },
            });
        }
    },
    mounted() {
        this.onDrops()
    }
}
</script>