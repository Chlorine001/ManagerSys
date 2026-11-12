<template>
    <div>
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: var(--app-panel);"
            :style="{ boxShadow: $root.boxShadow }">
            <el-select v-model="classFilter" placeholder="按班级筛选" clearable
                style="display: inline-block; margin: 12px 0px 0 12px; width: 180px" @change="handleSearch">
                <el-option v-for="c in classList" :key="c.id" :label="classLabel(c)" :value="c.id" />
            </el-select>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="primary"
                @click="handleSearch">查询</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" @click="openEdit(null)">新增</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" type="danger"
                :disabled="!selectedRows.length" @click="handleBatchDelete">批量删除</el-button>
        </div>

        <div id="tables" style="margin-bottom: 20px;" :style="{ boxShadow: $root.boxShadow }">
            <el-table ref="tableRef" :data="list" stripe row-key="id" v-loading="loading"
                @selection-change="handleSelectionChange">
                <el-table-column type="selection"></el-table-column>
                <el-table-column label="序号" width="70">
                    <template #default="scope">{{ (currentPage - 1) * pageSize + scope.$index + 1 }}</template>
                </el-table-column>
                <el-table-column label="学号" prop="id" width="90" />
                <el-table-column label="用户名" min-width="140">
                    <template #default="{ row }">{{ userName(row.userId) }}</template>
                </el-table-column>
                <el-table-column label="班级" min-width="140">
                    <template #default="{ row }">{{ className(row.classId) }}</template>
                </el-table-column>
                <el-table-column label="操作" width="160" align="center" fixed="right">
                    <template #default="{ row }">
                        <el-button size="small" @click="openEdit(row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
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

        <el-dialog v-model="showEdit" align-center :title="formMode === 'add' ? '新增学生' : '编辑学生'" width="480px">
            <el-form label-width="90px" label-position="left">
                <el-form-item label="用户：">
                    <el-select v-model="form.userId" style="width: 100%" filterable placeholder="请选择用户">
                        <el-option v-for="u in userList" :key="u.id" :label="u.name" :value="u.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="班级：">
                    <el-select v-model="form.classId" style="width: 100%" placeholder="请选择班级">
                        <el-option v-for="c in classList" :key="c.id" :label="classLabel(c)" :value="c.id" />
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
            classFilter: null, selectedRows: [], loading: false, saving: false,
            list: [], total: 0, currentPage: 1, pageSize: 10,
            classList: [], userList: [],
            showEdit: false, formMode: 'add',
            form: { id: null, userId: null, classId: null }
        }
    },
    methods: {
        async loadList() {
            this.loading = true
            try {
                const res = await http.get('/student/page', {
                    params: { classId: this.classFilter || undefined, current: this.currentPage, size: this.pageSize },
                    timeoutMs: 5000
                })
                const d = res?.data ?? {}
                this.list = d.records || []
                this.total = d.total || 0
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '加载学生列表失败') })
            } finally { this.loading = false }
        },
        async loadDict() {
            try {
                const [c, u] = await Promise.all([
                    http.get('/class/list', { timeoutMs: 5000 }),
                    http.get('/user', { timeoutMs: 5000 })
                ])
                this.classList = c?.data || []
                this.userList = u?.data || []
            } catch (e) { console.warn('加载字典失败', e) }
        },
        classLabel(c) { return c.grade ? `${c.grade}级${c.name}班` : `班级${c.name}` },
        className(id) { const c = this.classList.find(x => x.id === id); return c ? this.classLabel(c) : '-' },
        userName(id) { const u = this.userList.find(x => x.id === id); return u ? u.name : '-' },
        handleSearch() { this.currentPage = 1; this.loadList() },
        handlePageChange(p) { this.currentPage = p; this.loadList() },
        handleSizeChange(s) { this.pageSize = s; this.currentPage = 1; this.loadList() },
        handleSelectionChange(rows) { this.selectedRows = rows },
        openEdit(row) {
            this.formMode = row ? 'edit' : 'add'
            this.form = row ? { id: row.id, userId: row.userId, classId: row.classId } : { id: null, userId: null, classId: null }
            this.showEdit = true
        },
        async submitForm() {
            if (!this.form.userId) { ElMessage.warning('请选择用户'); return }
            if (!this.form.classId) { ElMessage.warning('请选择班级'); return }
            this.saving = true
            try {
                if (this.formMode === 'add') {
                    await http.post('/student', this.form, { timeoutMs: 5000 })
                    ElMessage.success('新增成功')
                } else {
                    await http.put('/student', this.form, { timeoutMs: 5000 })
                    ElMessage.success('编辑成功')
                }
                this.showEdit = false
                this.loadList()
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, this.formMode === 'add' ? '新增失败' : '编辑失败') })
            } finally { this.saving = false }
        },
        handleDelete(row) {
            ElMessageBox.confirm('确定删除该学生记录吗？', '提示', { type: 'warning' })
                .then(async () => {
                    try {
                        await http.delete(`/student/${row.id}`, { timeoutMs: 5000 })
                        ElMessage.success('删除成功')
                        this.loadList()
                    } catch (e) { ElMessage({ type: 'error', message: getErrorMessage(e, '删除失败') }) }
                }).catch(() => { })
        },
        async handleBatchDelete() {
            if (!this.selectedRows.length) return
            try { await ElMessageBox.confirm(`确定删除选中的 ${this.selectedRows.length} 条记录吗？`, '提示', { type: 'warning' }) } catch { return }
            try {
                await Promise.all(this.selectedRows.map(r => http.delete(`/student/${r.id}`, { timeoutMs: 5000 })))
                ElMessage.success('批量删除成功')
                this.$refs.tableRef?.clearSelection()
                this.loadList()
            } catch (e) { ElMessage({ type: 'error', message: getErrorMessage(e, '批量删除失败') }) }
        }
    },
    mounted() { this.loadList(); this.loadDict() }
}
</script>
