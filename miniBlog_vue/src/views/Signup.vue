<template>
  <br>
  <br>
  <br>
  <br>
  <br>
  <el-row>
    <el-col :span="9"></el-col>
    <el-col :span="5">
      <el-form label-position="left" :model="signUpForm" :rules="rules" ref="signUpFormRef" label-width="100px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="signUpForm.name"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="signUpForm.password" type="password"/>
        </el-form-item>
        <el-form-item label="重复密码" prop="password2">
          <el-input v-model="signUpForm.password2" type="password"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="signUpForm.email"/>
        </el-form-item>
        <el-form-item label="个人介绍" prop="description">
          <el-input v-model="signUpForm.description" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="signUp(signUpFormRef)">注册</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="10"></el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import JSEncrypt from 'jsencrypt';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getPublicKey, postSignUp } from '../api/api';

interface signUpForm {
  name: string
  password: string
  password2: string
  email: string 
  description: string
}

const signUpFormRef = ref<FormInstance>()
const signUpForm = reactive<signUpForm>({
  name: "",
  password: "",
  password2: "",
  email: "",
  description: "",
})

const rules = reactive<FormRules<signUpForm>>({
  name: [
    { required: true, message: '请输入用户名'},
    { min: 3, max: 50, message: '长度在3到50之间' },
  ],
  password: [
    { required: true, message: '请输入密码'},
    { min: 3, max: 50, message: '长度在3到50之间' },
  ],
  password2: [
    { required: true, message: '请再次输入密码'},
    { min: 3, max: 50, message: '长度在3到50之间' },
  ],
  email: [
    { max: 50, message: '长度不超过50' },
  ],
  description: [
    { max: 50, message: '长度不超过100' },
  ]
})

const router = useRouter()

const signUp = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  if (signUpForm.password !== signUpForm.password2){
    ElMessage.error('两次密码输入不一致')
    return
  }
  await formEl.validate((valid) => {
    if (valid) {
      getPublicKey()
      .then((res) => {
        let publicKey = res.data;
        if(publicKey){
          let encrypt = new JSEncrypt()
          encrypt.setPublicKey(publicKey)
          postSignUp(signUpForm.name, encrypt.encrypt(signUpForm.password), signUpForm.email, signUpForm.description)
          .then((res) => {
            sessionStorage.setItem("token", res.data)
            ElMessage.success("注册成功")
            router.push({ path: 'welcome' })
          })
          .catch((error) => {
            if(error.response.data.message === "该用户名已被使用"){
              ElMessage.error('该用户名已被使用')
            }
          });
        }
      })
    }
  })
}

</script>