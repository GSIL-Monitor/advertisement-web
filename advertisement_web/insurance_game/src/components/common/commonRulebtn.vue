<template>
  <a href="javascript:;" class="rule" @click="showRuleModal">
    <img :src="picSrc">
  </a>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'CommonRulebtn',
  data () {
    return {
      picSrc: ''
    }
  },
  props: {
    imgUrl: {
      type: String,
      default: '/common'
    }
  },
  methods: {
    showRuleModal () {
      this.$emit('showRuleModal')
    }
  },
  created() {
    this.picSrc = ASIGN.cdnUrl + this.imgUrl + '/rule@3x.png'
  }
}
</script>

<style lang="less" scoped>
  .rule{
    width: .98rem;
    height: .4rem;
    position: absolute;
    top: .14rem;
    left: .3rem;
    img{
      display: block;
      width: 100%;
    }
  }
</style>
