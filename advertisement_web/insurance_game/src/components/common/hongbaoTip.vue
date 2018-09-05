<template>
<transition>
  <div class="hb-alert-tip">
    <div class="center">
      <div class="hb-close" @click="closeTip">
        <img :src="alertSrc">
      </div>
      <a :href="hbObj.hbLink" class="adLink">
        <img :src="hbObj.hbImgUrl" alt="">
      </a>
    </div>
  </div>
</transition>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'hongbaoTip',
  data () {
    return {
      alertSrc: ASIGN.cdnUrl + '/collection/common/hb-alert-close.png'
    }
  },
  props: {
    hbObj: {
      type: Object,
      default() {
        return {
          hbLink: '',
          hbImgUrl: ''
        }
      }
    }
  },
  methods: {
    closeTip () {
      this.$emit('closeTip')
    }
  },
  computed: {
  }
}
</script>

<style lang="less" scoped>
.hb-alert-tip{
  width: 6.8rem;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  margin: -3rem 0 0 0;
  z-index:101;
  .center{
    .hb-close{
      margin-left: 6.1rem;
      margin-bottom: .28rem;
      width: .7rem;
      height: .7rem;
      img{
        width: 100%;
        display: block;
      }
    }
    .adLink{
      width: 100%;
      display: block;
      margin: 0.4rem auto;
      img{
        width: 100%;
        display: block;
      }
    }
  }
}
</style>
