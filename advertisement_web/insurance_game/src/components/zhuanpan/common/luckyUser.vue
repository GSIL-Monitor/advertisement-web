<template>
<div class="lucky-users">
  <div class="lucky-users-inner">
    <div class="wrap">
      <ul class="lucky-users-ul" id="luckyUsersUl">
        <!-- 获取数据 -->
        <li v-for="(user, index) in luckyUserList" :key="index">恭喜{{user.mobile}}获得<span class="money">{{user.name}}</span></li>
      </ul>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: 'luckyUser',
  data() {
    return {}
  },
  props: {
    luckyUserList: {
      type: Array,
      default() {
        return []
      }
    }
  },
  methods: {
    scroll() {
      const speed = -1
      let luckyUsersUl = document.getElementById('luckyUsersUl')
      let lis = luckyUsersUl.getElementsByTagName('li')
      luckyUsersUl.innerHTML += luckyUsersUl.innerHTML
      luckyUsersUl.style.height = lis.length * 35 + 'px'
      setInterval(() => {
        luckyUsersUl.style.top = luckyUsersUl.offsetTop + speed + 'px'
        if (luckyUsersUl.offsetTop < (-luckyUsersUl.offsetHeight) / 2) {
          luckyUsersUl.style.top = '0'
        } else if (luckyUsersUl.offsetTop > 0) {
          luckyUsersUl.style.top = -luckyUsersUl.offsetHeight / 2 + 'px'
        }
      }, 40)
    }
  },
  watch: {
    luckyUserList() {
      this.scroll()
    }
  },
  mounted() {
    this.scroll()
  }
}
</script>

<style lang="less" scoped>
.lucky-users {
  margin: -1.24rem 0 0.6rem;
  font-size: 12px;
  color: #fff;
  .lucky-users-inner {
    background-color: #f3e37a;
    border-radius: 0.1rem;
    color: #000;
    overflow: hidden;
    width: 4.5rem;
    height: 25px;
    margin-left: 1.5rem;
    .wrap {
      position: relative;
      height: 25px;
      width: 4.5rem;
      overflow: hidden;
    }
    .lucky-users-ul {
      position: absolute;
      top: 0;
      width: 4.5rem;
    }
    li {
      width: 4.5rem;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: keep-all;
      word-wrap: normal;
      height: 25px;
      line-height: 25px;
      text-align: center;
      background: #f3e37a;
      span {
        color: #fc271c;
      }
    }
  }
}
</style>
