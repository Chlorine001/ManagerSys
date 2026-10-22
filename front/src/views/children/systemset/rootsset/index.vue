<template>
    <div>
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: var(--app-panel);"
            :style="{ boxShadow: $root.boxShadow }">
            <el-input v-model="keyword" placeholder="请输入角色名称" clearable
                style="display: inline-block; white-space: nowrap; margin: 12px 0px 0 12px; width: 220px"
                @keyup.enter="handleSearch" @clear="handleSearch" />
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="primary"
                @click="handleSearch">查询</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;"
                @click="openEditDialog(null)">新增</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="warning"
                :disabled="!selectedRows.length" @click="handleBatchState('0')">批量禁用</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="danger"
                :disabled="!selectedRows.length" @click="handleBatchDelete">批量删除</el-button>
        </div>

        <div id="tables" style="margin-bottom: 20px;" :style="{ boxShadow: $root.boxShadow }">
            <el-table ref="tableRef" :data="pagedList" stripe row-key="id" v-loading="loading"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection"></el-table-column>
                <el-table-column label="序号" width="70">
                    <template #default="scope">
                        {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="角色名称" prop="name" min-width="140" />
                <el-table-column label="状态" width="90" align="center">
                    <template #default="{ row }">
                        <el-tag :type="row.state === '1' ? 'success' : 'info'" size="small">
                            {{ row.state === '1' ? '启用' : '禁用' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="备注" prop="info" min-width="180" show-overflow-tooltip />
                <el-table-column label="操作" width="300" align="center" fixed="right">
                    <template #default="{ row }">
                        <el-button size="small" @click="openEditDialog(row)">编辑</el-button>
                        <el-button size="small" :type="row.state === '1' ? 'warning' : 'success'"
                            @click="handleState(row)">
                            {{ row.state === '1' ? '禁用' : '启用' }}
                        </el-button>
                        <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
                        <el-button size="small" type="primary" @click="openPermDialog(row)">权限设置</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div style="margin-bottom: 20px; padding: 10px; background-color: var(--app-panel);"
            :style="{ boxShadow: $root.boxShadow }">
            <el-pagination small background layout="total, sizes, prev, pager, next, jumper" :total="total"
                :current-page="currentPage" :page-size="pageSize" :page-sizes="[10, 20, 50, 100]"
                @current-change="handlePageChange" @size-change="handleSizeChange" />
        </div>

        <!-- 新增 / 编辑 -->
        <el-dialog v-model="showEdit" align-center :title="formMode === 'add' ? '新增角色' : '编辑角色'" width="480px">
            <el-form label-width="100px" label-position="left">
                <el-form-item label="角色名称：">
                    <el-input v-model="form.name" placeholder="建议使用 部门_岗位 命名" style="width: 100%" />
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input v-model="form.info" placeholder="请输入备注" style="width: 100%" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="showEdit = false">取消</el-button>
                <el-button type="primary" :loading="saving" @click="submitForm">确定</el-button>
            </template>
        </el-dialog>

        <!-- 权限设置 -->
        <el-dialog v-model="showPerm" align-center :title="`权限设置 - ${currentRole?.name || ''}`" width="480px">
            <el-tree ref="treeRef" :data="menuTree" show-checkbox node-key="id" default-expand-all
                :props="{ label: 'name', children: 'children' }" />
            <template #footer>
                <el-button @click="showPerm = false">取消</el-button>
                <el-button type="primary" :loading="saving" @click="submitPerm">保存</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import { http } from '@/utils/http'
import { getErrorMessage } from '@/utils/errorMessage'

export default {
    data() {
        return {
            keyword: '',
            selectedRows: [],
            loading: false,
            saving: false,
            list: [],
            pagedList: [],
            total: 0,
            currentPage: 1,
            pageSize: 10,
            showEdit: false,
            formMode: 'add',
            form: { id: null, name: '', info: '' },
            showPerm: false,
            currentRole: null,
            menuTree: []
        }
    },
    methods: {
        async loadList() {
            this.loading = true
            try {
                const res = await http.get('/role/page', {
                    params: {
                        name: this.keyword.trim(),
                        current: this.currentPage,
                        size: this.pageSize
                    },
                    timeoutMs: 5000
                })
                const data = res?.data ?? {}
                this.list = data.records || []
                this.total = data.total || 0
                this.updatePagedList()
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '加载角色列表失败，请稍后重试') })
            } finally {
                this.loading = false
            }
        },
        // 后端分页，当前页就是接口返回的数据
        updatePagedList() {
            this.pagedList = this.list
            // 删光当前页最后一页数据时，回退一页
            if (!this.pagedList.length && this.currentPage > 1) {
                this.currentPage -= 1
                this.loadList()
            }
        },
        handleSearch() {
            this.currentPage = 1
            this.loadList()
        },
        handlePageChange(page) {
            this.currentPage = page
            this.loadList()
        },
        handleSizeChange(size) {
            this.pageSize = size
            this.currentPage = 1
            this.loadList()
        },
        handleSelectionChange(rows) {
            this.selectedRows = rows
        },
        openEditDialog(row) {
            this.formMode = row ? 'edit' : 'add'
            this.form = row ? { id: row.id, name: row.name, info: row.info } : { id: null, name: '', info: '' }
            this.showEdit = true
        },
        async submitForm() {
            if (!this.form.name.trim()) {
                ElMessage.warning('请输入角色名称')
                return
            }
            this.saving = true
            try {
                // 提交前先查重（编辑时排除自身）
                const dup = await this.checkNameDuplicate(this.form.name)
                if (dup) {
                    ElMessage.warning('角色名称已存在，请更换')
                    return
                }
                if (this.formMode === 'add') {
                    await http.post('/role', this.form, { timeoutMs: 5000 })
                    ElMessage.success('新增成功')
                } else {
                    await http.put('/role', this.form, { timeoutMs: 5000 })
                    ElMessage.success('编辑成功')
                }
                this.showEdit = false
                this.loadList()
            } catch (e) {
                const action = this.formMode === 'add' ? '新增' : '编辑'
                ElMessage({ type: 'error', message: getErrorMessage(e, `${action}失败，请稍后重试`) })
            } finally {
                this.saving = false
            }
        },
        // 查重：按名称精确查询，命中且不是自己 -> true
        async checkNameDuplicate(name) {
            const res = await http.get('/role/page', {
                params: { name, current: 1, size: 50 },
                timeoutMs: 5000
            })
            const records = res?.data?.records || []
            return records.some(r => r.name === name && r.id !== this.form.id)
        },
        handleState(row) {
            const target = row.state === '1' ? '0' : '1'
            const text = target === '0' ? '禁用' : '启用'
            http.put('/role/state', { ids: [row.id], state: target }, { timeoutMs: 5000 })
                .then(() => {
                    ElMessage.success(`${text}成功`)
                    this.loadList()
                })
                .catch(e => ElMessage({ type: 'error', message: getErrorMessage(e, `${text}失败，请稍后重试`) }))
        },
        handleBatchState(state) {
            const ids = this.selectedRows.map(r => r.id)
            const text = state === '0' ? '禁用' : '启用'
            ElMessageBox.confirm(`确定${text}选中的 ${ids.length} 个角色吗？`, '提示', { type: 'warning' })
                .then(async () => {
                    try {
                        await http.put('/role/state', { ids, state }, { timeoutMs: 5000 })
                        ElMessage.success(`${text}成功`)
                        this.$refs.tableRef?.clearSelection()
                        this.loadList()
                    } catch (e) {
                        ElMessage({ type: 'error', message: getErrorMessage(e, `${text}失败，请稍后重试`) })
                    }
                }).catch(() => { })
        },
        handleDelete(row) {
            ElMessageBox.confirm(
                `确定删除「${row.name}」吗？其菜单权限关联也会一并清除。`,
                '提示',
                { type: 'warning' }
            ).then(async () => {
                try {
                    await http.delete(`/role/${row.id}`, { timeoutMs: 5000 })
                    ElMessage.success('删除成功')
                    this.loadList()
                } catch (e) {
                    ElMessage({ type: 'error', message: getErrorMessage(e, '删除失败，请稍后重试') })
                }
            }).catch(() => { })
        },
        async handleBatchDelete() {
            if (!this.selectedRows.length) {
                ElMessage.warning('请先选择要删除的角色')
                return
            }
            try {
                await ElMessageBox.confirm(
                    `确定删除选中的 ${this.selectedRows.length} 个角色吗？`,
                    '提示',
                    { type: 'warning' }
                )
            } catch {
                return
            }
            try {
                const ids = this.selectedRows.map(r => r.id)
                // 后端没有批量删除接口时，前端循环调单个删除
                await Promise.all(ids.map(id => http.delete(`/role/${id}`, { timeoutMs: 5000 })))
                ElMessage.success('批量删除成功')
                this.$refs.tableRef?.clearSelection()
                this.loadList()
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '批量删除失败，请稍后重试') })
            }
        },
        async openPermDialog(row) {
            this.currentRole = row
            this.showPerm = true
            if (!this.menuTree.length) {
                try {
                    // 菜单数据复用菜单管理同一个接口即可
                    const user = JSON.parse(localStorage.getItem('currentUser') || '{}')
                    const res = await http.post('/menu/user', user, { timeoutMs: 5000 })
                    this.menuTree = this.buildTree(res?.data ?? [])
                } catch (e) {
                    ElMessage({ type: 'error', message: getErrorMessage(e, '加载菜单失败，请稍后重试') })
                    this.showPerm = false
                    return
                }
            }
            try {
                const res = await http.get(`/role/${row.id}/menus`, { timeoutMs: 5000 })
                const ids = res?.data ?? []
                // 只回显叶子节点，父级由 el-tree 自动半选
                const leafIds = ids.filter(id => !this.hasChildren(id))
                this.$nextTick(() => this.$refs.treeRef?.setCheckedKeys(leafIds))
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '加载角色权限失败，请稍后重试') })
            }
        },
        async submitPerm() {
            const ids = [
                ...this.$refs.treeRef.getCheckedKeys(),
                ...this.$refs.treeRef.getHalfCheckedKeys()
            ]
            this.saving = true
            try {
                await http.put(`/role/${this.currentRole.id}/menus`, ids, { timeoutMs: 5000 })
                ElMessage.success('权限保存成功')
                this.showPerm = false
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '保存权限失败，请稍后重试') })
            } finally {
                this.saving = false
            }
        },
        buildTree(list) {
            const byPid = {}
            list.forEach(m => {
                const pid = m.pid ?? 0
                if (!byPid[pid]) byPid[pid] = []
                byPid[pid].push({ id: m.id, pid: m.pid, name: m.name })
            })
            const build = pid => (byPid[pid] || []).map(m => ({
                ...m,
                children: build(m.id).length ? build(m.id) : undefined
            }))
            return build(0)
        },
        hasChildren(id) {
            const walk = nodes => nodes.some(n =>
                n.id === id ? !!n.children?.length : (n.children ? walk(n.children) : false)
            )
            return walk(this.menuTree)
        }
    },
    mounted() {
        this.loadList()
    }
}
</script>

<style scoped>
.my-form :deep(.el-form-item__label) {
    padding-left: 30px;
}
</style>