<template>
  <van-nav-bar
      left-arrow
      left-text="返回"
      title="参与的投票"
      @click-left="onClickLeft"
  />
  <van-pull-refresh v-model="loading" @refresh="onRefresh">
    <div style="padding-bottom: 10px">
      <div style="margin: 10px;">
        <div style="margin-top: 15px">
          <van-search v-model="search_vote" placeholder="请输入搜索关键词"
                      style="border-radius: 15px;margin-bottom: -5px" @search="searchClick"/>
        </div>
        <br>
        <router-link v-for="voting_item in vote_item" :to="{path: '/vote',query: { uid: voting_item.voteUid }}"
                     style="color: #000;">
          <div class="voting">
            <img :src="voting_item.cover" style="width: 100%;height: 200px;border-radius: 10px;">
            <div style="margin: 10px">
              <div class="parent">
                <p style="font-weight:bold;">{{ voting_item.title }}</p>
                <van-tag v-if="new Date(voting_item.deadline) > new Date()" size="medium" type="primary">进行中
                </van-tag>
                <van-tag v-else size="medium" type="danger">已结束</van-tag>
              </div>
              <div class="parent" style="padding-bottom: 15px">
                <img :src="voting_item.avatar" style="width: 20px;height: 20px;border-radius: 50%;">
                <span class="name">{{ voting_item.nickname }}</span>
                <span style="margin-left: 10px;font-size: 15px">已获得<span
                    style="color:#0076ff;">{{ voting_item.numberOfVotes }}</span>票</span>
              </div>
            </div>
          </div>
        </router-link>
      </div>
    </div>
  </van-pull-refresh>
</template>

<script>
import {ref} from "vue";
import {getCarouselList, getVoteByUsername, getVoteList, QueryParticipatingUsers} from '@/util/api.js';
import {showToast} from "vant";
import router from "@/router";

export default {
  setup() {
    const loading = ref(false);
    const search_vote = ref(null);
    const vote_item = ref([]);
    const onRefresh = () => {
      QueryParticipatingUsers(localStorage.getItem('username')).then(data => {
        // 处理返回的数据
        voting.value = data.data
        vote_item.value = data.data
        loading.value = false;
        showToast('刷新成功');
      }).catch(error => {
        console.error(error);
      });
    };
    const voting = ref([]);
    // 获取投票列表
    QueryParticipatingUsers(localStorage.getItem('username')).then(data => {
      // 处理返回的数据
      voting.value = data.data
      vote_item.value = data.data
    }).catch(error => {
      console.error(error);
    });
    const active = ref(0);
    const searchClick = () => {
      if (search_vote.value) {
        vote_item.value = voting.value.filter((voting) =>
            voting.title.includes(search_vote.value)
        );
      } else {
        vote_item.value = voting.value; // 如果搜索关键词为空，则显示全部投票
      }
    }
    const clearClick = () => {
      search_vote.value = null;
      vote_item.value = voting.value; // 清空搜索关键词后显示全部投票
    }
    const onClickLeft = () => {
      router.push('/user');
    }
    return {
      onClickLeft,
      loading,
      onRefresh,
      search_vote,
      voting,
      vote_item,
      active,
      searchClick,
      clearClick
    };
  },
};
</script>
<style>
body {
  background: #f5f5f5;
}

.voting {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
}

.parent {
  display: flex;
  align-items: center; /* 纵向居中 */
  justify-content: space-between;
  margin-top: -20px;
  margin-bottom: 15px;
}

.name {
  margin-left: 10px; /* 调整文字和图片之间的距离 */
  text-align: center; /* 水平居中 */
  font-size: 15px;
  color: #999;
}

.right-span {
  margin-left: auto; /* 第二个span靠右对齐 */
}

.browse {
  font-size: 13px;
  color: #999;
}
</style>
