import fetch from '../config/fetch'
// 曝光量isShow为true，点击量isShow为false
let adCount = (activityKey, channel, id, position, isShow) => fetch('POST', '/m/j/adCount.html', {
  channel: channel,
  activityKey: activityKey,
  id: id,
  position: position,
  isShow: isShow
})
let getChance = (activityKey, channel, pathVal) => fetch('POST', '/m/activity/' + pathVal + '/common/index.html', {
  activityKey: activityKey,
  channel: channel
})
let getLuck = (activityKey, channel, pathVal) => fetch('POST', '/m/activity/' + pathVal + '/common/luck.html', {
  activityKey: activityKey,
  channel: channel
})
let getMyPrize = (activityKey, pathVal) => fetch('POST', '/m/activity/' + pathVal + '/common/myPrize.html', {
  activityKey: activityKey
})
let getLuckyUser = (prizeName, pathVal) => fetch('POST', '/m/activity/' + pathVal + '/common/luckUserList.html', {
  prizeName: prizeName
})

export {
  getChance,
  getLuck,
  getMyPrize,
  getLuckyUser,
  adCount
}
