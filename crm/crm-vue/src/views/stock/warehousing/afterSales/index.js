function Good(){
    this.goodId='';
    this.goodCode='';
    this.goodName='';
    this.type='';
    this.color='';
    this.oldGoodSerial='';
    this.newGoodSerial='';
    this.inputGoodSerial='';
}

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
            
                
            },
            stockType:[],
            orderDetails:{},
            payment:[],
            orderType:[],
            goodTable:[new Good()],

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
