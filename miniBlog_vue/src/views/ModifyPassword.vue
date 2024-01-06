<template>
  <br>
  <br>
  <br>
  <br>
  <br>
  <el-row>
    <el-col :span="8"></el-col>
    <el-col :span="6">
      <el-form label-position="left" :model="passwordForm" :rules="rules" ref="passwordFormRef" label-width="140px">
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password"/>
        </el-form-item>
        <el-form-item label="再次输入新密码" prop="newPassword2">
          <el-input v-model="passwordForm.newPassword2" type="password"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="modifyPassword(passwordFormRef)">修改密码</el-button>
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
import { getPublicKey, postModifyPassword } from '../api/api';

interface passwordForm {
  oldPassword: string
  newPassword: string
  newPassword2: string
}

const passwordFormRef = ref<FormInstance>()
const passwordForm = reactive<passwordForm>({
  oldPassword: "",
  newPassword: "",
  newPassword2: "",
})

const rules = reactive<FormRules<passwordForm>>({
  oldPassword: [
    { required: true, message: '请输入旧密码'},
  ],
  newPassword: [
    { required: true, message: '请输入新密码'},
  ],
  newPassword2: [
    { required: true, message: '请再次输入新密码'},
  ],
})

const router = useRouter()

const modifyPassword = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  if (passwordForm.oldPassword === passwordForm.newPassword){
    ElMessage.error('旧密码和新密码输入一致')
    return
  }
  if (passwordForm.newPassword !== passwordForm.newPassword2){
    ElMessage.error('两次新密码输入不一致')
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
          postModifyPassword(encrypt.encrypt(passwordForm.oldPassword), encrypt.encrypt(passwordForm.newPassword))
          .then((res) => {
            ElMessage.success("修改密码成功，请重新登录")
            sessionStorage.clear()
            router.push( {path: '/login'} )
          })
          .catch((error) => {
            if(error.response.data.message === "旧密码有误"){
              ElMessage.error('旧密码有误')
            }
          });
        }
      })
    }
  })
}

</script>