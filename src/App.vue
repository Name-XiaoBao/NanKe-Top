<template>
  <div id="app" v-wechat-title="$route.meta.title">
    <div>
      <!--      <van-nav-bar v-if="$route.meta.bar" :title="title" />-->
      <router-view/>
      <van-tabbar v-if="$route.meta.tabbar" v-model="active" placeholder>
        <van-tabbar-item icon="home-o" name="home" replace to="/">首页</van-tabbar-item>
        <van-tabbar-item icon="records-o" name="InitiateVoting" replace to="/InitiateVoting">发起投票</van-tabbar-item>
        <van-tabbar-item icon="manager-o" name="user" replace to="/user">个人中心</van-tabbar-item>
      </van-tabbar>
    </div>
  </div>
</template>
<script>
window.onload = function () {
  document.addEventListener('touchstart', function (event) {
    if (event.touches.length > 1) {
      event.preventDefault()
    }
  })
  document.addEventListener('gesturestart', function (event) {
    event.preventDefault()
  })
}
import {computed, provide, ref} from 'vue';
import {useRoute} from 'vue-router';

export default {
  setup() {
    const active = ref('home');
    provide('active', active);
    const title = computed(() => {
      if (useRoute().meta.title) {
        return useRoute().meta.title;
      } else {
        return '南科Top';
      }
    });
    // 提供setTitle方法
    provide('setTitle', (newTitle) => {
      title.value = newTitle;
    });

    return {
      title,
      active,
    };
  },
};
</script>
<style>
body {
  background: #f5f5f5;
  margin: 0;
  padding: 0;
}
</style>
