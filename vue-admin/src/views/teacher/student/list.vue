<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">    
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="学生姓名"/>
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
      <el-button
        @click="dialogVisible2 = true"
        type="primary"
        icon="el-icon-download"
      >
        导入Excel
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
      <el-table-column prop="name" label="学生姓名" align="center" />
      <el-table-column prop="sex" label="性别" align="center" />
      <el-table-column prop="rubyClassName" label="所属班级" align="center"/>
      <el-table-column prop="email" label="邮箱" align="center"/>
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
    <!-- Excel导入 开始-->
    <el-dialog title="学生批量Excel导入" :visible.sync="dialogVisible2" width="30%">
      <el-form>
        <el-form-item label="请选择Excel文件">
          <el-upload
            :auto-upload="true"
            :multiple="false"
            :limit="1"
            :on-exceed="fileUploadExceed"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError"
            :action="'/leave/teacher/student/import'"
            name="file"
            accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>                                                                                                                                                                                           
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">
          取消
        </el-button>
      </div>
    </el-dialog>
    <!-- Excel导入 结束-->
    
    <!-- 添加或修改对话框 -->
    <el-dialog  @open="isDisabled=false" :before-close="onClearFrom" title="新增学生" :visible.sync="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules"  ref="form">
        <el-form-item label="学生姓名" :label-width="formLabelWidth" prop="name">
        <el-input type="text" v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
        <el-input type="text" v-model="form.sex" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
        <el-input type="text" v-model="form.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="form.id==null" label="所属班级" :label-width="formLabelWidth" prop="rubyClassId">
          <el-select v-model="form.rubyClassId" placeholder="请选择">
            <el-option
              v-for="item in this.RubyClassList"
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
import studentApi from '@/api/teacher/student'
export default {
  data() {
    return {
      RubyClassList:[],//班级列表
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
      dialogVisible2: false, //Excel导入对话框
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

    // 上传多于一个文件时
    fileUploadExceed() {
      this.$message.warning('只能选取一个文件')
    },
    //上传成功回调
    fileUploadSuccess(response) {
      if (response.code === 0) {
        this.$message.success('数据导入成功')
        this.dialogVisible2 = false
        this.fetchData()
      } else {
        this.$message.error(response.message)
      }
    },
    //上传失败回调
    fileUploadError(error) {
      this.$message.error('数据导入失败')
    },
    
    //清空form表单
    onClearFrom(){
      this.dialogVisible=false
      this.form= {
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
           studentApi.save(this.form)
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
          studentApi.updateById(this.form)
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
      studentApi.getById(id)
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
          studentApi.removeById(id)
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
      studentApi.listPage(this.page,this.limit,this.searchObj)
      .then(response => {
        this.list = response.data.map.list
        this.total = response.data.map.total
        this.RubyClassList = response.data.map.RubyClassList
        this.listLoading = false
      })
    }
  }
}
</script>
