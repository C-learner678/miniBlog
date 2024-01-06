import {request} from '../request/request' //导入request

export function getPublicKey(){
  return request({
    url: '/getPublicKey',
    method: 'get',
  })  
}

export function postLogin(name:string, password:string){
  return request({
    url: '/login',
    method: 'post',
    data: {
      name,
      password,
    }
  })  
}

export function postSignUp(name:string, password:string, email:string, description:string){
  return request({
    url: '/signUp',
    method: 'post',
    data: {
      name,
      password,
      email,
      description,
    }
  })  
}

export function getUserInfo(username:string) {
  return request({
    url: '/user/' + username,
    method: 'get',
  })
}

export function getCurrentUserInfo() {
  return request({
    url: '/currentUser',
    method: 'get',
  })
}

export function postModifyPassword(oldPassword:string, newPassword:string) {
  return request({
    url: '/modifyPassword',
    method: 'post',
    data: {
      oldPassword,
      newPassword,
    }
  })
}

export function postModifyInfo(email:string, description:string){
  return request({
    url: '/modifyInfo',
    method: 'post',
    data: {
      email,
      description,
    }
  })
}