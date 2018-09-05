<template>
  <transition name="overlay-fade">
    <div class="overlay" @click.prevent="clickOverlay" :style="style"></div>
  </transition>
</template>

<script>
export default {
  name: 'overlay',
  props: {
    opacity: {
      type: Number,
      default: 0.7
    },
    color: {
      type: String,
      default: '#000'
    }
  },
  data () {
    return {
    }
  },
  methods: {
    clickOverlay () {
      this.$emit('clickOverlay')
    }
  },
  computed: {
    style () {
      return {
        'opacity': this.opacity,
        'background-color': this.color
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .overlay {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background: #000;
    opacity: .4;
    z-index: 100;
  }
  .overlay-fade-enter-active,
  .overlay-fade-leave-active {
    transition: opacity .3s;
  }
  .overlay-fade-enter,
  .overlay-fade-leave-active {
    opacity: 0 !important;
  }
</style>
