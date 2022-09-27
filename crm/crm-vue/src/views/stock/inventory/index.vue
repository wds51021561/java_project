<template>
  <div class="inventory-main">

    <div class="inventory-breadCrumbs">

    </div>

    <div class="query">

      <el-form :inline="true" :model="queryForm" class="demo-form-inline">

        <el-form-item label="盘点员">
          <el-input v-model="queryForm.storageStaff"></el-input>
        </el-form-item>


        <br />

        <el-form-item label="仓库名">
          <el-select v-model="queryForm.storageId" placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="盘点日期">

          <el-date-picker v-model="pickerOptions.dates" type="daterange" align="right" unlink-panels range-separator="至"
            start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions" @change="setTime">
          </el-date-picker>

        </el-form-item>


        <el-form-item>
          <el-button type="warning" @click="reset">重置</el-button>
          <el-button type="primary" @click="query">查询</el-button>
          <el-button type="warning" @click="add">新增</el-button>
        </el-form-item>
      </el-form>

    </div>

    <div class="table">

      <el-table :data="tableData" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="inventoryCode" label="盘点编码" width="150">
        </el-table-column>
        <el-table-column prop="storageStaff" label="盘点员" width="150">
        </el-table-column>
        <el-table-column prop="createTime" label="盘点日期" width="120">
        </el-table-column>
        <el-table-column prop="storage" label="仓库名" width="120">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="show(scope.row)" type="text" size="small">查看</el-button>
            <el-button @click="down(scope.row)" type="text" size="small">导出</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="page">

      <el-pagination
          @size-change="pageSizeChange"
          @current-change="pageCurrentChange"
          :current-page="queryForm.currentPage"
          :page-sizes="[5,10, 15, 20]"
          :page-size="queryForm.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
        :total="tableTotal">
      </el-pagination>

    </div>


    <el-dialog title="创建盘点记录" :visible.sync="addFlog" width="1350px">

      <el-form :inline="true" :model="addFrom" class="demo-form-inline">

        <el-form-item label="盘点员">
          <el-input v-model="addFrom.staff"></el-input>
        </el-form-item>


        <br />

        <el-form-item label="仓库名">
          <el-select v-model="addFrom.storageId" placeholder="请选择" @change="find">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="盘点日期">
            <el-date-picker
                v-model="addFrom.createTime"
                type="date"
                placeholder="选择日期">
            </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="warning" @click="save">保存盘点记录</el-button>
<!--          <el-button type="warning" @click="find">查询库存情况</el-button>-->
        </el-form-item>
        <br />

        <el-form-item label="盘点备注">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4 }" placeholder="请输入内容" v-model="addFrom.remark">
          </el-input>
        </el-form-item>

      </el-form>

      <el-table :data="addData" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="goodSerial" label="库存串号" width="150">
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" width="120">
        </el-table-column>
        <el-table-column prop="type" label="商品型号" width="120">
        </el-table-column>
        <el-table-column prop="color" label="颜色" width="120">
        </el-table-column>
        <el-table-column prop="predict" label="库存盘点" width="120">
        </el-table-column>
        <el-table-column prop="actual" label="实盘数" width="120">
          <template v-slot="good">
            <el-input v-model="good.row.actual"></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="difference" label="差异数" width="120">
          <template v-slot="good">
            {{good.row.actual-good.row.predict}}
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="原因" width="120">
          <template v-slot="good">
            <el-input v-model="good.row.remark"></el-input>
          </template>
        </el-table-column>
      </el-table>


    </el-dialog>

    <el-dialog title="查看盘点记录" :visible.sync="showFlag" width="80%">

      <el-table :data="addData" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="goodSerial" label="库存串号" width="150">
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" width="120">
        </el-table-column>
        <el-table-column prop="type" label="商品型号" width="120">
        </el-table-column>
        <el-table-column prop="color" label="颜色" width="120">
        </el-table-column>
        <el-table-column prop="predict" label="库存盘点" width="120">
        </el-table-column>
        <el-table-column prop="actual" label="实盘数" width="120">
        </el-table-column>
        <el-table-column prop="difference" label="差异数" width="120">
        </el-table-column>
        <el-table-column prop="remark" label="原因" width="120">
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
import inventory from './index.js'

export default inventory
</script>

<style scoped>
</style>
