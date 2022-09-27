<template>
  <div class="audit-main">
    <div class="audit-breadCrumbs"></div>
    <!-- 分页查询 -->
    <div class="audit-query">
      <div class="audit-states">
        <el-button type="primary" size="medium" @click="queryStates(0)"
          >待审核</el-button
        >
        <el-button type="primary" size="medium" @click="queryStates(1)"
          >审核通过</el-button
        >
        <el-button type="primary" size="medium" @click="queryStates(2)"
          >审核驳回</el-button
        >
      </div>

      <el-form :inline="true" :model="queryForm" class="demo-form-inline">
        <el-form-item label="库单编号">
          <el-input v-model="queryForm.storageFormCode"></el-input>
        </el-form-item>

        <el-form-item label="库单类型">
          <el-select v-model="queryForm.storageFormType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="创建库单日期">
          <el-date-picker
            v-model="pickerOptions.dates"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions"
            @change="setTime"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="warning" @click="reset">重置</el-button>
          <el-button type="primary" @click="selectQuery">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 显示查询的库存单 -->
    <div class="audit-table">
      <el-table :data="tableData" border style="width: 100%; align-content: center">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="storageCode" label="库单编号" width="150">
        </el-table-column>
        <el-table-column prop="storageFormType" label="库单类型" width="120">
        </el-table-column>
        <el-table-column prop="storageFormStates" label="库单状态" width="120">
        </el-table-column>
        <el-table-column prop="storage" label="仓库类别" width="120">
        </el-table-column>
        <el-table-column prop="storageStaff" label="仓库管理员" width="120">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="300">
          <template v-slot="scope">
            {{scope.row.createTime}}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <el-button @click="showForm(scope.row)" type="text" size="small"
              >查看审核流程</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="createForm(scope.row)"
              v-if="(scope.row.storageFormStates!='已入库'&&scope.row.storageFormStates!='已出库')"
              >审核</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页控件 -->
    <div class="audit-page">
      <el-pagination
        @size-change="pageSizeChange"
        @current-change="pageCurrentChange"
        :current-page="queryForm.currentPage"
        :page-sizes="[5,10, 15, 20]"
        :page-size="queryForm.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataTotal"
      >
      </el-pagination>
    </div>

    <!-- 创建审核记录 -->
    <el-dialog
      title="创建审核记录"
      :visible.sync="createFormFolg"
      width="80%"
    >
      <!-- 显示库存单信息 -->
      <el-descriptions
        class="margin-top"
        title="入库单信息："
        :column="4"
        border
      >
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            入库单编号
          </template>
          {{ selectStorageForm.storageCode }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            入库单类型
          </template>
          {{ selectStorageForm.storageFormType }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            入库状态
          </template>
          {{ selectStorageForm.storageFormStates }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            仓库类别
          </template>
          {{ selectStorageForm.storage }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            入库时间
          </template>
          {{ selectStorageForm.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            入库库管
          </template>
          {{ selectStorageForm.storageStaff }}
        </el-descriptions-item>
      </el-descriptions>

      <!-- 显示出入库商品信息 -->
      <el-table :data="goodTable" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" width="120">
        </el-table-column>
        <el-table-column prop="type" label="商品型号" width="120">
        </el-table-column>
        <el-table-column prop="color" label="商品颜色" width="120">
        </el-table-column>
        <el-table-column prop="goodSerial" label="商品串码" width="120">
        </el-table-column>
      </el-table>

      <el-table :data="auditTable" border style="width: 100%">
        <el-table-column label="审核等级" prop="auditLevel"></el-table-column>
        <el-table-column label="审核状态" v-slot="data">
          {{data.row.auditState==0?"不通过":"通过"}}
        </el-table-column>
        <el-table-column label="审核人" prop="auditor"></el-table-column>
        <el-table-column label="审核意见" prop="remark"></el-table-column>
        <el-table-column label="审核时间" prop="modifyTime"></el-table-column>
      </el-table>

      <!-- 提交审核结果 -->
      <el-form>
        <el-form-item label="审核意见">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="请输入内容"
            v-model="form.remark"
          >
          </el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="warning" @click="formClear">取 消</el-button>
        <el-button type="warning" @click="formSubmit(0)">驳 回</el-button>
        <el-button type="primary" @click="formSubmit(1)">同 意</el-button>
      </div>
    </el-dialog>

    <el-dialog title="审核记录" :visible.sync="showFormFolg" width="80%">
      <el-table :data="auditTable" border style="width: 100%">
        <el-table-column label="审核等级" prop="auditLevel"></el-table-column>
        <el-table-column label="审核状态" v-slot="data">
          {{data.row.auditState==0?"不通过":"通过"}}
        </el-table-column>
        <el-table-column label="审核人" prop="auditor"></el-table-column>
        <el-table-column label="审核意见" prop="remark"></el-table-column>
        <el-table-column label="审核时间" prop="modifyTime"></el-table-column>
      </el-table>
      <br/>
      <br/>
      <el-table :data="goodTable" border style="width: 100%">
        <el-table-column label="序号" type="index" width="80">
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" width="120">
        </el-table-column>
        <el-table-column prop="type" label="商品型号" width="120">
        </el-table-column>
        <el-table-column prop="color" label="商品颜色" width="120">
        </el-table-column>
        <el-table-column prop="goodSerial" label="商品串码" width="120">
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import audit from "./index.js";

export default audit;
</script>


<style src="./index.scss" lang="scss">
</style>
