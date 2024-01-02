<template>
  <br>
  <br>
  <br>
  <br>
  <br>
  <el-row>
    <el-col :span="9"></el-col>
    <el-col :span="6">
      <el-form :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password"/>
        </el-form-item>
        <el-form-item>
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

const form = ref({
    name: "",
    password: "",
})

function login(){
    axios.post('http://localhost:8080/login', form.value)
    .then((res) => {
        console.log(res.data.data)
    })
    .catch((error) => {
        console.log(error)
    });
}
</script>

