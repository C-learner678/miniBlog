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
          <el-input v-model="blogForm.description" type="textarea" />
        </el-form-item>
        <el-form-item label="内容" prop="text">
          <v-md-editor v-model="blogForm.text" height="400px" :disabled-menus="[]" @upload-image="handleUploadImage"></v-md-editor>
        </el-form-item>
        <el-form-item>
          <el-button @click="exit">退出</el-button>
          <el-button @click="save(blogFormRef)">保存</el-button>
          <el-button type="primary" @click="create(blogFormRef)">创建</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="5"></el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { postSaveBlog, postCreateBlog, getUnfinishedBlog } from '../api/api'
import axios from 'axios'

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
      postSaveBlog(blogForm.title, blogForm.description, blogForm.text)
      .then((res) => {
        ElMessage.success("保存成功")
      })
    }
  })
}

const create = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if(valid){
      postCreateBlog(blogForm.title, blogForm.description, blogForm.text)
      .then((res) => {
        ElMessage.success("创建成功")
        router.push({ path: 'welcome' })
      })
    }
  })
}

function handleUploadImage(event, insertImage, files) {
  console.log(files[0])
  if (files[0].type !== 'image/jpeg') {
    ElMessage.error('必须是jpg/jpeg格式')
    return false
  } else if (files[0].size / 1024 / 1024 > 2) {
    ElMessage.error('大小不能超过2MB')
    return false
  }
  const formData = new FormData();
  formData.append('file', files[0]);
  const token = sessionStorage.getItem("token")
  const headers = {
    token: token
  }
  axios.post('http://localhost:8081/upload', formData, {headers})
  .then((res) => {
    insertImage({
      url:'http://localhost:8081/getImage/' + res.data.data,
      desc: files[0].name,
    });
  })
}

onMounted(() => {
  getUnfinishedBlog()
  .then((res) => {
    if(res.data){
      blogForm.title = res.data.title
      blogForm.description = res.data.description
      blogForm.text = res.data.content
    }
  })
})
</script>