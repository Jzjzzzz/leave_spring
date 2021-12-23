<template>
  <div class="app-container">
     <el-alert title="提示" type="warning" description="勾选未签到的学生，其余未勾选的默认已签到" show-icon></el-alert>

    <!-- 表格 -->
    <el-table
      ref="multipleTable" 
      v-loading="listLoading"
      :data="list"
      border
      stripe
      element-loading-text="数据加载中"
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index" width="50" align="center" />
      <el-table-column prop="name" label="学生姓名" align="center" />
      <el-table-column prop="sex" label="性别" align="center" />
      <el-table-column prop="email" label="邮箱" align="center"/>

    </el-table>
    <el-button style="margin-top:20px;margin-left:45%" size="small"  type="primary"  @click="onSubmit()">确认勾选</el-button>
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
import courseStudentApi from '@/api/teacher/courseStudent'

export default {
  data() {
    return {
      form:{
        students:[]
      },
      isDisabled:true, //防止表单重复提交
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      multipleSelection: [],
      list: [], //数据列表

    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 确认点名信息
    onSubmit() {
      this.$confirm('此操作会是勾选学生生成考勤记录为缺勤, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          courseStudentApi.rollBook(this.multipleSelection)
          .then(response => {
            this.$message.success(response.message)
            this.fetchData()
            this.isDisabled=false
            this.$router.push({ path: '/teacher/roll'})
          })
        })
        .catch(error => {
          this.$message.info('取消操作')
          this.isDisabled=false
        })
    },
    
    handleSelectionChange(val) {
        this.multipleSelection = val;
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
      courseStudentApi.listPageStudent(this.page,this.limit,this.$route.params.id)
      .then(response => {
        this.list = response.data.list.records
        this.total = response.data.total
        this.listLoading = false
      })
    }
  }
}
</script>
