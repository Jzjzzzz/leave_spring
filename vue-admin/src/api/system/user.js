import request from '@/utils/request'

export default {
  //分页查询列表
  listPage(page,limit) {
    return request({
      url: `/leave/admin/user/listPage/${page}/${limit}`,
      method: 'post'
    })
  },
  //根据id删除
  removeById(id){
    return request({
      url: `/leave/admin/user/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询
  getById(id) {
    return request({
      url: `/leave/admin/user/getById/${id}`,
      method: 'get'
    })
  },
  //新增
  save(role) {
    return request({
      url: `/leave/admin/user/save`,
      method: 'post',
      data: role
    })
  },
  //修改
  updateById(role) {
    return request({
      url: `/leave/admin/user/update`,
      method: 'put',
      data: role
    })
  },
  getUserInfo() {
    return request({
      url: `/leave/teacher/login/getInfo`,
      method: 'get'
    })
  },
}