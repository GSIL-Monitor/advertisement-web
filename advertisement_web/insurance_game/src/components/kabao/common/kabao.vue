<template>
<div class="kabao">
  <div id="bb-bookblock" class="bb-bookblock">
    <div class="bb-item">
      <div class="wallet-bg"></div>
      <div class="walletUp shake-animation" id="walletUp"></div>
    </div>
    <div class="bb-item">
      <div class="walletUp"></div>
      <div class="wallet">
        <v-touch @swipeup="swipeup">
          <div class="loading-car" id="cars" ref="car">
            <span class="no-chance">亲，您的抽奖次数用光了哦！</span>
            <div v-for="(item, index) in items" :key="index">
              <img :class="item" :style="{'top': initTop(index + 1)}" v-if="(index + 1) <= 4" :src="initSrc[index]">
            </div>
          </div>
        </v-touch>
        <div id="prizeBtn" class="wallet-btn btn-animation"></div>
        <img class="slip-up slipup-animation" :src="slipUpSrc" alt="">
        <img class="girl1" id="girl1" :src="girl1Src" alt="">
        <img class="girl2" id="girl2" :src="girl2Src" alt="">
        <img class="wallet-pointer pointer-animation" id="pointer" :src="pointerSrc" alt="">
      </div>
    </div>
  </div>
</div>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'kabao',
  data() {
    return {
      isShowAddAnimation: true,
      items: [],
      initSrc: [],
      imgUrl: '/kabao/common',
      loadingSrc: '',
      slipUpSrc: '',
      girl1Src: '',
      girl2Src: '',
      pointerSrc: ''
    }
  },
  props: {
    chance: {
      type: Number,
      default: 0
    },
    lastImg: {
      type: String,
      default: ''
    },
    isShowCar: {
      type: Boolean,
      default: true
    }
  },
  watch: {
    isShowCar () {
      this.isShowAddAnimation = false
    },
    chance () {
      for(let i = 0; i < this.chance; i++) {
        this.$set(this.items, i, {'loading-animation': false,'leave-animation': false})
        this.$set(this.initSrc, i, this.loadingSrc)
      }
    },
    lastImg (newValue, oldValue) {
      this.$set(this.initSrc, this.items.length-1, newValue)
      this.$set(this.items, this.items.length-1, {'loading-animation': false,'leave-animation': false})
    }
  },
  methods: {
    initTop (n) {
      if(n == this.chance && n > 4) {
        return 3 * 0.2 + 'rem'
      } else if (n == this.chance && n <= 4) {
        return (n - 1) * 0.2 + 'rem'
      } else {
        return (n - 1) * 0.2 + 'rem'
      }
    },
    swipeup () {
      this.$set(this.items, this.items.length-1, {'loading-animation': false,'leave-animation': true})
      // this.items[this.chance]['leave-animation'] = true
      setTimeout(() => {
        this.items.pop()
        if(this.chance > 4) {
          this.items.push({'loading-animation': true,'leave-animation': false}) 
        }else {
          this.$set(this.items, this.items.length-1,{'loading-animation': true,'leave-animation': false})
        }
        this.$emit('swipeupEnd')
      }, 800)
    },
    initImg() {
      this.loadingSrc = ASIGN.cdnUrl + this.imgUrl + '/loading@3x.png'
      this.slipUpSrc = ASIGN.cdnUrl + this.imgUrl + '/slipup@3x.png'
      this.girl1Src = ASIGN.cdnUrl + this.imgUrl + '/girl1@3x.png'
      this.girl2Src = ASIGN.cdnUrl + this.imgUrl + '/girl2@3x.png'
      this.pointerSrc = ASIGN.cdnUrl + this.imgUrl + '/pointer@3x.png'
    }
  },
  created() {
    this.initImg()
  }
}
</script>

<style lang="less" scoped>
@imgUrl: '/static/images/games/kabao/common';
@keyframes shake {
  0% {
    transform: rotateZ(-4deg);
  }
  100% {
    transform: rotateZ(4deg);
  }
}

@keyframes slipup {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(0.4rem);
  }
}

@keyframes updown {
  from {
    background-image: url('@{imgUrl}/btn-down@3x.png');
  }
  to {
    background-image: url('@{imgUrl}/btn-up@3x.png');
  }
}

@keyframes hand {
  0% {
    transform: rotateZ(0) translate(0, 0);
  }
  100% {
    transform: translate(1rem, -2rem) rotateZ(20deg);
  }
}

@keyframes leftfly {
  0% {
    right: 7.43rem;
  }
  100% {
    right: 4.43rem;
  }
}

@keyframes rightfly {
  0% {
    right: -3.11rem;
  }
  100% {
    right: -0.11rem;
  }
}

@keyframes leave {
  0% {
    left: 0rem;
    top: 0.6rem;
    width: 5.6rem;
    height: 2.91rem;
  }
  100% {
    width: 0;
    height: 0;
    top: -6.4rem;
    left: 7rem;
  }
}

@keyframes load {
  0% {
    top: 0.6rem;
  }
  100% {
    top: 0.48rem;
  }
}

.kabao {
  .walletUp {
    float: left;
    width: 6.6rem;
    height: 8rem;
    background: url('@{imgUrl}/wallet-up@3x.png') no-repeat;
    background-size: 100% 100%;
  }
  .shake-animation {
    -webkit-animation: shake 0.4s linear 0.2s infinite alternate;
    animation: shake 0.4s linear 0.2s infinite alternate;
    transform-origin: center;
  }
  .wallet-bg {
    float: left;
    width: 6.6rem;
    height: 8rem;
    background-size: 100% 100%;
  }
  .wallet {
    float: left;
    width: 6.6rem;
    height: 8rem;
    background: url('@{imgUrl}/wallet@3x.png') no-repeat;
    background-size: 100% 100%;
    .slipup-animation {
      -webkit-animation: slipup 0.5s linear 0s infinite alternate;
      animation: slipup 0.5s linear 0s infinite alternate;
      transform-origin: center;
    }
    .btn-animation {
      -webkit-animation: updown 0.5s ease 0s infinite alternate;
      animation: updown 0.5s ease 0s infinite alternate;
    }
    .pointer-animation {
      -webkit-animation: hand 0.5s linear 0s infinite alternate;
      animation: hand 0.5s linear 0s infinite alternate;
      transform-origin: center;
      -webkit-animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
      animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
    }
    .leftFly-animation {
      -webkit-animation: leftfly 0.5s linear 0s 1 normal;
      animation: leftfly 0.5s linear 0s 1 normal;
      transform-origin: center;
      -webkit-animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
      animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
      animation-fill-mode: forwards;
      -webkit-animation-fill-mode: forwards;
    }
    .rightFly-animation {
      -webkit-animation: rightfly 0.5s linear 0s 1 normal;
      animation: rightfly 0.5s linear 0s 1 normal;
      transform-origin: center;
      -webkit-animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
      animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
      animation-fill-mode: forwards;
      -webkit-animation-fill-mode: forwards;
    }
    .girl1 {
      position: absolute;
      width: 2.44rem;
      height: 2.5rem;
      right: 7.43rem;
      bottom: -0.49rem;
    }
    .girl2 {
      position: absolute;
      width: 2.44rem;
      height: 2.5rem;
      right: -3.11rem;
      bottom: -0.47rem;
    }
    .slip-up {
      width: 1.01rem;
      height: 1.27rem;
      position: absolute;
      right: 0.69rem;
      bottom: 2.75rem;
    }
    .wallet-pointer {
      position: absolute;
      width: 1.33rem;
      right: -0.075rem;
      top: 2.103rem;
    }
    .wallet-btn {
      width: 2.5rem;
      height: 2.42rem;
      position: absolute;
      right: 2.05rem;
      bottom: 1.08rem;
      background: url('@{imgUrl}/btn-down@3x.png') no-repeat;
      background-size: 100% 100%;
    }
    .loading-car {
      position: absolute;
      top: 0.348rem;
      right: 0.62rem;
      width: 5.6rem;
      img {
        width: 5.6rem;
        height: 2.91rem;
        position: absolute;
        left: 0;
        background: #fbb9c8;
        border: 3px solid #ffffff;
        border-radius: 0.2rem;
      }
      .no-chance {
        width: 5.9rem;
        height: 3.4rem;
        font-size: 14px;
        text-align: center;
        display: block;
        line-height: 3.4rem;
      }
      .car-bg1 {
        top: 0;
      }
      .car-bg2 {
        top: 0.2rem;
      }
      .car-bg3 {
        top: 0.4rem;
      }
      .car-bg4 {
        top: 0.6rem;
      }
      .leave-animation {
        -webkit-animation: leave 1s linear 0s 1 normal;
        animation: leave 1s linear 0s 1 normal;
        transform-origin: center;
        -webkit-animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
        animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
        animation-fill-mode: forwards;
        -webkit-animation-fill-mode: forwards;
      }
      .loading-animation {
        -webkit-animation: load 0.4s ease 0s infinite alternate;
        animation: load 0.4s ease 0s infinite alternate;
      }
    }
  }
}
</style>
