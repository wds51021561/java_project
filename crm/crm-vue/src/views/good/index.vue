<template>

    <div class="brand-wrapper">
        <!--      查询表单 -->
        <div class="search-form">
            <el-form :inline="true" class="demo-form-inline" size="mini">
                <el-form-item label="商品名称">
                    <el-input placeholder="请输入商品名称" v-model="searchParams.goodName"></el-input>
                </el-form-item>

                <el-form-item label="商品描述">
                    <el-input placeholder="请输入商品描述" v-model="searchParams.goodDesc"></el-input>
                </el-form-item>

                <el-form-item label="选择品牌">
                    <!--动态渲染  选择品牌-->
                              <el-select  placeholder="选择品牌" v-model="searchParams.brandId">
                                  <el-option v-for="(item,index) in brandList" :key="index" :label="item.brandName"
                                             :value="item.id"></el-option>
                              </el-select>
                </el-form-item>

                <el-form-item label="开始时间">
                    <el-date-picker
                            style="width: 420px"
                            type="datetimerange"
                            :picker-options="dateOptions.pickerOptions"
                            v-model="startDate"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            range-separator="-"
                            @change="chooseTime"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            align="left">
                    </el-date-picker>
                </el-form-item>
                <!--        <el-form-item label="活动区域">-->
                <!--          <el-select  placeholder="活动区域">-->
                <!--            <el-option label="区域一" value="shanghai"></el-option>-->
                <!--            <el-option label="区域二" value="beijing"></el-option>-->
                <!--          </el-select>-->
                <!--        </el-form-item>-->
                <el-form-item>
                    <el-button type="primary" @click="searchPage">查询</el-button>
                    <el-button type="warning" @click="resetForm">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <!--    操作功能 -->
        <div class="crud-box">
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="dialogVisible=true,formData={},selectIds=[]">新建</el-button>
            <el-button type="success" size="mini" icon="el-icon-edit" :disabled="batchIds.length!=1" @click="dialogVisible=true">修改</el-button>
            <el-button type="danger" size="mini" icon="el-icon-delete" :disabled="batchIds.length<=0"
                       @click="showBatchDeleteDialog">删除
            </el-button>
        </div>


        <!--    表格数据-->
        <!--:tree-props="{children: 'children'
            row-key="id"
         树状结构 children-->
        <div class="data-box">
            <el-table
                    :data="tableData"
                    style="width: 100%"
                    @selection-change="selectChange">
                <el-table-column
                        align="center"
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="goodName"
                        label="商品名称"
                        width="180">
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="goodDesc"
                        label="商品描述"
                        width="180">
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="goodImg"
                        label="商品图片">
                    <template v-slot="obj">
                        <img :src="obj.row.goodImg"  width="35px" alt="">
                    </template>
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="brandName"
                        label="商品品牌">
                </el-table-column>

                <el-table-column

                        label="操作">
                    <template v-slot="hanjie">
                        <el-button type="primary" size="mini" icon="el-icon-edit" @click="dialogVisible=true,formData.id=hanjie.row.id,findById()"></el-button>
                        <el-popconfirm
                                confirm-button-text='确定'
                                cancel-button-text='取消'
                                icon="el-icon-info"
                                icon-color="red"
                                @confirm="deleteById"
                                placement="top"
                                title="是否要删除本条记录？"
                        >
                            <el-button slot="reference" type="danger" size="mini" @click="formData.id=hanjie.row.id"
                                       icon="el-icon-delete"></el-button>
                        </el-popconfirm>
                    </template>


                </el-table-column>
            </el-table>

        </div>


        <!--    分页-->
        <div class="page-box">
            <el-pagination
                    background
                    layout="prev, pager, next"
                    :total="total"
                    :page-size="searchParams.pageSize"
                    :current-page="searchParams.currentPage"
                    @current-change="currentPageChange"
            >
            </el-pagination>

        </div>





        <!--弹框-->
        <el-dialog
                title="实体操作"
                :visible.sync="dialogVisible"
                width="38%"
        >
            <el-form ref="form" label-width="80px" :model="formData" size="small">
                <el-tabs v-model="activeName">
                    <!--element ui 的 Tabs 标签页 -->
                    <el-tab-pane label="基本信息" name="first">
                        <el-form-item label="商品名称">
                            <el-input v-model="formData.goodName"></el-input>
                        </el-form-item>
                        <el-form-item label="商品描述">
                            <el-input v-model="formData.goodDesc"></el-input>
                        </el-form-item>
                        <el-form-item label="商品价格">
                            <el-input v-model="formData.goodPrice"></el-input>
                        </el-form-item>
                    </el-tab-pane>

                    <el-tab-pane label="商品分类" name="second">
                        <!--勾选对应品牌-->
                        <el-form-item label="选择品牌">
                            <!--动态渲染  选择品牌-->
                            <el-select  placeholder="选择品牌" v-model="formData.brandId" style="width: 100%">
                                <el-option v-for="(item,index) in brandList" :key="index" :label="item.brandName"
                                           :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>


                        <!--勾选对应分类-->
                            <!--动态渲染  选择分类-->
                            <!--复选框  级联选择器-->
                            <el-form-item label="选择父级" v-if="categoryLevel!=1">

                                <el-cascader
                                        :props="prop"
                                        v-model="selectIds"
                                        placeholder="请选择父级分类"
                                        @change="selectCascader"
                                        :options="options"
                                ></el-cascader>

                            </el-form-item>




                    </el-tab-pane>

                    <el-tab-pane label="商品图片" name="third">

                        <el-row :gutter="10">
                            <el-col :span="16">
                                <img ref="goodImg" width="110px" height="110px" style="boorder:1px solid #ccc" alt="">
                            </el-col>
                            <el-col :span="18">

                            </el-col>

                        </el-row>

                        <el-upload
                                class="upload-demo"
                                drag
                                action="https://jsonplaceholder.typicode.com/posts/"
                                :show-file-list="false"
                                :http-request="getImgStr"
                                >
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>

                        </el-upload>
                    </el-tab-pane>

                    <el-tab-pane label="商品详情" name="fourth">
                        <el-form-item label="商品详情">
                            <el-input type="textarea" rows="8"  v-model="formData.goodContent"></el-input>
                        </el-form-item>
                    </el-tab-pane>
                </el-tabs>





            </el-form>


            <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="dialogVisible = false,addOrEdit()">确 定</el-button>
  </span>
        </el-dialog>
    </div>




</template>

<script>
import brand from "./index";

export  default  brand;
</script>

<!--src 引用css样式-->
<style src="./index.scss"  lang="scss"></style>