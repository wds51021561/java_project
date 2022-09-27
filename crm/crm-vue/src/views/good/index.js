import dateOptions from "@/utils/date"; //引入date的渲染
import good from '@/api/good'//引入axios的请求
import base64 from '@/utils/base64Utils' //引入上传图片base64
import {findPage} from "@/api/category" //分类信息


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
            prop: {
                label: 'catetoryName', //指定复选框 更改默认名字
                value: 'id'      //选择要的id  联机选择框
            },
            selectIds: [],//选中的数值
            options: [],//返回的信息数值
            batchIds: [],//批量删除  判断选中是否修改 和删除
            dialogVisible: false,//新增窗口默认不开
            formData: {},//新增表单信息
            total: 0,
            activeName: 'first',//添加列表默认勾选的框
            firstCategoryId: [],//添加窗体一级分类信息
            secondCategoryId: [],//添加窗体二级分类信息
            threeCategoryId: [],//添加窗体三级分类信息
            brandList: [] //搜索框中所有品牌信息

        }
    },
    created() {//回调函数
        //查询
        this.searchPage();
        //搜索下拉框渲染
        this.getAllBrands();
        //拿到分类信息
        this.getTreeCategory();

    },
    methods: {
        /**
         * 查询所有
         */
        async searchPage() {
            let response = await good.searchPage(this.searchParams);
            console.log(good.searchPage(this.searchParams))
            console.log(response.data);
            this.total = response.total;//总行数复制
            this.tableData = response.data;//数据信息赋值
        },

        /**
         * 添加
         */
        async addOrEdit(){
          if (this.formData.id){
              //修改
              await this.updateEntity();
          }else {
              //新增加
              await this.addEntity();
          }
          //刷新
          this.searchPage();
        },
        //修改
        async updateEntity(){
            await good.updateEntity(this.formData)

        },
        //新增
        async addEntity(){
            await  good.addEntity(this.formData)
        },

        //根据id查询
        async findById(){
            this.formData=await good.findById(this.formData.id)
            //渲染图片
            this.$refs.goodImg.src=this.formData.goodImg;
            //分类回写
            this.selectIds=[this.formData.firstCategoryId,this.formData.secondCategoryId,this.formData.threeCategoryId]
        },

        /**
         * 拿到品牌所有信息
         */
        async getAllBrands() {
            this.brandList = await  good.getAllBrand();
        },

        /**
         *通过分类的分页拿到新增窗体中的信息
         */
        async getTreeCategory() {
            this.options = await findPage();
        },

        /**
         * 级联选择器 选中进行对firstCategoryId 等赋值
         * @param val
         */
        selectCascader() {
            //给一级分类 赋值
            this.formData.firstCategoryId = this.selectIds[0];
            //给二级分类 赋值
            this.formData.secondCategoryId = this.selectIds[1];
            //给三级分类 赋值
            this.formData.threeCategoryId = this.selectIds[2];
        },


        //checkbox勾选改变
        selectChange(val) {
            console.log(val)
            /**
             * 复选框选择修改 进行id赋值
             */
            if (val.length == 1) {
                //id赋值
                this.formData.id = val[0].id;
            } else {
                this.formData.id = "";
            }


            //勾选二个的时候修改失效  删除有效
            this.batchIds = val.map(item => item.id)


        },
        /**
         * 批量删除
         */
        async batchDeleteByIds() {
            // await batchDelete(this.batchIds);
            console.log("批量删除触发")
            this.searchPage();
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
         * 新建图片 选择
         */
        async getImgStr(e) {
            //给formdata赋值
            this.formData.goodImg = await base64.getBase64Str(e.file);
            console.log(this.formData.goodImg)
            //图片回写
            this.$refs.goodImg.src=this.formData.goodImg


        }

    }


}

export default brand;