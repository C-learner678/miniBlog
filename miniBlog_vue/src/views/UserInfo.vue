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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

const name = ref("")
const email = ref("")
const created = ref(new Date())
const lastLogin = ref(new Date())

onMounted(() => {
  const route = useRoute()
  let username = route.params.username
  axios.get('http://localhost:8080/user/' + username)
  .then((res) => {
    //console.log(res.data.data)
    name.value = res.data.data.name
    email.value = res.data.data.email
    created.value = res.data.data.created
    lastLogin.value = res.data.data.created
  })
  .catch((error) => {
    console.log(error)
  });
})
</script>