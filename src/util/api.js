import axios from 'axios';
import config from './config.js';

const axiosConfig = {
    headers: {
        'Content-Type': 'multipart/form-data' // 设置请求头
    }
};
/**
 * 获取验证码
 * @param email
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const getCode = (email) => {
    return axios.post(config.url + 'getCode', {email: email}, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 注册
 * @param nickname
 * @param username
 * @param password
 * @returns {Promise<axios.AxiosResponse<any>>}
 * @constructor
 */
export const Register = (nickname, username, password) => {
    return axios.post(config.url + 'Register', {
        nickname: nickname,
        username: username,
        password: password
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 登录
 * @param username
 * @param password
 * @returns {Promise<axios.AxiosResponse<any>>}
 * @constructor
 */
export const Login = (username, password) => {
    return axios.post(config.url + 'Login', {username: username, password: password}, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 获取用户信息
 * @param username
 * @returns {Promise<axios.AxiosResponse<any>>}
 * @constructor
 */
export const UserInfos = (username) => {
    return axios.post(config.url + 'userInfo', {username: username}, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 浏览量+1
 * @param uid
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const addViews = (uid) => {
    return axios.post(config.url + 'addViews', {uid: uid}, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 获取轮播图列表
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const getCarouselList = () => {
    return axios.post(config.url + 'CarouselList')
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
};
/**
 * 获取投票列表
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const getVoteList = () => {
    return axios.post(config.url + 'getVoteList')
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
};
/**
 * 根据UID获取投票
 * @param uid
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const getVoteByUid = (uid) => {
    return axios.post(config.url + 'getVoteByUid', {uid: uid}, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 创建投票
 * @param user_username
 * @param cover
 * @param title
 * @param deadline
 * @param registration_time
 * @param end_registration_time
 * @param vote_num
 * @param backgroundMusic
 * @param rule
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const addVote = (user_username, cover, title, deadline, registration_time, end_registration_time, vote_num, backgroundMusic, rule) => {
    return axios.post(config.url + 'addVote', {
        user_username: user_username,
        cover: cover,
        title: title,
        deadline: deadline,
        registration_time: registration_time,
        end_registration_time: end_registration_time,
        vote_num: vote_num,
        backgroundMusic: backgroundMusic,
        rule: rule
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 参加投票
 * @param participatingUsers
 * @param voteUid
 * @param picture
 * @param title
 * @param content
 * @returns {Promise<axios.AxiosResponse<any>>}
 * @constructor
 */
export const ParticipateInVoting = (participatingUsers, voteUid, picture, title, content) => {
    return axios.post(config.url + 'ParticipateInVoting', {
        participatingUsers: participatingUsers,
        voteUid: voteUid,
        picture: picture,
        title: title,
        content: content
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 根据uid获取参与投票用户详情信息
 * @param uid
 * @returns {Promise<axios.AxiosResponse<any>>}
 * @constructor
 */
export const QueryBasedOnUid = (uid) => {
    return axios.post(config.url + 'QueryBasedOnUid', {
        uid: uid
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 投票
 * @param voteUid
 * @param participatingUid
 * @returns {Promise<axios.AxiosResponse<any> | void>}
 * @constructor
 */
export const Voter = (voteUid, participatingUid) => {
    return axios.post(config.url + 'Voter', {
        voteUid: voteUid,
        participatingUid: participatingUid
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 反馈
 * @param Content
 * @param ContactInformation
 * @returns {Promise<axios.AxiosResponse<any> | void>}
 * @constructor
 */
export const 反馈 = (Content, ContactInformation) => {
    return axios.post(config.url + 'Feedback', {
        Content: Content,
        ContactInformation: ContactInformation
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 修改资料
 * @param username
 * @param avatar
 * @param nickname
 * @returns {Promise<axios.AxiosResponse<any> | void>}
 */
export const 修改资料 = (username, avatar, nickname) => {
    return axios.post(config.url + 'updateUserInfo', {
        username: username,
        avatar: avatar,
        nickname: nickname
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 根据username查询投票
 * @param username
 * @returns {Promise<axios.AxiosResponse<any> | void>}
 */
export const getVoteByUsername = (username) => {
    return axios.post(config.url + 'getVoteByUsername', {
        username: username
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 根据username查询参与的投票
 * @param username
 * @returns {Promise<axios.AxiosResponse<any> | void>}
 * @constructor
 */
export const QueryParticipatingUsers = (username) => {
    return axios.post(config.url + 'QueryParticipatingUsers', {
        username: username
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 根据用户名修改密码
 * @param username
 * @param password
 * @returns {Promise<axios.AxiosResponse<any> | void>}
 */
export const updatePasswoed = (username, password) => {
    return axios.post(config.url + 'updatePasswoed', {
        username: username,
        password: password
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
/**
 * 获取网易云音乐ID
 * @param name
 * @returns {Promise<axios.AxiosResponse<any> | void>}
 */
export const 获取网易云音乐ID = (name) => {
    return axios.post('http://music.163.com/api/search/get/web', {
        s: name,
        type: 1,
        offset: 0,
        total: true,
        limit: 10
    }, axiosConfig)
        .then(res => {
            return res.data;
        })
        .catch(error => {
            console.error(error);
        });
}
