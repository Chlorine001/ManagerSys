<template>
    <div style="overflow: auto;">
        <table border="1">
            <tr>
                <td>id</td>
                <td>parentId</td>
                <td>type</td>
                <td>路由path</td>
                <td>路由title</td>
                <td>按钮权限</td>
                <td>字典数据显示</td>
            </tr>
            <tr>
                <td>1</td>
                <td>-1</td>
                <td>route</td>
                <td>/pages/index/index</td>
                <td>首页</td>
                <td>-</td>
                <td>-</td>
            </tr>
            <tr>
                <td>2</td>
                <td>1</td>
                <td>button</td>
                <td>-</td>
                <td>-</td>
                <td>add</td>
                <td>-</td>
            </tr>
            <tr>
                <td>3</td>
                <td>1</td>
                <td>button</td>
                <td>-</td>
                <td>-</td>
                <td>set</td>
                <td>-</td>
            </tr>
            <tr>
                <td>4</td>
                <td>1</td>
                <td>dicts</td>
                <td>-</td>
                <td>-</td>
                <td>-</td>
                <td>set:city</td>
            </tr>
            <tr>
                <td>5</td>
                <td>1</td>
                <td>dicts</td>
                <td>-</td>
                <td>-</td>
                <td>-</td>
                <td>set:num</td>
            </tr>
        </table>

    </div>
</template>

<style scoped>
table tr td {
    border: 1px solid #333
}
</style>