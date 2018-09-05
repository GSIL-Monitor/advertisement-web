/* eslint-disable */
(() => {
  let _hmt = _hmt || []
  let hm = document.createElement('script')
  var hostname = ''
  if(window.location.hostname.indexOf('yuanshanbao') != -1) {
    hostname = '490bf88a4a637a8b59c4e372065317c4'
  }else if(window.location.hostname.indexOf('dachuanbao') != -1) {
    hostname = 'e83985ca653bc52de9cf7c6c8d97b104'
  }else if(window.location.hostname.indexOf('yuanshanbx') != -1) {
    hostname = 'b44137cff193a70489f309967f0d4dac'
  }else {
    hostname = '11dfbf08c34db0db4758d639838fde2c'
  }
  hm.src = '//hm.baidu.com/hm.js?' + hostname
  let s = document.getElementsByTagName('script')[0]
  s.parentNode.insertBefore(hm, s)
})()
