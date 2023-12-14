import {createApp} from 'vue'
import App from './App.vue'
import Vant from 'vant';
import 'vant/lib/index.css';
import router from '@/router'
import VueWechatTitle from 'vue-wechat-title';

const app = createApp(App)
app.use(Vant)
app.use(router)
app.use(VueWechatTitle)
app.mount('#app')
