import {findPage, getChildrenById, addEntity, updateEntity, findById, deleteById} from '@/api/dept'
import dateOptions from "@/utils/date";
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'


let brand = {
    name: "index",
    components: {Treeselect},
    data() {
        return {
            dateOptions,
            startDate:'',
            tableData: [],
            total: 0,
            searchParams: {
                currentPage: 1,
                pageSize: 5
            },
            dialogVisible: false,
            formData: {},
            treeSelectData: [],
            isTop: true,
            expandId: 0,
            map: new Map(),
            normalizer(node) {
                return {
                    id: node.id,
                    label: node.deptName,
                    children: node.children,
                }
            },
            disable: true

        }
    },
    created() {
        this.searchPage();
    },
    methods: {
        /*
        * 查询所有
        */
        async searchPage() {
            let response = await findPage(this.searchParams);
            this.total = response.total;
            this.tableData = response.data;
        },
        /**
         * 获得一级部门
         */
        async getRootDeptList() {
            this.treeSelectData = await getChildrenById(0);
        },

        /**
         * 加载Table的数据
         */
        async loadTableData(tree, treeNode, resolve) {
            console.log(tree);
            this.expandId = tree.id;
            this.map.set(tree.id, {tree, treeNode, resolve})
            let response = await getChildrenById(tree.id)
            setTimeout(() => resolve(response), 222)
        },


        /**
         * 加载TreeSelect的数据
         */
        async loadTreeSelectData({action, parentNode, callback}) {
            console.log("loadTreeSelectData执行了");
            if (parentNode.hasChildren) {
                let response = await getChildrenById(parentNode.id)
                parentNode.children = response;
            } else {
                console.log("无孩子");
            }


        },


        /**
         * 添加数据
         * @param val
         */
        async addOrEdit() {
            this.formData.parentId = this.isTop ? 0 : this.formData.parentId;
            if (this.formData.id) {
                await updateEntity(this.formData);
            } else {
                await addEntity(this.formData);
            }
            this.refreshPage();
        },

        /**
         * 通过id查询
         * @param val
         */
        async findById() {
            let result = await findById(this.formData.id);
            this.formData = result.obj;
            this.treeSelectData = result.elements
            this.isTop = this.formData.parentId == 0;
        },
        rowClick(row) {
            this.disable = false;
            this.formData.id = row.id;
        },
        /**
         * 通过id删除
         */
        async deleteById() {
            await deleteById(this.formData.id);
            this.refreshPage();
        },

        //选择页数的时候 发生概念
        currentPageChange(page) {
            this.searchParams.currentPage = page;
            this.searchPage();
        },
        //时间框选择时间
        chooseTime() {
            this.searchParams.startTime = this.startDate[0];
            this.searchParams.endTime = this.startDate[1];
        },
        resetForm() {
            this.searchParams = {currentPage: 1, pageSize: 5}
            this.startDate = '';
        },
        /**
         * 展示批量删除的弹框
         */
        showBatchDeleteDialog() {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteById();
            })
        },

        /**
         * 刷新页面
         */
        refreshPage() {
            this.searchPage();
            if (this.expandId) {
                const {tree, treeNode, resolve} = this.map.get(this.expandId)
                this.$set(this.$refs.huige.store.states.lazyTreeNodeMap, this.expandId, [])
                this.loadTableData(tree, treeNode, resolve);
            }
            this.disable = true;
        }

    }

}

export default brand;
