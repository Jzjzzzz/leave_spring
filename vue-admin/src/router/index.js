import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/college/list',
    name: 'system',
    meta: { title: '系统管理', icon: 'el-icon-s-marketing' },
    alwaysShow: true,
    children: [
      {
        path: 'user',
        name: 'systemUserList',
        component: () => import('@/views/system/user/list'),
        meta: { title: '用户管理' },
      },
      {
        path: 'role',
        name: 'systemRoleList',
        component: () => import('@/views/system/role/list'),
        meta: { title: '角色管理' }
      },
      {
        path: 'college',
        name: 'systemCollegeList',
        component: () => import('@/views/system/college/list'),
        meta: { title: '二级学院管理' }
      },
      {
        path: 'teacher',
        name: 'systemTeacherList',
        component: () => import('@/views/system/teacher/list'),
        meta: { title: '教师管理' }
      },
      {
        path: 'course',
        name: 'systemCourseList',
        component: () => import('@/views/system/course/list'),
        meta: { title: '课程管理' }
      }
    ]
},
{
  path: '/teacher',
  component: Layout,
  redirect: '/teacher/rubyClass/list',
  name: 'teacher',
  meta: { title: '教师管理', icon: 'el-icon-s-marketing' },
  alwaysShow: true,
  children: [
    {
      path: 'rubyClass',
      name: 'teacherRubyClassList',
      component: () => import('@/views/teacher/rubyClass/list'),
      meta: { title: '班级管理' }
    },
    {
      path: 'student',
      name: 'teacherStudentList',
      component: () => import('@/views/teacher/student/list'),
      meta: { title: '学生管理' }
    },
    {
      path: 'courseStudent/list',
      name: 'systemcourseStudentList',
      component: () => import('@/views/teacher/courseStudent/list'),
      meta: { title: '选课管理' }
    },
    {
      path: 'roll',
      name: 'systemcourseStudentList',
      component: () => import('@/views/teacher/roll/list'),
      meta: { title: '缺勤统计' }
    },
    {
      path: 'courseStudent/roll/:id',
      name: 'systemcourseStudentList',
      component: () => import('@/views/teacher/courseStudent/roll'),
      meta: { title: '全班点名' },
      hidden: true
    },
    {
      path: 'courseStudent/randomRoll/:id',
      name: 'systemcourseStudentList',
      component: () => import('@/views/teacher/courseStudent/randomRoll'),
      meta: { title: '随机点名' },
      hidden: true
    }
  ]
},



]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  

 
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
