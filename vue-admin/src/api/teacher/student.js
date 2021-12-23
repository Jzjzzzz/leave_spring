import request from '@/utils/request'

export default {
  //分页查询科目列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/leave/teacher/student/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除科目
  removeById(id){
    return request({
      url: `/leave/teacher/student/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询科目
  getById(id) {
    return request({
      url: `/leave/teacher/student/getById/${id}`,
      method: 'get'
    })
  },
  //新增科目
  save(student) {
    return request({
      url: `/leave/teacher/student/save`,
      method: 'post',
      data: student
    })
  },
  //修改科目
  updateById(student) {
    return request({
      url: `/leave/teacher/student/update`,
      method: 'put',
      data: student
    })
  }
}