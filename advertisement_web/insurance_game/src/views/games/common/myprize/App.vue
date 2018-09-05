<template>
<div class="wrapper">
  <div v-if="myPrizeList.length == 0">
    <div class="noprize-img">
      <img :src="renSrc">
    </div>
    <p class="noprize-title">哎呀，这里什么也没有～</p>
    <p class="noprize-text">您还没有奖品，赶快去抽奖吧～</p>
  </div>
  <div v-else>
    <ul class="prize-list">
      <li class="prize-item" v-for="(item, index) in myPrizeList" :key="index">
        <a :href="item.link" class="prize-content">
          <div class="prize-img">
            <img :src="item.imageUrl">
          </div>
          <div class="prize-info">
            <div class="name ellipsis">{{item.title}}</div>
            <div class="desc ellipsis">{{item.description}}</div>
          </div>
          <div class="prize-arrow">
            <img :src="arrowSrc" alt="">
          </div>
        </a>
      </li>
    </ul>
    <p class="dixian"><span>我是有底线的</span></p>
  </div>
</div>
</template>

<script>
import ASIGN from '@/config/const'
import { getMyPrize } from '@/service/getData'
import { getUrlParams } from '../../../../utils/getUrlParams'
export default {
  name: 'App',
  data () {
    return {
      activityKey: '',
      path: '',
      arrowSrc: '',
      renSrc: '',
      imgUrl: '/common/myprize',
      myPrizeList: [{}]
    }
  },
  methods: {
    async getMyPrize () {
      let res = await getMyPrize(this.activityKey, this.path)
      console.log(res)
      if(res.retCode == '200') {
        this.myPrizeList = res.myPrizeList
      }
    },
    initImg() {
      this.arrowSrc = ASIGN.cdnUrl + this.imgUrl + '/arrow@3x.png'
      this.renSrc = ASIGN.cdnUrl + this.imgUrl + '/ren@3x.png'
    }
  },
  created () {
    this.activityKey = getUrlParams('activityKey')
    this.path = getUrlParams('path')
    this.initImg()
    this.getMyPrize()
  }
}
</script>

<style lang="less">
@baseUrl: '../../../../assets/styles/games';

@import '@{baseUrl}/common/base.less';
@import '@{baseUrl}/common/myprize/myprize.less';
</style>
