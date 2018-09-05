<template>
  <div class="prize-display">
    <div class="display-list-wrap">
      <ul class="display-list">
        <li><img :src="prizeSrc1"></li>
        <li><img :src="prizeSrc2"></li>
        <li><img :src="prizeSrc3"></li>
        <li><img :src="prizeSrc4"></li>
        <li><img :src="prizeSrc5"></li>
        <li><img :src="prizeSrc6"></li>
      </ul>
    </div>
  </div>
</template>

<script>
import ASIGN from '@/config/const'
export default {
  name: 'prizeList',
  data () {
    return {
      prizeSrc1: ASIGN.cdnUrl + '/fanpai/common/display1.png',
      prizeSrc2: ASIGN.cdnUrl + '/fanpai/common/display2.png',
      prizeSrc3: ASIGN.cdnUrl + '/fanpai/common/display3.png',
      prizeSrc4: ASIGN.cdnUrl + '/fanpai/common/display4.png',
      prizeSrc5: ASIGN.cdnUrl + '/fanpai/common/display5.png',
      prizeSrc6: ASIGN.cdnUrl + '/fanpai/common/display6.png'
    }
  },
  props: {
  },
  methods: {
  },
  computed: {
  }
}
</script>

<style lang="less" scoped>
.prize-display{
  position: relative;
  width: 7.5rem;
  .display-list-wrap{
    width: 7.5rem;
    height: 2rem;
    overflow-x: auto;
    overflow-y: hidden;
    background: rgba(0,0,0,0.6);
    .display-list{
      margin-top: .31rem;
      margin-left: .4rem;
      overflow: hidden;
      width: 12rem;
      li{
        float: left;
        width: 1.4rem;
        height: 1.4rem;
        margin-right: .6rem;
        img{
          width: 100%;
          display: block;
        }
      }
    }
  }
}
</style>
