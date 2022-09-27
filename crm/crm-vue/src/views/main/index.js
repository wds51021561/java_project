//import  auth from "@/utils/auth";//引入 cookie方式有bug
//引入自定义组件
import customitem from "@/components/customitem"

let mains={
    name: "index",
    components:{customitem},
    data(){
        return{
            menuList:[],
        }
    },
    created(){
        //cookie 方式 有问题 bug 不使用cookie
        //var menuList = auth.getmenuList();
        // console.log(menuList)
        // //转换为json
        // console.log(JSON.parse(menuList));
        // //赋值
        // this.menuList=JSON.parse(menuList);


        //从本地取出来
        this.menuList = JSON.parse(localStorage.getItem("menuList"));
        console.log("menuList==");
        console.log(this.menuList);


    }
}
export default mains;
