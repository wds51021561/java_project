import dateOptions from "@/utils/date"; //引入date的渲染
import base64 from '@/utils/base64Utils' //引入上传图片base64
import {findPage, deleteById, batchDelete, addEntity,findById,updateEntity,setRoleMenus,getMenuByRoleId} from '@/api/role'//引入axios的请求
import menu from "@/api/menu";

let brand = {
    name: "index",
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
            batchIds: [],//批量删除  判断选中是否修改 和删除
            dialogVisible: false,//新增窗口默认不开
            formData: {},//新增表单信息
            defaultProps: {//更改tree控件默认展示的名称
                children: 'children',
                label: 'menuTitle'
            },
            menuList: [],//权限列表
            clickId: 0,//点击表格拿到id数值
            selectMenuIds: [],//选择到的权限数组
            expandIds: []
        }
    },
    created() {//回调函数
        //查询
        this.searchPage();
        //刷新右边权限
        this.getAllTreeMenu()
    },
    methods: {
        /**
         * 查询所有
         */
        async searchPage() {
            let response = await findPage(this.searchParams);
            this.total = response.total;//总行数复制
            this.tableData = response.data;//数据信息赋值
        },

        /**
         * 查看所有权限
         */
        async getAllTreeMenu() {
            //去除主目录
            let response=await  menu.getAllMenuTree();
            //权限目录赋值
            this.menuList=response[0].children;
        },


        /**
         * 每一条数据的点击事件
         */
        rowClick(row, column, event) {
            this.expandIds=[]
            this.clickId = row.id;//拿到当前id数值
            this.getMenuByRoleId();
        },
        /**
         * 保存权限 弹出框体
         */
        async showMenuMessageBox(){
            this.$confirm('你确定要添加这些权限吗?', '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //确定 保存权限
                //用concat  进行拼接    this.$refs.tree.getHalfCheckedKeys() 是element ui中Tree里获取的属性
                this.selectMenuIds = this.$refs.tree.getHalfCheckedKeys().concat(this.$refs.tree.getCheckedKeys());
                this.setRoleMenus();


            }).catch(() => {
                //取消
            });
        },

        /**
         * 给角色添加菜单（权限）
         * @param val
         */
        async setRoleMenus() {
            //第一个参数是拿到当前表格表格中选中的id  以及选中的权限数组
            await setRoleMenus(this.clickId, this.selectMenuIds);

        },
        /**
         * 通过角色id获取到角色的权限
         * @param val
         */
        async getMenuByRoleId() {
            //通过id进行查询
            let response = await getMenuByRoleId(this.clickId);
            //赋值
            this.$refs.tree.setCheckedKeys(response);

        },


        /**
         * 通过id删除
         */
        async deleteById() {
            console.log(this.formData.id)
            await deleteById(this.formData.id);
            this.searchPage();
        },

        /**
         * 批量删除
         */
        async batchDeleteByIds() {
            await batchDelete(this.batchIds);
            this.searchPage();
        },

        /**
         * 添加数据以及修改数据
         */
        async addOrEdit() {
            if(this.formData.id){
                //修改
                await this.editEntity();
            }else {
                //添加
                await  this.addEntity();
            }
            //刷新页面
            this.searchPage();
        },

        //添加方法
        async addEntity(){
            await addEntity(this.formData);
        },
        //修改方法
        async editEntity(){
            await updateEntity(this.formData)

        },

        /**
         *修改方法根据id进行赋值  通过id查询
         */
        async findById(id){
            //表单赋值
            this.formData=await  findById(this.formData.id);
            //图片回写
            this.imageUrl=this.formData.brandLogo;
        },


        //checkbox勾选改变
        selectChange(val) {
            console.log(val)
            /**
             * 复选框选择修改 进行id赋值
             */
            if (val.length==1){
                //id赋值
                this.formData.id=val[0].id;
            }else {
                this.formData.id="";
            }
            
            
            //勾选二个的时候修改失效  删除有效
            this.batchIds = val.map(item => item.id)
            

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
        /**
         * 显示批量删除框
         */
        showBatchDeleteDialog() {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.batchDeleteByIds();
            })
        },
        /**
         * 选择图片的钩子函数
         */
        async getImgStr(e) {
            this.imageUrl = await base64.getBase64Str(e.file);//回写显示
            //给formdata赋值
            this.formData.brandLogo = this.imageUrl;
            console.log(this.formData.brandLogo)
            console.log(this.imageUrl)

        }


    }


}

export default brand;