<template>
<div class="mainArea">
  <ul class="poker-list">
    <li class="poker" v-for="(item,index) in cards" :key="index" @click="flipCards(index)" :class="{light:item.light,deal:item.deal,'popup-card':item.popupCard,'shake-card':item.shakeCard,'rotate-card':item.rotateCard}"></li>
  </ul>
</div>
</template>

<script>
export default {
  name: 'cards',
  data() {
    return {
      cards: [{
          light: false,
          deal: true,
          popupCard: false,
          shakeCard: false,
          rotateCard: false
        },
        {
          light: false,
          deal: true,
          popupCard: false,
          shakeCard: false,
          rotateCard: false
        },
        {
          light: false,
          deal: true,
          popupCard: false,
          shakeCard: false,
          rotateCard: false
        },
        {
          light: false,
          deal: true,
          popupCard: false,
          shakeCard: false,
          rotateCard: false
        },
        {
          light: false,
          deal: true,
          popupCard: false,
          shakeCard: false,
          rotateCard: false
        },
        {
          light: false,
          deal: true,
          popupCard: false,
          shakeCard: false,
          rotateCard: false
        }
      ],
      timer: null,
      initSucceed: false
    }
  },
  props: {
    successReq: {
      type: Boolean,
      default: false
    },
    next: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    successReq(newValue, oldValue) {
      this.resetCards()
    },
    next() {
      this.dealCards()
    }
  },
  methods: {
    lightCards() {
      let i = 0
      for (let j = 0; j < this.cards.length; j++) {
        this.timer = setTimeout(() => {
          for (let k = 0; k < this.cards.length; k++) {
            this.cards[k].light = false
          }
          this.$set(this.cards[i], 'light', true)
          i++
          if (i === this.cards.length) {
            setTimeout(this.lightCards, 1000)
          }
        }, 1000 * j)
      }
    },
    dealCards() {
      let i = 0
      for (let j = 0; j < this.cards.length; j++) {
        setTimeout(() => {
          this.$set(this.cards[i], 'deal', false)
          i++
          if (i === this.cards.length) {
            this.initSucceed = true
            this.lightCards()
          }
        }, 400 * j)
      }
    },
    resetCards() {
      for (let i = 0; i < this.cards.length; i++) {
        this.cards[i].light = false
        this.cards[i].deal = true
        this.cards[i].popupCard = false
        this.cards[i].shakeCard = false
        this.cards[i].rotateCard = false
      }
    },
    flipCards(index) {
      if (this.initSucceed) {
        this.initSucceed = false
        clearTimeout(this.timer)
        this.$set(this.cards[index], 'popupCard', true)
        this.$set(this.cards[index], 'shakeCard', true)
        setTimeout(() => {
          this.$set(this.cards[index], 'shakeCard', false)
          this.$set(this.cards[index], 'rotateCard', true)
        }, 800)
        clearTimeout(this.timer)
        this.$emit('flipCards', index)
      }
    }
  },
  created() {
    this.dealCards()
  }
}
</script>

<style lang="less" scoped>
@imgUrl: '/static/images/games/fanpai/common';
.mainArea {
  width: 7.5rem;
  height: 5.45rem;
  position: relative;
  .poker-list {
    li {
      position: absolute;
      display: inline-block;
      width: 1.6rem;
      height: 2.2rem;
      transition: all .3s;
      background: url('@{imgUrl}/poker.png') no-repeat;
      background-size: 100% 100%;
    }
    li:nth-child(1),
    li:nth-child(2),
    li:nth-child(3),
    {
      top: .22rem;
    }
    li:nth-child(1) {
      left: .75rem;
    }
    li:nth-child(2) {
      left: 2.95rem;
    }
    li:nth-child(3) {
      left: 5.15rem;
    }
    li:nth-child(4),
    li:nth-child(5),
    li:nth-child(6),
    {
      top: 2.78rem;
    }
    li:nth-child(4) {
      left: .75rem;
    }
    li:nth-child(5) {
      left: 2.95rem;
    }
    li:nth-child(6) {
      left: 5.15rem;
    }
    .deal {
      top: 2.9rem !important;
      left: 5.15rem !important;
      transform: translate(0, 0);
    }
    .light {
      background: url('@{imgUrl}/poker_light.png') no-repeat;
      background-size: 100% 100%;
    }
    .rotate-card {
      animation: rotateCard 1.4s linear 1;
    }
    .popup-card {
      z-index: 100;
      top: .6rem !important;
      left: 50% !important;
      transform: translate(-50%, 0) scale(1.5, 1.5);
    }
    .shake-card {
      animation: shakeCard .8s linear infinite;
    }
  }
}

@keyframes shakeCard {
  100%,
  30% {
    transform: translate(-50%, 0) scale(1.5, 1.5) rotate(0)
  }
  40%,
  60%,
  80% {
    transform: translate(-50%, 0) scale(1.5, 1.5) rotate(10deg)
  }
  50%,
  70%,
  90% {
    transform: translate(-50%, 0) scale(1.5, 1.5) rotate(-10deg)
  }
}

@keyframes rotateCard {
  from {
    transform: translate(-50%, 0) scale(1.5, 1.5)
  }
  to {
    transform: translate(-50%, 0) scale(1.5, 1.5) rotateY(-180deg)
  }
}
</style>
