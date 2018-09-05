<template>
<div class="counter">
  今日剩余 <span class="chances">{{chances}}</span> 次免费
</div>
</template>

<script>
export default {
  name: 'counter',
  data() {
    return {}
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
.counter {
  width: 3.52rem;
  height: .64rem;
  line-height: .64rem;
  border: none;
  box-shadow: none;
  font-size: .32rem;
  font-weight: 400;
  background-color: #e11e17;
  border-radius: .3rem;
  color: #fff;
  text-align: center;
  .chances {
    color: #fee816;
    font-weight: 700;
    font-size: .4rem;
    margin-top: -.01rem;
  }
}
</style>
