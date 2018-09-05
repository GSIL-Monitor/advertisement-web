<template>
<div id="app">
  <div class="body-bg">
    <img :src="bgSrc" >
  </div>
  <!-- 规则 -->
  <common-rulebtn @showRuleModal="isShowRule = true"></common-rulebtn>
  <!-- 我的奖品 -->
  <common-prize :activityKey="activityKey" :path="path"></common-prize>
  <!-- 转盘 -->
  <rotary-table class="zhuanpan" @startRotate="startRotate" :isShowFinger="isShowFinger"></rotary-table>
  <!-- 免费次数 -->
  <other-counter class="chance-box" :chances="chance"></other-counter>
  <!-- 活动规则弹窗 -->
  <modal v-if="isShowRule" :showDefaultBtn="true" :btnText="'好的'" :title="'活动规则'" @sure="isShowRule = false" :showClose="true" @close="isShowRule = false">
    <!-- 规则插槽 -->
    <div slot="custom">
      <rule></rule>
    </div>
  </modal>
  <!-- 蒙层 -->
  <overlay v-if="isShowRule || isShowNochancetip || isShowPrizetip || isShowNomoretip || isShowHbTip" @clickOverlay="isShowRule = false;isShowPrizetip = false;isShowNomoretip = false;"></overlay>
  <!-- 加载动画 -->
  <loading v-if="isShowLoading"></loading>
  <!-- 没有次数 -->
  <common-nochancetip :gameJumpImage="gameJumpImage" :gameJumpUrl="gameJumpUrl" v-if="isShowNochancetip"></common-nochancetip>
  <!-- 展示奖品弹窗 -->
  <common-prizetip :prizeLink="prizeLink" :countData="countData" :prizeName="prizeName" :prizeImage="prizeImage" v-if="isShowPrizetip" @close="isShowPrizetip = false"></common-prizetip>
  <!-- 没有更多 -->
  <common-nomoretip v-if="isShowNomoretip" @bindClose="isShowNomoretip = false"></common-nomoretip>
  <!-- 首页弹出框广告 -->
  <hongbao-tip v-if="isShowHbTip" @closeTip="isShowHbTip = false" :hbObj="popUpAdvertisement"></hongbao-tip>
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

import rule from '@/components/zhuanpan/common/rule'
import hongbaoTip from '@/components/common/hongbaoTip'
import otherCounter from '@/components/common/otherCounter'
import rotaryTable from '@/components/zhuanpan/common/rotaryTable'

import ASIGN from '@/config/const'
import { getChance, getLuck, adCount } from '@/service/getData'
import { getUrlParams } from '@/utils/getUrlParams'

import $ from 'jquery'
import '@/utils/awardRotate'

export default {
  name: 'App',
  data() {
    return {
      isShowRule: false,
      isShowHbTip: false,
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
      activityKey: 'ceshi',
      imgUrl: '/zhuanpan/nhshb',
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
    hongbaoTip,
    loading,
    overlay,
    modal,
    rule
  },
  methods: {
    // 获取次数
    async getChance() {
      let res = await getChance(this.activityKey, this.channel, this.path)
      console.log(res)
      if (res.retCode === '200') {
        this.chance = res.chance
      }
    },
    startRotate() {
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
    // 初始化
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
    // 判断channel
    judgeChannel() {
      if(this.channel === 'dptynhshb') {
        document.title = '一起瓜分百万福利'
        this.bgSrc = ASIGN.cdnUrl + this.imgUrl + '/bg-dptynhshb@3x.png'
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
@import '@{baseUrl}/common/common.less';
@import '@{baseUrl}/zhuanpan/nhshb/nhshb.less';
</style>
