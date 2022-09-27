<template>
  <div class="sell-main">

    <div class="sell-breadCrumbs">

    </div>

    <div class="sell-query">

      <el-form :inline="true" :model="queryForm" class="demo-form-inline">

        <el-form-item label="售后出库订单编号">
          <el-input v-model="queryForm"></el-input>
        </el-form-item>

        <el-form-item label="售后出库订单类型">
          <el-select v-model="queryForm" placeholder="请选择">
            <el-option v-for="item in orderType" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="对应出库订单编号">
          <el-select v-model="queryForm" placeholder="请选择">
            <el-option v-for="item in payment" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>


        <br />

        <el-form-item label="售后出库订单日期">

          <el-date-picker v-model="queryForm" type="daterange" align="right" unlink-panels range-separator="至"
            start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions">
          </el-date-picker>

        </el-form-item>

        <el-form-item label="售后出库订单状态">
          <el-select v-model="queryForm" placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="warning" @click="reset">重置</el-button>
          <el-button type="primary" @click="query">查询</el-button>
        </el-form-item>
      </el-form>

    </div>

    <div class="sell-table">

      <el-table :data="tableData" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="" label="售后出库订单编号" width="150">
        </el-table-column>
        <el-table-column prop="" label="售后出库订单类型" width="120">
        </el-table-column>
        <el-table-column prop="" label="对应出库订单编号" width="120">
        </el-table-column>
        <el-table-column prop="" label="售后出库订单状态" width="120">
        </el-table-column>
        <el-table-column prop="" label="订单日期" width="300  ">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="show(scope.row)" type="text" size="small">查看</el-button>
            <el-button type="text" size="small" @click="createForm(scope.row)" v-if="true">生成入库单</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="sell-page">

      <el-pagination @size-change="pageSizeChange" @current-change="pageCurrentChange" :current-page="queryForm"
        :page-sizes="[10, 15, 20]" :page-size="queryForm" layout="total, sizes, prev, pager, next, jumper"
        :total="queryForm">
      </el-pagination>

    </div>



    <el-dialog title="创建出库单" :visible.sync="formFlog" width="1350px">


      <el-descriptions class="margin-top" title="售后出库订单信息" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            售后出库单编号
          </template>

        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            销售出库类型
          </template>

        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            库管员
          </template>

        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            售后出库时间
          </template>

        </el-descriptions-item>
      </el-descriptions>



      <el-table :data="goodTable" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="" label="售后商品编号" width="150">
        </el-table-column>
        <el-table-column prop="" label="售后商品名称" width="120">
        </el-table-column>
        <el-table-column prop="" label="售后商品型号" width="120">
        </el-table-column>
        <el-table-column prop="" label="售后商品颜色" width="120">
        </el-table-column>
        <el-table-column prop="" label="返厂串码" width="150">
        </el-table-column>
        <el-table-column prop="" label="出库串码" width="120">
          <el-input  />
        </el-table-column>
      </el-table>

      <el-form>
        <el-form-item label="备注">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4 }" placeholder="请输入内容" v-model="form">
          </el-input>
        </el-form-item>
      </el-form>




      <div slot="footer" class="dialog-footer">
        <el-button type="warning" @click="formClear">取 消</el-button>
        <el-button type="primary" @click="formSubmit">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import sell from './index.js'

export default sell
</script>

<style scoped>
</style>
