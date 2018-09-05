let baseUrl
let routerMode

if (process.env.NODE_ENV === 'development') {
  routerMode = 'hash'
  baseUrl = ''
} else {
  routerMode = 'history'
  baseUrl = window.location.origin;
}

export {
  baseUrl,
  routerMode
}
