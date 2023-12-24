<template>
  <div style="padding: 10px;">
    <div
        style="width: 100%;height: 100%; background: #fff; border-radius: 10px; margin-bottom: 10px;display: flex; flex-direction: column; align-items: center;">
      <div style="height: 20px; text-align: center; padding-top: 15px;margin-bottom: -15px">
        <b>封面图</b>
      </div>
      <van-divider/>
      <van-uploader :after-read="afterRead"
                    style="flex: 1;width: 100%;height: 100%; display: flex; flex-direction: column; align-items: center; border-radius: 10px;">
        <img :src="CoverImage" style="width: 100%; height: 180px; object-fit: cover;">
        <van-button plain style="width: 100%; border-radius: 0 0 10px 10px;" type="default">
          点我上传/修改封面图
        </van-button>
      </van-uploader>
    </div>
    <div style="background: #fff;border-radius: 10px;margin-bottom: 10px">
      <van-field v-model="title" label="投票标题" maxlength="15" placeholder="请输入标题" required/>
      <van-field v-model="rules" autosize label="规则" label-align="top" maxlength="150"
                 placeholder="请输入投票规则及奖品" required show-word-limit type="textarea"/>
      <van-field v-model="vote_num" label="日投票次数" placeholder="请输入投票次数" required type="number"/>
      <van-field v-model="backgroundMusic" label="背景音乐" placeholder="音乐直链(选填)">
        <template #button>
          <van-button size="small" type="default" @click="选择音乐">选择音乐</van-button>
        </template>
      </van-field>
      <van-field v-model="registration_time" label="开始报名日期" label-align="top" placeholder="请选择日期"
                 required type="datetime-local" @blur="handleRegistrationTimeBlur"/>
      <van-field v-model="end_registration_time" label="截止报名日期" label-align="top" placeholder="请选择日期"
                 required type="datetime-local" @blur="handleEndRegistrationTimeBlur"/>
      <van-field v-model="date" label="投票结束日期" label-align="top" placeholder="请选择日期" required
                 type="datetime-local" @blur="handleVotingEndTimeBlur"/>
    </div>
    <van-button block style="margin-top: 10px" @click="publish">发布</van-button>
  </div>
  <van-popup
      v-model:show="music"
      :style="{ height: '70%',padding:'50px 10px'}"
      closeable
      position="bottom"
      round
  >
    <van-search v-model="musicName" placeholder="请输入音乐名称(仅支持网易云)" @search="搜索音乐"/>
    <div v-if="music_list.length === 0" style="text-align: center">
      <img src="/SearchIsEmpty.png" style="width: 80%;height: 60%">
      <p>暂无搜索内容</p>
    </div>
    <div v-for="(item, index) in music_list" v-else
         :key="index" style="background: #e8e8e8;margin: 11px;padding: 1px;border-radius: 10px">
      <van-card
          :desc="item.Singer"
          :thumb="item.Cover"
          :title="item.name"
      >
        <template #price-top>
          <audio :src="item.url" controls style="height: 25px;max-width: 100%"></audio>
        </template>
        <template #num>
          <van-button block size="mini" style="border-radius: 10px;" type="default" @click="获取音乐直链(item.url)">
            选择此音乐
          </van-button>
        </template>
      </van-card>
    </div>
  </van-popup>
</template>
<script>


import {inject, ref} from "vue";
import {closeToast, showDialog, showLoadingToast, showToast} from "vant";
import router from "@/router";
import {addVote, 获取网易云音乐} from "@/util/api";
export default {
  setup() {
    const actives = inject('active');
    const title = ref(null)
    const rules = ref(null)
    const vote_num = ref(5)
    const backgroundMusic = ref(null)
    const date = ref(null)
    const registration_time = ref(null)
    const end_registration_time = ref(null)
    actives.value = 'InitiateVoting'
    if (localStorage.getItem('username') == null) {
      showDialog({
        title: '提示',
        message: '要先登录才能发布投票哦~',
      }).then(() => {
        router.push('/user');
      });
    }
    const CoverImage = ref("/CoverImage.png")
    const afterRead = (file) => {
      // 此时可以自行将文件上传至服务器
      CoverImage.value = file.content
    };
    // 发布投票
    const publish = () => {
      if (localStorage.getItem('username') === '' || CoverImage.value === '/CoverImage.png' || title.value === '' || date.value === '' || registration_time.value === '' || end_registration_time.value === '' || rules.value === '') {
        showToast("投票信息未填写完整")
      } else {
        showLoadingToast({
          duration: 0,
          forbidClick: true,
          message: '发布中...',
        });
        addVote(
            localStorage.getItem('username'),
            CoverImage.value,
            title.value,
            date.value,
            registration_time.value,
            end_registration_time.value,
            vote_num.value,
            backgroundMusic.value,
            rules.value
        ).then(res => {
          if (res.code == 200) {
            closeToast();
            showDialog({
              title: '提示',
              message: '投票发布成功！',
            }).then(() => {
              location.reload()
            });
          } else {
            closeToast();
            showDialog({
              title: '提示',
              message: res.msg,
            }).then(() => {
            });
          }
        }).catch(err => {
          console.log(err)
          closeToast();
          showDialog({
            title: '提示',
            message: '要先登录才能发布投票哦~',
          }).then(() => {
            router.push('/user');
          });
        })
      }
    }
    const handleRegistrationTimeBlur = () => {
      if (end_registration_time.value < registration_time.value) {
        end_registration_time.value = '';
        // 可以给出提示或者其他处理
      }
    };

    const handleEndRegistrationTimeBlur = () => {
      if (end_registration_time.value < registration_time.value) {
        end_registration_time.value = '';
        // 可以给出提示或者其他处理
      }
      if (date.value < end_registration_time.value) {
        date.value = '';
        // 可以给出提示或者其他处理
      }
    };

    const handleVotingEndTimeBlur = () => {
      if (date.value < end_registration_time.value) {
        date.value = '';
        // 可以给出提示或者其他处理
      }
    };
    const music = ref(false);
    const 选择音乐 = () => {
      music.value = ref(true)
    }
    const musicName = ref(null)
    const music_list = ref([])
    const 搜索音乐 = () => {
      showLoadingToast({
        duration: 0,
        forbidClick: true,
        message: '搜索音乐中...',
      });
      获取网易云音乐(musicName.value).then(res => {
        if (res.code == 200) {
          music_list.value = res.data
          closeToast();
        } else {
          closeToast();
          showToast("搜索失败")
        }
      })
    }
    const 获取音乐直链 = (url) => {
      backgroundMusic.value = url
      music.value = false
    }
    return {
      afterRead,
      CoverImage,
      title,
      rules,
      vote_num,
      backgroundMusic,
      date,
      registration_time,
      end_registration_time,
      publish,
      handleRegistrationTimeBlur,
      handleEndRegistrationTimeBlur,
      handleVotingEndTimeBlur,
      music,
      选择音乐,
      musicName,
      搜索音乐,
      music_list,
      获取音乐直链
    };
  }
}
</script>

<style scoped>
body {
  background: #efefef;
}

audio::-webkit-media-controls-timeline {
  display: none !important;
}

audio::-webkit-media-controls {
  width: auto !important;
}

audio::-webkit-media-controls-volume-slider {
  -webkit-appearance: none;
}
</style>
