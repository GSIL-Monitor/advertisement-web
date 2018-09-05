import Vue from 'vue'
import App from './App'
import VueTouch from 'vue-touch'
// import Router from 'vue-router'
import '../../../../utils/resize'
import '../../../../utils/retina'
import 'normalize.css'
import '../../../../utils/baidu'
import '../../../../assets/styles/games/kabao/dymfk/bookblock.css'
// import routes from '../../../../router/index'

Vue.config.productionTip = false
Vue.use(VueTouch, {name: 'v-touch'})
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
