<template>
<div>
  <div class="cbt-top">
    <img :src="imgSrc[0]">
  </div>
  <div class="treasure-map">
    <img :src="imgSrc[1]">
  </div>
  <a href="">
    <div class="treasure">
      <img :src="imgSrc[2]">
    </div>
    <div class="tag1">
      <img :src="imgSrc[3]">
    </div>
  </a>
  <a href="">
    <div class="boat">
      <img :src="imgSrc[4]">
    </div>
    <div class="tag2">
      <img :src="imgSrc[5]">
    </div>
  </a>
  <a href="">
    <div class="castle">
      <img :src="imgSrc[6]">
    </div>
    <div class="tag3">
      <img :src="imgSrc[7]">
    </div>
  </a>
  <a href="/nhshb.html">
    <div class="coconut_tree1"></div>
    <div class="tag4">
      <img :src="imgSrc[8]">
    </div>
  </a>
  <a href="javascript:;">
    <div class="pyramid">
      <img :src="imgSrc[9]">
    </div>
    <div class="tag5">
      <img :src="imgSrc[10]">
    </div>
  </a>
  <hongbao-tip v-if="isShowHbTip" @closeTip="closeTip" :hbObj="popUpAdvertisement"></hongbao-tip>
  <overlay v-if="isShowHbTip"></overlay>
</div>
</template>

<script>
import modal from '@/components/common/modal'
import overlay from '@/components/common/overlay'
import loading from '@/components/common/loading'
import hongbaoTip from '@/components/common/hongbaoTip'
import ASIGN from '@/config/const'
import { getChance } from '@/service/getData'
import { getUrlParams } from '@/utils/getUrlParams'

export default {
  name: 'App',
  data() {
    return {
      isShowOverlay: false,
      isShowLoading: false,
      isShowHbTip: true,
      imgUrl: '/collection/cbt',
      imgSrc: [],
      popUpAdvertisement: {
        hbLink: '#',
        hbImgUrl: ''
      },
      channel: ''
    }
  },
  components: {
    modal,
    overlay,
    loading,
    hongbaoTip
  },
  methods: {
    closeTip() {
      this.isShowHbTip = false
    },
    initImg() {
      this.imgSrc = [
        ASIGN.cdnUrl + this.imgUrl + '/cbt_top.png',
        ASIGN.cdnUrl + this.imgUrl + '/land.png',
        ASIGN.cdnUrl + this.imgUrl + '/treasure.png',
        ASIGN.cdnUrl + this.imgUrl + '/tag_lhf.png',
        ASIGN.cdnUrl + this.imgUrl + '/boat.png',
        ASIGN.cdnUrl + this.imgUrl + '/tag_qvip.png',
        ASIGN.cdnUrl + this.imgUrl + '/castle.png',
        ASIGN.cdnUrl + this.imgUrl + '/tag_tlb.png',
        ASIGN.cdnUrl + this.imgUrl + '/tag_ysj.png',
        ASIGN.cdnUrl + this.imgUrl + '/pyramid.png',
        ASIGN.cdnUrl + this.imgUrl + '/tag_jjdl.png'
      ]
    },
    async initData() {
      this.channel = getUrlParams('channel')
      this.initImg()
      let res = await getChance(this.activityKey, this.channel, this.path)
      console.log(res)
      if (res.retCode === '200') {
        this.chance = res.chance
        if(res.popUpAdvertisement.link && res.popUpAdvertisement.imageUrl) {
          this.$set(this.popUpAdvertisement, 'hbLink', res.popUpAdvertisement.link)
          this.$set(this.popUpAdvertisement, 'hbImgUrl', res.popUpAdvertisement.imageUrl)
          this.isShowHbTip = true
        }
      }
    }
  },
  created() {
    this.initData()
  }
}
</script>

<style lang='less'>
@baseUrl: '../../../../assets/styles/games';

@import '@{baseUrl}/common/base.less';
@import '@{baseUrl}/collection/common/common.less';
@import '@{baseUrl}/collection/cbt/cbt.less';
</style>
