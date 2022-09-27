import Vue from 'vue'
import VueRouter from 'vue-router'
/*导入路径*/
import Home from '../views/main/index.vue'
import LOGIN from '../views/login/index.vue'
import Brand from '../views/brand/index.vue'
import Test from '../views/stock/test.vue'

Vue.use(VueRouter)

const routes = [
    //路由跳转  首页
    {
        path: '/',
        name: 'Main',
        component: Home,
        redirect: '/login',//默认/ 跳转路径
        //子页面品牌
        children: [
            {
                path: '/index',
                name: 'Index',
                component: () => import('../views/index/index.vue')
            },

            {
                path: '/brand',
                name: 'Brand',
                component: Brand,
            },
            {
                path: '/category',
                name: 'Category',
                component: () => import('../views/category/index.vue')
            },
            {
                path: '/good',
                name: 'Good',
                component: () => import('../views/good/index.vue')
            },
            {
                path: '/admin',
                name: 'Admin',
                component: () => import('../views/admin/index.vue')
            },
            {
                path: '/dept',
                name: 'Dept',
                component: () => import('../views/dept/index.vue')
            },
            {
                path: '/role',
                name: 'Role',
                component: () => import('../views/role/index.vue')
            },
            {
                path: '/menu',
                name: 'Menu',
                component: () => import('../views/menu/index.vue')
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: LOGIN
    },
    {
        path: '/test',
        name: 'test',
        component: Test,
        children: [
            {
                path: '/test/a',
                name: 'a',
                component: () => import('../views/stock/inventory/index.vue')
            },
            {
                path: '/test/b',
                name: 'b',
                component: () => import('../views/stock/shipment/sell/index.vue')
            },
            {
                path: '/test/c',
                name: 'c',
                component: () => import('../views/stock/warehousing/sell/index.vue')
            },
            {
                path: '/test/d',
                name: 'd',
                component: () => import('../views/stock/audit/index.vue')
            },

        ]
    }

    /*
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/!* webpackChunkName: "about" *!/ '../views/About.vue')
  }*/
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
