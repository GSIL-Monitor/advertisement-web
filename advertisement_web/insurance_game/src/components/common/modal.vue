<template>
  <transition name="modal-wrap-fade">
    <div class="modal" :style="style">
      <div class="modal-content">
        <div class="modal-header">
            <div class="modal-title">{{title}}</div>
        </div>
        <div class="modal-body">
          <slot name="custom"></slot>
        </div>
      </div>
      <div class="modal-footer" v-if="showDefaultBtn">
        <div class="modal-btn"><span class="modal-btn-text" @click="sure">{{btnText}}</span></div>
      </div>
      <div class="icon-close" v-if="showClose" @click="close">
        <img :src="closeSrc">
      </div>
    </div>
  </transition>
</template>

<script>
import { getZIndex } from '../../utils/zindex'
import ASIGN from '@/config/const'
export default {
  name: 'modal',
  data () {
    return {
      zIndex: getZIndex() + 2,
      closeSrc: ASIGN.cdnUrl + '/common/modal-close.png'
    }
  },
  props: {
    title: {
      type: String,
      default: ''
    },
    btnText: {
      type: String,
      default: '确定'
    },
    showDefaultBtn: {
      type: Boolean,
      default: true
    },
    showClose: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    sure () {
      this.$emit('sure')
    },
    close () {
      this.$emit('close')
    }
  },
  computed: {
    style () {
      return {
        'z-index': this.zIndex
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .modal {
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate3d(-50%, -50%, 0);
    background-color: #FFF;
    border-radius: .1rem;
    width: 90%;
  }
  .modal-header {
    .modal-title {
      font-size: 0.4rem;
      text-align: center;
      line-height: 0.8rem;
      margin: 0.2rem 0 0.2rem 0;
    }
  }
  .modal-body {
    max-height: 6rem;
    overflow: auto;
    padding: .25rem;
    margin: .2rem;
    border-radius: .05rem;
    background-color: #fdf9ed;
    text-align: left;
  }
  .modal-footer {
    .modal-btn {
    width: 4rem;
    height: .8rem;
    line-height: .8rem;
    border-radius: .05rem;
    text-align: center;
    color: #fff;
    background-color: #ff493b;
    font-size: 0.32rem;
    margin: .4rem auto 0.3rem;
    }
  }
  .icon-close {
    position: absolute;
    right: -.25rem;
    top: -.25rem;
    width: .61rem;
    height: .61rem;
    img{
      display: block;
      width: 100%;
    }
  }
</style>
