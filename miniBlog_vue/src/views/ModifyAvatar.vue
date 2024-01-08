<template>
  <div>
    <div>点击头像上传</div>
    <div>
      <el-upload
        action="http://localhost:8081/upload"
        :show-file-list="false"
        :with-credentials="true"
        accept = ".jpg,.jpeg"
        :headers="headers"
        :before-upload="beforeAvatarUpload"
        :on-success="handleAvatarSuccess"
      >
        <el-image style="width: 100px; height: 100px" :src="avatarUrl" v-if="available"/>
        <el-image style="width: 100px; height: 100px" v-else/>
      </el-upload>
    </div>
    <div>
      <ElButton @click="modifyAvatar" type="primary">确认新头像</ElButton>
      <ElButton @click="toWelcome">返回</ElButton>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import type { UploadProps } from 'element-plus'
import { ElMessage } from 'element-plus'
import { getCurrentUserInfo, postModifyAvatar } from '../api/api'
import { useRouter } from 'vue-router'

const router = useRouter()

const available = ref(false)
let avatarUrl = ""
let avatar = ""
const token = sessionStorage.getItem("token")
const headers = {
  token: token
}

onMounted(() => {
  getCurrentUserInfo()
  .then((res) => {
    avatarUrl = 'http://localhost:8081/getImage/' + res.data.avatar
    available.value = true
  })
})

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('必须是jpg/jpeg格式')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('大小不能超过2MB')
    return false
  }
  return true
}

const handleAvatarSuccess: UploadProps['onSuccess'] = (
  res,
) => {
  avatar = res.data
  avatarUrl = 'http://localhost:8081/getImage/' + avatar
  available.value = false
  available.value = true
}

function modifyAvatar(){
  if(!avatar){
    return
  }
  postModifyAvatar(avatar)
  .then((res) => {
    ElMessage.success("修改成功")
    router.push({ path: 'welcome' })
  })
}

function toWelcome(){
  router.push({ path: 'welcome' })
}
</script>