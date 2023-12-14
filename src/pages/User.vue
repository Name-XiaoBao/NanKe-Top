<template>
  <div>
    <van-pull-refresh v-model="loading" @refresh="onRefresh">
      <div class="User">
        <div class="personalData">
          <img :src="LoginState.username!=null ? userInfo.avatar : '/Avatar.png'"
               class="avatar"
               @click="LoginState.username!=null ?updateUserInfo=true:null">
          <h4 v-if="LoginState.username!=null" @click="updateUserInfo=true">{{ userInfo.nickname }}</h4>
          <van-button v-else color="#fff" style="background: rgba(0,0,0,0);border-radius: 10px;margin-top: 30px"
                      @click="invokeLogin">
            点我登录
          </van-button>
        </div>
      </div>
      <div :class="LoginState.username ? 'UserInfo' : 'UserInfoPlus'">
        <div style="flex-basis: 50%;" @click="polls">
          <h3>{{ userInfo.launch == null ? 0 : userInfo.launch }}</h3>
          <p>发起的投票</p>
        </div>
        <div style="flex-basis: 50%;" @click="Participation">
          <h3>{{ userInfo.participate == null ? 0 : userInfo.participate }}</h3>
          <p>参与的投票</p>
        </div>
      </div>
      <div class="items">
        <div v-if="LoginState.username!=null"
             style="background: #fff;border-radius: 10px 10px 0 0;padding: 20px;margin-bottom: 2px;display: flex;align-items: center;"
             @click="UpDateUserInfo">
          <div style="display: flex;align-items: center;flex-grow: 1;">
            <van-icon name="./UpdateUserInfo.png" size="20px" style="margin-right: 10px;"></van-icon>
            <p style="margin: 0;">修改资料</p>
          </div>
          <van-icon name="arrow" style="margin-left: auto;"></van-icon>
        </div>
        <div style="background: #fff;padding: 20px;margin-bottom: 2px;display: flex;align-items: center;"
             @click="feedback">
          <div style="display: flex;align-items: center;flex-grow: 1;">
            <van-icon name="./feedback.png" size="20px" style="margin-right: 10px;"></van-icon>
            <p style="margin: 0;">问题反馈</p>
          </div>
          <van-icon name="arrow" style="margin-left: auto;"></van-icon>
        </div>
        <div style="background: #fff;padding: 20px;margin-bottom: 2px;display: flex;align-items: center;"
             @click="aboutUs">
          <div style="display: flex;align-items: center;flex-grow: 1;">
            <van-icon name="./aboutUs.png" size="20px" style="margin-right: 10px;"></van-icon>
            <p style="margin: 0;">关于我们</p>
          </div>
          <van-icon name="arrow" style="margin-left: auto;"></van-icon>
        </div>
        <div v-if="LoginState.username!=null"
             style="background: #fff;border-radius: 0 0 10px 10px;padding: 20px;margin-bottom: 2px;display: flex;align-items: center;"
             @click="endLogin">
          <div style="display: flex;align-items: center;flex-grow: 1;">
            <van-icon name="./endLogin.png" size="20px" style="margin-right: 10px;"></van-icon>
            <p style="margin: 0;color: red">退出登录</p>
          </div>
          <van-icon name="arrow" style="margin-left: auto;"></van-icon>
        </div>
      </div>
    </van-pull-refresh>
  </div>
  <van-popup v-model:show="show"
             :style="{ background:'linear-gradient(to top, rgba(128, 194, 241), #fff)',height:'500px',paddingTop:'50px' }"
             closeable position="bottom"
             round>
    <van-tabs v-model:active="active" background="rgba(0,0,0,0)" swipeable>
      <van-tab title="登录">
        <van-form style="margin-top: 20%" @submit="onLogin">
          <van-cell-group inset>
            <van-field
                v-model="userInfo.username"
                label="邮箱"
                name="邮箱"
                placeholder="邮箱账号"
                type="email"
            />
          </van-cell-group>
          <br>
          <van-cell-group inset>
            <van-field
                v-model="userInfo.password"
                label="密码"
                name="密码"
                placeholder="密码"
                type="password"
            />
          </van-cell-group>
          <div style="margin: 30px 15px;">
            <van-button block native-type="submit" plain round type="primary">
              登录
            </van-button>
            <p style="float: left;">登录并同意<span style="color:#396ff8;" @click="用户协议">用户协议</span></p>
            <p style="float: right;margin-right: 10px;" @click="忘记密码">忘记密码</p>
          </div>
        </van-form>
      </van-tab>
      <van-tab title="注册">
        <van-form style="margin-top: 10%" @submit="onRegister">
          <van-cell-group inset>
            <van-field
                v-model="userInfo.nickname"
                label="昵称"
                name="昵称"
                placeholder="昵称"
            />
          </van-cell-group>
          <br>
          <van-cell-group inset>
            <van-field
                v-model="userInfo.username"
                label="邮箱"
                name="邮箱"
                placeholder="邮箱账号"
                type="email"
            />
          </van-cell-group>
          <br>
          <van-cell-group inset>
            <van-field
                v-model="userInfo.password"
                label="密码"
                name="密码"
                placeholder="密码"
                type="password"
            />
          </van-cell-group>
          <br>
          <van-cell-group inset>
            <van-field
                v-model="code"
                center
                clearable
                label="邮箱验证码"
                name="邮箱验证码"
                placeholder="邮箱验证码"
                type="number"
            >
              <template #button>
                <van-button :disabled="disabled" size="small" type="primary" @click="sendCode">
                  {{ disabled ? `${countdown}s后重新发送` : buttonText }}
                </van-button>
              </template>
            </van-field>
          </van-cell-group>
          <div style="margin: 30px 15px;">
            <van-button block native-type="submit" plain round type="primary">
              注册
            </van-button>
          </div>
        </van-form>
      </van-tab>
    </van-tabs>
  </van-popup>
  <van-popup
      v-model:show="AboutUs"
      :style="{ height: '50%', padding: '50px 10px'}"
      closeable
      position="bottom"
      round
  >
    <h3>关于我们</h3>
    <p style="line-height: 30px;letter-spacing: 0.2em;text-indent: 2em">
      南科Top网是“逻辑探索者”社团的社长暴振宇和副社长孙皓翔所开发的一款线上投票平台，致力于为广大南科学子提供更为便捷的网上服务。</p>
    <p>联系方式：</p>
    <p>小暴微信 Name-XiaoBao</p>
    <p>老孙微信 sunhx1116</p>
    <h3>系统说明</h3>
    <p style="line-height: 30px;letter-spacing: 0.2em;text-indent: 2em">
      该投票系统采用了后端SSM架构和前端Vue.js 3 + Vant 4组件库开发。
      后端使用SpringBoot作为基础框架，简化了项目的搭建和配置，提高了开发效率。同时使用MyBatis作为持久层框架，通过注解和XML文件实现对数据库的操作。数据库采用MySQL
      5.7，存储投票活动、用户信息、投票结果等数据。
    </p>
    <p style="line-height: 30px;letter-spacing: 0.2em;text-indent: 2em">
      前端使用Vue.js 3作为主要框架，结合Vant
      4组件库实现页面的搭建和交互。Vue.js是一套用于构建用户界面的渐进式框架，具有响应式数据绑定和组件化的特点，方便开发者进行前端开发和维护。Vant是一套基于Vue.js的移动端UI组件库，提供了丰富的组件和样式，能够快速构建出美观且易用的用户界面。
    </p>
    <p style="line-height: 30px;letter-spacing: 0.2em;text-indent: 2em">
      通过以上技术栈和架构设计，该投票系统能够实现用户的注册、登录、发起投票、参与投票、个人信息管理等功能。后端使用SSM框架提供接口服务，前端使用Vue.js
      3 + Vant 4实现页面展示和用户交互，为用户提供友好的投票体验。
      如有任何问题，请随时与我们联系。感谢您对本项目的关注！
    </p>
  </van-popup>
  <van-popup
      v-model:show="Feedback"
      :style="{ height: '40%', padding: '50px 10px'}"
      closeable
      position="bottom"
      round
  >
    <van-form @submit="提交反馈">
      <van-field v-model="feedbackContent" label="反馈内容" label-align="top" name="反馈内容"
                 placeholder="请输入要反馈的内容"
                 type="textarea"/>
      <van-field v-model="contact" label="联系方式" name="联系方式" placeholder="QQ\邮箱\电话"/>
      <br>
      <van-button native-type="submit" style="width: 100%" type="default">发送</van-button>
    </van-form>

  </van-popup>
  <van-popup
      v-model:show="updateUserInfo"
      :style="{ height: '40%', padding: '30px 10px'}"
      closeable
      position="bottom"
      round
  >
    <div style="width:100%;text-align: center;margin-bottom: 10px">
      <p style="font-size: 15px">修改头像</p>
      <van-uploader :after-read="afterRead">
        <img :src="userInfo.avatar" class="avatar">
      </van-uploader>
    </div>
    <van-field v-model="userInfo.nickname" label="修改昵称" placeholder="请输入昵称"/>
    <br>
    <van-button native-type="submit" style="width: 100%" type="default" @click="提交修改">修改</van-button>
  </van-popup>
  <van-dialog v-model:show="forgetPassword" :show-confirm-button="false" title="忘记密码">
    <van-form @submit="忘记密码的修改">
      <van-cell-group inset>
        <van-field v-model="email" name="邮箱" placeholder="请输入您的邮箱" type="email"/>
        <van-field v-model="emailCode" name="验证码" placeholder="请输入邮箱验证码" type="number">
          <template #button>
            <van-button :disabled="disabled" size="small" type="primary" @click="修改密码验证码发送">
              {{ disabled ? `${countdown}s后重新发送` : buttonText }}
            </van-button>
          </template>
        </van-field>
        <van-field v-model="newPasswords" name="新密码" placeholder="请输入新密码" type="password"/>
      </van-cell-group>
      <div style="display: flex;">
        <van-button style="flex: 1;border-radius: 0" type="default" @click="忘记密码的取消">取消</van-button>
        <van-button native-type="submit" style="flex: 1;border-radius: 0" type="default">修改</van-button>
      </div>
    </van-form>
  </van-dialog>
  <van-popup
      v-model:show="userAgreement"
      :style="{ height: '70%' }"
      position="bottom"
      round
  >
    <div style="padding: 10px;">
      <h4>南科Top榜 用户协议</h4>
      <p>
        欢迎您使用南科Top榜（以下简称“本网站”）服务。在注册成为本网站用户之前，请您仔细阅读本协议的所有内容。只有同意本协议所有条款，才能成为本网站的用户，并享受本网站提供的相关服务。一旦注册成功，即表示您已经完全接受了本协议的全部条款。</p>
      <h4>一、用户注册</h4>
      <p>1.用户注册成功后，将获得本网站账号，账号所有权归本网站所有。</p>
      <p>
        2.用户应妥善保管自己的账号和密码，因黑客行为或用户的保管疏忽导致账号、密码遭他人非法使用的，本网站不承担任何责任。</p>
      <h4>二、用户行为规范</h4>
      <p>1.用户在使用本网站服务时，必须遵守中华人民共和国相关法律法规。</p>
      <p>2.用户不得利用本网站服务进行违反法律法规的行为，包括但不限于发布违法信息、侵犯他人合法权益等行为。</p>
      <p>3.用户不得利用本网站服务从事影响本网站正常运转的行为。</p>
      <h4>三、免责声明</h4>
      <p>
        1.用户明确同意其使用本网站服务所存在的风险将完全由其自己承担；因其使用本网站服务而产生的一切后果也由其自己承担，本网站对用户不承担任何责任。</p>
      <p>
        2.本网站不担保网络服务一定能满足用户的要求，也不担保网络服务不会中断，对网络服务的及时性、安全性、准确性也都不作担保。</p>
      <h4>四、其他</h4>
      <p>1.本协议的订立、执行和解释及争议的解决均应适用中华人民共和国法律。</p>
      <p>
        2.若您和本网站之间发生任何纠纷或争议，应通过友好协商解决，协商不成的，任何一方均可向本网站所在地的人民法院提起诉讼。</p>
      <p>3.本协议的任何条款无论因何种原因无效或不具可执行性，其余条款仍有效，对双方具有约束力。</p>
      <p>4.本网站保留在法律允许的范围内自行修改本协议的权利。</p>
      <p>5.本网站保留随时修改、增加或删除本协议条款的权利。</p>
      <p>6.本协议最终解释权归南科Top榜所有。</p>
      <van-button style="margin-top: 10px;width: 100%;" type="default" @click="userAgreement = false">我已阅读并同意
      </van-button>
    </div>
  </van-popup>
</template>
<script>
import {computed, inject, onBeforeMount, ref} from 'vue';
import {closeToast, showConfirmDialog, showDialog, showLoadingToast, showToast} from "vant";
import md5 from 'js-md5';
import {反馈, getCode, Login, Register, UserInfos, 修改资料, updatePasswoed} from "@/util/api";
import router from "@/router";

export default {
  setup() {
    onBeforeMount(() => {
      if (LoginState.value.username != null) {
        UserInfos(LoginState.value.username).then(data => {
          if (data.code === 200) {
            userInfo.value = data.data[0]
            if (data.data[1][0].uid === 0) {
              userInfo.value.launch = 0
              userInfo.value.participate = 0
            } else {
              userInfo.value.launch = data.data[1].length
              userInfo.value.participate = data.data[2].length
            }
          }
        }).catch(error => {
          console.log(error)
        })
      }
    })
    const actives = inject('active');
    actives.value = 'user'
    const loading = ref(false);
    const onRefresh = () => {
      if (LoginState.value.username != null) {
        UserInfos(LoginState.value.username).then(data => {
          if (data.code === 200) {
            userInfo.value = data.data[0]
            if (data.data[1][0].uid === 0) {
              userInfo.value.launch = 0
              userInfo.value.participate = 0
            } else {
              userInfo.value.launch = data.data[1].length
              userInfo.value.participate = data.data[2].length
            }
            showToast("刷新成功")
          }
        }).catch(error => {
          console.log(error)
        })
      }
      loading.value = false;
    };
    const LoginState = ref({
      'username': computed(() => {
        return localStorage.getItem('username');
      })
    })
    const userInfo = ref({
      avatar: ref(null),
      nickname: ref(null),
      username: ref(null),
      password: ref(null)
    })
    const code = ref(null)
    const md5Code = ref(null)
    const countdown = ref(60);
    const buttonText = ref('发送验证码');
    const disabled = ref(false);
    const show = ref(false);
    const active = ref(0);
    const invokeLogin = () => {
      show.value = true;
    }
    const onLogin = (values) => {
      Login(userInfo.value.username, userInfo.value.password).then(data => {
        showToast(data.msg)
        if (data.code === 200) {
          UserInfos(userInfo.value.username).then(data => {
            if (data.code === 200) {
              userInfo.value = data.data[0]
              window.localStorage.setItem('username', userInfo.value.username)
              window.localStorage.setItem('password', md5(userInfo.value.password))
              show.value = false;
              showToast("登录成功")
              userInfo.value.username = null;
              userInfo.value.password = null;
              setTimeout(function () {
                window.location.reload();
              }, 1000);
            }
          }).catch(error => {
            console.log(error)
          })
        }
      }).catch(error => {
        console.log(error)
      })
    };
    const endLogin = () => {
      showConfirmDialog({
        title: '退出登录',
        message: '你要离开我了嘛~(ಥ _ ಥ)',
      }).then(() => {
        window.localStorage.clear()
        showToast("已退出登录~")
        window.open("/", "_self")
      }).catch(() => {
        // on cancel
      });
    }
    const sendCode = () => {
      showLoadingToast({
        duration: 0,
        forbidClick: true,
        message: '发送中...',
      });
      disabled.value = true;
      buttonText.value = '发送中';
      getCode(userInfo.value.username).then(data => {
        if (data.code === 200) {
          closeToast();
          showToast(data.msg)
          md5Code.value = data.data
          const timer = setInterval(() => {
            countdown.value--;
            if (countdown.value === 0) {
              clearInterval(timer);
              disabled.value = false;
              countdown.value = 60;
              buttonText.value = '重新发送';
            }
          }, 1000);
        } else {
          showToast(data.msg)
          disabled.value = false; // 恢复按钮为可点击状态
          buttonText.value = '重新发送'; // 将按钮文字设置为"重新发送"
        }
      }).catch(error => {
        console.error(error);
        closeToast();
      });
    }
    const onRegister = (values) => {
      if (values.邮箱验证码 == null || values.邮箱验证码 == '') {
        showToast("请输入验证码")
      } else {
        if (md5Code.value == md5(code.value)) {
          Register(userInfo.value.nickname, userInfo.value.username, userInfo.value.password).then(data => {
            showToast(data.msg)
          }).catch(error => {
            console.log(error)
          })
        } else {
          showToast("验证码错误\n请输入正确的验证码")
        }
      }
    };
    const polls = () => {
      if (LoginState.value.username == null) {
        show.value = true;
      } else {
        router.push('/VoteLaunch');
      }
    }
    const Participation = () => {
      if (LoginState.value.username == null) {
        show.value = true;
      } else {
        router.push('/VoteParticipateIn');
      }
    }
    const updateUserInfo = ref(false)
    const afterRead = (file) => {
      userInfo.value.avatar = file.content
    }
    const UpDateUserInfo = () => {
      updateUserInfo.value = ref(true)
    }
    const 提交修改 = () => {
      修改资料(LoginState.value.username, userInfo.value.avatar, userInfo.value.nickname).then(data => {
        if (data.code == 200) {
          showToast(data.msg)
        } else {
          showDialog({
            title: data.msg,
            message: '啊哦~\n修改失败了(ಥ _ ಥ)\n请稍候再试吧',
          }).then(() => {
            UserInfos(LoginState.value.username).then(data => {
              if (data.code === 200) {
                userInfo.value = data.data[0]
                if (data.data[1][0].uid === 0) {
                  userInfo.value.launch = 0
                  userInfo.value.participate = 0
                } else {
                  userInfo.value.launch = data.data[1].length
                  userInfo.value.participate = data.data[2].length
                }
              }
            }).catch(error => {
              console.log(error)
            })
          });
        }
      }).catch(error => {
        console.log(error)
      })
    }
    const AboutUs = ref(false)
    const aboutUs = () => {
      AboutUs.value = true;
    }
    const Feedback = ref(false)
    const feedback = () => {
      Feedback.value = true;
    }
    const userAgreement = ref(false)
    const 用户协议 = () => {
      userAgreement.value = true;
    }
    const feedbackContent = ref()
    const contact = ref()
    const 提交反馈 = (values) => {
      if (values.反馈内容 == '' || values.反馈内容 == '') {
        showToast("请填写完整")
      } else {
        showLoadingToast({
          duration: 0,
          forbidClick: true,
          message: '发送中...',
        });
        反馈(values.反馈内容, values.联系方式).then(res => {
          if (res.code == 200) {
            closeToast();
            showToast(res.msg)
            Feedback.value = false;
            feedbackContent.value = ref()
            contact.value = ref()
          } else {
            showToast(res.msg)
          }
        }).catch(err => {
          console.log(err)
        })
      }

    }
    const forgetPassword = ref(false)
    const 忘记密码 = () => {
      forgetPassword.value = ref(true)
    }
    const 修改密码验证码发送 = () => {
      showLoadingToast({
        duration: 0,
        forbidClick: true,
        message: '发送中...',
      });
      disabled.value = true;
      buttonText.value = '发送中';
      getCode(email.value).then(data => {
        if (data.code === 200) {
          closeToast();
          showToast(data.msg)
          md5Code.value = data.data
          const timer = setInterval(() => {
            countdown.value--;
            if (countdown.value === 0) {
              clearInterval(timer);
              disabled.value = false;
              countdown.value = 60;
              buttonText.value = '重新发送';
            }
          }, 1000);
        } else {
          showToast(data.msg)
          disabled.value = false; // 恢复按钮为可点击状态
          buttonText.value = '重新发送'; // 将按钮文字设置为"重新发送"
        }
      }).catch(error => {
        console.error(error);
        closeToast();
      });
    }
    const email = ref(null)
    const emailCode = ref(null)
    const newPasswords = ref(null)
    const 忘记密码的修改 = (values) => {
      if (values.邮箱 === '' || values.验证码 === '' || values.新密码 === '') {
        showToast('请填写完整')
      } else {
        if (md5Code.value === md5(values.验证码)) {
          updatePasswoed(values.邮箱, values.新密码).then(data => {
            if (data.code === 200) {
              showToast(data.msg)
              location.reload();
            } else {
              showToast(data.msg)
            }
          }).catch(error => {
            console.log(error)
          })
        } else {
          showToast('验证码错误')
        }
      }
    }
    const 忘记密码的取消 = () => {
      forgetPassword.value = false
    }
    return {
      loading,
      onRefresh,
      LoginState,
      code,
      active,
      disabled,
      countdown,
      buttonText,
      onLogin,
      onRegister,
      show,
      invokeLogin,
      sendCode,
      endLogin,
      polls,
      Participation,
      afterRead,
      updateUserInfo,
      UpDateUserInfo,
      提交修改,
      userInfo,
      AboutUs,
      aboutUs,
      Feedback,
      feedback,
      feedbackContent,
      contact,
      提交反馈,
      forgetPassword,
      修改密码验证码发送,
      忘记密码,
      email,
      emailCode,
      newPasswords,
      忘记密码的修改,
      忘记密码的取消,
      userAgreement,
      用户协议
    }
  }
}
</script>

<style>
body {
  background: #F7F7F7;
}

.User {
  width: 100%;
  height: 280px;
  background: linear-gradient(to right, rgba(128, 194, 241, 0.3), rgba(232, 178, 246, 0.6)), url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBpZD0ic3ZnIiB2aWV3Qm94PSIwIDAgMTQ0MCA2MDAiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgY2xhc3M9InRyYW5zaXRpb24gZHVyYXRpb24tMzAwIGVhc2UtaW4tb3V0IGRlbGF5LTE1MCI+PHN0eWxlPgoucGF0aC0wewphbmltYXRpb246cGF0aEFuaW0tMCA0czsKYW5pbWF0aW9uLXRpbWluZy1mdW5jdGlvbjogbGluZWFyOwphbmltYXRpb24taXRlcmF0aW9uLWNvdW50OiBpbmZpbml0ZTsKfQpAa2V5ZnJhbWVzIHBhdGhBbmltLTB7CjAlewpkOiBwYXRoKCJNIDAsNjAwIEMgMCw2MDAgMCwyMDAgMCwyMDAgQyAxMjEuNDA2Njk4NTY0NTkzMzEsMjAyLjY4ODk5NTIxNTMxMTAyIDI0Mi44MTMzOTcxMjkxODY2MywyMDUuMzc3OTkwNDMwNjIyMDIgMzMyLDIxNiBDIDQyMS4xODY2MDI4NzA4MTM0LDIyNi42MjIwMDk1NjkzNzc5OCA0NzguMTUzMTEwMDQ3ODQ2OSwyNDUuMTc3MDMzNDkyODIyOTQgNTcxLDI1MyBDIDY2My44NDY4ODk5NTIxNTMxLDI2MC44MjI5NjY1MDcxNzcwNiA3OTIuNTc0MTYyNjc5NDI1OSwyNTcuOTEzODc1NTk4MDg2MiA4ODUsMjQ4IEMgOTc3LjQyNTgzNzMyMDU3NDEsMjM4LjA4NjEyNDQwMTkxMzg1IDEwMzMuNTUwMjM5MjM0NDQ5NywyMjEuMTY3NDY0MTE0ODMyNSAxMTIwLDIxMiBDIDEyMDYuNDQ5NzYwNzY1NTUwMywyMDIuODMyNTM1ODg1MTY3NSAxMzIzLjIyNDg4MDM4Mjc3NTIsMjAxLjQxNjI2Nzk0MjU4Mzc0IDE0NDAsMjAwIEMgMTQ0MCwyMDAgMTQ0MCw2MDAgMTQ0MCw2MDAgWiIpOwp9CjI1JXsKZDogcGF0aCgiTSAwLDYwMCBDIDAsNjAwIDAsMjAwIDAsMjAwIEMgOTAuMzYzNjM2MzYzNjM2MzUsMTc0LjgzMjUzNTg4NTE2NzQ2IDE4MC43MjcyNzI3MjcyNzI3LDE0OS42NjUwNzE3NzAzMzQ5MiAyODYsMTU2IEMgMzkxLjI3MjcyNzI3MjcyNzMsMTYyLjMzNDkyODIyOTY2NTA4IDUxMS40NTQ1NDU0NTQ1NDU1LDIwMC4xNzIyNDg4MDM4Mjc3MyA2MTEsMjIxIEMgNzEwLjU0NTQ1NDU0NTQ1NDUsMjQxLjgyNzc1MTE5NjE3MjI3IDc4OS40NTQ1NDU0NTQ1NDU1LDI0NS42NDU5MzMwMTQzNTQxIDg4MCwyNDEgQyA5NzAuNTQ1NDU0NTQ1NDU0NSwyMzYuMzU0MDY2OTg1NjQ1OSAxMDcyLjcyNzI3MjcyNzI3MjcsMjIzLjI0NDAxOTEzODc1NTk2IDExNjgsMjE1IEMgMTI2My4yNzI3MjcyNzI3MjczLDIwNi43NTU5ODA4NjEyNDQwNCAxMzUxLjYzNjM2MzYzNjM2MzUsMjAzLjM3Nzk5MDQzMDYyMjAyIDE0NDAsMjAwIEMgMTQ0MCwyMDAgMTQ0MCw2MDAgMTQ0MCw2MDAgWiIpOwp9CjUwJXsKZDogcGF0aCgiTSAwLDYwMCBDIDAsNjAwIDAsMjAwIDAsMjAwIEMgOTYuNDQwMTkxMzg3NTU5NzksMTg5LjU5ODA4NjEyNDQwMTkgMTkyLjg4MDM4Mjc3NTExOTU5LDE3OS4xOTYxNzIyNDg4MDM4MyAyNzYsMTg5IEMgMzU5LjExOTYxNzIyNDg4MDQsMTk4LjgwMzgyNzc1MTE5NjE3IDQyOC45MTg2NjAyODcwODEzNywyMjguODEzMzk3MTI5MTg2NjMgNTI0LDIxMSBDIDYxOS4wODEzMzk3MTI5MTg2LDE5My4xODY2MDI4NzA4MTMzNyA3MzkuNDQ0OTc2MDc2NTU1MSwxMjcuNTUwMjM5MjM0NDQ5NzMgODQ2LDEyNiBDIDk1Mi41NTUwMjM5MjM0NDQ5LDEyNC40NDk3NjA3NjU1NTAyNyAxMDQ1LjMwMTQzNTQwNjY5ODYsMTg2Ljk4NTY0NTkzMzAxNDM4IDExNDIsMjEwIEMgMTIzOC42OTg1NjQ1OTMzMDE0LDIzMy4wMTQzNTQwNjY5ODU2MiAxMzM5LjM0OTI4MjI5NjY1MDcsMjE2LjUwNzE3NzAzMzQ5MjggMTQ0MCwyMDAgQyAxNDQwLDIwMCAxNDQwLDYwMCAxNDQwLDYwMCBaIik7Cn0KNzUlewpkOiBwYXRoKCJNIDAsNjAwIEMgMCw2MDAgMCwyMDAgMCwyMDAgQyAxMTkuNzQxNjI2Nzk0MjU4MzQsMjA2Ljc0NjQxMTQ4MzI1MzYgMjM5LjQ4MzI1MzU4ODUxNjY3LDIxMy40OTI4MjI5NjY1MDcyIDMyOCwyMTkgQyA0MTYuNTE2NzQ2NDExNDgzMywyMjQuNTA3MTc3MDMzNDkyOCA0NzMuODA4NjEyNDQwMTkxNSwyMjguNzc1MTE5NjE3MjI0ODcgNTY0LDIzMyBDIDY1NC4xOTEzODc1NTk4MDg1LDIzNy4yMjQ4ODAzODI3NzUxMyA3NzcuMjgyMjk2NjUwNzE3NiwyNDEuNDA2Njk4NTY0NTkzMyA4ODUsMjIxIEMgOTkyLjcxNzcwMzM0OTI4MjQsMjAwLjU5MzMwMTQzNTQwNjcgMTA4NS4wNjIyMDA5NTY5Mzc5LDE1NS41OTgwODYxMjQ0MDE5IDExNzUsMTQ4IEMgMTI2NC45Mzc3OTkwNDMwNjIxLDE0MC40MDE5MTM4NzU1OTgxIDEzNTIuNDY4ODk5NTIxNTMxLDE3MC4yMDA5NTY5Mzc3OTkwNSAxNDQwLDIwMCBDIDE0NDAsMjAwIDE0NDAsNjAwIDE0NDAsNjAwIFoiKTsKfQoxMDAlewpkOiBwYXRoKCJNIDAsNjAwIEMgMCw2MDAgMCwyMDAgMCwyMDAgQyAxMjEuNDA2Njk4NTY0NTkzMzEsMjAyLjY4ODk5NTIxNTMxMTAyIDI0Mi44MTMzOTcxMjkxODY2MywyMDUuMzc3OTkwNDMwNjIyMDIgMzMyLDIxNiBDIDQyMS4xODY2MDI4NzA4MTM0LDIyNi42MjIwMDk1NjkzNzc5OCA0NzguMTUzMTEwMDQ3ODQ2OSwyNDUuMTc3MDMzNDkyODIyOTQgNTcxLDI1MyBDIDY2My44NDY4ODk5NTIxNTMxLDI2MC44MjI5NjY1MDcxNzcwNiA3OTIuNTc0MTYyNjc5NDI1OSwyNTcuOTEzODc1NTk4MDg2MiA4ODUsMjQ4IEMgOTc3LjQyNTgzNzMyMDU3NDEsMjM4LjA4NjEyNDQwMTkxMzg1IDEwMzMuNTUwMjM5MjM0NDQ5NywyMjEuMTY3NDY0MTE0ODMyNSAxMTIwLDIxMiBDIDEyMDYuNDQ5NzYwNzY1NTUwMywyMDIuODMyNTM1ODg1MTY3NSAxMzIzLjIyNDg4MDM4Mjc3NTIsMjAxLjQxNjI2Nzk0MjU4Mzc0IDE0NDAsMjAwIEMgMTQ0MCwyMDAgMTQ0MCw2MDAgMTQ0MCw2MDAgWiIpOwp9Cn08L3N0eWxlPjxkZWZzPjxsaW5lYXJHcmFkaWVudCBpZD0iZ3JhZGllbnQiIHgxPSIwJSIgeTE9IjUwJSIgeDI9IjEwMCUiIHkyPSI1MCUiPjxzdG9wIG9mZnNldD0iNSUiIHN0b3AtY29sb3I9IiMwMDJiZGM4OCI+PC9zdG9wPjxzdG9wIG9mZnNldD0iOTUlIiBzdG9wLWNvbG9yPSIjMzJkZWQ0ODgiPjwvc3RvcD48L2xpbmVhckdyYWRpZW50PjwvZGVmcz48cGF0aCBkPSJNIDAsNjAwIEMgMCw2MDAgMCwyMDAgMCwyMDAgQyAxMjEuNDA2Njk4NTY0NTkzMzEsMjAyLjY4ODk5NTIxNTMxMTAyIDI0Mi44MTMzOTcxMjkxODY2MywyMDUuMzc3OTkwNDMwNjIyMDIgMzMyLDIxNiBDIDQyMS4xODY2MDI4NzA4MTM0LDIyNi42MjIwMDk1NjkzNzc5OCA0NzguMTUzMTEwMDQ3ODQ2OSwyNDUuMTc3MDMzNDkyODIyOTQgNTcxLDI1MyBDIDY2My44NDY4ODk5NTIxNTMxLDI2MC44MjI5NjY1MDcxNzcwNiA3OTIuNTc0MTYyNjc5NDI1OSwyNTcuOTEzODc1NTk4MDg2MiA4ODUsMjQ4IEMgOTc3LjQyNTgzNzMyMDU3NDEsMjM4LjA4NjEyNDQwMTkxMzg1IDEwMzMuNTUwMjM5MjM0NDQ5NywyMjEuMTY3NDY0MTE0ODMyNSAxMTIwLDIxMiBDIDEyMDYuNDQ5NzYwNzY1NTUwMywyMDIuODMyNTM1ODg1MTY3NSAxMzIzLjIyNDg4MDM4Mjc3NTIsMjAxLjQxNjI2Nzk0MjU4Mzc0IDE0NDAsMjAwIEMgMTQ0MCwyMDAgMTQ0MCw2MDAgMTQ0MCw2MDAgWiIgc3Ryb2tlPSJub25lIiBzdHJva2Utd2lkdGg9IjAiIGZpbGw9InVybCgjZ3JhZGllbnQpIiBjbGFzcz0idHJhbnNpdGlvbi1hbGwgZHVyYXRpb24tMzAwIGVhc2UtaW4tb3V0IGRlbGF5LTE1MCBwYXRoLTAiPjwvcGF0aD48c3R5bGU+Ci5wYXRoLTF7CmFuaW1hdGlvbjpwYXRoQW5pbS0xIDRzOwphbmltYXRpb24tdGltaW5nLWZ1bmN0aW9uOiBsaW5lYXI7CmFuaW1hdGlvbi1pdGVyYXRpb24tY291bnQ6IGluZmluaXRlOwp9CkBrZXlmcmFtZXMgcGF0aEFuaW0tMXsKMCV7CmQ6IHBhdGgoIk0gMCw2MDAgQyAwLDYwMCAwLDQwMCAwLDQwMCBDIDExNC4yOTY2NTA3MTc3MDMzNiwzNzUuNTU5ODA4NjEyNDQwMiAyMjguNTkzMzAxNDM1NDA2NzIsMzUxLjExOTYxNzIyNDg4MDQgMzA5LDMzOCBDIDM4OS40MDY2OTg1NjQ1OTMzLDMyNC44ODAzODI3NzUxMTk2IDQzNS45MjM0NDQ5NzYwNzY1NSwzMjMuMDgxMzM5NzEyOTE4NyA1MzQsMzYwIEMgNjMyLjA3NjU1NTAyMzkyMzQsMzk2LjkxODY2MDI4NzA4MTMgNzgxLjcxMjkxODY2MDI4NzIsNDcyLjU1NTAyMzkyMzQ0NSA4OTUsNDcxIEMgMTAwOC4yODcwODEzMzk3MTI4LDQ2OS40NDQ5NzYwNzY1NTUgMTA4NS4yMjQ4ODAzODI3NzUyLDM5MC42OTg1NjQ1OTMzMDE0NiAxMTcwLDM2NiBDIDEyNTQuNzc1MTE5NjE3MjI0OCwzNDEuMzAxNDM1NDA2Njk4NTQgMTM0Ny4zODc1NTk4MDg2MTI0LDM3MC42NTA3MTc3MDMzNDkzIDE0NDAsNDAwIEMgMTQ0MCw0MDAgMTQ0MCw2MDAgMTQ0MCw2MDAgWiIpOwp9CjI1JXsKZDogcGF0aCgiTSAwLDYwMCBDIDAsNjAwIDAsNDAwIDAsNDAwIEMgNzMuMDkwOTA5MDkwOTA5MDcsNDE5LjA2MjIwMDk1NjkzNzgzIDE0Ni4xODE4MTgxODE4MTgxMyw0MzguMTI0NDAxOTEzODc1NjYgMjQ1LDQ1NiBDIDM0My44MTgxODE4MTgxODE4Nyw0NzMuODc1NTk4MDg2MTI0MzQgNDY4LjM2MzYzNjM2MzYzNjQsNDkwLjU2NDU5MzMwMTQzNTM3IDU2OCw0NjcgQyA2NjcuNjM2MzYzNjM2MzYzNiw0NDMuNDM1NDA2Njk4NTY0NjMgNzQyLjM2MzYzNjM2MzYzNjUsMzc5LjYxNzIyNDg4MDM4MjggODQ2LDM4MyBDIDk0OS42MzYzNjM2MzYzNjM1LDM4Ni4zODI3NzUxMTk2MTcyIDEwODIuMTgxODE4MTgxODE4Miw0NTYuOTY2NTA3MTc3MDMzNDYgMTE4Niw0NzEgQyAxMjg5LjgxODE4MTgxODE4MTgsNDg1LjAzMzQ5MjgyMjk2NjU0IDEzNjQuOTA5MDkwOTA5MDkxLDQ0Mi41MTY3NDY0MTE0ODMyNyAxNDQwLDQwMCBDIDE0NDAsNDAwIDE0NDAsNjAwIDE0NDAsNjAwIFoiKTsKfQo1MCV7CmQ6IHBhdGgoIk0gMCw2MDAgQyAwLDYwMCAwLDQwMCAwLDQwMCBDIDcyLjAyODcwODEzMzk3MTMyLDM3NS41MzExMDA0Nzg0Njg5NCAxNDQuMDU3NDE2MjY3OTQyNjQsMzUxLjA2MjIwMDk1NjkzNzgzIDI1OSwzNjIgQyAzNzMuOTQyNTgzNzMyMDU3MzYsMzcyLjkzNzc5OTA0MzA2MjE3IDUzMS43OTkwNDMwNjIyMDA5LDQxOS4yODIyOTY2NTA3MTc3IDYyNyw0MjYgQyA3MjIuMjAwOTU2OTM3Nzk5MSw0MzIuNzE3NzAzMzQ5MjgyMyA3NTQuNzQ2NDExNDgzMjUzNiwzOTkuODA4NjEyNDQwMTkxNDQgODM1LDQwNCBDIDkxNS4yNTM1ODg1MTY3NDY0LDQwOC4xOTEzODc1NTk4MDg1NiAxMDQzLjIxNTMxMTAwNDc4NDgsNDQ5LjQ4MzI1MzU4ODUxNjczIDExNTIsNDU1IEMgMTI2MC43ODQ2ODg5OTUyMTUyLDQ2MC41MTY3NDY0MTE0ODMyNyAxMzUwLjM5MjM0NDQ5NzYwNzYsNDMwLjI1ODM3MzIwNTc0MTYzIDE0NDAsNDAwIEMgMTQ0MCw0MDAgMTQ0MCw2MDAgMTQ0MCw2MDAgWiIpOwp9Cjc1JXsKZDogcGF0aCgiTSAwLDYwMCBDIDAsNjAwIDAsNDAwIDAsNDAwIEMgNjguMDA5NTY5Mzc3OTkwNDMsMzU5LjM5NzEyOTE4NjYwMjg1IDEzNi4wMTkxMzg3NTU5ODA4NiwzMTguNzk0MjU4MzczMjA1NyAyNDQsMzQxIEMgMzUxLjk4MDg2MTI0NDAxOTE0LDM2My4yMDU3NDE2MjY3OTQzIDQ5OS45MzMwMTQzNTQwNjcwNCw0NDguMjIwMDk1NjkzNzc5OSA2MTIsNDcyIEMgNzI0LjA2Njk4NTY0NTkzMyw0OTUuNzc5OTA0MzA2MjIwMSA4MDAuMjQ4ODAzODI3NzUxMyw0NTguMzI1MzU4ODUxNjc0NjUgODkwLDQyOCBDIDk3OS43NTExOTYxNzIyNDg3LDM5Ny42NzQ2NDExNDgzMjUzNSAxMDgzLjA3MTc3MDMzNDkyODMsMzc0LjQ3ODQ2ODg5OTUyMTU0IDExNzcsMzcxIEMgMTI3MC45MjgyMjk2NjUwNzE3LDM2Ny41MjE1MzExMDA0Nzg0NiAxMzU1LjQ2NDExNDgzMjUzNTksMzgzLjc2MDc2NTU1MDIzOTIgMTQ0MCw0MDAgQyAxNDQwLDQwMCAxNDQwLDYwMCAxNDQwLDYwMCBaIik7Cn0KMTAwJXsKZDogcGF0aCgiTSAwLDYwMCBDIDAsNjAwIDAsNDAwIDAsNDAwIEMgMTE0LjI5NjY1MDcxNzcwMzM2LDM3NS41NTk4MDg2MTI0NDAyIDIyOC41OTMzMDE0MzU0MDY3MiwzNTEuMTE5NjE3MjI0ODgwNCAzMDksMzM4IEMgMzg5LjQwNjY5ODU2NDU5MzMsMzI0Ljg4MDM4Mjc3NTExOTYgNDM1LjkyMzQ0NDk3NjA3NjU1LDMyMy4wODEzMzk3MTI5MTg3IDUzNCwzNjAgQyA2MzIuMDc2NTU1MDIzOTIzNCwzOTYuOTE4NjYwMjg3MDgxMyA3ODEuNzEyOTE4NjYwMjg3Miw0NzIuNTU1MDIzOTIzNDQ1IDg5NSw0NzEgQyAxMDA4LjI4NzA4MTMzOTcxMjgsNDY5LjQ0NDk3NjA3NjU1NSAxMDg1LjIyNDg4MDM4Mjc3NTIsMzkwLjY5ODU2NDU5MzMwMTQ2IDExNzAsMzY2IEMgMTI1NC43NzUxMTk2MTcyMjQ4LDM0MS4zMDE0MzU0MDY2OTg1NCAxMzQ3LjM4NzU1OTgwODYxMjQsMzcwLjY1MDcxNzcwMzM0OTMgMTQ0MCw0MDAgQyAxNDQwLDQwMCAxNDQwLDYwMCAxNDQwLDYwMCBaIik7Cn0KfTwvc3R5bGU+PGRlZnM+PGxpbmVhckdyYWRpZW50IGlkPSJncmFkaWVudCIgeDE9IjAlIiB5MT0iNTAlIiB4Mj0iMTAwJSIgeTI9IjUwJSI+PHN0b3Agb2Zmc2V0PSI1JSIgc3RvcC1jb2xvcj0iIzAwMmJkY2ZmIj48L3N0b3A+PHN0b3Agb2Zmc2V0PSI5NSUiIHN0b3AtY29sb3I9IiMzMmRlZDRmZiI+PC9zdG9wPjwvbGluZWFyR3JhZGllbnQ+PC9kZWZzPjxwYXRoIGQ9Ik0gMCw2MDAgQyAwLDYwMCAwLDQwMCAwLDQwMCBDIDExNC4yOTY2NTA3MTc3MDMzNiwzNzUuNTU5ODA4NjEyNDQwMiAyMjguNTkzMzAxNDM1NDA2NzIsMzUxLjExOTYxNzIyNDg4MDQgMzA5LDMzOCBDIDM4OS40MDY2OTg1NjQ1OTMzLDMyNC44ODAzODI3NzUxMTk2IDQzNS45MjM0NDQ5NzYwNzY1NSwzMjMuMDgxMzM5NzEyOTE4NyA1MzQsMzYwIEMgNjMyLjA3NjU1NTAyMzkyMzQsMzk2LjkxODY2MDI4NzA4MTMgNzgxLjcxMjkxODY2MDI4NzIsNDcyLjU1NTAyMzkyMzQ0NSA4OTUsNDcxIEMgMTAwOC4yODcwODEzMzk3MTI4LDQ2OS40NDQ5NzYwNzY1NTUgMTA4NS4yMjQ4ODAzODI3NzUyLDM5MC42OTg1NjQ1OTMzMDE0NiAxMTcwLDM2NiBDIDEyNTQuNzc1MTE5NjE3MjI0OCwzNDEuMzAxNDM1NDA2Njk4NTQgMTM0Ny4zODc1NTk4MDg2MTI0LDM3MC42NTA3MTc3MDMzNDkzIDE0NDAsNDAwIEMgMTQ0MCw0MDAgMTQ0MCw2MDAgMTQ0MCw2MDAgWiIgc3Ryb2tlPSJub25lIiBzdHJva2Utd2lkdGg9IjAiIGZpbGw9InVybCgjZ3JhZGllbnQpIiBjbGFzcz0idHJhbnNpdGlvbi1hbGwgZHVyYXRpb24tMzAwIGVhc2UtaW4tb3V0IGRlbGF5LTE1MCBwYXRoLTEiPjwvcGF0aD48L3N2Zz4=') 0 / auto 100%;
}

.personalData {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
  height: 30vh;
  z-index: -1;
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: block; /* 将图片设置为块级元素，以便设置margin属性 */
  margin: 0 auto; /* 将图片水平居中对齐 */
}

.UserInfo {
  margin: 10px;
  border-radius: 10px;
  background: #fff;
  height: 100px;
  position: absolute;
  top: 34.8%;
  left: 0;
  right: 0;
  display: flex;
  flex-wrap: wrap;
  text-align: center;
}

.UserInfoPlus {
  margin: 10px;
  border-radius: 10px;
  background: #fff;
  height: 100px;
  position: absolute;
  top: 43%;
  left: 0;
  right: 0;
  display: flex;
  flex-wrap: wrap;
  text-align: center;
}

.items {
  margin-top: 30%;
  padding: 10px;
}
</style>
