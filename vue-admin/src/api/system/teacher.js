import request from '@/utils/request'

export default {
  //分页查询科目列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/leave/admin/teacher/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除科目
  removeById(id){
    return request({
      url: `/leave/admin/teacher/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询科目
  getById(id) {
    return request({
      url: `/leave/admin/teacher/getById/${id}`,
      method: 'get'
    })
  },
  //新增科目
  save(teacher) {
    return request({
      url: `/leave/admin/teacher/save`,
      method: 'post',
      data: teacher
    })
  },
  //修改科目
  updateById(teacher) {
    return request({
      url: `/leave/admin/teacher/update`,
      method: 'put',
      data: teacher
    })
  }
}