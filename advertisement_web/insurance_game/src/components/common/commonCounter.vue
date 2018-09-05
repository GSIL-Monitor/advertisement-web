<template>
  <div class="counter">
    今日免费 <span>{{chances}}</span> 次
  </div>
</template>

<script>
export default {
  name: 'counter',
  data () {
    return {
    }
  },
  props: {
    chances: {
      type: Number,
      default: 0
    }
  }
}
</script>

<style lang="less" scoped>
  .counter{
    width: 2.4rem;
    font-size: .26rem;
    text-align: center;
    color: #fff;
  }
</style>
