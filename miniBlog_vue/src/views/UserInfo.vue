<template>
  <div>
    <h3>用户信息</h3>
    <div>
      用户名：{{ name }}
    </div>
    <div>
      邮箱：{{ email }}
    </div>
    <div>
      注册时间：<el-date-picker v-model="created" type="datetime" readonly/>
    </div>
    <div>
      上次登录：<el-date-picker v-model="lastLogin" type="datetime" readonly/>
    </div>
    <div>
      <ElButton @click="logout">退出登录</ElButton>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElButton, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

import { getUserInfo } from '../api/api'

const router = useRouter()

const name = ref("")
const email = ref("")
const created = ref(new Date())
const lastLogin = ref(new Date())

onMounted(() => {
  const route = useRoute()
  let username = route.params.username
  getUserInfo(username)
  .then((res) => {
    console.log(res)
    name.value = res.data.name
    email.value = res.data.email
    created.value = res.data.created
    lastLogin.value = res.data.lastLogin
  })
})

function logout(){
  sessionStorage.clear()
  router.push( {path: '/login'} )
}
</script>