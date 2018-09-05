<template>
<div id="app">
  <div class="body-bg">
    <img :src="bgSrc">
  </div>
  <common-rulebtn @showRuleModal="bindShowRule"></common-rulebtn>
  <common-prize :activityKey="activityKey" :path="path"></common-prize>
  <other-counter class="chance-box" :chances="chance"></other-counter>
  <hong-bao @bindClick="bindClick" :successReq="successReq"></hong-bao>
  <prize-list></prize-list>
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <div slot="custom">
      <rule :channel="channel"></rule>
    </div>
  </modal>
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip || isShowNomoretip" @clickOverlay="isShowRule = false;isShowPrizetip = false;isShowNomoretip = false;"></overlay>
  <loading v-if="isShowLoading"></loading>
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <common-prizetip :prizeLink="prizeLink" :countData="countData" :prizeName="prizeName" :prizeImage="prizeImage" v-if="isShowPrizetip"></common-prizetip>
  <common-nomoretip v-if="isShowNomoretip" @bindClose="bindCloseNomoretip"></common-nomoretip>
</div>
</template>

<script>
import modal from '@/components/common/modal'
import overlay from '@/components/common/overlay'
import loading from '@/components/common/loading'
import commonPrize from '@/components/common/commonPrize'
import commonBanner from '@/components/common/commonBanner'
import otherCounter from '@/components/common/otherCounter'
import commonRulebtn from '@/components/common/commonRulebtn'
import commonPrizetip from '@/components/common/commonPrizetip'
import commonNomoretip from '@/components/common/commonNomoretip'
import commonNochancetip from '@/components/common/commonNochancetip'

import rule from '@/components/dahongbao/thk/rule'
import hongBao from '@/components/dahongbao/common/hongBao'
import prizeList from '@/components/dahongbao/common/prizeList'

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
      isShowNomoretip: false,
      isShowNochancetip: false,
      bgSrc: '',
      chance: 0,
      gameJumpImage: '',
      gameJumpUrl: '',
      prizeName: '',
      prizeImage: '',
      prizeLink: '',
      successReq: false,
      path: 'dhb',
      activityKey: 'ceshi',
      channel: '',
      countData: [],
      imgUrl: '/dahongbao/thk',
    }
  },
  components: {
    commonNochancetip,
    commonNomoretip,
    commonPrizetip,
    commonRulebtn,
    otherCounter,
    commonBanner,
    commonPrize,
    prizeList,
    overlay,
    loading,
    hongBao,
    modal,
    rule
  },
  methods: {
    bindShowRule() {
      this.isShowRule = true
    },
    bindCloseNomoretip() {
      this.isShowNomoretip = false
    },
    async bindClick() {
      this.isShowLoading = true
      let res = await getLuck(this.activityKey, this.channel, this.path)
      console.log(res)
      this.isShowLoading = false
      if (res.retCode === '200') {
        this.successReq = true
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
      let res = await getChance(this.activityKey, this.channel, 'dhb')
      console.log(res)
      if (res.retCode === '200') {
        this.chance = res.chance
      }
    },
    async initData() {
      this.channel = getUrlParams('channel')
      this.getChance()
      this.bgSrc = ASIGN.cdnUrl + this.imgUrl + '/bg@3x.png'
    },
    async reqAdCount(id, position, isShow) {
      this.countData = [this.activityKey, this.channel, id, position, false]
      let res = await adCount(this.activityKey, this.channel, id, position, isShow)
      console.log(res)
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
@import '@{baseUrl}/dahongbao/thk/thk.less';
</style>
