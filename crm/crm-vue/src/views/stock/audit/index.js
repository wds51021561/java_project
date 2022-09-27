import { storageFormReq, auditReq, goodStorageReq, dicReq } from "@/api/store";
import {  PickerOptions } from "@/utils/construction";

function QueryForm() {
    this.storageCode = "";
    this.storageFormType = "";
    this.states = 0;
    this.starTime="";
    this.endTime="";
    this.currentPage=1;
    this.pageSize=5;

}



let audit = {
    data() {
        return {
            //页面名
            name: 'audit',
            //查询的表单条件
            queryForm: new QueryForm(),
            //页面主显示表格数据
            tableData: [""],
            //日期快捷选项
            pickerOptions: new PickerOptions,
            //入库单类型
            options: [],
            //创建审核记录
            createFormFolg: false,
            //显示审核记录
            showFormFolg: false,
            //提交审核表单
            form: {
                storageCode: '',
                remark: '',
                states: ''

            },
            goodTable: [],
            auditTable: [],
            selectStorageForm: {},
            dataTotal: 0,

        }
    },
    methods: {
        //重置查询条件
        reset() {
            this.queryForm = new QueryForm();
        },
        //提交查询
        async query() {
            let data = await storageFormReq.reqPageStorageForm(this.queryForm);
            this.tableData = data.data;
            this.dataTotal = data.total;
        },
        //查找
        selectQuery(){
            this.queryForm.currentPage = 1;
            this.query();
        },
        //按库存单状态查询
        queryStates(states) {
            this.queryForm = new QueryForm();
            this.queryForm.states = states;
            this.query();
        },
        //显示审核记录
        async showForm(storageForm) {
            this.showFormFolg = true;
            this.auditTable = await auditReq.reqAuditFormByStorageFormCode(storageForm.storageCode);
            this.goodTable = await goodStorageReq.reqGoodStorageByStorageFormCode(storageForm.storageCode);
        },
        //创建审核记录
        async createForm(storageForm) {
            //获取选中的仓库单
            this.selectStorageForm = storageForm;
            this.form.remark = "";
            let code = storageForm.storageCode;
            this.auditTable = await auditReq.reqAuditFormByStorageFormCode(code);
            this.goodTable = await goodStorageReq.reqGoodStorageByStorageFormCode(code);
            this.createFormFolg = true;
        },
        //修改每页显示数量
        pageSizeChange(size) {
            this.queryForm.pageSize = size;
            this.query();
        },
        //翻页
        pageCurrentChange(page) {
            this.queryForm.currentPage = page;
            this.query();
        },
        //清空表单
        formClear() {
            this.form = {}
        },
        //提交
        async formSubmit(states) {
            this.form.states = states;
            this.form.storageCode = this.selectStorageForm.storageCode;
            let result = await auditReq.reqAddAuditForm(this.form);
            if (result != null) {
                this.createFormFolg = false;
            }
            this.query();
        },
        //设置检索时间
        setTime(){
            this.queryForm.starTime = this.pickerOptions.dates[0];
            this.queryForm.endTime = this.pickerOptions.dates[1];
        }
    },
    async created() {
        this.options = await dicReq.reqStorageType();

    },
}
export default audit
