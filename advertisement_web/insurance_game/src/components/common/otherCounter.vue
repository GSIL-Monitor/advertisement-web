<template>
  <div class="counter">
    <img class="counter-bg" :src="picSrc">
    <div class="counter-chance">今日免费 <span>{{chances}}</span> 次</div>
  </div>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'otherCounter',
  data () {
    return {
      picSrc: ''
    }
  },
  props: {
    chances: {
      type: Number,
      default: 0
    },
    imgUrl: {
      type: String,
      default: '/common'
    }
  },
  created() {
    this.picSrc = ASIGN.cdnUrl + this.imgUrl + '/counter@3x.png'
  }
}
</script>

<style lang="less" scoped>
  @text-color: red;
  @border-color: #fff;
  .counter{
    width: 2.4rem;
    height: .6rem;
    line-height: .6rem;
    font-size: .26rem;
    text-align: center;
    border-radius: .3rem;
    border: 2px solid @border-color;
    color: @text-color;
    .counter-bg {
      width: 2.4rem;
      height: .6rem;
      position: absolute;
      left: 0;
    }
    .counter-chance{
      position: absolute;
      width: 100%;
    }
  }
</style>
