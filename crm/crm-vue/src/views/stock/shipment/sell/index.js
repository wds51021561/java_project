import {PickerOptions} from "@/utils/construction";
import {dicReq, orderReq, storageFormReq} from "@/api/store";

function QueryForm(){
    this.orderCode = '';
    this.orderType = '';
    this.payType='';
    this.orderStates="";
    this.starTime='';
    this.endTime='';
    this.currentPage=1;
    this.pageSize=5;
}



function Form(){
    this.returnGoodOrderCode= '';
    this.goodInfos= [];
    this.storageId= "";
    this.remark= "";
}

function GoodInfo() {
    this.goodId = '';
    this.goodSerial = '';

    this.copy =function (id,serial){
        this.goodId = id;
        this.goodSerial = serial;
    }
}

function Order(){
    this.id='';
    this.orderCode='';
    this.orderType='';
    this.payType='';
    this.memAct='';
    this.orderStates='';
    this.createTime='';
}


let sell = {
    data() {
        return {
            //组件名
            name: 'sell',
            //查询表单
            queryForm: new QueryForm(),
            //表格数据
            tableData: [],
            //时间对象
            pickerOptions:new PickerOptions(),
            //状态选项
            options:[],
            //创建表单显示
            formFlog:false,
            showFlag:false,
            //生成出库单表单
            form:new Form(),
            //仓库类别
            stockType:[],
            //支付方式
            payment:[],
            //订单类型
            orderType:[],
            //订单关联商品数据
            goodTable:[],
            //订单总数
            dataTotal:0,
            //选中的订单
            selectOrder:new Order(),
        }
    },
    methods:{
        //重置搜索框
        reset(){
            this.queryForm = new QueryForm();
        },
        //查询
        async query(){
          let data = await  orderReq.reqSellOrder(this.queryForm);
          this.tableData = data.data;
          this.dataTotal = data.total;
        },
        //提交查询
        selectQuery(){
            this.queryForm.currentPage = 1;
            this.query();
        },
        //显示订单信息
        async show(order){
            this.selectOrder = order;
            this.goodTable = await orderReq.reqSellOrderGood(order.id);
            this.showFlag=true;
        },
        //创建入库单
        async createForm(order){
            this.selectOrder = order;
            this.form.returnGoodOrderCode = order.orderCode;
            this.goodTable = await orderReq.reqSellOrderGood(order.id);
            this.formFlog=true;
        },
        //调整每页数量
        pageSizeChange(size){
            this.queryForm.pageSize = size;
            this.query();
        },
        //翻页
        pageCurrentChange(page){
            this.queryForm.currentPage = page;
            this.query();

        },
        //
        formClear(){
            this.form = new Form();
            this.formFlog = false;
        },
        async formSubmit(){
            this.form.goodInfos=[];
            for (let good of this.goodTable) {
                let goodInfo = new GoodInfo();
                goodInfo.copy(good.id,good.goodSerial);
                this.form.goodInfos.push(goodInfo);
            }
            let result = await storageFormReq.reqAddSellStorageForm(this.form);
            if(result==1){
                this.$notify({
                    title: '成功',
                    message: '已生成入库单',
                    type: 'success'
                });
                this.formFlog =false;
            }

        },
        selectTime(){
            this.queryForm.starTime = this.pickerOptions.dates[0];
            this.queryForm.endTime = this.pickerOptions.dates[1];
        }
    },
     async created() {
        this.stockType = await dicReq.reqStorage();
        this.options = await dicReq.reqSellOrderStates();
        this.payment = await dicReq.reqPayType();
        this.orderType = await dicReq.reqOrderType();
    }
}
export default sell
