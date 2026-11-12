<template>
    <div>
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: var(--app-panel);"
            :style="{ boxShadow: $root.boxShadow }">
            <el-input v-model="keyword" placeholder="请输入课程名" clearable
                style="display: inline-block; white-space: nowrap; margin: 12px 0px 0 12px; width: 220px"
                @keyup.enter="handleSearch" @clear="handleSearch" />
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
                <el-table-column label="课程名" prop="name" min-width="160" />
                <el-table-column label="备注" prop="info" min-width="200" show-overflow-tooltip />
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

        <el-dialog v-model="showEdit" align-center :title="formMode === 'add' ? '新增课程' : '编辑课程'" width="480px">
            <el-form label-width="90px" label-position="left">
                <el-form-item label="课程名：">
                    <el-input v-model="form.name" placeholder="请输入课程名" />
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input v-model="form.info" type="textarea" placeholder="请输入备注" />
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
            keyword: '', selectedRows: [], loading: false, saving: false,
            list: [], total: 0, currentPage: 1, pageSize: 10,
            showEdit: false, formMode: 'add',
            form: { id: null, name: '', info: '' }
        }
    },
    methods: {
        async loadList() {
            this.loading = true
            try {
                const res = await http.get('/course/page', {
                    params: { name: this.keyword.trim() || undefined, current: this.currentPage, size: this.pageSize },
                    timeoutMs: 5000
                })
                const d = res?.data ?? {}
                this.list = d.records || []
                this.total = d.total || 0
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '加载课程列表失败') })
            } finally { this.loading = false }
        },
        handleSearch() { this.currentPage = 1; this.loadList() },
        handlePageChange(p) { this.currentPage = p; this.loadList() },
        handleSizeChange(s) { this.pageSize = s; this.currentPage = 1; this.loadList() },
        handleSelectionChange(rows) { this.selectedRows = rows },
        openEdit(row) {
            this.formMode = row ? 'edit' : 'add'
            this.form = row ? { id: row.id, name: row.name, info: row.info } : { id: null, name: '', info: '' }
            this.showEdit = true
        },
        async submitForm() {
            if (!this.form.name) { ElMessage.warning('请输入课程名'); return }
            this.saving = true
            try {
                if (this.formMode === 'add') {
                    await http.post('/course', this.form, { timeoutMs: 5000 })
                    ElMessage.success('新增成功')
                } else {
                    await http.put('/course', this.form, { timeoutMs: 5000 })
                    ElMessage.success('编辑成功')
                }
                this.showEdit = false
                this.loadList()
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, this.formMode === 'add' ? '新增失败' : '编辑失败') })
            } finally { this.saving = false }
        },
        handleDelete(row) {
            ElMessageBox.confirm(`确定删除课程「${row.name}」吗？`, '提示', { type: 'warning' })
                .then(async () => {
                    try {
                        await http.delete(`/course/${row.id}`, { timeoutMs: 5000 })
                        ElMessage.success('删除成功')
                        this.loadList()
                    } catch (e) { ElMessage({ type: 'error', message: getErrorMessage(e, '删除失败') }) }
                }).catch(() => { })
        },
        async handleBatchDelete() {
            if (!this.selectedRows.length) return
            try { await ElMessageBox.confirm(`确定删除选中的 ${this.selectedRows.length} 个课程吗？`, '提示', { type: 'warning' }) } catch { return }
            try {
                await Promise.all(this.selectedRows.map(r => http.delete(`/course/${r.id}`, { timeoutMs: 5000 })))
                ElMessage.success('批量删除成功')
                this.$refs.tableRef?.clearSelection()
                this.loadList()
            } catch (e) { ElMessage({ type: 'error', message: getErrorMessage(e, '批量删除失败') }) }
        }
    },
    mounted() { this.loadList() }
}
</script>
