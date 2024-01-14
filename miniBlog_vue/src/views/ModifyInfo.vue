<template>
  <br>
  <br>
  <br>
  <br>
  <br>
  <el-row>
    <el-col :span="8"></el-col>
    <el-col :span="6">
      <el-form label-position="left" :model="infoForm" :rules="rules" ref="infoFormRef" label-width="140px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="infoForm.name" disabled/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="infoForm.email"/>
        </el-form-item>
        <el-form-item label="个人介绍" prop="description">
          <el-input v-model="infoForm.description" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="modifyInfo(infoFormRef)">修改个人信息</el-button>
          <ElButton @click="toWelcome">返回</ElButton>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="10"></el-col>
</el-row>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getCurrentUserInfo, postModifyInfo } from '../api/api';

interface infoForm {
  name: string
  email: string
  description: string
}

const infoFormRef = ref<FormInstance>()
const infoForm = reactive<infoForm>({
  name: "",
  email: "",
  description: "",
})

const rules = reactive<FormRules<infoForm>>({
  email: [
  { max: 50, message: '长度不超过50' },
  ],
  description: [
  { max: 100, message: '长度不超过100' },
  ],
})

const router = useRouter()

const modifyInfo = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    postModifyInfo(infoForm.email, infoForm.description)
    .then((res) => {
      ElMessage.success("修改个人信息成功")
      router.push( {path: '/welcome'} )
    })
  })
}

onMounted(() => {
  getCurrentUserInfo()
  .then((res) => {
    infoForm.name = res.data.name
    infoForm.email = res.data.email
    infoForm.description = res.data.description
  })
})

function toWelcome(){
  router.push({ path: 'welcome' })
}
</script>