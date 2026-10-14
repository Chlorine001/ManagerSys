<template>
    <div>
        <!--        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">-->
        <!--            <span style="color:#999">该表格用于维护存储到后端的路由表，拿了几个简单来举例放到表格中</span>-->
        <!--        </div>-->
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">对应路由与角色的权限匹配方式：比如这个角色有这个路由显示权限，然后这个路由有这些权限：<span style="white-space: nowrap;">{{
                '[{path:xxx,root:[],dicts:[]}]' }}</span>，root是按钮显示权限，dicts是数据显示权限，通过字典标识来控制</span>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">路由追加了之后，默认路由显示权限，所有按钮权限都是打开的</span>
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
                <el-table-column label="序号">
                    <template #default="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="标题">
                    <template #default="{ row }">
                        <span :style="{
                            paddingLeft: row.depth * 20 + 'px',
                            fontWeight: row.depth === 0 ? 'bold' : 'normal',
                            color: row.depth === 0 ? '#303133' : '#606266'
                        }">
                            <span v-if="row.depth > 0" style="color:#c0c4cc; margin-right:4px;">└</span>
                            {{ row.name }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column label="路径">
                    <template #default="scope">
                        {{ scope.row.path }}
                    </template>
                </el-table-column>
                <el-table-column label="icon标识">
                    <template #default="scope">
                        {{ scope.row.icon }}
                    </template>
                </el-table-column>
                <el-table-column label="首页是否展示">
                    <template #default="scope">
                        {{ scope.row.ishide ? '隐藏' : '展示' }}
                       
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default>
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
                <div><span style="display: inline-block;width:100px">路径：</span> </div>
                <div style="display: flex;">
                    <span style="vertical-align: middle;margin-right: 20px;">/home/</span>
                    <el-input></el-input>
                </div>
            </div>
            <div style="display: flex;margin-bottom: 20px;">
                <div><span style="display: inline-block;width:100px">标题：</span> </div>
                <div title="建议前端根据路径来本地自动读取" style="width:100%">
                    <el-input placeholder="建议前端根据路径来本地自动读取"></el-input>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import { http } from '@/utils/http'
export default {
    data() {
        return {
            showsetadd: 0,
            loading: false,
            list: []
        }
    },
    methods: {
        viewdetail2() {
            ElMessage({
                message: '删除操作',
                type: 'success'
            })
        },
        async loadMenuList() {
            this.loading = true
            try {
                const user = JSON.parse(localStorage.getItem('currentUser') || '{}')
                const res = await http.postJson('/menu/user', user, { timeoutMs: 5000 })
                const data = res?.data ?? []
                const flat = (data || []).map(v => ({
                    id: String(v.id),
                    pid: String(v.pid ?? 0),
                    path: v.url,
                    name: v.name,
                    ishide: v.ishide,
                    icon: v.icon,
                }))
                this.list = this.buildTreeOrder(flat)
            } catch (e) {
                ElMessage({ type: 'error', message: '加载路由表失败：' + e.message })
            } finally {
                this.loading = false
            }
        },
        getParentName(pid) {
            if (pid === 0 || pid === '0') return '顶级菜单'
            const parent = this.list.find(item => item.id === pid || item.id === String(pid))
            return parent ? parent.name : `未知(${pid})`
        },
        // 构建顺序 + 记录深度
        buildTreeOrder(flatList) {
            const byPid = {}
            flatList.forEach(item => {
                const pid = item.pid ?? '0'
                if (!byPid[pid]) byPid[pid] = []
                byPid[pid].push(item)
            })

            const result = []
            const walk = (pid, depth) => {
                const children = byPid[pid] || []
                children.forEach(item => {
                    result.push({ ...item, depth })   // ← 记录深度
                    walk(item.id, depth + 1)
                })
            }
            walk('0', 0)

            // 孤儿节点兜底
            const ids = new Set(result.map(i => i.id))
            flatList.forEach(item => {
                if (!ids.has(item.id)) result.push({ ...item, depth: 0 })
            })

            return result
        }
    },
    mounted() {
        this.loadMenuList()
    }
}
</script>