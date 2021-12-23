<template>
  <div class="app-container">
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      stripe
      element-loading-text="数据加载中"
      fit
      highlight-current-row
    >
      <el-table-column type="index" width="50" align="center" />
      <el-table-column prop="studentId" label="学生id" align="center" />
      <el-table-column prop="studentName" label="学生姓名" align="center" />
      <el-table-column prop="gmtCreate" label="考勤时间" align="center" />
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeById(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10]"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      background
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
      
    />
  </div>
</template>
<script>
import rollApi from '@/api/teacher/roll'
export default {
  data() {
    return {
      isDisabled:true, //防止表单重复提交
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      list: [], //数据列表
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //清空form表单
    onClearFrom(){
      this.dialogVisible=false
      this.form= {
      }
      this.$refs.form.resetFields(); //清空校验规则
    },
    // 根据id删除数据
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          rollApi.removeById(id)
          .then(response => {
            this.$message.success(response.message)
            this.fetchData()
          })
        })
        .catch(error => {
          this.$message.info('取消删除')
        })
    },
    //分页相关方法
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },
    //列表数据
    fetchData() {
      rollApi.listPage(this.page,this.limit,)
      .then(response => {
        this.list = response.data.listPage.records
        this.total = response.data.listPage.total
        this.listLoading = false
      })
    }
  }
}
</script>
