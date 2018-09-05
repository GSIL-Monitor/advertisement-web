<template>
<div class="hongbao">
  <div class="face" :class="{'shake-animation':activeClass.shake,'change-animation':activeClass.change}"></div>
  <div class="attack-btn" @click="bindClick">
    <img class="heigh-animation" :src="initialSrc" alt="">
  </div>
</div>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'hongBao',
  data () {
    return {
      activeClass: {
        shake: true,
        change: false
      },
      initialSrc: ASIGN.cdnUrl + '/dahongbao/common/initial-btn@3x.png'
    }
  },
  props: {
    successReq: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    successReq () {
      this.activeClass.shake = false
      this.activeClass.change = true
    }
  },
  methods: {
    bindClick () {
      this.$emit('bindClick')
    }
  },
  computed: {}
}
</script>

<style lang="less" scoped>
@imgUrl: '/static/images/games/dahongbao/common';
.hongbao {
  position: absolute;
  bottom: 0;
  width: 100%;
  margin: 0 auto;
  .face {
    position: absolute;
    left: 50%;
    margin-left: -2.5rem;
    width: 5rem;
    height: 5.8rem;
    bottom: 5.52rem;
    background: url('@{imgUrl}/initial-face@3x.png')
      no-repeat;
    background-size: 100% 100%;
  }
  .shake-animation {
    -webkit-animation: shake 0.4s linear 0.1s infinite alternate;
    -webkit-animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
    animation: shake 0.4s linear 0.1s infinite alternate;
    animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
    transform-origin: center;
  }
  .change-animation {
    -webkit-animation: change1 0.01s linear 0s infinite alternate,
      change2 0.1s linear 0s infinite alternate,
      change3 0.01s linear 0.41s infinite alternate,
      change2 0.1s linear 0.41s infinite alternate,
      change4 0.01s linear 0.92s infinite alternate,
      change2 0.1s linear 0.92s infinite alternate,
      change5 0.01s linear 1.33s infinite alternate,
      change2 0.1s linear 1.33s infinite alternate;
    -webkit-animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
    animation: change1 0.01s linear 0s infinite alternate,
      change2 0.1s linear 0s infinite alternate,
      change3 0.01s linear 0.41s infinite alternate,
      change2 0.1s linear 0.41s infinite alternate,
      change4 0.01s linear 0.92s infinite alternate,
      change2 0.1s linear 0.92s infinite alternate,
      change5 0.01s linear 1.33s infinite alternate,
      change2 0.1s linear 1.33s infinite alternate;
    animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
    transform-origin: bottom;
  }
  .attack-btn {
    position: absolute;
    left: 50%;
    margin-left: -1.22rem;
    bottom: 3.48rem;
    width: 2.44rem;
    height: 2.04rem;
    img {
      width: 2.44rem;
      height: 2.04rem;
    }
    .heigh-animation {
      -webkit-animation: heigh 0.4s linear 0.1s infinite alternate;
      -webkit-animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
      animation: heigh 0.4s linear 0.1s infinite alternate;
      animation-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
      transform-origin: bottom;
    }
  }
}
@keyframes shake {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(1.1);
  }
}

@-webkit-keyframes shake {
  0% {
    -webkit-transform: scale(1);
  }
  100% {
    -webkit-transform: scale(1.1);
  }
}
@keyframes change1 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face1@3x.png');
  }
}

@-webkit-keyframes change1 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face1@3x.png');
  }
}
@keyframes change2 {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(1.1);
  }
}

@-webkit-keyframes change2 {
  0% {
    -webkit-transform: scale(1);
  }
  100% {
    -webkit-transform: scale(1.1);
  }
}
@keyframes change3 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face2@3x.png');
  }
}

@-webkit-keyframes change3 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face2@3x.png');
  }
}
@keyframes change4 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face3@3x.png');
  }
}

@-webkit-keyframes change4 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face3@3x.png');
  }
}
@keyframes change5 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face4@3x.png');
  }
}

@-webkit-keyframes change5 {
  0%,
  100% {
    background-image: url('@{imgUrl}/face4@3x.png');
  }
}
@keyframes heigh {
  0% {
    transform: scaleY(1);
  }
  100% {
    transform: scaleY(0.863);
  }
}

@-webkit-keyframes heigh {
  0% {
    transform: scaleY(1);
  }
  100% {
    transform: scaleY(0.863);
  }
}
</style>
