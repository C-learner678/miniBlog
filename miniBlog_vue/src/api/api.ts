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

export function postSignUp(name:string, password:string, email:string){
  return request({
    url: '/signUp',
    method: 'post',
    data: {
      name,
      password,
      email,
    }
  })  
}

export function getUserInfo(username:string) {
    return request({
        url: '/user/' + username,
        method: 'get',
    })
}