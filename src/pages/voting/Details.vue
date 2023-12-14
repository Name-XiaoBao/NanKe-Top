<template>
  <van-nav-bar
      left-arrow
      title="选手详情"
      @click-left="onClickLeft"
  />
  <van-pull-refresh v-model="loading" @refresh="onRefresh">
    <div style="padding: 10px;">
      <div style="background: rgba(234, 228, 203, 0.8);border-radius: 10px">
        <div
            style="display: flex;justify-content: center;align-items: center;flex-direction: column;text-align: center;">
          <img :src="details.avatar"
               style="width: 80px;height: 80px;border-radius: 50%;display: block;margin: 20px 0px auto;">
          <h4 style="margin-bottom: 0px">{{ details.nickname }}</h4>
        </div>
        <div style="display: flex;text-align: center">
          <div style="flex: 1;">
            <h4 style="margin-bottom: -15px">{{ details.Ranking }}</h4>
            <h4>排名</h4>
          </div>
          <div style="flex: 1;">
            <h4 style="margin-bottom: -15px">{{ details.numberOfVotes }}</h4>
            <h4>票数</h4>
          </div>
          <div style="flex: 1;">
            <h4 style="margin-bottom: -15px">{{ details.differ ? details.differ : 0 }}票</h4>
            <h4>距前名</h4>
          </div>
        </div>
        <van-divider/>
        <div style="margin: 10px">
          <p style="text-align: center">{{ details.title }}</p>
          <pre>{{ details.content }}</pre>
          <img :src="details.picture" style="width: 100%;">
        </div>
      </div>
      <div style="margin:0 0 50px 0;background: rgba(234, 228, 203, 0.8);border-radius: 10px">
        <div style="text-align: center;padding-top: 5px">
          <h4>排行榜</h4>
        </div>
        <router-link v-for="(participants, index) in detailsList"
                     :to="{path: '/detail',query: { vote: useRoute().query.vote,uid: participants.participatingUid }}"
                     style="color:#000;height: 80px;border-bottom:1px solid #e3dfdf;display: flex;align-items: center;">
          <span style="margin:0px 15px">{{ index + 1 }}</span>
          <img :src="participants.avatar" style="width: 50px;height: 50px;border-radius: 50%;margin-right: 10px;">
          <span>{{ participants.nickname }}</span>
          <span style="margin-left: auto;margin-right: 15px">票数：{{ participants.numberOfVotes }}</span>
        </router-link>
      </div>
    </div>
  </van-pull-refresh>
  <van-action-bar style="height: 54px;">
    <van-action-bar-button class="topic" icon="/application.png"
                           style="border-radius: 20px;margin-right: 2px;color: #000"
                           text="给TA投票" type="warning" @click="voter"/>
    <van-action-bar-button class="topic" icon="/share.png" style="border-radius: 20px;margin-left: 2px;color: #000"
                           text="分享给好友" type="warning" @click="test"/>
  </van-action-bar>
</template>
<script>
import {onMounted, ref} from "vue";
import {QueryBasedOnUid, Voter} from "@/util/api";
import {useRoute} from "vue-router";
import {showDialog, showToast} from "vant";
import vote from "@/pages/voting/Vote.vue";

function getParameterByName(name, url) {
  if (!url) url = window.location.href;
  name = name.replace(/[[\]]/g, '\\$&');
  const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
      results = regex.exec(url);
  if (!results) return null;
  if (!results[2]) return '';
  return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

export default {
  watch: {
    '$route': function () {
      location.reload()
    }
  },
  methods: {useRoute},
  setup() {
    const uid = useRoute().query.uid;
    const vote = useRoute().query.vote;
    const details = ref([])
    const detailsList = ref([])
    onMounted(() => {
      QueryBasedOnUid(vote).then(res => {
        detailsList.value = res.data;
        const index = detailsList.value.findIndex((item) => item.participatingUid === parseInt(uid));
        if (index !== -1) {
          details.value = detailsList.value[index];
          details.value.Ranking = index + 1
          details.value.differ = detailsList.value[index - 1].numberOfVotes - details.value.numberOfVotes
        }
      }).catch(err => {
        console.error(err);
      });
    })
    const loading = ref(false);

    const onClickLeft = () => {
      window.open("/#/vote?uid=" + getParameterByName('vote', window.location.href), "_self")
    }
    const onRefresh = () => {
      QueryBasedOnUid(vote).then(res => {
        detailsList.value = res.data;
        const index = detailsList.value.findIndex((item) => item.participatingUid === parseInt(uid));
        if (index !== -1) {
          details.value = detailsList.value[index];
          details.value.Ranking = index + 1
          if (detailsList.value[index - 1] != null) {
            details.value.differ = detailsList.value[index - 1].numberOfVotes - details.value.numberOfVotes
            showToast('刷新成功');
            loading.value = false;
          } else {
            showToast('刷新成功');
            loading.value = false;
          }
        }
      }).catch(err => {
        console.error(err);
      });
    };
    const voter = () => {
      if (new Date() > new Date(detailsList.value[0].deadline.replace(/-/g, '/'))) {
        showDialog({
          title: '提示',
          message: "这个投票已经结束啦~"
        })
      } else {
        Voter(vote, uid).then(res => {
          if (res.code == 200) {
            showDialog({
              title: res.msg,
              message: 'ヾ(≧▽≦*)o',
            }).then(() => {
              QueryBasedOnUid(vote).then(res => {
                detailsList.value = res.data;
                const index = detailsList.value.findIndex((item) => item.participatingUid === parseInt(uid));
                if (index !== -1) {
                  details.value = detailsList.value[index];
                  details.value.Ranking = index + 1
                  details.value.differ = detailsList.value[index - 1].numberOfVotes - details.value.numberOfVotes
                }
              }).catch(err => {
                console.error(err);
              });
            });
          } else {
            showDialog({
              title: res.msg,
              message: '::>_<::',
            }).then(() => {

            });
          }
        }).catch(err => {
          showToast(err.msg);
        })
      }
    }
    const test = () => {
      alert("暂未实现该功能\n小暴正在努力开发中~")
    }
    return {
      loading,
      onRefresh,
      onClickLeft,
      detailsList,
      details,
      voter,
      test
    }
  }
}
</script>

<style>
body {
  background: rgba(245, 242, 210, 0.8);
}
</style>
