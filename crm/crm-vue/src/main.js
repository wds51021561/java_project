import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//导入element ui样式  使用import导入后需要使用element ui  Vue.use(ElementUI)
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false;

//重置样式 npm 引入 npm install --save normalize.css
import 'normalize.css/normalize.css'

//https://gitee.com/cnovel/e-icon-picker
import iconPicker from 'e-icon-picker';
import "e-icon-picker/dist/symbol.js"; //基本彩色图标库
import 'e-icon-picker/dist/index.css'; // 基本样式，包含基本图标
import 'font-awesome/css/font-awesome.min.css'; //font-awesome 图标库
import 'element-ui/lib/theme-chalk/icon.css'; //element-ui 图标库
Vue.use(iconPicker, {FontAwesome: true, ElementUI: true, eIcon: true, eIconSymbol: true});


/**
 * 自定义指令 来控制权限按钮的隐藏
 */
// 注册一个全局自定义指令 `hasPerm`
Vue.directive('hasPerm', {
    // 当被绑定的元素插入到 DOM 中时……
    inserted: function (el,binding,vnode) {
      //获取权限按钮列表
      var btnPerms = JSON.parse(localStorage.getItem("btnPerm"));
      //获得对象绑定的指令值
      var value=  binding.value;
        console.log("binding==");
        console.log(binding);
      //  console.log("binding=="+binding);
        //拿去一个
      var hasPerm=btnPerms.some(item=>value.includes(item.permission));
      if (!hasPerm){
        //隐藏
          el.remove();
      }


    }
})

//使用element
Vue.use(ElementUI)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
