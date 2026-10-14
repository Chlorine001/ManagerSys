<template>
    <div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">用于维护存储到后端的路由表</span>
        </div>
        <!-- <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">对应路由与角色的权限匹配方式：比如这个角色有这个路由显示权限，然后这个路由有这些权限：<span style="white-space: nowrap;">{{
                '[{path:xxx,root:[],dicts:[]}]' }}</span>，root是按钮显示权限，dicts是数据显示权限，通过字典标识来控制</span>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">路由追加了之后，默认路由显示权限，所有按钮权限都是打开的</span>
        </div> -->
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: white;"
            :style="{ boxShadow: $root.boxShadow }">
            <span style="display:inline-block;white-space: nowrap;">
                <span style="display: inline-block;margin: 12px 0px 0 12px;">
                    <el-input placeholder="Filter keyword" />
                </span>
                <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="primary">查询</el-button>
            </span>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" @click="openAddDialog(null)">新增</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" @click="handleBatchDelete">批量删除</el-button>
        </div>

        <div id="tables" style="margin-bottom: 20px;" :style="{ boxShadow: $root.boxShadow }">
            <el-table ref="tableRef" :data="list" stripe row-key="id" @selection-change="handleSelectionChange">
                <el-table-column type="selection"></el-table-column>
                <el-table-column label="序号" width="70">
                    <template #default="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="标题" min-width="180">
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
                <el-table-column label="路径" min-width="180">
                    <template #default="{ row }">
                        <el-tooltip :content="'单段: ' + row.path" placement="top">
                            <span>{{ row.fullPath }}</span>
                        </el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column label="icon标识" width="130">
                    <template #default="scope">
                        {{ scope.row.icon }}
                    </template>
                </el-table-column>
                <el-table-column label="是否展示" align="center">
                    <template #default="scope">
                        <el-tag :type="scope.row.ishide ? 'info' : 'success'" size="small">
                            {{ scope.row.ishide ? '隐藏' : '展示' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="是否叶子节点" width="130" align="center">
                    <template #default="scope">
                        <el-tag :type="scope.row.isleaf ? 'success' : 'info'" size="small">
                            {{ scope.row.isleaf ? '是' : '否' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="280" align="center" fixed="right">
                    <template #default="{ row }">
                        <el-button size="small" type="primary" :disabled="row.isleaf"
                            @click="openAddDialog(row)">新增下级</el-button>
                        <el-button size="small" @click="openEditDialog(row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 弹窗 -->
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <el-pagination small background layout="prev, pager, next" :total="list.length" class="mt-4" />
        </div>
        <el-dialog v-model="showsetadd" align-center :title="formMode === 'add' ? '新增条目' : '编辑条目'" width="520px">
            <el-form label-width="150px" label-position="left" class="my-form">
                <!-- 直属上级菜单 -->
                <el-form-item label="直属上级菜单：">
                    <span>{{ getParentName(this.form.pid) }}</span>
                    <!-- <el-tree-select v-model="form.pid" :data="parentTree"
                        :props="{ label: 'name', children: 'children', value: 'id' }" check-strictly clearable
                        placeholder="顶级菜单" style="width: 100%" /> -->
                </el-form-item>

                <!-- 路径：前缀 + 输入框 -->
                <el-form-item label="路径：">
                    <div style="display: flex; align-items: center; width: 100%;">
                        <span style="margin-right: 4px; color: #909399; white-space: nowrap;">
                            {{ pathPrefix }}
                        </span>
                        <el-input v-model="form.path" placeholder="请输入子路径，如 systemset" style="flex: 1" />
                    </div>
                </el-form-item>

                <!-- 标题 -->
                <el-form-item label="标题：">
                    <el-input v-model="form.name" placeholder="请输入标题" style="width: 100%" />
                </el-form-item>
                <!-- icon -->
                <el-form-item label="icon标识：">
                    <el-input v-model="form.icon" placeholder="如 userinfo" style="width: 100%" />
                </el-form-item>

                <!-- 首页是否展示 -->
                <el-form-item label="首页是否展示：">
                    <el-switch v-model="form.ishide" :active-value="false" :inactive-value="true" />
                    <span style="margin-left: 8px; color: #909399;">
                        {{ form.ishide ? '隐藏' : '展示' }}
                    </span>
                </el-form-item>

                <!-- 是否叶子节点 -->
                <el-form-item label="是否叶子节点：">
                    <el-switch v-model="form.isleaf" :active-text="false" :data-allow-mismatch="true" />
                    <span style="margin-left: 8px; color: #909399;">
                        {{ form.isleaf ? '是' : '否' }}
                    </span>
                </el-form-item>
            </el-form>

            <template #footer>
                <el-button @click="showsetadd = false">取消</el-button>
                <el-button type="primary" @click="submitForm">确定</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import { http } from '@/utils/http'
import { BASE_PATH } from '@/router/constants'
export default {
    data() {
        return {
            selectedRow: [], // 当前选中的行
            showsetadd: false,
            loading: false,
            list: [],
            flatList: [],           // ← 新增：扁平数据，给 computed 用
            formMode: 'add',        // ← 新增：'add' | 'edit'
            form: {
                id: null,
                pid: 0,
                path: '',
                name: '',
                icon: '',
                ishide: false,
                isleaf: false,
            }
        }
    },
    computed: {
        basePath() {
            return BASE_PATH
        },
        parentTree() {
            const flat = this.flatList || []
            const byPid = {}
            flat.forEach(item => {
                const pid = item.pid ?? '0'
                if (!byPid[pid]) byPid[pid] = []
                byPid[pid].push(item)
            })

            const build = (pid) => {
                return (byPid[pid] || []).map(item => {
                    const children = build(item.id)
                    return {
                        id: item.id,
                        name: item.name,
                        disabled: item.isleaf,   // ← 不允许加下级的节点置灰
                        children: children.length ? children : undefined
                    }
                })
            }
            return build('0')
        },
        // 路径前缀：随所选父菜单变化
        pathPrefix() {
            const pid = this.form.pid
            if (!pid) return BASE_PATH + '/'
            const parent = (this.flatList || []).find(i => i.id === pid)
            return parent && parent.fullPath ? parent.fullPath + '/' : BASE_PATH + '/'
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
                    ishide: v.isHide,
                    icon: v.icon,
                    isleaf: v.isLeaf,
                }))
                this.fillFullPath(flat)
                this.flatList = flat
                this.list = this.buildTreeOrder(flat)
            } catch (e) {
                ElMessage({ type: 'error', message: '加载路由表失败：' + e.message })
            } finally {
                this.loading = false
            }
        },
        getParentName(pid) {
            if (!pid || pid === 0 || pid === '0') return '顶级菜单'
            const parent = this.flatList.find(i => i.id === pid)
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
        },
        fillFullPath(flatList) {
            const byId = {}
            flatList.forEach(item => { byId[item.id] = item })

            flatList.forEach(item => {
                const segments = []
                let cur = item
                while (cur) {
                    segments.unshift((cur.path || '').replace(/^\//, ''))
                    cur = cur.pid && cur.pid !== '0' ? byId[cur.pid] : null
                }
                item.fullPath = BASE_PATH + '/' + segments.join('/')
            })
        },
        // 打开对话框时重置表单
        openAddDialog(parent) {
            this.formMode = 'add'
            this.form = {
                id: null,
                pid: parent ? String(parent.id) : null,
                path: '',
                name: '',
                icon: '',
                ishide: false,
                isleaf: false,
            }
            this.showsetadd = true
        },
        openEditDialog(row) {
            this.formMode = 'edit'
            this.form = {
                id: row.id,
                pid: row.pid,
                path: row.path,          // 单段路径，如 /systemset
                name: row.name,
                icon: row.icon,
                ishide: row.ishide,
                isleaf: row.isleaf,
            }
            this.showsetadd = true
        },

        // 切换父菜单时清空已输入的子路径
        handleParentChange() {
            this.form.path = ''
        },

        handleSelectionChange(rows) {
            this.selectedRow = rows
        },
        // 提交
        async submitForm() {
            const sub = this.form.path.trim().replace(/^\//, '')
            if (!sub) {
                ElMessage.warning('请输入子路径')
                return
            }
            if (!this.form.name.trim()) {
                ElMessage.warning('请输入标题')
                return
            }

            const payload = {
                pid: this.form.pid || '0',
                url: '/' + sub,
                name: this.form.name.trim(),
                icon: this.form.icon.trim(),
                isHide: this.form.ishide,     // 注意后端字段名大小写
                isLeaf: this.form.isleaf,
            }

            try {
                if (this.formMode === 'add') {
                    await http.postJson('/menu/add', payload)
                    ElMessage.success('新增成功')
                } else {
                    await http.postJson('/menu/update', { ...payload, id: this.form.id })
                    ElMessage.success('编辑成功')
                }
                this.showsetadd = false
                this.loadMenuList()
            } catch (e) {
                ElMessage.error((this.formMode === 'add' ? '新增' : '编辑') + '失败：' + e.message)
            }
        },
        handleDelete(row) {
            ElMessageBox.confirm(
                `确定删除「${row.name}」吗？`,
                '提示',
                { type: 'warning' }
            ).then(async () => {
                try {
                    await http.postJson('/menu/delete', { id: row.id })
                    ElMessage.success('删除成功')
                    this.loadMenuList()
                } catch (e) {
                    ElMessage.error('删除失败：' + e.message)
                }
            }).catch(() => { })
        },
        handleBatchDelete() {
            console.log(this.selectedRows)
            if (this.selectedRows.length === 0) {
                ElMessage.warning('请先选择要删除的菜单')
                return
            }
            ElMessageBox.confirm(
                `确定删除选中的 ${this.selectedRows.length} 个菜单吗？`,
                '提示',
                { type: 'warning' }
            ).then(async () => {
                const ids = this.selectedRows.map(r => r.id)
                await http.postJson('/menu/batchDelete', { ids })
                ElMessage.success('删除成功')
                this.loadMenuList()
            }).catch(() => { })
        }
    },
    mounted() {
        this.loadMenuList()
    }
}
</script>

<style scoped>
/* 加在 <style scoped> 里，需要用 :deep() */
.my-form :deep(.el-form-item__label) {
    padding-left: 30px;
}
</style>