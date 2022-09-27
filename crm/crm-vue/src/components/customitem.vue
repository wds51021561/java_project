<template>
    <div class="box">
        <!--1 表示菜单-->
        <el-menu-item :index="item.menuPath" v-if="item.menuType==1">
            <e-icon :icon-name="item.menuIcon"/>
            <span slot="title">{{item.menuTitle}}</span>
        </el-menu-item>

        <!-- 0 表示目录-->
        <el-submenu :index="item.menuPath" v-if="item.menuType==0">
            <template slot="title">
                <e-icon :icon-name="item.menuIcon"/>
                <span slot="title">{{item.menuTitle}}</span>
            </template>

            <!--自己遍历自己-->
            <customitem v-for="(item,index) in item.children" :key="index" :item="item">

            </customitem>
        </el-submenu>

    </div>

</template>

<script>
    export default {
        name: "customitem",
        props:['item']
    }
</script>

<style scoped>

</style>