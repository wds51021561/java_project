<template>

    <div class="brand-wrapper">
        <!--      查询表单 -->
        <div class="search-form">
            <el-form :inline="true" class="demo-form-inline" size="mini">

                <el-form-item label="员工名称">
                    <el-input placeholder="请输入员工名称" v-model="searchParams.adminName"></el-input>
                </el-form-item>
                <el-form-item label="员工手机">
                    <el-input placeholder="请输入员工手机号" v-model="searchParams.adminPhone"></el-input>
                </el-form-item>

                <el-form-item label="员工部门">
                    <!--  :load-options="loadTreeSelectData" 是Treeselect的懒加载-->
                    <treeselect v-model="searchParams.deptId"
                                :options="deptList"
                                :load-options="loadTreeSelectData"
                                :normalizer="normalizer"
                                placeholder="请选择部门"
                                noChildrenText="暂无更多数据"
                                style="width: 180px"/>
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
        <!--v-has-perm="['admin:add']" 自定义指令 在main.js里面-->
        <div class="crud-box">
            <el-button type="primary" size="mini" icon="el-icon-edit" v-has-perm="['admin:add']"
                       @click="dialogVisible=true,formData={},resetFormData()">新建
            </el-button>
            <el-button type="success" size="mini" icon="el-icon-edit" :disabled="batchIds.length!=1"
                        @click="dialogVisible=true">修改
            </el-button>
            <el-button type="danger" size="mini" icon="el-icon-delete" :disabled="batchIds.length<=0"
                      @click="showBatchDeleteDialog">删除
            </el-button>

            <el-link icon="el-icon-download" class="el-button el-button--warning el-button--mini" href="http://localhost:8088/admin/export">导出</el-link>

        </div>
        <!--    表格数据-->
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
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="员工Id">
                                <span>{{ props.row.id }}</span>
                            </el-form-item>
                            <el-form-item label="员工名称">
                                <span>{{ props.row.adminName }}</span>
                            </el-form-item>
                            <el-form-item label="员工昵称">
                                <span>{{ props.row.nickName }}</span>
                            </el-form-item>
                            <el-form-item label="员工性别">
                                <span>{{ props.row.sex }}</span>
                            </el-form-item>
                            <el-form-item label="员工电话">
                                <span>{{ props.row.adminPhone }}</span>
                            </el-form-item>
                            <el-form-item label="员工邮箱">
                                <span>{{ props.row.adminEmail }}</span>
                            </el-form-item>
                            <el-form-item label="所属部门">
                                <span>{{ props.row.deptName }}</span>
                            </el-form-item>


                            <el-form-item label="是否可用">
                                <el-switch
                                        v-model="props.row.isEnable"
                                        active-color="#13ce66"
                                        inactive-color="#ff4949">
                                </el-switch>
                            </el-form-item>
                            <el-form-item label="是否超管">

                                <el-switch
                                        disabled
                                        v-model="props.row.isAdmin"
                                        active-color="#13ce66"
                                        inactive-color="#ff4949">
                                </el-switch>
                            </el-form-item>

                            <el-form-item label="员工头像">
                                <el-avatar size="30" :src="props.row.adminAvatar"></el-avatar>
                            </el-form-item>

                            <el-form-item label="员工地址" style="width: 100%">
                                <span>{{ props.row.adminAddress }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>

                <el-table-column
                        align="center"
                        prop="adminName"
                        label="员工名称"
                        width="180">
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="nickName"
                        label="员工昵称"
                        width="180">
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="deptName"
                        label="所属部门"
                        width="180">
                </el-table-column>

                <el-table-column
                        align="center"
                        prop="deptName"
                        label="是否可用"
                        width="180">
                    <template v-slot="obj">
                        <el-switch
                                disabled
                                v-model="obj.row.isEnable"
                                active-color="#13ce66"
                                inactive-color="#ff4949">
                        </el-switch>
                    </template>
                </el-table-column>

                <el-table-column
                        align="center"
                        prop="adminAvatar"
                        label="员工头像"
                        width="180">
                    <template v-slot="obj">
                        <el-avatar size="30" :src="obj.row.adminAvatar"></el-avatar>
                    </template>
                </el-table-column>

                <el-table-column
                        align="center"
                        prop="adminAddress"
                        label="员工地址"
                        show-overflow-tooltip
                        width="180">
                </el-table-column>


                <el-table-column
                        align="center"
                        label="操作">
                    <template v-slot="hanjie">
                        <el-button type="primary" size="mini" icon="el-icon-edit"  v-has-perm="['admin:edit']"
                                   @click="dialogVisible=true,formData.id=hanjie.row.id,findById()"></el-button>
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
                                       v-has-perm="['admin:delete']"      icon="el-icon-delete"></el-button>
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
                    <el-col :span="12">
                        <el-form-item label="员工名称">
                            <el-input v-model="formData.adminName"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="员工昵称">
                            <el-input v-model="formData.nickName"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="手机号码">
                            <el-input v-model="formData.adminPhone"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="邮箱地址">
                            <el-input v-model="formData.adminEmail"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="员工性别">
                            <el-radio v-model="formData.gender" :label="0">男</el-radio>
                            <el-radio v-model="formData.gender" :label="1">女</el-radio>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否可用">
                            <el-switch
                                    v-model="formData.isEnable"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="员工部门">
                            <treeselect v-model="formData.deptId"
                                        :options="deptList"
                                        :load-options="loadTreeSelectData"
                                        noChildrenText="暂无更多数据"
                                        :normalizer="normalizer"
                                        placeholder="请选择部门"
                            />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="选择角色">
                            <el-select v-model="formData.roleIds" multiple placeholder="请选择" style="width: 100%">
                                <el-option
                                        v-for="item in roleList"
                                        :key="item.id"
                                        :label="item.roleName"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-form-item label="员工部门">
                        <el-select v-model="province" placeholder="请选择省" style="width: 33%" @change="getAllCity">
                            <el-option
                                    v-for="item in provinceList"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.name">
                            </el-option>
                        </el-select>
                        <el-select v-model="city" placeholder="请选择市" style="width: 33%;margin: 0px 2px"
                                   @change="getAllArea">
                            <el-option
                                    v-for="item in cityList"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.name">
                            </el-option>
                        </el-select>
                        <el-select v-model="area" placeholder="请选择区" style="width: 33%">
                            <el-option
                                    v-for="item in areaList"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="详细地址">
                            <el-input type="textarea" v-model="formData.adminAddress"/>
                        </el-form-item>
                    </el-col>

                </el-row>

                <el-row :gutter="20">
                    <el-col :span="8">
                        <el-form-item label="上传图片">
                            <el-upload
                                    class="upload-demo"
                                    action="http://localhost:8088/common/upload"
                                    :show-file-list="false"
                                    :on-success="uploadSuccess">
                                <el-button size="small" type="primary">点击上传</el-button>
                            </el-upload>
                        </el-form-item>
                    </el-col>

                    <el-col :span="16">
                        <img :src="formData.adminAvatar" v-if="formData.adminAvatar" alt="" width="70px" height="70px">
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

    export default brand;
</script>

<!--src 引用css样式-->
<style src="./index.scss" lang="scss"></style>