import {createRouter, createWebHistory} from 'vue-router'
import { defineAsyncComponent } from 'vue'
import UserInfo from '../views/UserInfo.vue'
import Login from '../views/Login.vue'
import Signup from '../views/Signup.vue'
import Welcome from '../views/Welcome.vue'
import ModifyPassword from '../views/ModifyPassword.vue'
import ModifyInfo from '../views/ModifyInfo.vue'
import ModifyAvatar from '../views/ModifyAvatar.vue'
import CreateBlog from '../views/CreateBlog.vue'

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: { name: 'Login' }
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/userInfo/:username',
    name: 'UserInfo',
    component: UserInfo
  },
  {
    path: '/welcome',
    name: 'Welcome',
    component: Welcome
  },
  {
    path: '/modifyPassword',
    name: 'modifyPassword',
    component: ModifyPassword
  },
  {
    path: '/modifyInfo',
    name: 'modifyInfo',
    component: ModifyInfo
  },
  {
    path: '/modifyAvatar',
    name: 'modifyAvatar',
    component: ModifyAvatar
  },
  {
    path: '/createBlog',
    name: 'createBlog',
    component: CreateBlog
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

//from: https://juejin.cn/post/7225478065391927356#heading-5
// 检查是否存在于免登录白名单
function inWhiteList(toPath) {
  const whiteList = ['/login', '/signup', '/userinfo/',]
  const path = whiteList.find((value) => {
    return !toPath.search(value)
  })
  return !!path
}

router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem("token")
  if (inWhiteList(to.path)) {
    next()
  } else {
    //用户已登录
    if (token) {
      next()
    } else {
      next(`/login`)
    }
  }
})

export default router