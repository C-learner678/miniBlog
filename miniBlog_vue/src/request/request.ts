// from: https://blog.csdn.net/qq_43605401/article/details/132478422

import axios from 'axios'
import { ElMessage  } from 'element-plus'

export  const request =(options:any)=> {
    return new Promise((resolve, reject) => {

        // create an axios instance
        const service = axios.create({
            // baseURL: process.env.BASE_API, // api 的 base_url
            baseURL: 'http://localhost:8081/',//这里填写后端的ip和端口号
            timeout: 30000 // request timeout
        })

        // request interceptor
        service.interceptors.request.use(
            (config:any) => {
                const token:string | undefined = sessionStorage.getItem("token") ? sessionStorage.getItem("token") : null
                if (token) {
                    // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
                    config.headers['token'] = token
                }
                return config
            },
            error => {
                // Do something with request error
                console.log("出错啦", error) // for debug
                Promise.reject(error)
            }
        )
        // response interceptor
        service.interceptors.response.use(
            (response:any) => {
                return response.data
            },
            error => {
                console.log(error)
                if(error.response.data.code === 401){
                    ElMessage.error('没有权限访问，请重新登录')
                }else{
                    ElMessage.error('服务器请求错误，请稍后再试')
                }
                return Promise.reject(error)
            }
        )
        // 请求处理
        service(options)
            .then((res) => {
                resolve(res)
            })
            .catch((error) => {
                reject(error)
            })
    })
}
export default request