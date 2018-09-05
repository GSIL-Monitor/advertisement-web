<template>
<div class="rotary-table">
  <div class="bg" id="rotateBg">
    <img :src="bgSrc" alt="">
  </div>
  <div class="marquee">
    <img :src="marqueeSrc" alt="">
  </div>
  <div class="pointer">
    <img :src="pointerSrc" alt="">
  </div>
  <div class="click" @click="startRotate">
    <img :src="clickSrc" alt="">
  </div>
  <div class="finger-content" v-show="isShowFinger">
    <div class="finger-circle">
      <img :src="fingerCircleSrc" alt="">
    </div>
    <div class="finger-pointer">
      <img :src="fingerPointerSrc" alt="">
    </div>
  </div>
</div>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'rotaryTable',
  data() {
    return {
      chance: 0,
      off: true,
      bgSrc: '',
      clickSrc: '',
      pointerSrc: '',
      marqueeSrc: '',
      marqueeSrc1: '',
      marqueeSrc2: '',
      fingerCircleSrc: '',
      fingerPointerSrc: ''
    }
  },
  props: {
    isShowFinger: {
      type: Boolean,
      default: true
    },
    imgUrl: {
      type: String,
      default: '/zhuanpan/common'
    }
  },
  methods: {
    startRotate() {
      this.$emit('startRotate')
    },
    paomadengAnimation() {
      if (this.off) {
        this.marqueeSrc = this.marqueeSrc1
        this.off = !this.off
      } else {
        this.marqueeSrc = this.marqueeSrc2
        this.off = !this.off
      }
    },
    initImg() {
      this.bgSrc = ASIGN.cdnUrl + this.imgUrl + '/prize.png'
      this.clickSrc = ASIGN.cdnUrl + this.imgUrl + '/click.png'
      this.pointerSrc = ASIGN.cdnUrl + this.imgUrl + '/pointer.png'
      this.marqueeSrc1 = ASIGN.cdnUrl + this.imgUrl + '/paomadeng0.png'
      this.marqueeSrc2 = ASIGN.cdnUrl + this.imgUrl + '/paomadeng1.png'
      this.fingerCircleSrc = ASIGN.cdnUrl + this.imgUrl + '/finger-circle.png'
      this.fingerPointerSrc = ASIGN.cdnUrl + this.imgUrl + '/finger-pointer.png'
    }
  },
  computed: {},
  mounted() {
    this.initImg()
    setInterval(this.paomadengAnimation, 1000)
  }
}
</script>

<style lang="less" scoped>
@imgUrl: '../../../assets/images/games/zhuanpan/common';
.rotary-table {
  width: 6rem;
  height: 6rem;
  .marquee {
    position: absolute;
    left: 0;
    top: 0;
    width: 6rem;
    height: 6rem;
    img {
      width: 100%;
      display: block;
    }
  }
  .bg {
    position: absolute;
    left: 0;
    top: 0;
    width: 6rem;
    height: 6rem;
    img {
      width: 100%;
      display: block;
    }
  }
  .pointer {
    position: absolute;
    left: 50%;
    top: -0.35rem;
    width: 1.38rem;
    height: 1.35rem;
    transform: translateX(-50%);
    img {
      width: 100%;
      display: block;
    }
  }
  .click {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 1.4rem;
    height: 1.4rem;
    img {
      width: 100%;
      display: block;
    }
  }
  .finger-content {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 0.1rem;
    height: 0.1rem;
    pointer-events: none;
    .finger-circle {
      width: 0.8rem;
      height: 0.8rem;
      animation: finger-circle 1.5s ease-out 0.3s infinite;
      img {
        display: block;
        width: 100%;
      }
    }
    .finger-pointer {
      position: absolute;
      left: 0;
      top: 0;
      width: 1.96rem;
      height: 1.94rem;
      opacity: 0;
      animation: finger-pointer 1.5s ease-out 0s infinite;
      img {
        display: block;
        width: 100%;
      }
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
}
</style>
