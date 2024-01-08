<template>
  <br>
  <br>
  <br>
  <br>
  <br>
  <el-row>
    <el-col :span="9"></el-col>
    <el-col :span="5">
      <el-form label-position="left" :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="loginForm.name"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="signup">注册</el-button>
          <el-button type="primary" @click="login(loginFormRef)">登录</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="10"></el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import JSEncrypt from 'jsencrypt';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getPublicKey, postLogin } from '../api/api'

interface loginForm {
  name: string
  password: string
}

const loginFormRef = ref<FormInstance>()
const loginForm = reactive<loginForm>({
  name: "",
  password: "",
})

const rules = reactive<FormRules<loginForm>>({
  name: [
    { required: true, message: '请输入用户名'},
  ],
  password: [
    { required: true, message: '请输入密码'},
  ],
})

const router = useRouter()

const login = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      getPublicKey()
      .then((res) => {
        let publicKey = res.data;
        if(publicKey){
          let encrypt = new JSEncrypt()
          encrypt.setPublicKey(publicKey)
          postLogin(loginForm.name, encrypt.encrypt(loginForm.password))
          .then((res) => {
            sessionStorage.setItem("token", res.data)
            ElMessage.success("登录成功")
            router.push({ path: 'welcome' })
          })
          .catch((error) => {
            ElMessage.error('用户名或密码错误')
          });
        }
      })
    }
  })
}

function signup(){
  router.push({ path: 'signup' })
}

//已登录则自动进入欢迎界面
onMounted(() => {
  const token = sessionStorage.getItem("token")
  if (token){
    router.push({ path: 'welcome' })
  }
})

</script>

