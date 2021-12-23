import request from '@/utils/request'

export default {
  //分页查询科目列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/leave/admin/college/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除科目
  removeById(id){
    return request({
      url: `/leave/admin/college/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询科目
  getById(id) {
    return request({
      url: `/leave/admin/college/getById/${id}`,
      method: 'get'
    })
  },
  //新增科目
  save(college) {
    return request({
      url: `/leave/admin/college/save`,
      method: 'post',
      data: college
    })
  },
  //修改科目
  updateById(college) {
    return request({
      url: `/leave/admin/college/update`,
      method: 'put',
      data: college
    })
  }
}