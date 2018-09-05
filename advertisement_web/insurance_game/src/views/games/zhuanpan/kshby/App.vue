<template>
<div id="app">
  <div class="body-bg">
    <img :src="bgSrc" >
  </div>
  <common-rulebtn @showRuleModal="isShowRule = true"></common-rulebtn>
  <common-prize :imgUrl="imgUrl" :activityKey="activityKey" :path="path"></common-prize>
  <rotary-table class="zhuanpan" :imgUrl="imgUrl" @startRotate="startRotate" :isShowFinger="isShowFinger"></rotary-table>
  <other-counter class="chance-box" :chances="chance" :imgUrl="imgUrl"></other-counter>
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <div slot="custom">
      <rule></rule>
    </div>
  </modal>
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip || isShowNomoretip" @clickOverlay="isShowRule = false;isShowPrizetip = false;isShowNomoretip = false;"></overlay>
  <loading v-if="isShowLoading"></loading>
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <common-prizetip :prizeLink="prizeLink" :countData="countData" :prizeName="prizeName" :prizeImage="prizeImage" v-if="isShowPrizetip" @close="isShowPrizetip = false"></common-prizetip>
  <common-nomoretip v-if="isShowNomoretip" @bindClose="isShowNomoretip = false"></common-nomoretip>
</div>
</template>

<script>
import modal from '@/components/common/modal'
import overlay from '@/components/common/overlay'
import loading from '@/components/common/loading'
import commonPrize from '@/components/common/commonPrize'
import commonRulebtn from '@/components/common/commonRulebtn'
import commonPrizetip from '@/components/common/commonPrizetip'
import commonNomoretip from '@/components/common/commonNomoretip'
import commonNochancetip from '@/components/common/commonNochancetip'

import rotaryTable from '@/components/zhuanpan/common/rotaryTable'
import otherCounter from '@/components/common/otherCounter'
import rule from '@/components/zhuanpan/kshby/rule'

import ASIGN from '@/config/const'

import { getChance, getLuck } from '@/service/getData'
import { getUrlParams } from '@/utils/getUrlParams'

import $ from 'jquery'
import '@/utils/awardRotate'

export default {
  name: 'App',
  data() {
    return {
      isShowRule: false,
      isShowOverlay: false,
      isShowLoading: false,
      isShowNochancetip: false,
      isShowPrizetip: false,
      isShowNomoretip: false,
      isShowFinger: true,
      bgSrc: '',
      chance: 0,
      gameJumpImage: '',
      gameJumpUrl: '',
      prizeName: '',
      prizeImage: '',
      prizeLink: '',
      path: 'zhuanpan',
      imgUrl: '/zhuanpan/kshby',
      activityKey: 'ceshi',
      channel: '',
      bRotate: false,
      countData: [],
      prizeList: ['神秘礼包', '10元红包', '50元红包', '200元红包'],
      popUpAdvertisement: {
        hbLink: '',
        hbImgUrl: ''
      }
    }
  },
  components: {
    commonNochancetip,
    commonNomoretip,
    commonPrizetip,
    commonRulebtn,
    otherCounter,
    commonPrize,
    rotaryTable,
    loading,
    overlay,
    modal,
    rule
  },
  methods: {
    async getChance() {
      let res = await getChance(this.activityKey, this.channel, this.path)
      console.log(res)
      if (res.retCode === '200') {
        this.chance = res.chance
      }
    },
    startRotate () {
      if(this.chance == 0) {
        this.isShowNomoretip = true
      } else {
        this.getLuck()
      }
    },
    async getLuck() {
      if (this.bRotate) {
        return
      }
      this.isShowFinger = false
      this.isShowLoading = true
      let res = await getLuck(this.activityKey, this.channel, this.path)
      console.log(res)
      this.isShowLoading = false
      if (res.retCode === '200') {
        this.rotateFn(res.angle, '')
        this.getChance()
        this.reqAdCount(res.prize.advertisementId, res.prize.position, true)
        this.prizeImage = res.prize.imageUrl
        this.prizeName = res.prize.title
        this.prizeLink = res.prize.link
      } else if (res.retCode == '801') {
        if (res.gameJumpImage && res.gameJumpUrl) {
          this.gameJumpImage = res.gameJumpImage
          this.gameJumpUrl = res.gameJumpUrl
          this.isShowNochancetip = true
          setTimeout(() => {
            window.location.href = res.gameJumpUrl
          }, 5000)
        } else {
          this.isShowNomoretip = true
        }
      }
    },
    rotateFn (angles, prizeName) {
      this.bRotate = !this.bRotate
      var _this = this
      this.$nextTick(() => {
        $('#rotateBg').stopRotate()
        $('#rotateBg').rotate({
          angle: 0,
          animateTo: angles + 3600,
          duration: 7000,
          callback() {
            _this.isShowFinger = true
            _this.bRotate = !_this.bRotate
            _this.isShowPrizetip = true
          }
        })
      })
    },
    async reqAdCount(id, position, isShow) {
      this.countData = [this.activityKey, this.channel, id, position, false]
      let res = await adCount(this.activityKey, this.channel, id, position, isShow)
      console.log(res)
    },
    async initData() {
      this.channel = getUrlParams('channel')
      this.bgSrc = ASIGN.cdnUrl + this.imgUrl + '/bg@3x.png'
      this.judgeChannel()
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
    },
    judgeChannel() {
      if(this.channel === 'dptykshby') {
        document.title = '酷暑夏日抽百万福利'
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
@bgColor: #FF8532;

@import '@{baseUrl}/common/base.less';
@import '@{baseUrl}/common/common.less';
@import '@{baseUrl}/zhuanpan/kshby/kshby.less';
</style>
