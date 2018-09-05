<template>
  <a class="prize" :href="myPrizeHref">
    <img :src="picSrc">
  </a>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'commonPrize',
  data () {
    return {
      myPrizeHref: '/myprize.html',
      picSrc: ''
    }
  },
  props: {
    imgUrl: {
      type: String,
      default: '/common'
    },
    activityKey: {
      type: String,
      default: ''
    },
    path: {
      type: String,
      default: ''
    }
  },
  created() {
    this.myPrizeHref = '/myprize.html?activityKey=' + this.activityKey + '&path=' + this.path
    this.picSrc = ASIGN.cdnUrl + this.imgUrl + '/my-prize@3x.png'
  }
}
</script>

<style lang="less" scoped>
  .prize{
    color: #fff;
    width: 1.37rem;
    height: 1.37rem;
    position: absolute;
    top: 0;
    right: 0;
    img{
      width: 1.37rem;
      height: 1.37rem;
    }
  }
</style>
