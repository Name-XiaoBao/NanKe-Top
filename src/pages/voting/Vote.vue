<template>
  <div class="topic">
    <van-nav-bar
        left-arrow
        title="投票详情"
        @click-left="onClickLeft"
        @click-right="onClickRight"
    >
      <template v-if="voting.backgroundMusic" #right>
        <van-icon class="rotate" name="music-o" size="25"/>
      </template>
    </van-nav-bar>
    <audio id="audioPlayer" :src="voting.backgroundMusic" autoplay>
    </audio>
    <van-pull-refresh v-model="loading" @refresh="onRefresh">
      <img :src="voting.cover" style="width: 100%;">
      <div class="box">
        <div class="date">
          <p class="title">{{ voting.title }}</p>
          <div class="boxs">
            <div class="item">
              <p class="num text">{{ Participants.length }}</p>
              <p>参与人数</p>
            </div>
            <div class="item">
              <p class="num text">{{ voting.votes }}</p>
              <p>累计票数</p>
            </div>
            <div class="item">
              <p class="num text">{{ voting.views }}</p>
              <p>访问次数</p>
            </div>
          </div>
          <div class="timing">
            <van-count-down ref="countDown" :time="timeDiff" format="HH:mm:ss:SS" @finish="onFinish">
              <template #default="timeData">
                <span class="colon"><b>距离投票结束还有：</b></span>
                <span class="block text">{{ timeData.days }}</span>
                <span class="colon">天</span>
                <span class="block text">{{ timeData.hours }}</span>
                <span class="colon">时</span>
                <span class="block text">{{ timeData.minutes }}</span>
                <span class="colon">分</span>
                <span class="block text">{{ timeData.seconds }}</span>
                <span class="colon">秒</span>
              </template>
            </van-count-down>
          </div>
        </div>
        <br>
        <van-search
            v-model="search"
            background="rgba(255,255,255,0.5)"
            input-align="center"
            placeholder="请输入搜索关键词"
            style="border-radius: 10px"
            @clear="clearClick"
            @search="searchClick"
        />
        <van-tabs v-model:active="active" animated background="rgba(255,255,255,0.5)" style="margin: 10px 0 50px 0"
                  swipeable>
          <van-tab title="参与列表">
            <div class="tabs">
              <div style="display: flex; flex-wrap: wrap;">
                <div v-if="Participants.length==0" style="width: 100%;text-align: center">
                  <van-empty
                      description="暂无参与人员"
                      image="/null_personnel.png"
                      image-size="180"
                  />
                </div>
                <router-link
                    v-for="participants in filteredParticipants"
                    v-else
                    :to="{path: '/detail',query: { vote: useRoute().query.uid,uid: participants.participatingUid }}"
                    style="background: #fff;color:#000;border-radius: 10px;flex-basis: 48%;margin: 1% 1%;text-align: center;padding-bottom:10px;">
                  <img :src="participants.picture"
                       style="width: 100%;height: 150px;border-radius: 10px 10px 0 0;margin-bottom: 5px"/>
                  <span>{{ participants.title }}</span>
                  <p class="text" style="margin-top: 5px;margin-bottom: 6px"><span class="text"
                                                                                   style="margin-right: 5px;">{{
                      participants.numberOfVotes
                    }}</span>票
                  </p>
                  <van-button round
                              style="background: rgb(225,220,166);color:#000000;border-radius:10px;border: none;width: 90%;height: 30px;"
                              type="success">
                    为TA投票
                  </van-button>
                </router-link>
              </div>
            </div>
          </van-tab>
          <van-tab title="排行榜">
            <div class="tabs">
              <van-empty
                  v-if="Participants.length==0"
                  description="暂无参与人员"
                  image="/null_personnel.png"
                  image-size="180"
              />
              <router-link
                  v-for="(participants, index) in Participants.sort((a, b) => b.numberOfVotes - a.numberOfVotes)"
                  v-else
                  :to="{path: '/detail',query: { vote: useRoute().query.uid,uid: participants.participatingUid }}"
                  style="color:#000;height: 80px;border-bottom:1px solid #e3dfdf;display: flex;align-items: center;">
                <span style="margin:0px 15px">{{ index + 1 }}</span>
                <img :src="participants.avatar" class="avatars">
                <span>{{ participants.nickname }}</span>
                <span style="margin-left: auto;margin-right: 15px">票数：{{ participants.numberOfVotes }}</span>
              </router-link>
            </div>
          </van-tab>
          <van-tab title="规则详情">
            <div class="tabs">
              <div style="padding:10px">
                <b>规则详情：</b>
                <pre>{{ voting.rule }}</pre>
              </div>
            </div>
          </van-tab>
        </van-tabs>
      </div>
    </van-pull-refresh>
  </div>
  <van-action-bar style="height: 54px;">
    <van-action-bar-button class="topic" icon="/application.png"
                           style="border-radius: 20px;margin-right: 2px;color: #000"
                           text="我要报名" type="warning" @click="SignUp"/>
    <van-action-bar-button class="topic" icon="/share.png" style="border-radius: 20px;margin-left: 2px;color: #000"
                           text="分享给好友" type="warning" @click="showShare = true"/>
  </van-action-bar>
  <van-share-sheet
      v-model:show="showShare"
      :options="options"
      title="立即分享给好友"
      @select="onSelect"
  />
  <van-dialog v-model:show="qr_code" show-cancel-button style="text-align: center" title="分享海报">
    <img ref="qrCodeImg" style="width: 200px;margin-top: 10px;">
  </van-dialog>
</template>
<script>
import {ref, watchEffect, onMounted} from 'vue';
import qrCode from 'qrcode';
import {closeToast, showConfirmDialog, showDialog, showLoadingToast, showToast} from 'vant';
import {useRoute} from "vue-router";
import {addViews, getVoteByUid} from '@/util/api.js';
import router from "@/router";

export default {
  methods: {useRoute},
  setup() {
    const uid = useRoute().query.uid;
    // 浏览量+1
    addViews(uid);
    const onClickLeft = () => {
      window.location.href = '/';
    };
    const isRotating = ref(true); // 添加一个变量用于控制旋转状态
    const onClickRight = () => {
      const audioElement = document.getElementById('audioPlayer'); // 获取音频播放器元素
      // 找到需要控制动画的元素
      const rotateIcon = document.querySelector('.rotate');
      if (isRotating.value) {
        // 暂停旋转
        rotateIcon.style.animationPlayState = 'paused';
        audioElement.pause(); // 调用 pause() 方法暂停音乐
      } else {
        // 恢复旋转
        rotateIcon.style.animationPlayState = 'running';
        audioElement.play(); // 调用 play() 方法播放音乐
      }
      // 切换旋转状态
      isRotating.value = !isRotating.value;
    };
    const loading = ref(false);
    const onRefresh = () => {
      getVoteByUid(uid).then(data => {
        if (data.code == 200) {
          // 处理返回的数据
          document.title = data.data[0][0].title
          voting.value = data.data[0][0]
          Participants.value = data.data[1]
          filteredParticipants.value = data.data[1]; // 初始化filteredParticipants
          loading.value = false;
          showToast('刷新成功');
        } else {
          showDialog({
            title: '提示',
            message: data.msg,
          }).then(() => {
            router.push('/');
          });
        }
      }).catch(error => {
        console.error(error);
      });
    };
    const voting = ref([])
    const currentTime = ref(new Date().getTime()); // 获取当前时间并转换为时间戳
    const timeDiff = ref(new Date(voting.value.deadline).getTime() - currentTime.value); // 计算时间差
    const active = ref(0);
    const showShare = ref(false);
    const search = ref(null);
    const filteredParticipants = ref([]); // 存储过滤后的参与人员列表
    const options = [
      {name: '分享海报', icon: 'poster'},
      {name: '复制链接', icon: 'link'},
      {name: '微信', icon: 'wechat'},
      {name: 'QQ', icon: 'qq'},
    ];
    const qr_code = ref(false);
    const qrCodeImg = ref(null);
    //参与列表
    const Participants = ref([])
    // 搜索
    const searchClick = () => {
      if (search.value) {
        filteredParticipants.value = Participants.value.filter((participant) =>
            participant.title.includes(search.value)
        );
      } else {
        filteredParticipants.value = Participants.value; // 如果搜索关键词为空，则显示全部参与人员
      }
    }
    const clearClick = () => {
      search.value = null;
      filteredParticipants.value = Participants.value; // 清空搜索关键词后显示全部参与人员
    }
    const onSelect = (option) => {
      if (option.name == "复制链接") {
        showConfirmDialog({
          title: '提示',
          message: '复制失败，请手动复制链接'
        })
      }
      if (option.name == "QQ") {
        showConfirmDialog({
          title: '提示',
          message: '打开QQ失败，请手动分享到QQ'
        })
      }
      if (option.name == "微信") {
        showConfirmDialog({
          title: '提示',
          message: '打开微信失败，请手动分享到微信'
        })
      }
      if (option.name === "分享海报") {
        showShare.value = false;
        qr_code.value = true;
        setTimeout(() => {
          const canvas = document.createElement("canvas");
          qrCode.toCanvas(canvas, window.location.href, (error) => {
            if (error) {
              console.error('生成二维码失败', error);
            } else {
              // 创建海报
              const posterCanvas = document.createElement("canvas");
              const posterContext = posterCanvas.getContext("2d");
              const posterWidth = 540;
              const posterHeight = 720;

              posterCanvas.width = posterWidth;
              posterCanvas.height = posterHeight;

              // 绘制背景
              posterContext.fillStyle = "#EFF2F5";
              posterContext.fillRect(0, 0, posterWidth, posterHeight);

              // 在此处插入一张图片
              const img = new Image();
              img.setAttribute('crossOrigin', 'anonymous')
              img.src = voting.value.cover; // 使用cover参数作为图片链接
              img.onload = () => {
                posterContext.drawImage(img, 0, 0, 540, 580); // 替换x, y, width, height为具体数值
                // 绘制二维码
                posterContext.drawImage(canvas, 20, 600, 100, 100);

                // 标题
                posterContext.font = "30px Arial";
                posterContext.fillStyle = "#000";
                posterContext.fillText("南科Top投票", 180, 50);
                // 标题
                posterContext.font = "20px Arial";
                posterContext.fillStyle = "#000";
                posterContext.fillText(voting.value.title, 140, 620);
                // num
                posterContext.font = "15px Arial";
                posterContext.fillStyle = "#ff0000";
                posterContext.fillText("参与人数：" + Participants.value.length, 140, 655);
                posterContext.fillText("累计票数：" + voting.value.votes, 140, 675);
                posterContext.fillText("访问人数：" + voting.value.views, 140, 695);
                // 将海报转换为图片链接
                const posterImgURL = posterCanvas.toDataURL();

                // 更新海报图片
                qrCodeImg.value.src = posterImgURL;
              };
            }
          });
        }, 100);
      }
      showShare.value = false;
    };

    onMounted(() => {
      showLoadingToast({
        duration: 0,
        forbidClick: true,
        message: '加载中...',
      });
      getVoteByUid(uid).then(data => {
        if (data.code == 200) {
          // 处理返回的数据
          document.title = data.data[0][0].title
          voting.value = data.data[0][0]
          Participants.value = data.data[1]
          filteredParticipants.value = data.data[1]; // 初始化filteredParticipants
          closeToast();
        } else {
          showDialog({
            title: '提示',
            message: data.msg,
          }).then(() => {
            router.push('/');
          });
        }
      }).catch(error => {
        showDialog({
          title: '提示',
          message: "该投票不存在或已被删除！",
        }).then(() => {
          router.push('/');
        });
      });
      // 监听时间变化
      watchEffect(() => {
        currentTime.value = new Date().getTime(); // 更新当前时间
        timeDiff.value = new Date(voting.value.deadline).getTime() - currentTime.value; // 计算新的时间差
      });
    });

    const onFinish = () => {
      // 倒计时结束时的操作
    };
    // 报名
    const SignUp = () => {
      if (new Date() < new Date(voting.value.registrationTime.replace(/-/g, '/'))) {
        showDialog({
          title: '提示',
          message: "还没到报名时间哦~\n报名时间为：" + voting.value.registrationTime + "\n报名截至为：" + voting.value.endRegistrationTime,
        })
      } else if (new Date() > new Date(voting.value.endRegistrationTime.replace(/-/g, '/'))) {
        showDialog({
          title: '提示',
          message: "报名已经结束啦~"
        })
      } else {
        router.push({'path': '/signup', query: {uid: uid}})
      }
    }
    return {
      onClickLeft,
      onClickRight,
      loading,
      onRefresh,
      currentTime,
      timeDiff,
      active,
      search,
      onFinish,
      options,
      onSelect,
      showShare,
      qr_code,
      qrCodeImg,
      Participants,
      filteredParticipants,
      searchClick,
      clearClick,
      voting,
      SignUp
    };
  }
}
</script>

<style>
.topic {
  background: rgba(245, 242, 210, 0.8);
}

.text {
  color: #ea7878;
}

.box {
  margin: -10px 10px 10px;
}

.date {
  background: rgba(234, 228, 203, 0.8);
  border-radius: 10px;
  text-align: center;
}

.boxs {
  line-height: 3px;
  display: flex;
  justify-content: space-between;
}

.item {
  flex: 1;
  position: relative;
}

.item:last-child::after {
  display: none; /* 隐藏最后一个item的分割线 */
}

.item::after {
  content: '';
  position: absolute;
  top: 15px;
  bottom: 0;
  right: 0;
  width: 1px;
  height: 30px;
  background-color: #cdc4c4;
}

.title {
  padding-top: 20px;
}

.num {
  font-size: 20px;
}

.timing {
  padding: 8px;
  background: rgba(0, 0, 0, 0.25);
  border-radius: 10px;
}

.colon {
  display: inline-block;
  margin: 0 4px;
  color: #000;
}

.block {
  display: inline-block;
  width: 22px;
  font-size: 12px;
  font-weight: bold;
  text-align: center;
  background-color: #ffffff;
  border-radius: 5px;
}

.tabs {
  margin: 10px 0;
  border-radius: 10px;
}

.avatars {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.rotate {
  animation: rotate 3s infinite linear;
}

.rotate:hover .van-floating-bubble__wrapper {
  animation-play-state: paused;
  pointer-events: none;
}

@keyframes rotate {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

</style>
