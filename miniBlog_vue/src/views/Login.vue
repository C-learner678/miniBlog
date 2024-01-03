<template>
  <br>
  <br>
  <br>
  <br>
  <br>
  <el-row>
    <el-col :span="9"></el-col>
    <el-col :span="6">
      <el-form label-position="left" :model="loginForm" label-width="60px">
        <el-form-item label="用户名">
          <el-input v-model="loginForm.name" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="loginForm.password" type="password"/>
        </el-form-item>
        <el-form-item>
          <el-button>注册</el-button>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="9"></el-col>
</el-row>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import JSEncrypt from 'jsencrypt';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const loginForm = ref({
  name: "",
  password: "",
})
const router = useRouter()

function login(){
  axios.get('http://localhost:8080/getPublicKey')
  .then((res) => {
    let publicKey = res.data.data;
    if(publicKey){
      let encrypt = new JSEncrypt()
      encrypt.setPublicKey(publicKey)
      axios.post('http://localhost:8080/login', {
        name: loginForm.value.name,
        password: encrypt.encrypt(loginForm.value.password)
      })
      .then((res) => {
        ElMessage.success(res.data.data)
        router.push({ path: 'userinfo/' + loginForm.value.name })
      })
      .catch((error) => {
        ElMessage.error(error.response.data.message)
      });
    }else{
      console.log('获取公钥失败')
    }
  }).catch((error) => {
    console.log(error)
  })
}

</script>

