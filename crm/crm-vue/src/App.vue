<!--
    vue 脚手架创建项目的方式
        1： webstorm创建
        2： vue ui  在可视化窗口勾选
        3: 执行命令
              vue create 项目名称


     脚手架项目的文件夹
        public： 固定的 2个 一个是html  一个是图标
        src 编码的地方
         assets： 放置静态资源
         components： 放置小组件
         router: 表示的路由
         store： 表示的是状态管理
         views： 用户展示的页面
         App.vue： 表示我们的页面
         main.j： 是项目的入口
         package.json 类似maven工程pom文件

     SPA单页面应用程序
        使用Vue脚手架创建的项目 是单页面应用程序  整个项目只有一个页面  index.html
  -->
<!--
一个Vue组件 有三部分组成
      模板  template template标签是html5的标签 表示模板标签
             编写的是html代码
             要求 有且仅有一个根标签

      js   script

      css  style


-->


<template>
  <div id="app">
    <!--动态跳转 <router-view/>-->
    <router-view/>
  </div>
</template>

<style lang="scss">
  html,body,#app {
    width: 100%;
    height: 100%;
  }
  * {
    padding: 0;
    margin: 0;
  }
</style>
