let sell = {
    data() {
        return {
            name: 'sell',
            queryForm: {},
            tableData: ['adf'],
            page: {},
            pickerOptions:()=>import('../../../../utils/date'),
            options:[],
            formFlog:false,
            form:{
                goodSerial:[""],
                giftSerial:[""]
            },
            stockType:[],
            orderDetails:{},
            payment:[],
            orderType:[],
            goodTable:[""],

        }
    },
    methods:{
        reset(){

        },
        query(){

        },
        show(){

        },
        createForm(){
            this.formFlog=true;
        },
        pageSizeChange(){

        },
        pageCurrentChange(){

        },
        addSerial(){
            this.form.goodSerial.push("");
        },
        formClear(){

        },
        formSubmit(){

        }
    }
}
export default sell
