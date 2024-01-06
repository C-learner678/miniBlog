<template>
  <div>
    <div>
      <h3>欢迎您，{{ name }}</h3>
    </div>
    <div>
      个人信息
    </div>
    <div>
      邮箱：{{ email }}
    </div>
    <div>
      个人介绍：{{ description }}
    </div>
    <div>
      注册时间：<el-date-picker v-model="created" type="datetime" readonly/>
    </div>
    <div>
      上次登录：<el-date-picker v-model="lastLogin" type="datetime" readonly/>
    </div>
    <div>
      <ElButton @click="modifyPassword">修改密码</ElButton>
      <ElButton @click="modifyUserInfo">修改个人信息</ElButton>
      <ElButton @click="logout">退出登录</ElButton>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElButton, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

import { getCurrentUserInfo } from '../api/api'

const router = useRouter()

const name = ref("")
const email = ref("")
const description = ref("")
const created = ref(new Date())
const lastLogin = ref(new Date())

onMounted(() => {
  //不传其他参数，只通过token判断身份
  getCurrentUserInfo()
  .then((res) => {
    name.value = res.data.name
    email.value = res.data.email
    description.value = res.data.description
    created.value = res.data.created
    lastLogin.value = res.data.lastLogin
  })
})

function logout(){
  sessionStorage.clear()
  router.push( {path: '/login'} )
}

function modifyUserInfo(){
  router.push( {path: '/modifyInfo'} )
}

function modifyPassword(){
  router.push( {path: '/modifyPassword'} )
}

</script>