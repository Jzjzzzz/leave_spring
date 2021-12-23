import request from '@/utils/request'

export default {
  //分页查询科目列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/leave/admin/course/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除科目
  removeById(id){
    return request({
      url: `/leave/admin/course/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询科目
  getById(id) {
    return request({
      url: `/leave/admin/course/getById/${id}`,
      method: 'get'
    })
  },
  //新增科目
  save(course) {
    return request({
      url: `/leave/admin/course/save`,
      method: 'post',
      data: course
    })
  },
  //修改科目
  updateById(course) {
    return request({
      url: `/leave/admin/course/update`,
      method: 'put',
      data: course
    })
  }
}