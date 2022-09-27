<template>

    <div class="main-wrapper">

        <!--布局-->
        <el-container>
             <!--左导航-->
            <el-aside width="200px">
                <!--Logo部分-->
                <div class="aside-logo-wrapper">
                    <!--@标识src下-->
                    <img src="../../assets/logo.png" alt="">
                    <span>Crm管理后台系统</span>
                </div>
                <!--导航菜单-->
                <el-scrollbar>
                    <el-menu
                            active-text-color="#F56C6C"
                            text-color="#606266"
                            router
                            default-active="1">
                    <!--router  路由-->

                        <!--自定义组件-->
                        <customitem v-for="(item,index) in menuList" :key="index" :item="item">

                        </customitem>


                        <!--&lt;!&ndash;导航头&ndash;&gt;-->
                        <!--<el-menu-item index="1">-->
                            <!--<i class="el-icon-menu"></i>-->
                            <!--<span slot="title">系统首页</span>-->
                        <!--</el-menu-item>-->

                        <!--&lt;!&ndash;商品相关&ndash;&gt;-->
                        <!--<el-submenu index="2">-->
                            <!--<template slot="title">-->
                                <!--<i class="el-icon-location"></i>-->
                                <!--<span>商品相关</span>-->
                            <!--</template>-->
                            <!--<el-menu-item index="/brand">-->
                                <!--<i class="el-icon-menu"></i>-->
                                <!--<span slot="title">品牌管理</span>-->
                            <!--</el-menu-item>-->
                            <!--<el-menu-item index="/category">-->
                                <!--<i class="el-icon-menu"></i>-->
                                <!--<span slot="title">分类管理</span>-->
                            <!--</el-menu-item>-->

                            <!--<el-menu-item index="/good">-->
                                <!--<i class="el-icon-menu"></i>-->
                                <!--<span slot="title">商品管理</span>-->
                            <!--</el-menu-item>-->
                        <!--</el-submenu>-->

                        <!--&lt;!&ndash;员工相关&ndash;&gt;-->
                        <!--<el-submenu index="3">-->
                            <!--<template slot="title">-->
                                <!--<i class="el-icon-location"></i>-->
                                <!--<span>员工相关</span>-->
                            <!--</template>-->
                            <!--<el-menu-item index="/dept">-->
                                <!--<i class="el-icon-menu"></i>-->
                                <!--<span slot="title">部门管理</span>-->
                            <!--</el-menu-item>-->
                            <!--<el-menu-item index="/admin">-->
                                <!--<i class="el-icon-menu"></i>-->
                                <!--<span slot="title">员工管理</span>-->
                            <!--</el-menu-item>-->
                        <!--</el-submenu>-->

                        <!--&lt;!&ndash;系统管理&ndash;&gt;-->
                        <!--<el-submenu index="4">-->
                            <!--<template slot="title">-->
                                <!--<i class="el-icon-location"></i>-->
                                <!--<span>系统管理</span>-->
                            <!--</template>-->
                            <!--<el-menu-item index="/role">-->
                                <!--<i class="el-icon-menu"></i>-->
                                <!--<span slot="title">角色管理</span>-->
                            <!--</el-menu-item>-->
                            <!--<el-menu-item index="/menu">-->
                                <!--<i class="el-icon-menu"></i>-->
                                <!--<span slot="title">权限管理</span>-->
                            <!--</el-menu-item>-->

                            <!--&lt;!&ndash;日志相关&ndash;&gt;-->
                            <!--<el-submenu index="5">-->
                                <!--<template slot="title">-->
                                    <!--<i class="el-icon-location"></i>-->
                                    <!--<span>日志相关</span>-->
                                <!--</template>-->
                                <!--<el-menu-item index="5-1">-->
                                    <!--<i class="el-icon-menu"></i>-->
                                    <!--<span slot="title">登录日志</span>-->
                                <!--</el-menu-item>-->
                                <!--<el-menu-item index="5-2">-->
                                    <!--<i class="el-icon-menu"></i>-->
                                    <!--<span slot="title">操作日志</span>-->
                                <!--</el-menu-item>-->
                            <!--</el-submenu>-->

                            <!--</el-submenu>-->

                    </el-menu>
                </el-scrollbar>

            </el-aside>
            <!--右导航-->
            <el-container>
                <!--右导航头-->
                <el-header></el-header>
                <!--右主题内容-->
                <el-main>

                    <!--动态渲染-->
                    <div class="main-box">
                        <router-view/>
                    </div>

                </el-main>
            </el-container>
        </el-container>

    </div>

</template>

<script>
    import  mains from "./index.js"
    export default mains


</script>

<style scoped lang="scss">
    .main-wrapper {
        height: 100%;
    }

    .el-header {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #fff;
        color: #333;


    .aside-logo-wrapper {
        text-align: center;
        background-color: #B3C0D1;
        height: 60px;
        line-height: 60px;

    img {
        width: 35px;
        height: 35px;
        border-radius: 50%;
        vertical-align: middle;
        margin-right: 8px;
    }

    span {
        font-weight: 700;
        color: #fff;
        font-family: "华文行楷";
    }


    }


    }

    .el-scrollbar {
        height: calc(100% - 60px);
    }

    .el-main {
        background-color: #E9EEF3;
        color: #606266;
        padding: 6px 0 0 6px;

    .main-box {
        width: 100%;
        height: 100%;
        background-color: #fff;
        padding: 15px 0px 0px 15px;
        box-sizing: border-box;
    }


    }

    .el-container {
        height: 100%;
    }


</style>