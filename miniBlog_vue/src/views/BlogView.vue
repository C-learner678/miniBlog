<template>
  <div>
    <div v-if="available">
      <el-image style="width: 100px; height: 100px" :src="avatarUrl" fit="fill"/>
    </div>
    <div>
      <h3>{{ title }}</h3>
    </div>
    <div>
      作者：{{ name }}
    </div>
    <div>
      摘要：{{ description }}
    </div>
    <div>
      发表时间：<el-date-picker v-model="created" type="datetime" readonly/>
      上次修改：<el-date-picker v-model="lastModified" type="datetime" readonly/>
    </div>
    <div>
      <v-md-preview :text="content"></v-md-preview>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

import { getBlog } from '../api/api'

const name = ref("")
const title = ref("")
const description = ref("")
const content = ref("")
const status = ref("")
const created = ref("")
const lastModified = ref("")

const available = ref(false)
let avatarUrl = ""

const route = useRoute()

onMounted(() => {
  let blogId = route.params.blogId
  getBlog(blogId)
  .then((res) => {
    name.value = res.data[1].name
    avatarUrl = 'http://localhost:8081/getImage/' + res.data[1].avatar
    available.value = true
    title.value = res.data[0].title
    description.value = res.data[0].description
    content.value = res.data[0].content
    status.value = res.data[0].status
    created.value = res.data[0].created
    lastModified.value = res.data[0].lastModified
  })
})
</script>