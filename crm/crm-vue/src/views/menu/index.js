import dateOptions from "@/utils/date"; //引入date的渲染
import menu from "@/api/menu";
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

let brand = {
    name: "index",
    components: {Treeselect},
    data() {
        return {
            dateOptions,
            startDate:'',
            tableData: [],//数据信息
            total: '',//总条数
            searchParams: {
                currentPage: 1,//当前页
                pageSize: 5,//每页显示条数


            },
            dialogVisible: false,//新增窗口默认不开
            formData: {},//新增表单信息
            menuList:[],//菜单信息
            normalizer(node) {//指定菜单下拉框的数值
                return {
                    id: node.id,
                    label: node.menuTitle,
                    children: node.children,
                }
            },
            options: {//图标选择库
                FontAwesome: true,
                ElementUI: true,
                eIcon: true,//自带的图标，来自阿里妈妈
                eIconSymbol: true,//是否开启彩色图标
                addIconList: [],
                removeIconList: []
            }
            
        }
    },
    created() {//回调函数
        //查询
        this.searchPage();
        //新增获取所有的菜单
        this.getAllTreeMenu();
    },
    methods: {
        /**
         * 查询所有
         */
        async searchPage() {
            let response = await menu.searchPage(this.searchParams);
            this.total = response.total;//总行数复制
            this.tableData = response.data;//数据信息赋值
        },
        /**
         * 新增获取所有的菜单
         */
        async getAllTreeMenu() {
            this.menuList=await  menu.getAllMenuTree();
        },




        /**
         * 通过id删除
         */
        async deleteById() {
            console.log(this.formData.id)
            await menu.deleteById(this.formData.id);
            this.searchPage();
        },



        /**
         * 添加数据以及修改数据
         */
        async addOrEdit() {
            if(this.formData.id){
                //修改
                await menu.updateEntity(this.formData);
            }else {
                console.log("asd")
                //添加
                await  menu.addEntity(this.formData);
            }
            //刷新页面
            this.searchPage();
        },


        //重置信息
        resetFromData() {
            this.formData = {
                menuType: 0,//默认新增选中目录
                isHidden: false//默认可用
            }
        },

        /**
         *修改方法根据id进行赋值  通过id查询
         */
        async findById(id){
            //表单赋值
            this.formData=await  menu.findById(this.formData.id);
            //图片回写
            this.imageUrl=this.formData.brandLogo;
        },



        //点击下一页 发生改变
        currentPageChange(page) {
            this.searchParams.currentPage = page;//当前页赋值
            this.searchPage();//刷新页面
        },
        //时间框 选择时间
        chooseTime() {
            this.searchParams.startTime = this.startDate[0];
            this.searchParams.endTime = this.startDate[1];
        },
        //点击重置
        resetForm() {
            //重置第一页信息
            this.searchParams = {currentPage: 1, pageSize: 5}
            this.startDate = '';//时间选择框清空
        },




    }


}

export default brand;