<template>
<div class="prize-display">
  <div class="title">
    <img :src="titleSrc" alt="">
  </div>
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
  data() {
    return {
      prizeSrc1: ASIGN.cdnUrl + '/guajiang/common/display1.png',
      prizeSrc2: ASIGN.cdnUrl + '/guajiang/common/display2.png',
      prizeSrc3: ASIGN.cdnUrl + '/guajiang/common/display3.png',
      prizeSrc4: ASIGN.cdnUrl + '/guajiang/common/display4.png',
      prizeSrc5: ASIGN.cdnUrl + '/guajiang/common/display5.png',
      prizeSrc6: ASIGN.cdnUrl + '/guajiang/common/display6.png',
      titleSrc: ASIGN.cdnUrl + '/guajiang/common/display_title.png'
    }
  },
  props: {},
  methods: {},
  computed: {}
}
</script>

<style lang="less" scoped>
.prize-display {
  position: relative;
  width: 7.5rem;
  .title {
    width: 4.72rem;
    height: .35rem;
    margin: .27rem auto 0;
    img {
      width: 100%;
      display: block;
    }
  }
  .display-list-wrap {
    width: 7.5rem;
    overflow-x: auto;
    overflow-y: hidden;
    .display-list {
      margin-top: .45rem;
      margin-left: .3rem;
      overflow: hidden;
      width: 12.36rem;
      li {
        float: left;
        width: 1.78rem;
        height: 1.9rem;
        margin-right: .28rem;
        img {
          width: 100%;
          display: block;
        }
      }
    }
  }
}
</style>
