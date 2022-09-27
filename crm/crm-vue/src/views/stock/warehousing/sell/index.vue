<template>
  <div class="sell-main">
    <div class="sell-breadCrumbs">

    </div>
    <div class="sell-query">

      <el-form :inline="true" :model="queryForm" class="demo-form-inline">

        <el-form-item label="退货单编号">
          <el-input v-model="queryForm.orderCode"></el-input>
        </el-form-item>


        <el-form-item label="订单日期">

          <el-date-picker
              v-model="pickerOptions.dates"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions"
              @change="setTime()">
          </el-date-picker>

        </el-form-item>

        <el-form-item label="退货单状态">
          <el-select v-model="queryForm.states" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="warning" @click="reset">重置</el-button>
          <el-button type="primary" @click="selectQuery">查询</el-button>
        </el-form-item>
      </el-form>

    </div>
    <!-- 显示查询到的退货订单 -->
    <div class="sell-table">

      <el-table
          :data="tableData"
          border
          style="width: 100%">
        <el-table-column
            label="序号"
            type="index"
            width="80">
        </el-table-column>
        <el-table-column
            prop="returnGoodOrderCode"
            label="退货单编号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="states"
            label="退货单状态"
            width="120">
        </el-table-column>
        <el-table-column
            prop="returnGoodOrderDate"
            label="订单日期"
            width="300  ">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="200">
          <template slot-scope="scope">
            <el-button
                @click="show(scope.row)"
                type="text"
                size="small">
              查看
            </el-button>
            <el-button type="text"
                       size="small"
                       @click="createForm(scope.row)"
                       v-if="scope.row.states=='拒收'">
              生成入库单
            </el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <!-- 分页控件 -->
    <div class="sell-page">

      <el-pagination
          @size-change="pageSizeChange"
          @current-change="pageCurrentChange"
          :current-page="queryForm.currentPage"
          :page-sizes="[5,10,15,20]"
          :page-size="queryForm.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="dataTotal">
      </el-pagination>

    </div>

    <!-- 查看订单详情 -->
    <el-dialog title="退货订单详情" :visible.sync="showFlag">
      <el-descriptions class="margin-top" title="退货单信息" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            销售退货单编号
          </template>
          {{ returnGoodOrder.returnGoodOrderCode }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            销售退货单状态
          </template>
          {{ returnGoodOrder.states }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            退货订单日期
          </template>
          {{ returnGoodOrder.returnGoodOrderDate }}
        </el-descriptions-item>
      </el-descriptions>

      <el-table :data="goodTable" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" width="120">
        </el-table-column>
        <el-table-column prop="goodType" label="商品型号" width="120">
        </el-table-column>
        <el-table-column prop="goodColor" label="商品颜色" width="120">
        </el-table-column>
      </el-table>


    </el-dialog>

    <!-- 创建入库单弹窗 -->
    <el-dialog title="创建入库单" :visible.sync="formFlog">

      <el-descriptions class="margin-top" title="退货单信息" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            销售退货单编号
          </template>
          {{ returnGoodOrder.returnGoodOrderCode }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            销售退货单状态
          </template>
          {{ returnGoodOrder.states }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            退货订单日期
          </template>
          {{ returnGoodOrder.returnGoodOrderDate }}
        </el-descriptions-item>
      </el-descriptions>


      <el-form :model="form">
        <el-form-item label="仓库类型">
          <el-select v-model="form.storageId" placeholder="请选择">
            <el-option
                v-for="item in stockType"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-table :data="goodTable" border style="width: 100%">
          <el-table-column label="序号" type="index" width="80">
          </el-table-column>
          <el-table-column prop="goodName" label="商品名称" width="120">
          </el-table-column>
          <el-table-column prop="goodType" label="商品型号" width="120">
          </el-table-column>
          <el-table-column prop="goodColor" label="商品颜色" width="120">
          </el-table-column>
          <el-table-column label="输入商品串码" width="200">
            <template v-slot="data">
              <input v-model="data.row.goodSerial" />
            </template>
          </el-table-column>
        </el-table>

        <el-form-item label="备注">
          <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              placeholder="请输入内容"
              v-model="form.remark">
          </el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="formClear">取 消</el-button>
        <el-button type="primary" @click="formSubmit">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import sell from './index'

export default sell
</script>

<style scoped>


</style>
