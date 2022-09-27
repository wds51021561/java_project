import dateOptions from "@/utils/date"; //引入date的渲染
import base64 from '@/utils/base64Utils' //引入上传图片base64
import admin from '@/api/admin'//引入axios的请求
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import {findAll} from '@/api/role'

let brand = {
    components: {Treeselect},//treeselect的组件
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
            normalizer(node) {//指定部门下拉框的数值
                return {
                    id: node.id,
                    label: node.deptName,
                    children: node.children,
                }
            },
            deptList: [],//搜索框部门的信息
            province: '',//选中省
            city: '',//选中市
            area: '',//选择区
            provinceList: [],//省的数据
            cityList: [],//市的数据
            areaList: [],//区的数据
            roleList: []//选择角色 初始化为空

        }
    },
    created() {//回调函数
        //查询
        this.searchPage();
        /*        //刷新所有部门（Tree）
                this.getDeptTree();*/
        //刷新所有部门（懒加载）
        this.getRootDeptList();
        //获得省市区信息
       this.getAllProvince();
        //拿到所有角色
        this.getAllRole();
    },
    methods: {
        /**
         * 查询所有
         */
        async searchPage() {
            console.log(this.searchParams)
            let response = await admin.findPage(this.searchParams);
            this.total = response.total;//总行数复制
            this.tableData = response.data;//数据信息赋值
        },

        /**
         * 加载所有的角色
         */
        async getAllRole(){
            this.roleList=await findAll()
        },


        /**
         * 查询所有的部门（Tree） 不使用
         */
        /*        async getDeptTree(){
                    this.deptList=await admin.getAllDeptTree()
                },*/

        /**
         *  查询所有的部门（懒加载） 查询第一层数据
         */
        async getRootDeptList() {
            this.deptList = await admin.getChildrenById(0)
        },

        /**
         *  查询所有的部门（懒加载） treeSelect插件的查询
         */
        async loadTreeSelectData({action, parentNode, callback}) {
            //通过id找孩子
            let response = await admin.getChildrenById(parentNode.id);
            //赋值过去
            parentNode.children = response;

        },

        /**
         * 获得所有的省
         */
        async getAllProvince() {
            //访问后台拿到 省的信息
            this.provinceList = await admin.getAreaChildrenById(0);

        },
        /**
         * 获得所有的市
         */
        async getAllCity(name) {
            //根据省的信息  找到对应省下面的市信息的索引
            var index = this.provinceList.findIndex(item => item.name == name);
            //根据索引拿到对应的信息
            var item = this.provinceList[index];
            console.log(item.id)
            //根据信息进行 给市进行赋值
            this.cityList = await admin.getAreaChildrenById(item.id);
        },
        /**
         * 获得所有的区
         */
        async getAllArea(name) {
            //根据市的信息 找到对应市下面的索引
            var index = this.cityList.findIndex(item => item.name == name);
            //根据索引拿到对应的信息
            var item = this.cityList[index];
            console.log(item.id)
            //根据信息进行 给区进行赋值
            this.areaList = await admin.getAreaChildrenById(item.id);
        },

        /**
         * 上传图片  response上传后的返回值
         */
        uploadSuccess(response) {
            //获取返回值赋值
            this.formData.adminAvatar = response.data;

        },

        /**
         * 点击新建按钮时候重置表单
         */
        resetFormData() {
            this.formData = {
                adminAvatar: '',//图片信息重置
                gender: 0,//员工性别默认数值
                isEnable: false,//是否可用默认数值
                adminAddress: '',//详细地址
                roleIds: [],//角色
            }

        },


        /**
         * 通过id删除
         */
        async deleteById() {
            console.log(this.formData.id)
            await admin.deleteById(this.formData.id);
            this.searchPage();
        },

        /**
         * 批量删除
         */
        async batchDeleteByIds() {
            await admin.batchDelete(this.batchIds);
            this.searchPage();
        },

        /**
         * 添加数据以及修改数据
         */
        async addOrEdit() {
            //添加的时候要把详细地址 根据上面的省级联动加进去
            this.formData.adminAddress = this.province + " " + this.city + " " + this.area + " " + this.formData.adminAddress;

            if (this.formData.id) {
                //修改
                await this.editEntity();
            } else {
                //添加
                await  this.addEntity();
            }
            //刷新页面
            this.searchPage();
        },

        //添加方法
        async addEntity() {
            await admin.addEntity(this.formData);
        },
        //修改方法
        async editEntity() {
            await admin.updateEntity(this.formData)

        },

        /**
         *修改方法根据id进行赋值  通过id查询
         */
        async findById(id) {
            //表单赋值
            this.formData = await  admin.findById(this.formData.id);
            //回显员工坐在部门信息
            this.deptList = await  admin.getDeptTree(this.formData.deptId);
            //回显部门 省市级联动
            var strings = this.formData.adminAddress.split(" ");
            //给省赋值
            this.province = strings[0]
            //拿到省的id
            await this.getAllCity(this.province)
            //给市赋值
            this.city = strings[1]
            //拿到市的id
            await this.getAllArea(this.city)
            //给区赋值
            this.area = strings[2]

            //详细信息要重新赋值
            let address = '';
            for (let i = 3; i < strings.length; i++) {
                address += strings[i] + " "
            }
            this.formData.adminAddress = address;
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