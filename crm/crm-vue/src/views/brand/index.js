import dateOptions from "@/utils/date"; //引入date的渲染
import base64 from '@/utils/base64Utils' //引入上传图片base64
import {findPage, deleteById, batchDelete, addEntity,findById,updateEntity} from '@/api/brand'//引入axios的请求


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
                startTime: '',//开始时间
                endTime: '',//结束时间
                brandName: ''//品牌名称

            },
            batchIds: [],//批量删除  判断选中是否修改 和删除
            dialogVisible: false,//新增窗口默认不开
            formData: {},//新增表单信息
            imageUrl: '' //上传品牌图片
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

            let response = await findPage(this.searchParams);


            this.total = response.total;//总行数复制

            this.tableData = response.data;//数据信息赋值
            if(this.tableData.length ==0){
                this.searchParams.currentPage = this.searchParams.currentPage -1;
                this.searchPage();
            }


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