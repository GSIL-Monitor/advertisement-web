<template>
<div class="prize-display">
  <div class="display-list-wrap">
    <ul class="display-list">
      <li><img :src="prizeSrc1"></li>
      <li><img :src="prizeSrc2"></li>
      <li><img :src="prizeSrc3"></li>
      <li><img :src="prizeSrc4"></li>
      <li><img :src="prizeSrc5"></li>
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
      prizeSrc1: ASIGN.cdnUrl + '/dahongbao/common/200@3x.png',
      prizeSrc2: ASIGN.cdnUrl + '/dahongbao/common/50@3x.png',
      prizeSrc3: ASIGN.cdnUrl + '/dahongbao/common/5@3x.png',
      prizeSrc4: ASIGN.cdnUrl + '/dahongbao/common/fudai@3x.png',
      prizeSrc5: ASIGN.cdnUrl + '/dahongbao/common/suiji@3x.png'
    }
  },
  props: {},
  methods: {},
  computed: {}
}
</script>

<style lang="less" scoped>
.prize-display {
  overflow: hidden;
  position: absolute;
  width: 7.5rem;
  height: 1.7rem;
  bottom: 1.37rem;
  left: 0;
  .display-list-wrap {
    width: 7.5rem;
    height: 3.07rem;
    overflow-x: auto;
    overflow-y: hidden;
    .display-list {
      width: 11.8rem;
      margin-left: .4rem;
      overflow: hidden;
      li {
        float: left;
        width: 1.7rem;
        height: 1.7rem;
        margin-right: .4rem;
        img {
          display: block;
          width: 100%;
        }
      }
      li:last-child {
        margin-right: .4rem;
      }
    }
  }
}
</style>
