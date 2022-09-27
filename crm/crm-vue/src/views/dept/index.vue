<template>
  <div class="brand-wrapper">

    <!--固定区域-->
    <div class="fixed-wrapper">
      <!--      查询表单 -->
      <div class="search-form">
        <el-form :inline="true" class="demo-form-inline" size="mini">
          <el-form-item label="部门名称">
            <el-input placeholder="请输入品牌名称" v-model="searchParams.deptName"></el-input>
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
          <el-form-item>
            <el-button type="primary" @click="searchPage">查询</el-button>
            <el-button type="warning" @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--    操作功能 -->
      <div class="crud-box">
        <el-button type="primary" size="mini" icon="el-icon-edit" @click="dialogVisible=true,formData={},getRootDeptList(),isTop=true">
          新建
        </el-button>
        <el-button type="success" size="mini" icon="el-icon-edit"
                   @click="dialogVisible=true,findById()" :disabled="disable">修改
        </el-button>
        <el-button type="danger" size="mini" icon="el-icon-delete"
                   @click="showBatchDeleteDialog" :disabled="disable">删除
        </el-button>
      </div>
    </div>
    <!--可滚动区域-->
    <el-scrollbar>
      <!--    表格数据-->
      <div class="data-box">
        <el-table
            :data="tableData"
            style="width: 100%"
            ref="huige"
            highlight-current-row
            lazy
            :load="loadTableData"
            @row-click="rowClick"
            row-key="id"
            :tree-props="{children:'children',hasChildren:'hasChildren'}"
        >
          <el-table-column
              align="center"
              prop="deptName"
              label="部门名称"
              width="180">
          </el-table-column>
          <el-table-column
              align="center"
              prop="deptDesc"
              label="部门描述"
              width="180">
          </el-table-column>

          <el-table-column
              align="center"
              prop="deptSort"
              label="部门排序">
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
    </el-scrollbar>
    <!--    弹框-->
    <el-dialog
        title="实体操作"
        :visible.sync="dialogVisible"
        width="33%"
    >
      <el-form ref="form" label-width="80px" size="small">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="部门名称">
              <el-input v-model="formData.deptName" placeholder="请输入部门名称"></el-input>
            </el-form-item>
            <el-form-item label="部门排序">
              <el-input-number v-model="formData.deptSort" placeholder="请勾选部门排序" controls-position="right" style="width: 100%"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门描述">
              <el-input v-model="formData.deptDesc" placeholder="请勾选部门描述"></el-input>
            </el-form-item>
            <el-form-item label="是否顶级">
              <el-radio v-model="isTop" :label="true">是</el-radio>
              <el-radio v-model="isTop" :label="false">否</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="!isTop">
          <el-form-item label="选择父级">
            <treeselect
                v-model="formData.parentId"
                :options="treeSelectData"
                :load-options="loadTreeSelectData"
                :normalizer="normalizer"
                placeholder="请选择父级"/>
          </el-form-item>
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
import brand from './index.js'

export default brand;
</script>

<style src="./index.scss" lang="scss"></style>
