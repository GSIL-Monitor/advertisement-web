<template>
<div id="app">
  <common-banner :bannerSrc="bannerSrc"></common-banner>
  <common-rulebtn @showRuleModal="isShowRule = true"></common-rulebtn>
  <common-prize :activityKey="activityKey" :path="path"></common-prize>
  <common-counter class="chance-box" :chances="chance"></common-counter>
  <hit-egg @hitEgg="hitEgg" :hammerCss="hammerCss" :isHited="isHited"></hit-egg>
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <div slot="custom">
      <rule></rule>
    </div>
  </modal>
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip || isShowNomoretip || isShowGift" @clickOverlay="isShowRule = false;isShowPrizetip = false;isShowNomoretip = false;isShowGift = false"></overlay>
  <loading v-if="isShowLoading"></loading>
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <common-prizetip :prizeLink="prizeLink" :countData="countData" :prizeName="prizeName" :prizeImage="prizeImage" @close="isShowPrizetip = false" v-if="isShowPrizetip"></common-prizetip>
  <common-nomoretip v-if="isShowNomoretip" @bindClose="isShowNomoretip = false"></common-nomoretip>
  <gift-box v-if="isShowGift"></gift-box>
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

import hitEgg from '@/components/zajindan/common/hitEgg'
import giftBox from '@/components/zajindan/common/giftBox'
import rule from '@/components/zajindan/common/rule'

import { getChance, getLuck, adCount } from '@/service/getData'
import { getUrlParams } from '../../../../utils/getUrlParams'
import ASIGN from '@/config/const'

export default {
  name: 'App',
  data() {
    return {
      isShowRule: false,
      isShowGift: false,
      isShowOverlay: false,
      isShowLoading: false,
      isShowPrizetip: false,
      isShowNomoretip: false,
      isShowNochancetip: false,
      gameJumpImage: '',
      gameJumpUrl: '',
      prizeName: '',
      prizeImage: '',
      prizeLink: '',
      bannerSrc: '',
      chance: 0,
      path: 'zjd',
      activityKey: 'ceshi',
      imgUrl: '/zajindan/ykhy',
      channel: '',
      countData: [],
      hammerCss: {},
      isHited: false,
      isLock: true
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
    overlay,
    loading,
    giftBox,
    hitEgg,
    modal,
    rule,
  },
  methods: {
    async hitEgg(e) {
      if(e.target.classList.contains('egg-smashed')){
        return
      } else {
        if(this.isLock) {
          this.isLock = false
          let hammerLeft = e.target.offsetParent.offsetLeft * 2 / 100 + 0.9 + 'rem'
          let hammerTop = e.target.offsetParent.offsetTop * 2 / 100 - 0.9 + 'rem'
          this.hammerCss = {
            'left': hammerLeft,
            'top': hammerTop,
            'transition': 'transform .2s linear 0s'
          }
          let res = await getLuck(this.activityKey, this.channel, this.path)
          console.log(res)
          if (res.retCode === '200') {
            e.target.classList.add('egg-gift')
            this.isHited = true
            this.getChance()
            this.reqAdCount(res.prize.advertisementId, res.prize.position, true)
            setTimeout(() => {
              this.isShowGift = true
              this.hammerCss = {}
              setTimeout(() => {
                this.isShowGift = false
                this.isShowPrizetip = true
                e.target.classList.remove('egg-gift')
                e.target.classList.add('egg-smashed')
                this.isLock = true
              },800)
            }, 800)
            this.prizeImage = res.prize.imageUrl
            this.prizeName = res.prize.title
            this.prizeLink = res.prize.link
          } else if (res.retCode == '801') {
            this.isLock = true
            this.hammerCss = {}
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
      this.bannerSrc = ASIGN.cdnUrl + this.imgUrl + '/banner.png'
      this.getChance()
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
@import '@{baseUrl}/zajindan/ykhy/ykhy.less';
</style>
