<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">    
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="班级名"/>
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
      <el-table-column prop="name" label="班级名称" align="center" />
      <el-table-column prop="collegeName" label="二级学院" align="center" />
      <el-table-column prop="specialized" label="专业" align="center" />
      <el-table-column prop="grade" label="年级" align="center" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.grade === 1" type="success" size="mini">大一</el-tag>
          <el-tag v-if="scope.row.grade === 2" type="success" size="mini">大二</el-tag>
          <el-tag v-if="scope.row.grade === 3" type="success" size="mini">大三</el-tag>
          <el-tag v-if="scope.row.grade === 4" type="success" size="mini">大四</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="number" label="人数" align="center">
        <template slot-scope="scope">
          <el-tag type="warning" size="mini">
            {{scope.row.number}}
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
    <el-dialog  @open="isDisabled=false" :before-close="onClearFrom" title="新增班级" :visible.sync="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules"  ref="form">
        <el-form-item label="班级名称" :label-width="formLabelWidth" prop="name">
        <el-input type="text" v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="二级学院" :label-width="formLabelWidth" prop="collegeId">
          <el-select v-model="form.collegeId" placeholder="请选择">
            <el-option
              v-for="item in this.collegeList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" :label-width="formLabelWidth" prop="specialized">
        <el-input type="text" v-model="form.specialized" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="年级" :label-width="formLabelWidth" prop="grade">
          <el-select v-model="form.grade" placeholder="请选择">
            <el-option
              v-for="item in this.grades"
              :key="item.key"
              :label="item.value"
              :value="item.key">
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
import rubyClassApi from '@/api/teacher/rubyClass'
export default {
  data() {
    return {
      collegeList:[], //二级学院列表
      isDisabled:true, //防止表单重复提交
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      grades:[
        {key:1,value:'大一'},
        {key:2,value:'大二'},
        {key:3,value:'大三'},
        {key:4,value:'大四'}
      ], //年级列表
      searchObj:{
        name:''
      },//条件封装对象
      list: [], //数据列表
      dialogVisible: false, //添加或修改对话框
      formLabelWidth: '80px',
      form: {
        number:1
      },
      rules: {
        name: [
          { required: true, message: '班级名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' }
        ],
        specialized: [
          { required: true, message: '专业不能为空', trigger: 'blur' },
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
           rubyClassApi.save(this.form)
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
          rubyClassApi.updateById(this.form)
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
      rubyClassApi.getById(id)
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
          rubyClassApi.removeById(id)
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
      rubyClassApi.listPage(this.page,this.limit,this.searchObj)
      .then(response => {
        this.collegeList = response.data.map.collegeList
        this.list = response.data.map.list
        this.total = response.data.map.total
        this.listLoading = false
      })
    }
  }
}
</script>
