<template>
  <div class="sell-main">

    <div class="sell-breadCrumbs">

    </div>

    <div class="sell-query">

      <el-form :inline="true" :model="queryForm.orderCode" class="demo-form-inline">

        <el-form-item label="销售出库订单号">
          <el-input v-model="queryForm.orderCode"></el-input>
        </el-form-item>

        <el-form-item label="订单类型">
          <el-select v-model="queryForm.orderType" placeholder="请选择">
            <el-option v-for="item in orderType" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="支付方式">
          <el-select v-model="queryForm.payType" placeholder="请选择">
            <el-option v-for="item in payment" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>


        <br />

        <el-form-item label="订单日期">

          <el-date-picker v-model="pickerOptions.dates"
                          type="daterange" align="right"
                          @change = "selectTime"
                          unlink-panels range-separator="至"
            start-placeholder="开始日期"
                          end-placeholder="结束日期"
                          :picker-options="pickerOptions">
          </el-date-picker>

        </el-form-item>

        <el-form-item label="订单状态">
          <el-select v-model="queryForm.orderStates" placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="warning" @click="reset">重 置</el-button>
          <el-button type="primary" @click="selectQuery">查 询</el-button>
        </el-form-item>
      </el-form>

    </div>

    <div class="sell-table">

      <el-table :data="tableData" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="orderCode" label="销售出库订单号" width="150">
        </el-table-column>
        <el-table-column prop="orderType" label="订单类型" width="120">
        </el-table-column>
        <el-table-column prop="payType" label="支付方式" width="120">
        </el-table-column>
        <el-table-column prop="orderStates" label="订单状态" width="300">
        </el-table-column>
        <el-table-column prop="createTime" label="订单日期" width="300  ">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="300">
          <template slot-scope="scope">
            <el-button @click="show(scope.row)" type="text" size="small">查看</el-button>
            <el-button type="text" size="small" @click="createForm(scope.row)" v-if="scope.row.orderStates == '待出库' ">生成入库单</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="sell-page">

      <el-pagination @size-change="pageSizeChange" @current-change="pageCurrentChange" :current-page="queryForm.currentPage"
        :page-sizes="[5,10, 15, 20]" :page-size="queryForm.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="dataTotal">
      </el-pagination>

    </div>

    <el-dialog title="订单详情" :visible.sync="showFlag" width="80%">

      <el-descriptions class="margin-top" title="销售订单信息" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            销售出库单编号
          </template>
          {{selectOrder.orderCode}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            订单类型
          </template>
          {{selectOrder.orderType}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            销售出单时间
          </template>
          {{selectOrder.createTime}}
        </el-descriptions-item>
      </el-descriptions>
      <br/>
      <br/>
      <el-table :data="goodTable" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="goodType" label="商品型号" width="150">
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" width="120">
        </el-table-column>
        <el-table-column prop="goodColor" label="商品颜色" width="120">
        </el-table-column>
      </el-table>


    </el-dialog>


    <el-dialog title="创建出库单" :visible.sync="formFlog" width="80%">


      <el-descriptions class="margin-top" title="销售订单信息" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            销售出库单编号
          </template>
          {{selectOrder.orderCode}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            订单类型
          </template>
          {{selectOrder.orderType}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            销售出单时间
          </template>
          {{selectOrder.createTime}}
        </el-descriptions-item>
      </el-descriptions>



      <el-form>
        <el-select v-model="form.storageId" placeholder="请选择">
          <el-option v-for="item in stockType" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form>

      <el-table :data="goodTable" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="goodType" label="商品型号" width="150">
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" width="120">
        </el-table-column>
        <el-table-column prop="goodColor" label="商品颜色" width="120">
        </el-table-column>
        <el-table-column label="输入商品串码" width="200">
          <template v-slot="data">
            <input v-model="data.row.goodSerial" />
          </template>
        </el-table-column>
      </el-table>

      <el-form>
        <el-form-item label="备注">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4 }" placeholder="请输入内容" v-model="form.remark">
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
