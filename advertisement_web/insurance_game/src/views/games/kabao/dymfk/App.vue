<template>
<div id="app">
  <common-rulebtn @showRuleModal="isShowRule = true"></common-rulebtn>
  <common-prize :activityKey="activityKey" :path="path"></common-prize>
  <kabao :chance="chance" :lastImg="prizeImage" :isShowCar="isShowCar" @swipeupEnd="swipeupEnd"></kabao>
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <div slot="custom">
      <rule></rule>
    </div>
  </modal>
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip || isShowNomoretip" @clickOverlay="isShowRule = false;isShowPrizetip = false;isShowNomoretip = false;"></overlay>
  <loading v-if="isShowLoading"></loading>
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <common-prizetip :prizeLink="prizeLink" :countData="countData" :prizeName="prizeName" :prizeImage="prizeImage" v-if="isShowPrizetip"></common-prizetip>
  <common-nomoretip v-if="isShowNomoretip" @bindClose="isShowNomoretip = false"></common-nomoretip>
</div>
</template>

<script>
import modal from '@/components/common/modal'
import overlay from '@/components/common/overlay'
import loading from '@/components/common/loading'
import rule from '@/components/kabao/common/rule'
import kabao from '@/components/kabao/common/kabao.vue'
import commonPrize from '@/components/common/commonPrize'
import commonRulebtn from '@/components/common/commonRulebtn'
import commonPrizetip from '@/components/common/commonPrizetip'
import commonNomoretip from '@/components/common/commonNomoretip'
import commonNochancetip from '@/components/common/commonNochancetip'

import { getChance, getLuck, adCount } from '@/service/getData'
import { getUrlParams } from '@/utils/getUrlParams'

export default {
  name: 'App',
  data() {
    return {
      isShowCar: true,
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
      path: 'kb',
      activityKey:'ceshi',
      channel:'',
      countData: [],
      imgUrl: '/kabao/dymfk'
    }
  },
  components: {
    commonNochancetip,
    commonNomoretip,
    commonPrizetip,
    commonRulebtn,
    commonPrize,
    overlay,
    loading,
    modal,
    kabao,
    rule,
  },
  methods: {
    async initPage() {
      let res = await getLuck(this.activityKey, this.channel, this.path)
      console.log(res)
      if (res.retCode === '200') {
        this.isShowCar = false
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
    async getChance() {
      let res = await getChance(this.activityKey, this.channel, 'kb')
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
      this.initPage()
    },
    swipeupEnd () {
      this.initPage()
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
@import '@{baseUrl}/kabao/dymfk/dymfk.less';
</style>
