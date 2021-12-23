import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/leave/teacher/login/loginByType',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/leave/teacher/login/getInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post',
    baseURL: '/dev-api',
  })
}
