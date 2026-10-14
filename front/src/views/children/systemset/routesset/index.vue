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
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;"
                @click="openAddDialog(selectedRow)">新增</el-button>
            <el-button style="margin:12px 0px 0px 12px;vertical-align: top;" @click="viewdetail2">批量删除</el-button>
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
                <el-table-column label="icon标识">
                    <template #default="scope">
                        {{ scope.row.icon }}
                    </template>
                </el-table-column>
                <el-table-column label="首页是否展示" align="center">
                    <template #default="scope">
                        <el-tag :type="scope.row.ishide ? 'info' : 'success'" size="small">
                            {{ scope.row.ishide ? '隐藏' : '展示' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <!-- <el-table-column label="是否叶子节点" width="130" align="center">
                    <template #default="scope">
                        <el-icon v-if="scope.row.isleaf" color="#67C23A">
                            <CircleCheck />
                        </el-icon>
                        <el-icon v-else color="#909399">
                            <Remove />
                        </el-icon>
                    </template>
                </el-table-column> -->
                <el-table-column label="是否叶子节点" width="130" align="center">
                    <template #default="scope">
                        <el-tag :type="scope.row.isleaf ? 'success' : 'info'" size="small">
                            {{ scope.row.isleaf ? '是' : '否' }}
                        </el-tag>
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
            <el-pagination small background layout="prev, pager, next" :total="list.length" class="mt-4" />
        </div>
        <el-dialog v-model="showsetadd" align-center title="新增条目" width="520px">
            <el-form label-width="100px">
                <!-- 直属上级菜单 -->
                <el-form-item label="直属上级">
                    <el-tree-select v-model="form.pid" :data="parentTree"
                        :props="{ label: 'name', children: 'children', value: 'id' }" check-strictly clearable
                        placeholder="不选则为顶级菜单" style="width: 100%" />
                </el-form-item>

                <!-- 路径：前缀 + 输入框 -->
                <el-form-item label="路径">
                    <div style="display: flex; align-items: center; width: 100%;">
                        <span style="margin-right: 4px; color: #909399; white-space: nowrap;">
                            {{ pathPrefix }}
                        </span>
                        <el-input v-model="form.path" placeholder="请输入子路径，如 systemset" style="flex: 1" />
                    </div>
                </el-form-item>

                <!-- 标题 -->
                <el-form-item label="标题">
                    <el-input v-model="form.name" placeholder="请输入标题" style="width: 100%" />
                </el-form-item>
            </el-form>

            <template #footer>
                <el-button @click="showsetadd = false">取消</el-button>
                <el-button type="primary" @click="submitAdd">确定</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import { http } from '@/utils/http'
import { BASE_PATH } from '@/router/constants'
// import { CircleCheck, Remove } from '@element-plus/icons-vue'
export default {
    data() {
        return {
            selectedRow: [], // 当前选中的行
            showsetadd: false,
            loading: false,
            list: [],
            form: {
                pid: null,
                path: '',
                name: ''
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
            if (parent && parent.length > 1) {
                ElMessage.warning('只能选择一个父菜单新增！')
                return;
            }
           
            const parent0 = parent?.[0]
            this.form = {
                pid: parent0 ? parent0.id : null,   // 选中行作为父级，没选则顶级
                path: '',
                name: ''
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
        async submitAdd() {
            const sub = this.form.path.trim().replace(/^\//, '')
            if (!sub) {
                ElMessage.warning('请输入子路径')
                return
            }
            if (!this.form.name.trim()) {
                ElMessage.warning('请输入标题')
                return
            }

            try {
                await http.postJson('/menu/add', {
                    pid: this.form.pid || '0',      // null 转 '0'
                    url: '/' + sub,                  // 只存单段
                    name: this.form.name.trim()
                })
                ElMessage.success('新增成功')
                this.showsetadd = false
                this.loadMenuList()
            } catch (e) {
                ElMessage.error('新增失败：' + e.message)
            }
        }
    },
    mounted() {
        this.loadMenuList()
    }
}
</script>