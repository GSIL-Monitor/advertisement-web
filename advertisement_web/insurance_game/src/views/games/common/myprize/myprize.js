// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
// import Router from 'vue-router'
import '../../../../utils/resize'
import '../../../../utils/retina'
import 'normalize.css'
import '../../../../utils/baidu'
// import routes from '../../../../router/index'

Vue.config.productionTip = false

// Vue.use(Router)

// const router = new Router({
//   routes
// })
/* eslint-disable no-new */
new Vue({
  el: '#app',
  // router,
  components: { App },
  template: '<App/>'
})
