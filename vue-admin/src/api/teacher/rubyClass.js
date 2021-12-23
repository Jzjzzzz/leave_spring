import request from '@/utils/request'

export default {
  //分页查询科目列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/leave/teacher/rubyClass/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除科目
  removeById(id){
    return request({
      url: `/leave/teacher/rubyClass/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询科目
  getById(id) {
    return request({
      url: `/leave/teacher/rubyClass/getById/${id}`,
      method: 'get'
    })
  },
  //新增科目
  save(rubyClass) {
    return request({
      url: `/leave/teacher/rubyClass/save`,
      method: 'post',
      data: rubyClass
    })
  },
  //修改科目
  updateById(rubyClass) {
    return request({
      url: `/leave/teacher/rubyClass/update`,
      method: 'put',
      data: rubyClass
    })
  }
}