<template>
<div>
  <div class="mainArea" ref="mainArea">
    <div class="result-box">
      <div class="result-cont">
        <img :src="prizeSrc">
      </div>
    </div>
    <canvas width="325" height="180" class="canvas" ref="cover" @touchstart="startCover" @touchmove="moveCover" @touchend="endCover"></canvas>
  </div>
  <div class="start-tip" ref="startTip" v-if="isShowStartTip">
    <div>使</div>
    <div>劲</div>
    <div>刮</div>
    <div>我</div>
  </div>
  <div class="start-box" v-if="isShowStartBox" @click="bindClickStart">
    <div class="start-btn">
      <img :src="startSrc">
    </div>
    <div class="finger-area">
      <div class="finger-circle">
        <img :src="fingerCircleSrc" alt="">
      </div>
      <div class="finger">
        <img :src="fingerPointerSrc" alt="">
      </div>
    </div>
  </div>
</div>
</template>

<script>
import Lottery from '@/utils/guajiang'
import ASIGN from '@/config/const'
export default {
  name: 'scratchCard',
  data() {
    return {
      coverPng: ASIGN.cdnUrl + '/guajiang/common/cover.png',
      startSrc: ASIGN.cdnUrl + '/guajiang/common/start_button.png',
      fingerCircleSrc: ASIGN.cdnUrl + '/guajiang/common/finger-circle.png',
      fingerPointerSrc: ASIGN.cdnUrl + '/guajiang/common/finger-pointer.png',
      lottery: null,
      lock:false,
      percent: 0,
      isShowStartTip: false,
      prizeSrc: ASIGN.cdnUrl + '/guajiang/iphonex/prize1.png'
    }
  },
  props: {
    isShowStartBox: {
      type: Boolean,
      default: true
    },
    canShowStartTip: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    drawPercent(percent) {
      if (percent) {
        this.isShowStartTip = false
        this.percent = percent
      } else {
        this.isShowStartTip = true
      }
    },
    initLottery() {
      this.lottery = new Lottery(this.$refs.mainArea, this.$refs.cover, this.coverPng, this.drawPercent)
      this.lottery.init()
      this.lock = false
    },
    startCover() {
      this.isShowStartTip = false
    },
    moveCover(e) {
    },
    endCover (e) {
      if(this.percent > 20){
        this.lock = true
        setTimeout(() => {
          this.lottery.init()
          this.lock = false
          this.$emit('endCover')
        }, 500)
      }
    },
    bindClickStart() {
      this.isShowStartTip = true
      this.$emit('bindClickStart')
    }
  },
  watch: {
    canShowStartTip(newValue, oldValue) {
      console.log('dsfsdf')
      this.isShowStartTip = false
    }
  },
  mounted() {
    this.initLottery()
  }
}
</script>

<style lang="less" scoped>
@imgUrl: '/static/images/games/guajiang/common';
@font-face {
    font-family: HYZZTNTW;
    src: url('../../../assets/fonts/HYZhuZiTongNianTiW.ttf')
}
.mainArea {
  position: absolute;
  top: 4.64rem;
  left: 50%;
  transform: translate(-50%, 0);
  width: 7.1rem;
  height: 3.96rem;
  background: url('@{imgUrl}/card_bg.png') no-repeat;
  background-size: 100% 100%;
  .canvas {
    position: absolute;
    left: .3rem;
    top: .18rem;
    width: 6.5rem;
    height: 3.6rem;
    touch-action: none;
  }
  .result-box{
    .result-cont {
      background: rgb(255, 246, 203);
      width: 6.5rem;
      height: 3.55rem;
      margin: 0.2rem auto;
      img{
        display: block;
        width: 2.08rem;
        height: 2.88rem;
        margin: .36rem auto 0;
      }
    }
  }
}

.start-box {
  position: absolute;
  top: 4.64rem;
  left: 50%;
  margin-left: -3.55rem;
  width: 7.1rem;
  height: 3.96rem;
  z-index: 3;
  .start-btn {
    position: absolute;
    left: 50%;
    top: 1.28rem;
    margin-left: -1.5rem;
    width: 3rem;
    height: 1rem;
    img {
      width: 100%;
      display: block;
    }
  }
  .finger-area {
    position: absolute;
    bottom: .38rem;
    right: .5rem;
    width: 2.14rem;
    height: 2.14rem;
    .finger-circle {
      width: 0.8rem;
      height: 0.8rem;
      animation: finger-circle 1.5s ease-out 0.3s infinite;
      img{
        display: block;
        width: 100%;
      }
    }
    .finger {
      position: absolute;
      left: 0;
      top: 0;
      width: 1.96rem;
      height: 1.94rem;
      opacity: 0;
      animation: finger-pointer 1.5s ease-out 0s infinite;
      img{
        display: block;
        width: 100%;
      }
    }
  }
}

.start-tip {
  z-index: 2;
  position: absolute;
  top: 6.6rem;
  left: 50%;
  width: 7.1rem;
  font-family: HYZZTNTW;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  pointer-events: none;
  text-align: center;
  color: #333;
  div {
    display: inline-block
  }
  div:nth-child(1) {
    animation: showTip1 1s ease-in-out infinite
  }
  div:nth-child(2) {
    animation: showTip2 1s ease-in-out infinite
  }
  div:nth-child(3) {
    animation: showTip3 1s ease-in-out infinite
  }
  div:nth-child(4) {
    animation: showTip4 1s ease-in-out infinite
  }
}

@keyframes finger-circle {
  0% {
    transform: scale(0, 0);
    opacity: 0;
  }
  25% {
    transform: scale(0.5, 0.5);
    opacity: 0.5;
  }
  50% {
    transform: scale(1, 1);
    opacity: 1;
  }
  80% {
    transform: scale(1, 1);
    opacity: 1;
  }
  90% {
    transform: scale(0.5, 0.5);
    opacity: 0.5;
  }
  100% {
    transform: scale(0, 0);
    opacity: 0;
  }
}

@keyframes finger-pointer {
  0% {
    transform: scale(1, 1);
    opacity: 0;
  }
  30% {
    transform: scale(0.7, 0.7);
    opacity: 1;
  }
  80% {
    transform: scale(0.7, 0.7);
    opacity: 1;
  }
  100% {
    transform: scale(0.7, 0.7);
    opacity: 0;
  }
}
</style>
