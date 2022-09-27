import {  PickerOptions } from "@/utils/construction";
import {dicReq, inventoryGoodReq, inventoryReq} from "@/api/store";
function QueryForm(){
    this.storageStaff='';
    this.storageId='';
    this.endTime='';
    this.starTime='';
    this.currentPage=1;
    this.pageSize=5;
}

function AddForm(){
    this.staff='';
    this.storageId=0;
    this.createTime='';
    this.remark='';
    this.list=[];
}



let sell = {
    data() {
        return {
            //组件名
            name: 'inventory',
            //查询表单
            queryForm: new QueryForm(),
            //添加记录表单
            addFrom:new AddForm(),
            //盘点记录
            tableData: [''],
            //盘点记录条数
            tableTotal:0,
            //日期组件
            pickerOptions:new PickerOptions(),
            //仓库选项
            options:[],
            //添加弹窗标记
            addFlog:false,
            //查看弹窗标记
            showFlag:false,
            //盘点商品数据
            addData:[],
            //盘点记录
            selectInventory:{},
        }
    },
    methods:{
        //重置查询控件
        reset(){
            this.queryForm = new QueryForm;
        },
        //查询
        async query(){
          let data = await  inventoryReq.reqPageQuery(this.queryForm);
          this.tableData = data.data;
          this.tableTotal = data.total;
        },
        //条件查询
        selectQuery(){
            this.queryForm.currentPage = 1;
            this.query();
        },
        //添加表单记录
        async add(){
            this.addFrom = new AddForm;
            this.addData = await inventoryGoodReq.reqInventoryGood(this.addFrom.storageId);
            this.addFlog = true;
        },
        //显示盘点记录详情
        async show(data){
            this.selectInventory = data;
            this.addData = await inventoryGoodReq.reqInventoryGoodByCode(this.selectInventory.inventoryCode);
            this.showFlag = true;
        },
        //导出盘点记录
        down(){

        },
        //创建表单
        createForm(){
            this.formFlog=true;
        },
        //调整每页显示数量
        pageSizeChange(size){
            this.queryForm.pageSize = size;
            this.query();

        },
        //翻页
        pageCurrentChange(page){
            this.queryForm.currentPage = page;
            this.query();
        },
        //放弃提交
        formClear(){
            this.addFrom = new AddForm;
        },
        async save(){
            this.addFrom.list = this.addData;
            let result = await inventoryReq.reqAddInventory(this.addFrom);
            if(result==1){
                this.$notify({
                    title: '成功',
                    message: '已生成入库单',
                    type: 'success'
                });
                this.addFlog =false;
            }
        },
        async find(){
            this.addData = await inventoryGoodReq.reqInventoryGood(this.addFrom.storageId);
        },
        setTime(){
            this.queryForm.starTime=this.pickerOptions.dates[0];
            this.queryForm.endTime=this.pickerOptions.dates[1];
        }
    },
    async created() {
        this.options =await dicReq.reqStorage();
    }
}
export default sell
