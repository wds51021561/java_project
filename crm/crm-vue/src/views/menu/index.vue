<template>

    <div class="brand-wrapper">
        <!--      查询表单 -->
        <div class="search-form">
            <el-form :inline="true" class="demo-form-inline" size="mini">
                <el-form-item label="权限名称">
                    <el-input placeholder="请输入权限名称" v-model="searchParams.menuTitle"></el-input>
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
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="dialogVisible=true,resetFromData()">新建</el-button>
            <el-button type="success" size="mini" icon="el-icon-edit"  @click="dialogVisible=true">修改</el-button>
            <el-button type="danger" size="mini" icon="el-icon-delete"
                       @click="showBatchDeleteDialog">删除
            </el-button>
        </div>


        <!--    表格数据-->
        <div class="data-box">
            <el-table
                    :data="tableData"
                    row-key="id"
                    style="width: 100%"
                    :tree-props="{children: 'children'}"
            >
                <el-table-column
                        prop="menuTitle"
                        label="菜单名称"
                >
                </el-table-column>
                <el-table-column
                        prop="menuIcon"
                        label="菜单图标"
                >
                    <template slot-scope="obj">
                        <e-icon :icon-name="obj.row.menuIcon"/>
                    </template>

                </el-table-column>
                <el-table-column
                        prop="menuPath"
                        label="路由地址"
                >
                </el-table-column>
                <el-table-column
                        prop="menuSort"
                        label="菜单排序"
                >
                </el-table-column>

                <el-table-column
                        prop="isHidden"
                        label="是否隐藏"
                >
                    <template v-slot="obj">
                        <el-switch
                                v-model="obj.row.isHidden"
                                active-color="#13ce66"
                                disabled
                                inactive-color="#ff4949">
                        </el-switch>
                    </template>
                </el-table-column>

                <el-table-column
                        prop="permission"
                        label="权限标识"
                >
                </el-table-column>
                <el-table-column
                        prop="menuType"
                        label="菜单类型"
                >
                    <template v-slot="obj">
                        <el-tag v-if="obj.row.menuType==0" type="primary">目录</el-tag>
                        <el-tag v-if="obj.row.menuType==1" type="success">菜单</el-tag>
                        <el-tag v-if="obj.row.menuType==2" type="danger">按钮</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        align="center"
                        label="操作">
                    <template v-slot="obj">
                        <el-button type="primary" size="mini" icon="el-icon-edit"
                                   @click="dialogVisible=true,formData.id=obj.row.id,findById()"
                                   style="margin-right: 5px"></el-button>
                        <el-popconfirm
                                confirm-button-text='确定'
                                cancel-button-text='取消'
                                icon="el-icon-info"
                                icon-color="red"
                                @confirm="deleteById"
                                placement="top"
                                title="是否要删除本条记录？"
                        >
                            <el-button slot="reference" type="danger" size="mini" @click="formData.id=obj.row.id"
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
                width="33%"
        >
            <el-form ref="form" label-width="80px" size="small">

                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="上级菜单">
                            <treeselect v-model="formData.parentId"
                                        :options="menuList"
                                        noChildrenText="暂无更多数据"
                                        :normalizer="normalizer"
                                        placeholder="请选择上级"/>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="菜单分类">
                            <el-radio-group v-model="formData.menuType" size="small">
                                <el-radio-button :label="0">目录</el-radio-button>
                                <el-radio-button :label="1">菜单</el-radio-button>
                                <el-radio-button :label="2">按钮</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-row :gutter="20" v-if="formData.menuType!=2">
                    <el-col :span="24">
                        <el-form-item label="菜单图标">
                            <e-icon-picker v-model="formData.menuIcon"/>

                        </el-form-item>
                  <!--      <el-form-item label="菜单图标">
                            <el-input v-model="formData.menuIcon"></el-input>
                        </el-form-item>-->
                    </el-col>

                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="菜单名称">
                            <el-input v-model="formData.menuTitle"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="菜单排序">
                            <el-input-number v-model="formData.menuSort" controls-position="right"></el-input-number>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20" v-if="formData.menuType!=2">
                    <el-col :span="12">
                        <el-form-item label="路由地址">
                            <el-input v-model="formData.menuPath"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否隐藏">
                            <el-switch
                                    v-model="formData.isHidden"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </el-form-item>

                    </el-col>
                </el-row>
                <el-row :gutter="20" v-if="formData.menuType!=0">
                    <el-col :span="12">
                        <el-form-item label="权限标识">
                            <el-input v-model="formData.permission"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" v-if="formData.menuType!=2">
                        <el-form-item label="组件地址">
                            <el-input v-model="formData.component"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>






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