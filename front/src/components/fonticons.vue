<template>
    <span>
        <template v-if="name == 'home'">
            <el-icon>
                <House />
            </el-icon>
        </template>
        <template v-else-if="name == 'routes'">
            <el-icon>
                <Eleme />
            </el-icon>
        </template>
        <template v-else-if="name == 'dictsset'">
            <el-icon>
                <Memo />
            </el-icon>
        </template>
        <template v-else-if="name == 'department'">
            <el-icon>
                <OfficeBuilding />
            </el-icon>
        </template>
        <template v-else-if="name == 'role'">
            <el-icon>
                <Coin />
            </el-icon>
        </template>
        <template v-else-if="name == 'userset' || name == 'personal'">
            <el-icon>
                <User />
            </el-icon>
        </template>
        <template v-else-if="name == 'root'">
            <el-icon>
                <Switch />
            </el-icon>
        </template>
        <template v-else-if="name == 'note'">
            <el-icon>
                <ScaleToOriginal />
            </el-icon>
        </template>
        <template v-else-if="name == 'icons'">
            <el-icon>
                <Notification />
            </el-icon>
        </template>
        <template v-else-if="name == 'setting'">
            <el-icon>
                <Setting />
            </el-icon>
        </template>
        <template v-else-if="name == 'mobilepageview'">
            <el-icon>
                <Iphone />
            </el-icon>
        </template>
        <template v-else-if="name == 'collection'">
            <el-icon>
                <Collection />
            </el-icon>
        </template>
        <template v-else>
            <!-- <img class="navimg" :src="$root.isDark ? '/favicon-dark.ico' : '/favicon.ico'" /> -->
            <el-icon>
                <More />
            </el-icon>
        </template>
    </span>
</template>

<script>
export default {
    props: ['name']
}
</script>