<template>
  <div>
    <h3>用户信息</h3>
    <div v-if="available">
      <el-image style="width: 100px; height: 100px" :src="avatarUrl" fit="fill"/>
    </div>
    <div v-else>
      <el-image style="width: 100px; height: 100px" />
    </div>
    <div>
      用户名：{{ name }}
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

import { getUserInfo } from '../api/api'

const name = ref("")
const email = ref("")
const description = ref("")
const created = ref("")
const lastLogin = ref("")

const available = ref(false)
let avatarUrl = ""

const route = useRoute()

onMounted(() => {
  let username = route.params.username
  getUserInfo(username)
  .then((res) => {
    name.value = res.data.name
    email.value = res.data.email
    description.value = res.data.description
    created.value = res.data.created
    lastLogin.value = res.data.lastLogin
    avatarUrl = 'http://localhost:8081/getImage/' + res.data.avatar
    available.value = true
  })
})
</script>