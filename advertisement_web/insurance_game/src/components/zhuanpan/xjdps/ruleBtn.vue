<template>
<a href="javascript:;" class="rule" @click="showRuleModal">
  活动规则
</a>
</template>

<script>
export default {
  name: 'ruleBtn',
  data() {
    return {}
  },
  methods: {
    showRuleModal() {
      this.$emit('showRuleModal')
    }
  }
}
</script>

<style lang="less" scoped>
.rule {
  width: 1.27rem;
  height: .4rem;
  position: absolute;
  left: .3rem;
  top: .3rem;
  padding: 0;
  font-size: .22rem;
  text-align: center;
  line-height: .4rem;
  background-color: #660d08;
  text-decoration: none;
  border-radius: .2rem;
  color: #fff;
}
</style>
