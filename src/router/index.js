import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";

const history = createWebHistory();
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            name: 'home',
            meta: {
                title: '南科Top',
                bar: true,
                tabbar: true
            },
            component: () => import("@/pages/Home.vue")
        },
        {
            path: '/InitiateVoting',
            name: 'initiateVoting',
            component: () => import("@/pages/InitiateVoting.vue"),
            meta: {
                title: "发起投票",
                bar: true,
                tabbar: true
            }
        },
        {
            path: '/Vote',
            name: 'vote/:uid',
            component: () => import("@/pages/voting/Vote.vue"),
            meta: {
                title: "投票详情",
                bar: false,
                tabbar: false
            }
        },
        {
            path: '/detail',
            name: 'detail/:vote/:uid',
            meta: {
                title: '选手详情',
                bar: true,
                tabbar: false
            },
            component: () => import("@/pages/voting/Details.vue")
        },
        {
            path: '/signup',
            name: 'signup/:uid/:uid',
            meta: {
                title: '参与投票',
                bar: true,
                tabbar: false
            },
            component: () => import("@/pages/voting/SignUp.vue")
        },
        {
            path: '/User',
            name: 'user',
            meta: {
                title: '个人中心',
                bar: true,
                tabbar: true
            },
            component: () => import("@/pages/User.vue")
        },
        {
            path: '/VoteLaunch',
            name: 'VoteLaunch',
            meta: {
                title: '发起的投票',
                bar: false,
                tabbar: false
            },
            component: () => import("@/pages/user/VoteLaunch.vue")
        },
        {
            path: '/VoteParticipateIn',
            name: 'VoteParticipateIn',
            meta: {
                title: '参与的投票',
                bar: false,
                tabbar: false
            },
            component: () => import("@/pages/user/VoteParticipateIn.vue")
        }
    ]
})
export default router;
