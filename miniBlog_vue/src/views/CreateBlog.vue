<template>
  <br>
  <br>
  <br>
  <el-row>
    <el-col :span="4"></el-col>
    <el-col :span="15">
      <el-form label-position="left" :model="blogForm" :rules="rules" ref="blogFormRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="blogForm.title"/>
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input v-model="blogForm.description" textarea/>
        </el-form-item>
        <el-form-item label="内容" prop="text">
          <v-md-editor v-model="blogForm.text" height="400px"></v-md-editor>
        </el-form-item>
        <el-form-item>
          <el-button @click="exit">不保存退出</el-button>
          <el-button @click="save(blogFormRef)">保存</el-button>
          <el-button type="primary" @click="create(blogFormRef)">创建</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="5"></el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { postCreateBlog } from '../api/api'

interface blogForm {
  title: string
  description: string
  text: string
}

const blogFormRef = ref<FormInstance>()
const blogForm = reactive<blogForm>({
  title: "",
  description: "",
  text: "",
})

const rules = reactive<FormRules<blogForm>>({
  title: [
    { required: true, message: '请输入标题'},
    { min: 3, max: 50, message: '长度在3到50之间' },
  ],
  description: [
    { max: 100, message: '长度不超过100' },
  ],
  text: [
    { max: 30000, message: '长度不超过30000' },
  ]
})

const router = useRouter()

function exit(){
  router.push({ path: 'welcome' })
}

const save = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if(valid){
      postCreateBlog(blogForm.title, blogForm.description, blogForm.text, 0)
      .then((res) => {
        ElMessage.success("创建成功")
        router.push({ path: 'welcome' })
      })
    }
  })
}

const create = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if(valid){
      postCreateBlog(blogForm.title, blogForm.description, blogForm.text, 1)
      .then((res) => {
        ElMessage.success("创建成功")
        router.push({ path: 'welcome' })
      })
    }
  })
}
</script>