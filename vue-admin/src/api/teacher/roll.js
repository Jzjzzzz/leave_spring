import request from '@/utils/request'

export default {
  //分页查询列表
  listPage(page,limit) {
    return request({
      url: `/leave/teacher/roll/listPage/${page}/${limit}`,
      method: 'post'
    })
  },
  //根据id删除
  removeById(id){
    return request({
      url: `/leave/teacher/roll/removeById/${id}`,
      method: 'delete'
    })
  }
  
}