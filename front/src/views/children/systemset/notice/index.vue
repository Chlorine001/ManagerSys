<template>
    <div>
        <div style="margin-bottom: 20px;padding:0 0 10px 0; background-color: var(--app-panel);"
            :style="{ boxShadow: $root.boxShadow }">
            <el-input v-model="keyword" placeholder="请输入公告标题" clearable
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
                <el-table-column label="标题" prop="title" min-width="180" show-overflow-tooltip />
                <el-table-column label="发布时间" prop="publishTime" min-width="160" />
                <el-table-column label="过期时间" prop="expirationTime" min-width="160" />
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

        <el-dialog v-model="showEdit" align-center :title="formMode === 'add' ? '新增公告' : '编辑公告'" width="560px">
            <el-form label-width="90px" label-position="left">
                <el-form-item label="标题：">
                    <el-input v-model="form.title" placeholder="请输入公告标题" />
                </el-form-item>
                <el-form-item label="内容：">
                    <el-input v-model="form.content" type="textarea" :rows="5" placeholder="请输入公告内容" />
                </el-form-item>
                <el-form-item label="过期时间：">
                    <el-date-picker v-model="form.expirationTime" type="datetime" placeholder="选择过期时间"
                        style="width: 100%" value-format="YYYY-MM-DD HH:mm:ss" />
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
            form: { id: null, title: '', content: '', expirationTime: '' }
        }
    },
    methods: {
        async loadList() {
            this.loading = true
            try {
                const res = await http.get('/notice/page', {
                    params: { title: this.keyword.trim() || undefined, current: this.currentPage, size: this.pageSize },
                    timeoutMs: 5000
                })
                const d = res?.data ?? {}
                this.list = d.records || []
                this.total = d.total || 0
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, '加载公告列表失败') })
            } finally { this.loading = false }
        },
        handleSearch() { this.currentPage = 1; this.loadList() },
        handlePageChange(p) { this.currentPage = p; this.loadList() },
        handleSizeChange(s) { this.pageSize = s; this.currentPage = 1; this.loadList() },
        handleSelectionChange(rows) { this.selectedRows = rows },
        openEdit(row) {
            this.formMode = row ? 'edit' : 'add'
            this.form = row
                ? { id: row.id, title: row.title, content: row.content, expirationTime: row.expirationTime }
                : { id: null, title: '', content: '', expirationTime: '' }
            this.showEdit = true
        },
        async submitForm() {
            if (!this.form.title) { ElMessage.warning('请输入标题'); return }
            this.saving = true
            try {
                if (this.formMode === 'add') {
                    await http.post('/notice', this.form, { timeoutMs: 5000 })
                    ElMessage.success('新增成功')
                } else {
                    await http.put('/notice', this.form, { timeoutMs: 5000 })
                    ElMessage.success('编辑成功')
                }
                this.showEdit = false
                this.loadList()
            } catch (e) {
                ElMessage({ type: 'error', message: getErrorMessage(e, this.formMode === 'add' ? '新增失败' : '编辑失败') })
            } finally { this.saving = false }
        },
        handleDelete(row) {
            ElMessageBox.confirm(`确定删除公告「${row.title}」吗？`, '提示', { type: 'warning' })
                .then(async () => {
                    try {
                        await http.delete(`/notice/${row.id}`, { timeoutMs: 5000 })
                        ElMessage.success('删除成功')
                        this.loadList()
                    } catch (e) { ElMessage({ type: 'error', message: getErrorMessage(e, '删除失败') }) }
                }).catch(() => { })
        },
        async handleBatchDelete() {
            if (!this.selectedRows.length) return
            try { await ElMessageBox.confirm(`确定删除选中的 ${this.selectedRows.length} 条公告吗？`, '提示', { type: 'warning' }) } catch { return }
            try {
                await Promise.all(this.selectedRows.map(r => http.delete(`/notice/${r.id}`, { timeoutMs: 5000 })))
                ElMessage.success('批量删除成功')
                this.$refs.tableRef?.clearSelection()
                this.loadList()
            } catch (e) { ElMessage({ type: 'error', message: getErrorMessage(e, '批量删除失败') }) }
        }
    },
    mounted() { this.loadList() }
}
</script>
