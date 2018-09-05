<template>
<div class="mainArea">
  <div class="hammer" :style="hammerCss"></div>
  <ul class="egg-list">
    <li class="egg-wrapper" v-for="(item, index) in eggs" :key="index">
      <div class="egg" :class="{up: item}" @click="hitEgg"></div>
    </li>
  </ul>
</div>
</template>

<script>
export default {
  name: 'hitEgg',
  data() {
    return {
      eggs: [false, false, false, false, false, false, false, false, false],
      timer: null
    }
  },
  props: {
    hammerCss: {
      type: Object,
      default () {
        return {}
      }
    },
    isHited: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    raiseEgg() {
      let i = 0
      for (let j = 0; j < this.eggs.length; j++) {
        this.timer = setTimeout(() => {
          this.resetEgg()
          if(!this.isHited) {
            this.$set(this.eggs, i, true)
          }
          i++
          if (i === this.eggs.length) {
            setTimeout(this.raiseEgg, 300)
          }
        }, 300 * j)
      }
    },
    resetEgg() {
      for (let i = 0; i < this.eggs.length; i++) {
        this.eggs[i] = false
      }
    },
    hitEgg(e) {
      this.$emit('hitEgg', e)
    }
  },
  computed: {},
  created() {
    this.raiseEgg()
  }
}
</script>

<style lang="less" scoped>
@imgUrl: '/static/images/games/zajindan/common';
.mainArea {
  position: relative;
  width: 7.5rem;
  height: 7.97rem;
  margin: 0.37rem 0 0;
  .hammer {
    position: absolute;
    top: -1.2rem;
    right: .6rem;
    width: .94rem;
    height: .97rem;
    transform-origin: right bottom;
    animation: rotateHammer .6s 0s infinite;
    background: url('@{imgUrl}/hammer.png') no-repeat;
    background-size: 100% 100%;
    z-index: 2;
  }
  .egg-list {
    li {
      position: absolute;
      display: inline-block;
      width: 1.82rem;
      height: 2.38rem;
      background: url('@{imgUrl}/egg_shadow.png') no-repeat;
      background-size: 100% 100%;
    }
    .egg {
      position: absolute;
      width: 1.82rem;
      height: 2.38rem;
      left: 0;
      right: 0;
      bottom: 0;
      transition: all .2s;
      background: url('@{imgUrl}/gold_egg.png') no-repeat;
      background-size: 100% 100%;
    }
    .egg-gift {
      background: url('@{imgUrl}/egg_gift.png') no-repeat;
      background-size: 100% 100%;
    }
    .egg-smashed {
      background: url('@{imgUrl}/egg_smashed.png') no-repeat;
      background-size: 100% 100%;
    }
    .up {
      bottom: .3rem;
    }
    li:nth-child(4),
    li:nth-child(5),
    li:nth-child(6),
    {
      top: 2.55rem;
    }
    li:nth-child(7),
    li:nth-child(8),
    li:nth-child(9) {
      top: 5.13rem;
    }
    li:nth-child(1),
    li:nth-child(4),
    li:nth-child(7) {
      left: .6rem;
    }
    li:nth-child(2),
    li:nth-child(5),
    li:nth-child(8) {
      left: 2.84rem;
    }
    li:nth-child(3),
    li:nth-child(6),
    li:nth-child(9) {
      left: 5.08rem;
    }
  }
}

@keyframes rotateHammer {
  0% {
    transform: rotate(-15deg);
  }
  50% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(15deg);
  }
}
</style>
