<template>
  <div class="dashboard-container">
    <h1>欢迎您: {{userInfo.name}}</h1>
    <div v-if="userInfo.type==0"><h3>角色:管理员</h3></div>
    <div v-if="userInfo.type==1"><h3>角色:教师</h3></div>
  </div>
</template>

<script>
import userApi from '@/api/system/user'

export default {
  data() {
    return {
     userInfo:{}
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //根据id查询
    fetchData(){
      userApi.getUserInfo()
      .then(response=>{
        this.userInfo = response.data.userInfo
      })
    }
   

  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
