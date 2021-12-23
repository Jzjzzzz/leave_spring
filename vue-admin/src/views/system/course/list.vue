<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">    
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="课程名称"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button
        class="grid-content bg-purple"
        type="primary"
        icon="el-icon-edit"
        @click="dialogVisible = true"
      >
        新增
      </el-button>
      
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
      <el-table-column prop="teacherName" label="任课老师" align="center" />
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
            修改
          </el-button>
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
    <!-- 添加或修改对话框 -->
    <el-dialog  @open="isDisabled=false" :before-close="onClearFrom" title="新增学生" :visible.sync="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules"  ref="form">
        <el-form-item label="课程名称" :label-width="formLabelWidth" prop="name">
        <el-input type="text" v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth" prop="semester">
          <el-select v-model="form.semester" placeholder="请选择">
            <el-option
              v-for="item in this.semesterList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学时" :label-width="formLabelWidth" prop="hours">
        <el-input-number  v-model="form.hours" :min="1" :max="10000" label="学时" auto-complete="off"></el-input-number>
        </el-form-item>
        <el-form-item label="学分" :label-width="formLabelWidth" prop="number">
        <el-input-number  v-model="form.number" :min="1" :max="10000" label="学分" auto-complete="off"></el-input-number>
        </el-form-item>

        <el-form-item label="任课老师" :label-width="formLabelWidth" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择">
            <el-option
              v-for="item in this.teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
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
import courseApi from '@/api/system/course'
export default {
  data() {
    return {
      semesterList:[1,2,3,4], //学期列表
      teacherList:[],//教师列表
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
        hours:1,
        number:1
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
    //清空form表单
    onClearFrom(){
      this.dialogVisible=false
      this.form= {
        hours:1,
        number:1
      }
      this.$refs.form.resetFields(); //清空校验规则
    },
    //新增修改提交表单
    onSubmit(){
      if(this.form.id!=null){
        this.updateTop()
      }else{
        this.cteateTop()
      }
    },
    //新增
    cteateTop(){
      this.$refs.form.validate(valid=>{
         if (!valid) {
           console.log('校验出错')
         }else{
           this.isDisabled=true //禁用提交按钮
           courseApi.save(this.form)
           .then(response=>{
            this.dialogVisible = false
            this.$message.success(response.message)
            this.fetchData()
            this.onClearFrom()
      })
      .catch(response=>{
        this.isDisabled=false
      })
         }
      })
    },
    //修改
    updateTop(){
      this.$refs.form.validate(valid=>{
        if (!valid) {
          console.log('校验出错')
        }else{
          this.isDisabled=true //禁用提交按钮
          courseApi.updateById(this.form)
          .then(response=>{
            this.dialogVisible = false
            this.$message.success(response.message)
            this.fetchData()
            this.onClearFrom()
      })
      .catch(response=>{
        this.isDisabled=false
      })
        }
      })
    },

    //根据id查询
    approvalShow(id){
      this.dialogVisible = true
      courseApi.getById(id)
      .then(response=>{
        this.form = response.data.model
      })
    },
    //清空搜索框
    resetData() {
      this.searchObj = {
        name:''
      }
      this.fetchData() 
      },
    // 根据id删除数据
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          courseApi.removeById(id)
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
      courseApi.listPage(this.page,this.limit,this.searchObj)
      .then(response => {
        this.list = response.data.map.list
        this.total = response.data.map.total
        this.teacherList = response.data.map.teacherList
        this.listLoading = false
      })
    }
  }
}
</script>
