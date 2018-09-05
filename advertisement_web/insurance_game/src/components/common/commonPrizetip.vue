<template>
  <div class="prize-tip">
    <div class="tip-wrap">
      <div class="light"><img :src="lightSrc"></div>
      <div class="prize-title"><img :src="prizeSrc"></div>
      <div class="coin"><img :src="coinSrc"></div>
      <div class="close" @click="close"><img :src="closeSrc"></div>
      <div class="hb-back"><img :src="backSrc"></div>
      <div class="hb-inner">
        <img class="prize-img" :src="prizeImage">
        <div class="title-name">
          <div class="nm">{{prizeName}}</div>
        </div>
      </div>
      <div class="hb-before"><img :src="beforeSrc"></div>
      <div class="prize-btn" @click="toLink"><img :src="buttonSrc"></div>
    </div>
  </div>
</template>

<script>
import { adCount } from '@/service/getData'
import ASIGN from '@/config/const'
export default {
  name: 'commonPrizetip',
  data () {
    return {
      lightSrc: ASIGN.cdnUrl + '/common/light@3x.png',
      prizeSrc: ASIGN.cdnUrl + '/common/prize@3x.png',
      coinSrc: ASIGN.cdnUrl + '/common/coin@3x.png',
      closeSrc: ASIGN.cdnUrl + '/common/close@3x.png',
      backSrc: ASIGN.cdnUrl + '/common/back@3x.png',
      beforeSrc: ASIGN.cdnUrl + '/common/before@3x.png',
      buttonSrc: ASIGN.cdnUrl + '/common/button@3x.png'
    }
  },
  props: {
    prizeImage: {
      type: String,
      default: ''
    },
    prizeName: {
      type: String,
      default: ''
    },
    prizeLink: {
      type: String,
      default: ''
    },
    countData: {
      type: Array,
      default() {
        return []
      }
    }
  },
  methods: {
    toLink () {
      if(this.countData.length) {
        this.reqAdCounter()
      }
      window.location.href = this.prizeLink
    },
    close () {
      this.$emit('close')
    },
    async reqAdCounter(id, position, isShow) {
      let res = await adCount(...this.countData)
      console.log(res)
    }
  }
}
</script>

<style lang="less" scoped>
.prize-tip{
  border-radius: .1rem;
  position: absolute;
  top: 0;
  width: 7.5rem;
  margin-top: 0.92rem;
  animation: prizeTipScale .8s;
  z-index: 101;
  .tip-wrap{
    position: relative;
    width: 7.5rem;
    height: 9.1rem;
    overflow: hidden;
    .light{
      width: 7.5rem;
      height: 7.5rem;
      animation: lightRotate 20s linear 0s normal none infinite;
      position: absolute;
      img{
        width: 100%;
        display: block;
      }
    }
    .prize-title{
      top: 1.08rem;
      left: 50%;
      margin-left: -3.1rem;
      width: 6.2rem;
      height: 1.6rem;
      position: absolute;
      img{
        width: 100%;
        display: block;
      }
    }
    .coin{
      top: 1.08rem;
      left: 50%;
      margin-left: -3.1rem;
      width: 6.2rem;
      height: 1.6rem;
      position: absolute;
      animation: coinScale .8s .7s forwards;
      img{
        width: 100%;
      }
    }
    .close{
      top: .58rem;
      right: .65rem;
      width: .5rem;
      height: .5rem;
      position: absolute;
      img{
        width: 100%;
        display: block;
      }
    }
    .hb-back{
      top: 3.1rem;
      left: 50%;
      margin-left: -3.25rem;
      width: 6.5rem;
      height: 4.5rem;
      position: absolute;
      img{
        width: 100%;
        display: block;
      }
    }
    .hb-inner{
      position: absolute;
      left: 50%;
      top: 5rem;
      margin-left: -3.05rem;
      width: 6.1rem;
      height: 5.4rem;
      background-color: #fff;
      border-radius: .2rem;
      animation: hbInnerUp .5s .7s forwards;
      .prize-img{
        margin: .16rem .16rem .14rem;
        width: 5.78rem;
        height: 3rem;
        border-radius: .1rem;
        overflow: hidden;
        display: block;
      }
      .title-name{
        font-size: .26rem;
        text-align: center;
        .nm{
          display: inline-block;
          height: .52rem;
          padding: 0 .2rem;
          line-height: .52rem;
          background-color: #F81E24;
          color: #fff;
          border-radius: .08rem;
          max-width: 5.18rem;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          word-break: keep-all;
          word-wrap: normal;
        }
      }
    }
    .hb-before{
      position: absolute;
      bottom: 0;
      left: 50%;
      margin-left: -3.25rem;
      width: 6.5rem;
      height: 2.7rem;
      img{
        width: 100%;
        display: block;
      }
    }
    .prize-btn{
      position: absolute;
      bottom: .34rem;
      left: 50%;
      margin-left: -1.05rem;
      width: 2.1rem;
      height: 2.14rem;
      img{
        width: 100%;
        display: block;
      }
    }
  }
}
@keyframes lightRotate{
  from {
    transform: rotate(0deg)
  }
  to {
    transform:rotate(360deg)
  }
}
@keyframes prizeTipScale {
  0% {
    transform: scale(.1);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes coinScale {
  0% {
    opacity: 0;
    transform: scale(.1);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}
@keyframes hbInnerUp {
  0% {
    top: 5rem;
  }
  100% {
    top: 2.68rem;
  }
}
</style>
