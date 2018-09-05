<template>
  <div class="banner">
    <img :src="bannerSrc">
  </div>
</template>

<script>
export default {
  name: 'CommonBanner',
  data () {
    return {
    }
  },
  props: {
    bannerSrc: {
      type: String,
      default: ''
    }
  }
}
</script>

<style lang="less" scoped>
  .banner{
    img{
      width: 100%;
      display: block;
    }
  }
</style>
