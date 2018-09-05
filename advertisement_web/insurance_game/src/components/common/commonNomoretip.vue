<template>
  <div class="my-prize-tip">
    <div class="tip-box">
      <div class="btn-close" @click="bindClose">
        <img :src="closeSrc">
      </div>
      <div class="img">
        <img :src="cartoonSrc">
      </div>
      <div class="title">{{title}}</div>
      <div class="subtitle">{{subTitle}}</div>
      <a href="/myprize.html" class="chakan">{{btnText}}</a>
    </div>
  </div>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'commonNomoretip',
  data () {
    return {
      closeSrc: '',
      cartoonSrc: '',
      title: '抽奖次数已用完',
      subTitle: '今日抽奖次数已用完，明日继续参与活动抽奖吧！',
      btnText: '查看我的奖品'
    }
  },
  props: {
    imgUrl: {
      type: String,
      default: '/common'
    }
  },
  methods: {
    bindClose () {
      this.$emit('bindClose')
    }
  },
  created() {
    this.closeSrc = ASIGN.cdnUrl + this.imgUrl + '/close@3x.png'
    this.cartoonSrc = ASIGN.cdnUrl + this.imgUrl + '/cartoon@3x.png'
  }
}
</script>

<style lang="less" scoped>
@btn-color: #fb5b2b;
.my-prize-tip {
  .tip-box {
    position: fixed;
    left: 50%;
    top: 50%;
    margin-left: -3.15rem;
    margin-top: -3.36rem;
    width: 6.3rem;
    height: 6.72rem;
    background-color: #fff;
    border-radius: 0.16rem;
    z-index: 101;
    .img {
      width: 2.41rem;
      height: 2.56rem;
      margin: 0.46rem auto 0.51rem;
      img{
        display: block;
        width: 100%;
      }
    }
    .btn-close {
      position: absolute;
      top: -0.74rem;
      right: 0;
      width: 0.5rem;
      height: 0.5rem;
      img{
        display: block;
        width: 100%;
      }
    }
    .title,.subtitle {
      text-align: center;
    }
    .title {
      font-size: 0.32rem;
      color: #222222;
      letter-spacing: 0;
    }
    .subtitle {
      margin-top: 0.12rem;
      font-size: 0.26rem;
      color: #666666;
      letter-spacing: 0;
    }
    .chakan {
      display: block;
      width: 3.15rem;
      height: 0.8rem;
      margin: 0.35rem auto 0;
      background: @btn-color;
      border: 2px solid @btn-color;
      border-radius: 1rem;
      font-size: 0.32rem;
      color: #fff;
      letter-spacing: 0;
      line-height: 0.8rem;
      text-align: center;
    }
  }
}
</style>
