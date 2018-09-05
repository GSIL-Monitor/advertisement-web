// import Vue from 'vue'
// import Router from 'vue-router'
import App from '../views/games/zhuanpan/nhshb/App'

// Vue.use(Router)

const MyPrize = r => require.ensure([], () => r(require('../views/games/common/myprize/myPrize')), 'myPrize')

export default [
  {
    path: '/',
    component: App,
    children: [
      {
        path: 'myprize',
        component: MyPrize
      }
    ]
  }
]
