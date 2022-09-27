import {PickerOptions} from '@/utils/construction';
import {dicReq, orderReq, storageFormReq} from "@/api/store";

// 销售入库查询请求对象
function QueryForm() {
    this.orderId = '';
    this.orderCode = '';
    this.states = '';
    this.currentPage = 1;
    this.pageSize = 5;
    this.starTime = '';
    this.endTime = '';
}

//退货订单
function ReturnGoodOrder() {
    this.returnGoodOrderCode = '';
    this.states = '';
    this.goodCode = '';
    this.goodName = '';
    this.goodNum = '';
    this.returnGoodOrderDate = '';
}

function GoodInfo() {
    this.goodId = '';
    this.goodSerial = '';

    this.copy =function (id,serial){
        this.goodId = id;
        this.goodSerial = serial;
    }
}
function Form(){
    this.returnGoodOrderCode= '';
        this.goodInfos= [];
        this.storageId= "";
        this.remark= "";
}

let sell = {
    data() {
        return {
            name: 'sell',
            //查询退货订单条件
            queryForm: new QueryForm(),
            //退货订单分页数据
            tableData: [""],
            //日期快捷选择
            pickerOptions: new PickerOptions(),
            //退货单状态
            options: [],
            //创建入库单
            formFlog: false,
            //显示入库单
            showFlag: false,
            //入库单表单
            form: {
                returnGoodOrderCode: '',
                goodInfos: [],
                storageId: "",
                remark: "",
            },
            //仓库类型
            stockType: [],
            //订单数据总条数
            dataTotal: 0,
            //退货订单
            returnGoodOrder: new ReturnGoodOrder(),
            //订单商品数据
            goodTable: [{
                goodId: 1,
                goodCode: 1,
                goodName: 1,
                goodType: 1,
                goodColor: 1,
                goodSerial: 1
            }],

        }
    },
    methods: {
        //重置查询条件
        reset() {
            this.queryForm = new QueryForm();
        },
        //请求查询
        async query() {
            let data = await orderReq.reqPageQuerySellReturnOrder(this.queryForm);
            this.tableData = data.data;
            this.dataTotal = data.total;
        },
        selectQuery() {
            this.queryForm.currentPage = 1;
            this.query();
        },
        //查看入库单
        async show(order) {
            this.returnGoodOrder = order;
            this.goodTable = await orderReq.reqSellOrderGood(order.returnGoodOrderId);
            this.showFlag = true;

        },
        //创建入库单
        async createForm(order) {
            this.form = new Form();
            this.returnGoodOrder = order;
            this.form.returnGoodOrderCode = this.returnGoodOrder.returnGoodOrderCode;
            this.goodTable = await orderReq.reqSellOrderGood(order.returnGoodOrderId);
            this.formFlog = true;

        },
        //修改每页显示条树
        pageSizeChange(size) {
            this.queryForm.pageSize = size;
            this.query();
        },
        //翻页
        pageCurrentChange(current) {
            this.queryForm.currentPage = current;
            this.query.currentPage = 1;
            this.query();
        },
        //取消创建入库单
        formClear() {
            this.form = new Form();
            this.returnGoodOrder = new ReturnGoodOrder();
            this.goodTable = [];
            this.formFlog = false;
        },
        //提交创建入库单
        async formSubmit() {
            this.form.goodInfos=[];
            for (let good of this.goodTable) {
                let goodInfo = new GoodInfo();
                goodInfo.copy(good.id,good.goodSerial);
                this.form.goodInfos.push(goodInfo);
            }
           let result = await storageFormReq.reqAddStorageForm(this.form);
            if(result==1){
                this.$notify({
                    title: '成功',
                    message: '已生成入库单',
                    type: 'success'
                });
                this.formFlog =false;
            }
        },
        //设置查询时间
        setTime() {
            this.queryForm.starTime = this.pickerOptions.dates[0];
            this.queryForm.endTime = this.pickerOptions.dates[1];
        }
    },
    async created() {
        this.options = await dicReq.reqReturnOrderStates();
        this.stockType = await dicReq.reqStorage();
    }
}
export default sell
