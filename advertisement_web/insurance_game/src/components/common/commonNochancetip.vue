<template>
  <div>
    <div class="tip-box" :style="style">
      <div class="nochance-img"><img :src="tittleSrc"></div>
      <div class="game-box-img">
        <a :href="gameJumpUrl">
          <img :src="gameJumpImage">
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import { getZIndex } from '../../utils/zindex'
import ASIGN from '@/config/const'
export default {
  name: 'commonNochancetip',
  data () {
    return {
      zIndex: getZIndex() + 3,
      tittleSrc: ASIGN.cdnUrl + '/common/tittle@3x.png'
    }
  },
  props: {
    gameJumpImage: {
      type: String,
      default: ''
    },
    gameJumpUrl: {
      type: String,
      default: ''
    }
  },
  methods: {
  },
  computed: {
    style () {
      return {
        'z-index': this.zIndex
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .tip-box{
    position: fixed;
    left: 50%;
    top: 50%;
    margin-left: -3.25rem;
    margin-top: -3.5rem;
    width: 6.5rem;
    height: 7rem;
    border-radius: .16rem;
    .nochance-img{
      width: 6.5rem;
      height: 1.28rem;
      margin: 0 auto .3rem;
      img{
        width: 100%;
      }
    }
    .game-box-img{
      width: 6.5rem;
      height: 4rem;
      margin: 0 auto .6rem;
      img{
        width: 100%;
      }
    }
  }
</style>
