<template>
<div class="loading-window">
  <div class="loading-effect">
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
  </div>
</div>
</template>

<script>
export default {
  name: 'loading',
  created () {
    document.body.setAttribute('class', 'modal-open')
  },
  beforeDestroy () {
    document.body.setAttribute('class', '')
  }
}
</script>

<style lang="less" scoped>
.loading-window {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  width: 2rem;
  height: 2rem;
  border-radius: .1rem;
  background-color: #000;
  filter: alpha(opacity=50);
  opacity: 0.55;
  z-index: 2000;
  .loading-effect{
    width: 1rem;
    height: 1rem;
    position: relative;
    margin: 0 auto;
    margin-top: 0.5rem;
    span{
      display: inline-block;
      width: 0.2rem;
      height: 0.2rem;
      border-radius: 50%;
      background: #fff;
      position: absolute;
      animation: load 1.04s ease infinite;
    }
  }
}
.loading-effect span:nth-child(1){
  left: 0;
  top: 50%;
  margin-top:-.1rem;
  animation-delay:0.13s;
}
.loading-effect span:nth-child(2){
  left: .14rem;
  top: .14rem;
  animation-delay:0.26s;
}
.loading-effect span:nth-child(3){
  left: 50%;
  top: 0;
  margin-left: -.1rem;
  animation-delay:0.39s;
}
.loading-effect span:nth-child(4){
  top: .14rem;
  right:.14rem;
  animation-delay:0.52s;
}
.loading-effect span:nth-child(5){
  right: 0;
  top: 50%;
  margin-top:-.1rem;
  animation-delay:0.65s;
}
.loading-effect span:nth-child(6){
  right: .14rem;
  bottom:.14rem;
  animation-delay:0.78s;
}
.loading-effect span:nth-child(7){
  bottom: 0;
  left: 50%;
  margin-left: -.1rem;
  animation-delay:0.91s;
}
.loading-effect span:nth-child(8){
  bottom: .14rem;
  left: .14rem;
  animation-delay:1.04s;
}
@keyframes load{
  0%{
    transform: scale(1.2);
    opacity: 1;
  }
  100%{
    transform: scale(.3);
    opacity: 0.5;
  }
}
</style>
