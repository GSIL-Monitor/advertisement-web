<template>
<div id="app">
  <div class="body-bg">
    <img :src="bgSrc">
  </div>
  <rule-btn @showRuleModal="isShowRule = true"></rule-btn>
  <common-prize :imgUrl="imgUrl" :activityKey="activityKey" :path="path"></common-prize>
  <rotary-table class="zhuanpan" :imgUrl="imgUrl" @startRotate="startRotate" :isShowFinger="isShowFinger"></rotary-table>
  <counter class="chance-box" :chances="chance"></counter>
  <lucky-user :luckyUserList="luckyUserList"></lucky-user>
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <div slot="custom">
      <rules></rules>
    </div>
  </modal>
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip" @clickOverlay="isShowRule = false;isShowPrizetip = false"></overlay>
  <loading v-if="isShowLoading"></loading>
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <common-prizetip :prizeLink="prizeLink" :prizeName="prizeName" :prizeImage="prizeImage" v-if="isShowPrizetip" @close="isShowPrizetip = false"></common-prizetip>
  <common-nomoretip v-if="isShowNomoretip" @bindClose="bindCloseNomoretip"></common-nomoretip>
</div>
</template>

<script>
import modal from '@/components/common/modal'
import overlay from '@/components/common/overlay'
import loading from '@/components/common/loading'
import commonPrize from '@/components/common/commonPrize'
import commonPrizetip from '@/components/common/commonPrizetip'
import commonNomoretip from '@/components/common/commonNomoretip'
import commonNochancetip from '@/components/common/commonNochancetip'

import rule from '@/components/zhuanpan/common/rule'
import ruleBtn from '@/components/zhuanpan/xjdps/ruleBtn'
import counter from '@/components/zhuanpan/xjdps/counter'
import luckyUser from '@/components/zhuanpan/common/luckyUser'
import rotaryTable from '@/components/zhuanpan/xjdps/rotaryTable'

import ASIGN from '@/config/const'

import { getChance, getLuck, getLuckyUser, adCount } from '@/service/getData'
import { getUrlParams } from '@/utils/getUrlParams'

import $ from 'jquery'
import '../../../../utils/awardRotate'

export default {
  name: 'App',
  data() {
    return {
      isShowRule: false,
      isShowFinger: true,
      isShowOverlay: false,
      isShowLoading: false,
      isShowPrizetip: false,
      isShowNomoretip: false,
      isShowNochancetip: false,
      bgSrc: '',
      chance: 0,
      gameJumpImage: '',
      gameJumpUrl: '',
      prizeName: '',
      prizeImage: '',
      prizeLink: '',
      path: 'zhuanpan',
      imgUrl: '/zhuanpan/xjdps',
      activityKey: 'ceshi',
      channel: '',
      bRotate: false,
      prizeList: ['神秘礼包', '10元红包', '50元红包', '200元红包'],
      luckyUserList: []
    }
  },
  components: {
    commonNochancetip,
    commonNomoretip,
    commonPrizetip,
    commonPrize,
    rotaryTable,
    luckyUser,
    ruleBtn,
    counter,
    overlay,
    loading,
    modal,
    rule
  },
  methods: {
    async getChance() {
      let res = await getChance(this.activityKey, this.channel, 'zhuanpan')
      console.log(res)
      if (res.retCode === '200') {
        this.chance = res.chance
      }
    },
    async startRotate() {
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
    rotateFn: function(angles, prizeName) {
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
    async reqLuckyUser() {
      let prizeName = this.prizeList.join(',')
      let res = await getLuckyUser(prizeName, this.path)
      console.log(res)
      this.luckyUserList = res.luckUserList
    },
    initData() {
      this.channel = getUrlParams('channel')
      this.bgSrc = ASIGN.cdnUrl + this.imgUrl + '/bg@3x.png'
      this.reqLuckyUser()
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
@import '@{baseUrl}/zhuanpan/xjdps/xjdps.less';
</style>
