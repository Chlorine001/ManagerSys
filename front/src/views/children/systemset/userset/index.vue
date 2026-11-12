<template>
    <div>
        <!-- 查询区 -->
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: var(--app-panel);"
            :style="{ boxShadow: $root.boxShadow }">
            <el-input v-model="keyword" placeholder="请输入用户名/昵称" clearable
                style="display: inline-block; white-space: nowrap; margin: 12px 0px 0 12px; width: 220px"
                @keyup.enter="handleSearch" @clear="handleSearch" />
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="primary"
                @click="handleSearch">查询</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" @click="openEditDialog(null)">新增</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="danger"
                :disabled="!selectedRows.length" @click="handleBatchDelete">批量删除</el-button>
        </div>

        <!-- 表格 -->
        <div id="tables" style="margin-bottom: 20px;" :style="{ boxShadow: $root.boxShadow }">
            <el-table ref="tableRef" :data="list" stripe row-key="id" v-loading="loading"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection"></el-table-column>
                <el-table-column label="序号" width="70">
                    <template #default="scope">
                        {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="用户名" prop="name" min-width="130" />
                <el-table-column label="昵称" prop="nickName" min-width="110" />
                <el-table-column label="邮箱" prop="email" min-width="180" show-overflow-tooltip />
                <el-table-column label="手机号" prop="phone" min-width="120" />
                <el-table-column label="角色" width="110" align="center">
                    <template #default="{ row }">
                        <el-tag size="small" :type="roleTagType(row.roleId)">{{ roleName(row.roleId) }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="状态" width="90" align="center">
                    <template #default="{ row }">
                        <el-tag :type="row.state === 0 ? 'success' : 'danger'" size="small">
                            {{ row.state === 0 ? '正常' : '锁定' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="160" align="center" fixed="right">
                    <template #default="{ row }">
                        <el-button size="small" @click="openEditDialog(row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 分页 -->
        <div style="margin-bottom: 20px; padding: 10px; background-color: var(--app-panel);"
            :style="{ boxShadow: $root.boxShadow }">
            <el-pagination small background layout="total, sizes, prev, pager, next, jumper" :total="total"
                :current-page="currentPage" :page-size="pageSize" :page-sizes="[10, 20, 50, 100]"
                @current-change="handlePageChange" @size-change="handleSizeChange" />
        </div>

        <!-- 新增/编辑弹窗 -->
        <el-dialog v-model="showEdit" align-center :title="formMode === 'add' ? '新增用户' : '编辑用户'" width="480px">
            <el-form label-width="90px" label-position="left">
                <el-form-item label="用户名：">
                    <el-input v-model="form.name" :disabled="formMode === 'edit'" placeholder="请输入用户名" />
                </el-form-item>
                <el-form-item v-if="formMode === 'add'" label="密码：">
                    <el-input v-model="form.password" type="password" placeholder="请输入密码" />
                </el-form-item>
                <el-form-item label="昵称：">
                    <el-input v-model="form.nickName" placeholder="请输入昵称" />
                </el-form-item>
                <el-form-item label="邮箱：">
                    <el-input v-model="form.email" placeholder="请输入邮箱" />
                </el-form-item>
                <el-form-item label="手机号：">
                    <el-input v-model="form.phone" placeholder="请输入手机号" maxlength="11" />
                </el-form-item>
                <el-form-item label="角色：">
                    <el-select v-model="form.roleId" style="width: 100%" placeholder="请选择角色">
                        <el-option v-for="r in roleList" :key="r.id" :label="r.name" :value="r.id" />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="showEdit = false">取消</el-button>
                <el-button type="primary" :loading="saving" @click="submitForm">确定</el-button>
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
            total: 0,
            currentPage: 1,
            pageSize: 10,
            showEdit: false,
            formMode: 'add',
            form: { id: null, name: '', password: '', nickName: '', email: '', phone: '', roleId: 1 },
            roleList: []
        }
    },
    methods: {
        async loadList() {
            this.loading = true
            try {
                const res = await http.get('/user/page', {
                    params: { pageNum: this.currentPage, pageSize: this.pageSize },
                    timeoutMs: 5000
                })
                const data = res?.data ?? {}
                this.list = data.records || []
                this.total = data.total || 0
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '加载用户列表失败') })
            } finally {
                this.loading = false
            }
        },
        async loadRoles() {
            try {
                const res = await http.get('/role/page', { params: { current: 1, size: 100 }, timeoutMs: 5000 })
                this.roleList = res?.data?.records || []
            } catch (e) {
                // 角色加载失败不阻塞主流程
                console.warn('加载角色失败', e)
            }
        },
        handleSearch() {
            this.currentPage = 1
            this.loadList()
        },
        handlePageChange(page) { this.currentPage = page; this.loadList() },
        handleSizeChange(size) { this.pageSize = size; this.currentPage = 1; this.loadList() },
        handleSelectionChange(rows) { this.selectedRows = rows },
        openEditDialog(row) {
            this.formMode = row ? 'edit' : 'add'
            this.form = row
                ? { id: row.id, name: row.name, password: '', nickName: row.nickName, email: row.email, phone: row.phone, roleId: row.roleId }
                : { id: null, name: '', password: '', nickName: '', email: '', phone: '', roleId: 1 }
            this.showEdit = true
        },
        async submitForm() {
            if (!this.form.name) { ElMessage.warning('请输入用户名'); return }
            if (this.formMode === 'add' && !this.form.password) { ElMessage.warning('请输入密码'); return }
            this.saving = true
            try {
                if (this.formMode === 'add') {
                    await http.post('/user/register', this.form, { timeoutMs: 5000 })
                    ElMessage.success('新增成功')
                } else {
                    // 编辑走自定义更新：后端暂无 /user 更新接口时用 put 更新实体
                    await http.put('/user', this.form, { timeoutMs: 5000 })
                    ElMessage.success('编辑成功')
                }
                this.showEdit = false
                this.loadList()
            } catch (e) {
                const action = this.formMode === 'add' ? '新增' : '编辑'
                ElMessage({ type: 'error', message: getErrorMessage(e, `${action}失败`) })
            } finally {
                this.saving = false
            }
        },
        handleDelete(row) {
            ElMessageBox.confirm(`确定删除用户「${row.name}」吗？`, '提示', { type: 'warning' })
                .then(async () => {
                    try {
                        await http.delete(`/user/${row.id}`, { timeoutMs: 5000 })
                        ElMessage.success('删除成功')
                        this.loadList()
                    } catch (e) {
                        ElMessage({ type: 'error', message: getErrorMessage(e, '删除失败') })
                    }
                }).catch(() => { })
        },
        async handleBatchDelete() {
            if (!this.selectedRows.length) return
            try {
                await ElMessageBox.confirm(`确定删除选中的 ${this.selectedRows.length} 个用户吗？`, '提示', { type: 'warning' })
            } catch { return }
            try {
                await Promise.all(this.selectedRows.map(r => http.delete(`/user/${r.id}`, { timeoutMs: 5000 })))
                ElMessage.success('批量删除成功')
                this.$refs.tableRef?.clearSelection()
                this.loadList()
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '批量删除失败') })
            }
        },
        roleName(roleId) {
            const r = this.roleList.find(x => x.id === roleId)
            return r ? r.name : `角色${roleId}`
        },
        roleTagType(roleId) {
            return roleId === 1 ? 'danger' : 'primary'
        }
    },
    mounted() {
        this.loadList()
        this.loadRoles()
    }
}
</script>
