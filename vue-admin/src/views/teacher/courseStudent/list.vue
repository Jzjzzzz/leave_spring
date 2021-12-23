<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">    
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="课程名称"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
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
      <el-table-column prop="name" label="课程名称" align="center" />
      <el-table-column prop="semester" label="学期" align="center" >
        <template slot-scope="scope">
          <el-tag type="warning" size="mini">
            {{scope.row.semester}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="hours" label="学时" align="center" >
        <template slot-scope="scope">
          <el-tag type="warning" size="mini">
            {{scope.row.hours}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="number" label="学分" align="center" >
        <template slot-scope="scope">
          <el-tag type="warning" size="mini">
            {{scope.row.number}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="count" label="选课班级数" align="center" >
        <template slot-scope="scope">
          <el-tag type="warning" size="mini">
            {{scope.row.count}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="创建时间" align="center" />
      <el-table-column prop="gmtModified" label="修改时间" align="center" />
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="approvalShow(scope.row.id)"
          >
            班级
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
    <!-- 选课对话框 -->
    <el-dialog  @open="isDisabled=false" :before-close="onClearFrom" title="选课" :visible.sync="dialogVisible" width="50%">
      <el-form :model="form" :rules="rules"  ref="form">
        <el-table
      v-loading="listLoading"
      :data="rubyClassList"
      border
      stripe
      element-loading-text="数据加载中"
      fit
      highlight-current-row
    >
      <el-table-column prop="name" label="班级名称" align="center" />
      <el-table-column prop="specialized" label="专业" align="center" />
      <el-table-column prop="grade" label="年级" align="center" />
      <el-table-column prop="number" label="人数" align="center" />
      <el-table-column label="操作" width="400" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="addRubyClass(scope.row.id)"
            v-if="scope.row.choose==false"
          >
            选课
          </el-button>
          
          <router-link :to="'/teacher/courseStudent/roll/'+ scope.row.id" style="margin-right:5px;" >
            <el-button
            type="success"
            size="mini"
            icon="el-icon-search"
            v-if="scope.row.choose==true"
          >
            全班点名
          </el-button>
          </router-link>
          <router-link :to="'/teacher/courseStudent/randomRoll/'+ scope.row.id" style="margin-right:5px;" >
            <el-button
            type="success"
            size="mini"
            icon="el-icon-search"
            v-if="scope.row.choose==true"
          >
            随机点名
          </el-button>
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeById(scope.row.id)"
            v-if="scope.row.choose==true"
          >
            退选
          </el-button>
        </template>
      </el-table-column>
    </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onClearFrom">
          取消
        </el-button>
        <el-button type="primary" @click="onSubmit" :disabled="isDisabled">
          确定
        </el-button>
      </div>
    </el-dialog>
    
  </div>
</template>
<script>
import courseStudentApi from '@/api/teacher/courseStudent'
export default {
  data() {
    return {
      courseId:0, //课程id
      semesterList:[1,2,3,4], //学期列表
      rubyClassList:[],//班级列表
      isDisabled:true, //防止表单重复提交
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      searchObj:{
        name:''
      },//条件封装对象
      list: [], //数据列表
      dialogVisible: false, //添加或修改对话框
      formLabelWidth: '80px',
      form: {
      },
      rules: {
        name: [
          { required: true, message: '学生姓名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' }
        ]

      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //选课
    addRubyClass(id){
      this.$refs.form.validate(valid=>{
         if (!valid) {
           console.log('校验出错')
         }else{
           this.isDisabled=true //禁用提交按钮
           courseStudentApi.save(this.courseId,id)
           .then(response=>{
            this.$message.success(response.message)
            this.approvalShow(this.courseId,id)
            this.isDisabled=false
           
      })
      .catch(response=>{
        this.isDisabled=false
      })
         }
      })
    },
    //清空form表单
    onClearFrom(){
      this.dialogVisible=false
      this.form= {
      }
      this.$refs.form.resetFields(); //清空校验规则
    },
    onSubmit(){
      this.dialogVisible = false
      this.fetchData()
      this.onClearFrom()
    },
   

    //根据id查询
    approvalShow(id){
      this.courseId = id
      this.dialogVisible = true
      courseStudentApi.getRubyClassList(this.courseId)
      .then(response=>{
        this.rubyClassList = response.data.rubyClassList
      })
    },
    //清空搜索框
    resetData() {
      this.searchObj = {
        name:''
      }
      this.fetchData() 
      },
    // 退选
    removeById(id) {
      this.$confirm('此操作将退选该课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          courseStudentApi.removeByCourseId(this.courseId,id)
          .then(response => {
            this.$message.success(response.message)
            this.approvalShow(this.courseId,id)
            this.isDisabled=false
          })
        })
        .catch(error => {
          this.$message.info('取消删除')
          this.isDisabled=false
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
      courseStudentApi.listPage(this.page,this.limit,this.searchObj,2)
      .then(response => {
        this.list = response.data.map.courseList.records
        this.total = response.data.map.total
        this.rubyClassList = response.data.map.rubyClassList
        this.listLoading = false
      })
    }
  }
}
</script>
