<template>
<div id="app">
  <common-banner :bannerSrc="bannerSrc"></common-banner>
  <common-rulebtn @showRuleModal="isShowRule = true"></common-rulebtn>
  <common-prize :activityKey="activityKey" :path="path"></common-prize>
  <common-counter class="chance-box" :chances="chance"></common-counter>
  <cards @flipCards="flipCards" :successReq="successReq" :next="next"></cards>
  <prize-list></prize-list>
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <div slot="custom">
      <rule></rule>
    </div>
  </modal>
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip || isShowNomoretip" @clickOverlay="clickOverlay"></overlay>
  <loading v-if="isShowLoading"></loading>
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <common-prizetip @close="closePrizeTip" :countData="countData" :prizeLink="prizeLink" :prizeName="prizeName" :prizeImage="prizeImage" v-if="isShowPrizetip"></common-prizetip>
  <common-nomoretip v-if="isShowNomoretip" @bindClose="isShowNomoretip = false"></common-nomoretip>
</div>
</template>

<script>
import modal from '@/components/common/modal'
import overlay from '@/components/common/overlay'
import loading from '@/components/common/loading'
import commonPrize from '@/components/common/commonPrize'
import commonBanner from '@/components/common/commonBanner'
import commonRulebtn from '@/components/common/commonRulebtn'
import commonCounter from '@/components/common/commonCounter'
import commonPrizetip from '@/components/common/commonPrizetip'
import commonNomoretip from '@/components/common/commonNomoretip'
import commonNochancetip from '@/components/common/commonNochancetip'


import cards from '@/components/fanpai/common/cards'
import rule from '@/components/fanpai/common/rule'
import prizeList from '@/components/fanpai/common/prizeList'

import { getChance, getLuck, adCount } from '@/service/getData'
import { getUrlParams } from '@/utils/getUrlParams'
import ASIGN from '@/config/const'

export default {
  name: 'App',
  data() {
    return {
      isShowRule: false,
      isShowOverlay: false,
      isShowLoading: false,
      isShowPrizetip: false,
      isShowNomoretip: false,
      isShowNochancetip: false,
      chance: 0,
      gameJumpImage: '',
      gameJumpUrl: '',
      prizeName: '',
      prizeImage: '',
      prizeLink: '',
      bannerSrc: '',
      path: 'fanpai',
      activityKey: 'ceshi',
      channel: '',
      successReq: false,
      next: false,
      imgUrl: '/fanpai/dhf',
      countData: []
    }
  },
  components: {
    commonNochancetip,
    commonNomoretip,
    commonPrizetip,
    commonCounter,
    commonRulebtn,
    commonBanner,
    commonPrize,
    prizeList,
    overlay,
    loading,
    modal,
    cards,
    rule
  },
  methods: {
    closePrizeTip() {
      this.isShowPrizetip = false
      this.next = !this.next
    },
    clickOverlay() {
      this.isShowRule = false
      this.isShowNomoretip = false
      if(this.isShowPrizetip) {
        this.isShowPrizetip = false
        this.next = !this.next
      }
    },
    async flipCards() {
      let res = await getLuck(this.activityKey, this.channel, this.path)
      console.log(res)
      if (res.retCode === '200') {
        this.getChance()
        this.reqAdCount(res.prize.advertisementId, res.prize.position, true)
        this.prizeImage = res.prize.imageUrl
        this.prizeName = res.prize.title
        this.prizeLink = res.prize.link
        setTimeout(() => {
          this.isShowPrizetip = true
          this.successReq = !this.successReq
        }, 1600)
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
    async getChance() {
      let res = await getChance(this.activityKey, this.channel, this.path)
      console.log(res)
      if (res.retCode === '200') {
        this.chance = res.chance
      }
    },
    async reqAdCount(id, position, isShow) {
      this.countData = [this.activityKey, this.channel, id, position, false]
      let res = await adCount(this.activityKey, this.channel, id, position, isShow)
      console.log(res)
    },
    async initData() {
      this.channel = getUrlParams('channel')
      this.getChance()
      this.bannerSrc = ASIGN.cdnUrl + this.imgUrl + '/banner@3x.png'
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
@import '@{baseUrl}/common/common.less';
@import '@{baseUrl}/fanpai/dhf/dhf.less';
</style>
