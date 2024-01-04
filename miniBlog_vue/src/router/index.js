import {createRouter, createWebHistory} from 'vue-router'
import { defineAsyncComponent } from 'vue'
import UserInfo from '../views/UserInfo.vue'
import Login from '../views/Login.vue'
import Signup from '../views/Signup.vue'

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
    path: '/userinfo/:username',
    name: 'UserInfo',
    component: UserInfo
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})
export default router