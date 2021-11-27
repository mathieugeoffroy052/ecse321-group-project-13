import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import CreateAccount from '@/components/CreateAccount'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    } ,
    {
      path: '/',
      name: 'CreateAccount',
      component: CreateAccount
    }
  ]
})
