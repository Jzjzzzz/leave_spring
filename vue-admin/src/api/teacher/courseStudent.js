import request from '@/utils/request'

export default {
  //分页查询列表
  listPage(page,limit,searchObj,teacherId) {
    return request({
      url: `/leave/teacher/course-student/listPage/${page}/${limit}/${teacherId}`,
      method: 'post',
      data: searchObj
    })
  },
  //选课
  save(courseId,rubyClassId) {
    return request({
      url: `/leave/teacher/course-student/curricula/${courseId}/${rubyClassId}`,
      method: 'post'
    })
  },
  //根据id查询
  getRubyClassList(id) {
    return request({
      url: `/leave/teacher/course-student/getRubyClassList/${id}`,
      method: 'get'
    })
  },
  //退选
  removeByCourseId(courseId,rubyClassId){
    return request({
      url: `/leave/teacher/course-student/removeByCourseId/${courseId}/${rubyClassId}`,
      method: 'delete'
    })
  },
  //全班同学列表
  listPageStudent(page,limit,rubyClassId){
    return request({
      url: `/leave/teacher/course-student/listPageStudent/${page}/${limit}/${rubyClassId}`,
      method: 'post'
    })
  },
  //随机同学列表
  randomRoll(rubyClassId){
    return request({
      url: `/leave/teacher/course-student/randomRoll/${rubyClassId}`,
      method: 'post'
    })
  },
  //点名
  rollBook(studentList) {
    return request({
      url: `/leave/teacher/course-student/rollBook`,
      method: 'post',
      data: studentList
    })
  }


}