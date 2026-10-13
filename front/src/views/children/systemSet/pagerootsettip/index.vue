<template>
    <div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            <span style="color:#999">
                这个项目一开始要定好，有哪些角色，哪些页面，哪些按钮显示权限<br><br>
                然后还要定好，每个页面，它显示的某部分内容的字典标识，以及对应的按钮权限，能操作的所有内容的字典标识<br><br>
                然后对应角色，它能显示哪些页面，项目可以设置，它能显示哪些按钮，哪些字典对应数据，项目也可以设置
            </span>
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: _white;">
            前端渲染处理：
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            页面展示权限：当后端在页面访问数组里面有[{path:'xxx',root:[]}]，这样的对象的时候，表明该页面相对于这个身份，是可以访问的
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            按钮权限查询：{{ $route.meta.root }}<br><br>
            当$route.meta.root长度不为空的时候，它会有'edit','add','del'这三种当中的其中一种或多种的操作按钮权限<br>
            存在对应权限的时候，并为对应的按钮配置好相应权限，当该权限存在，即可展示对应操作按钮
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            字典查询：{{ $root.dictionarysuse }}<br><br>
            当$root.dictionarysuse对象为null的时候，表示是初始为null，且显示全部和字典相关的数据模块内容<br>
            即$root.dictionarysuse为null的时候，显示全部对应字典的相关数据模块，当不为null的时候，并做好相应的字典对应模块的判断，即属性为1的时候，只显示对应已提交到后台的相关字典标识的数据模块内容
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: _white;">
            后端数据处理：
        </div>
        <div style="margin-bottom: 20px;padding:10px; background-color: white;" :style="{ boxShadow: $root.boxShadow }">
            一张表以路由（path，title）为关键点，按钮权限关联添加到这张表路由id，字典操作数据也一样。以下例子：<br><br>
            一张路由按钮字典的联动表<br><br>
            <tableAll></tableAll><br>
            然后一张角色表<br><br>
            <tableRoot /><br>
            然后上面两张表联动<br><br>
            <tableRootTargetVue /><br>
            然后一张用户表和部门表<br><br>
            <tableUserVue></tableUserVue><br>
            然后用户联动角色表和部门表（即角色和部门都可以多选）<br><br>
            <tableUserTargetVue></tableUserTargetVue>
        </div>
    </div>
</template>

<script>
import tableAll from './table-all.vue';
import tableRoot from './table-root.vue'
import tableRootTargetVue from './table-root-target.vue'
import tableUserVue from './table-user.vue'
import tableUserTargetVue from './table-user-target.vue'
export default {
    components: {
        tableAll,
        tableRoot,
        tableRootTargetVue,
        tableUserVue,
        tableUserTargetVue
    },
    mounted() {

    }
}
</script>
