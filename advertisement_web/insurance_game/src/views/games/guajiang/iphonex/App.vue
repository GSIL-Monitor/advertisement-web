<template>
<div id="app">
  <common-banner :bannerSrc="bannerSrc"></common-banner>
  <common-rulebtn @showRuleModal="isShowRule = true"></common-rulebtn>
  <common-prize :activityKey="activityKey" :path="path"></common-prize>
  <common-counter class="chance-box" :chances="chance" v-if="isShowCounter"></common-counter>
  <scratch-card @bindClickStart="bindClickStart" :canShowStartTip="isShowStartTip" :isShowStartBox="isShowStartBox" @endCover="endCover"></scratch-card>
  <prize-list></prize-list>
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <div slot="custom">
      <rule></rule>
    </div>
  </modal>
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip || isShowNomoretip" @clickOverlay="clickOverlay"></overlay>
  <loading v-if="isShowLoading"></loading>
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <common-prizetip :prizeLink="prizeLink" :countData="countData" :prizeName="prizeName" :prizeImage="prizeImage" v-if="isShowPrizetip" @close="bindClosePrizeTip"></common-prizetip>
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

import rule from '@/components/guajiang/common/rule'
import prizeList from '@/components/guajiang/common/prizeList'
import scratchCard from '@/components/guajiang/common/scratchCard'
import Lottery from '@/utils/guajiang'

import { getChance, getLuck, adCount } from '@/service/getData'
import { getUrlParams } from '@/utils/getUrlParams'
import ASIGN from '@/config/const'

export default {
  name: 'App',
  data() {
    return {
      isShowRule: false,
      isShowCounter: true,
      isShowOverlay: false,
      isShowLoading: false,
      isShowStartBox: true,
      isShowStartTip: true,
      isShowPrizetip: false,
      isShowNomoretip: false,
      isShowNochancetip: false,
      chance: 0,
      gameJumpImage: '',
      gameJumpUrl: '',
      prizeName: '',
      prizeImage: '',
      prizeLink: '',
      imgUrl: '/guajiang/iphonex',
      path: 'gj',
      activityKey: 'ceshi',
      channel: '',
      countData: [],
      bannerSrc: ''
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
    scratchCard,
    prizeList,
    loading,
    overlay,
    modal,
    rule
  },
  methods: {
    bindClosePrizeTip () {
      this.isShowPrizetip = false
      this.isShowStartBox = true
      this.isShowCounter = true
    },
    clickOverlay () {
      this.isShowRule = false
      this.isShowPrizetip = false
      this.isShowNomoretip = false
      this.isShowCounter = true
      this.isShowStartBox = true
    },
    endCover () {
      this.isShowPrizetip = true
    },
    bindClickStart () {
      if(this.chance == 0) {
        this.isShowStartTip = false
        this.isShowNomoretip = true
      } else {
        this.isShowStartBox = false
        this.isShowCounter = false
        this.getLuck()
      }
    },
    async getChance() {
      let res = await getChance(this.activityKey, this.channel, this.path)
      console.log(res)
      if (res.retCode === '200') {
        this.chance = res.chance
      }
    },
    async getLuck() {
      this.isShowLoading = true
      let res = await getLuck(this.activityKey, this.channel, this.path)
      console.log(res)
      this.isShowLoading = false
      if (res.retCode === '200') {
        this.getChance()
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
    async initData() {
      this.channel = getUrlParams('channel')
      this.getChance()
      this.bannerSrc = ASIGN.cdnUrl + this.imgUrl + '/banner@3x.png'
    },
    async reqAdCount(id, position, isShow) {
      this.countData = [this.activityKey, this.channel, id, position, false]
      let res = await adCount(this.activityKey, this.channel, id, position, isShow)
      console.log(res)
    }
  },
  created () {
    this.initData()
  }
}
</script>

<style lang='less'>
@baseUrl: '../../../../assets/styles/games';

@import '@{baseUrl}/common/base.less';
@import '@{baseUrl}/common/common.less';
@import '@{baseUrl}/guajiang/iphonex/iphonex.less';
</style>
