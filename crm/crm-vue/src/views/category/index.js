import dateOptions from "@/utils/date"; //引入date的渲染
import {findPage, deleteById, batchDelete, addEntity,findById,updateEntity,getSelectData} from '@/api/category'//引入axios的请求


let brand = {
    name: "index",
    data() {
        return {
            dateOptions,
            startDate:'',
            tableData: [],//数据信息
            searchParams: {
                currentPage: 1,//当前页
                pageSize: 5,//每页显示条数

            },
            batchIds: [],//批量删除
            dialogVisible: false,//新增窗口默认不开
            formData: {},//新增表单信息
            categoryLevel:1,//默认选中
            radio:'',//新增分类等级

            selectIds: [],//联动选择框 选中的数值
            options: [],// 联动选择框  返回的数据
            prop:{
                label:'categoryName', //指定复选框 更改默认名字
                value:'id'      //选择要的id  联机选择框
            },

        }
    },
    created() {//回调函数

        //查询
        this.searchPage();
    },
    methods: {
        /**
         * 查询所有
         */
        async searchPage() {
            this.tableData = await findPage(this.searchParams);




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
            //添加给分类赋值
            this.formData.categoryLevel=this.categoryLevel;
            //如果是一级分类parentId=0
            if (this.categoryLevel==1){
                this.formData.parentId=0;
            } else {
                this.formData.parentId=this.selectIds[this.selectIds.length-1];
            }

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
        async findById(){
            //表单赋值
            this.formData=await  findById(this.formData.id);
            //联动选择框回写
            this.chooseLevel(this.formData.chooseLevel)
            ////联动选择框默认选中
            this.selectIds=this.formData.parentId
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

   /*     //点击下一页 发生改变
        currentPageChange(page) {
            this.searchParams.currentPage = page;//当前页赋值
            this.searchPage();//刷新页面
        },*/
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
         * 选择联机触发点击事件
         */
        async chooseLevel(level){
            console.log(level)
            //赋值给联机选择框
            this.options=await getSelectData();
            switch (level){
                case 1:
                    //复选框为1 清空
                    this.options=[];
                    break;
                case 2:
                    //复选框为2  删除三级分类
                    this.options.forEach(item=>delete  item.children)//删除联机选择框下的分级
                    break;
        /*        case 3:
                    //显示分类  二级 三级
                    this.options.forEach(first=>{
                        if(first.children){
                            first.children.forEach(second=> delete second.children)
                        }

                    })
                    break;*/
            }



        }



    }


}

export default brand;