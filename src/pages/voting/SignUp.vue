<template>
  <van-cell-group inset style="margin: 10px;">
    <van-field v-model="title" label="标题" maxlength="15" placeholder="请输入作品标题" required
               style="border-radius: 10px;"/>
  </van-cell-group>
  <van-cell-group inset style="margin: 10px;">
    <van-field v-model="Describe" autosize label="描述" label-align="top" maxlength="150" placeholder="请输入作品描述"
               required show-word-limit style="border-radius: 10px;" type="textarea"/>
  </van-cell-group>
  <div style="padding: 10px;">
    <div
        style="width: 100%;height: 100%; background: #fff; border-radius: 10px; margin-bottom: 10px;display: flex; flex-direction: column; align-items: center;">
      <div style="height: 20px; text-align: center; padding-top: 15px;margin-bottom: -15px">
        <b>上传图片</b>
      </div>
      <van-divider/>
      <van-uploader :after-read="afterRead"
                    style="flex: 1;width: 100%;height: 100%; display: flex; flex-direction: column; align-items: center; border-radius: 10px;">
        <img :src="Works" style="width: 100%; height: 180px; object-fit: cover;">
        <van-button plain style="width: 100%; border-radius: 0 0 10px 10px;" type="default">
          点我上传/修改图片
        </van-button>
      </van-uploader>
    </div>
    <van-button block style="margin-top: 10px" @click="publish">发布</van-button>
  </div>
</template>
<script>


import {ref} from "vue";
import {showDialog, showToast} from "vant";
import router from "@/router";
import {ParticipateInVoting} from "@/util/api";
import {useRoute} from "vue-router";

export default {
  setup() {
    const uid = useRoute().query.uid
    if (localStorage.getItem('username') == null) {
      showDialog({
        title: '提示',
        message: '要先登录才能参加投票哦~',
      }).then(() => {
        router.push('/user');
      });
    }
    const Works = ref("/CoverImage.png")
    const title = ref(null)
    const Describe = ref(null)
    const afterRead = (file) => {
      // 此时可以自行将文件上传至服务器
      Works.value = file.content
    };
    const publish = () => {
      if (localStorage.getItem('username') === '' || Works.value === '/CoverImage.png' || uid === '' || Works.value === '' || title.value === '' || Describe.value === '') {
        showToast("信息未填写完整")
      } else {
        ParticipateInVoting(localStorage.getItem('username'), uid, Works.value, title.value, Describe.value).then(res => {
          if (res.code == 200) {
            showDialog({
              title: '提示',
              message: '参与投票成功！',
            }).then(() => {
              history.go(-1);
            });
          } else {
            showToast(res.msg)
          }
        }).catch(err => {
          console.log(err)
        })
      }
    }
    return {
      Works,
      afterRead,
      Describe,
      title,
      publish
    }
  }
}
</script>

<style scoped>
body {
  background: #efefef;
}
</style>
