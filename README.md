## 南科Top——项目说明

### 一、	项目信息

项目名称：

南科Top榜

### 二、	项目背景

南科Top网是一款由“逻辑探索者”社团开发的线上投票平台，旨在为广大南科学子提供更为便捷的网上服务。作为南阳科技职业学院一个新兴但活跃的社团，“逻辑探索者”社团一直致力于推动校园文化建设和创新创业教育，而南科Top网就是该社团在探索如何更好地服务南科学子的过程中产生的一个想法。

南科Top网旨在为学生们提供更加便捷、快速、高效的投票方式，从而让学生们更加方便地参与到学校民主管理和决策中来。

南科Top网采用先进的技术手段和安全机制，能够保证投票的公正性和安全性。同时，南科Top网还提供了丰富的功能和服务，如投票结果实时查看、候选人信息浏览、投票规则说明等，以满足学生们不同的需求和要求。

总之，南科Top网是逻辑探索者社团为了更好地服务南科学子而开发的一款线上投票平台，它将为学生们提供更加便捷、高效、公正、安全的投票方式。

### 三、	项目功能

1. 首页功能
    - 搜索投票：在首页提供搜索框，用户可以输入关键字进行投票搜索。
    - 轮播图：展示一组精选的投票活动的封面图和标题，吸引用户点击进入投票详情页。
    - 投票活动列表：显示所有投票活动的列表，包括发起投票用户的头像昵称、浏览量、总票数和投票是否结束的状态，方便用户快速浏览投票信息。
2. 发起投票页面功能
    - 登录访问限制：只有登录用户才能访问发起投票页面，确保投票的真实性和有效性。
    - 投票信息填写：用户可以填写投票的相关信息，包括投票的封面图、标题、规则、每人可投票数、背景音乐（直链）、报名时间、报名截止时间和投票截止时间。
    - 发起投票按钮：用户填写完投票信息后，可以点击按钮发布投票活动。
3. 个人中心功能
    - 显示发起的投票和参与的投票数量：用户可以在个人中心页面查看自己已经发起的投票和参与的投票数量，方便管理和统计。
    - 修改资料：用户可以点击头像和昵称弹出修改资料窗口，或者点击底部的修改资料选项进行修改。用户可以修改个人资料信息，如昵称、头像等。
        - 问题反馈：输入要反馈的内容和联系方式即可向我们反馈本系统的问题。
    - 登录状态判断：未登录时，隐藏修改资料和退出登录选项，确保用户安全与隐私。
4. 注册功能
    - 用户注册：支持用户使用邮箱进行注册，确保账号的真实性和安全性。
5. 投票详情页功能
    - 显示投票的封面图、标题、参与人数、累计票数、浏览人数和投票结束时间倒计时，向用户展示投票活动的基本信息。
    - 提供搜索框：用户可以通过搜索框输入关键字筛选投票活动，提高用户体验。
    - 参与人员列表和排行榜：展示参与人员的列表和投票结果的排行榜，让用户了解当前投票的状况。
    - 规则详情：显示投票的详细规则，包括每人可投票数、投票截止时间等。
    - 底部功能：提供报名和分享给好友选项，方便用户进行相关操作。
6. 参加人员详情页功能
    - 显示参加人员的头像、昵称、排名、获得的票数以及与前一名相差的票数，让用户了解参加人员的竞争情况。
    - 显示参加的标题、内容和图片信息，让用户了解参加人员的作品。
    - 显示排行榜，点击可跳转至排行榜页面，方便用户查看整体投票结果。
    - 底部功能：提供给该参加人员投票和分享给好友选项，方便用户进行相关操作。
    - 如果投票时间已结束，不允许再进行投票，确保投票的有效性。
7. 报名参加页面功能
    - 登录访问限制：只有登录用户才能访问报名参加页面，确保报名的真实性和有效性。
    - 填写信息：用户在指定时间范围内填写报名信息，包括标题、描述和图片等相关信息。
    - 提交报名：用户在填写完报名信息后，可以点击提交按钮提交报名信息。
      通过以上功能设计，本投票系统提供了丰富的功能，涵盖了用户发起投票、参与投票、个人管理等多方面的需求。希望本文档对您有所帮助，如有任何问题，请随时与我们联系。

### 四、	项目架构

#### 后端技术栈：

      框架：SpringBoot 3.1.5
      持久层框架：MyBatis
      数据库：MySQL 5.7
      Java版本：JDK 17

#### 前端技术栈：
      框架：Vue.js 3
      组件库：Vant 4

### 五、	项目说明

该投票系统采用了后端SSM架构和前端Vue.js 3 + Vant 4组件库开发。

后端使用SpringBoot作为基础框架，简化了项目的搭建和配置，提高了开发效率。同时使用MyBatis作为持久层框架，通过注解和XML文件实现对数据库的操作。数据库采用MySQL 5.7，存储投票活动、用户信息、投票结果等数据。

前端使用Vue.js 3作为主要框架，结合Vant 4组件库实现页面的搭建和交互。Vue.js是一套用于构建用户界面的渐进式框架，具有响应式数据绑定和组件化的特点，方便开发者进行前端开发和维护。Vant是一套基于Vue.js的移动端UI组件库，提供了丰富的组件和样式，能够快速构建出美观且易用的用户界面。

通过以上技术栈和架构设计，该投票系统能够实现用户的注册、登录、发起投票、参与投票、个人信息管理等功能。后端使用SSM框架提供接口服务，前端使用Vue.js 3 + Vant 4实现页面展示和用户交互，为用户提供友好的投票体验。

如有任何问题，请随时与我们联系。感谢您对本项目的关注！
