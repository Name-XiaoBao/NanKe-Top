import{_ as T,i as C,r as n,h as y,a as c,o as U,e as S,b as p,d as a,w as g,F as q,s as N,j as R,k as w,g as B,p as E,l as j}from"./index-ca32026e.js";import{b as M,获 as D}from"./api-bcd6585b.js";import{s as f}from"./function-call-a99c9d20.js";const F={setup(){const u=C("active"),l=n(null),v=n(null),e=n(5),_=n(null),d=n(null),r=n(null),t=n(null);u.value="InitiateVoting",localStorage.getItem("username")==null&&f({title:"提示",message:"要先登录才能发布投票哦~"}).then(()=>{y.push("/user")});const m=n("/CoverImage.png"),i=s=>{m.value=s.content},b=()=>{localStorage.getItem("username")===""||m.value==="/CoverImage.png"||l.value===""||d.value===""||r.value===""||t.value===""||v.value===""?N("投票信息未填写完整"):(R({duration:0,forbidClick:!0,message:"发布中..."}),M(localStorage.getItem("username"),m.value,l.value,d.value,r.value,t.value,e.value,_.value,v.value).then(s=>{s.code==200&&(w(),f({title:"提示",message:"投票发布成功！"}).then(()=>{location.reload()}))}).catch(s=>{console.log(s),w(),f({title:"提示",message:"要先登录才能发布投票哦~"}).then(()=>{y.push("/user")})}))},h=()=>{t.value<r.value&&(t.value="")},o=()=>{t.value<r.value&&(t.value=""),d.value<t.value&&(d.value="")},I=()=>{d.value<t.value&&(d.value="")},V=n(!1),k=()=>{V.value=n(!0)},x=n(null);return{afterRead:i,CoverImage:m,title:l,rules:v,vote_num:e,backgroundMusic:_,date:d,registration_time:r,end_registration_time:t,publish:b,handleRegistrationTimeBlur:h,handleEndRegistrationTimeBlur:o,handleVotingEndTimeBlur:I,music:V,选择音乐:k,musicName:x,搜索音乐:()=>{D(x.value).then(s=>{console.log(s)})}}}},z=u=>(E("data-v-67beb3d4"),u=u(),j(),u),L={style:{padding:"10px"}},A={style:{width:"100%",height:"100%",background:"#fff","border-radius":"10px","margin-bottom":"10px",display:"flex","flex-direction":"column","align-items":"center"}},G=z(()=>p("div",{style:{height:"20px","text-align":"center","padding-top":"15px","margin-bottom":"-15px"}},[p("b",null,"封面图")],-1)),H=["src"],J={style:{background:"#fff","border-radius":"10px","margin-bottom":"10px"}};function K(u,l,v,e,_,d){const r=c("van-divider"),t=c("van-button"),m=c("van-uploader"),i=c("van-field"),b=c("van-search"),h=c("van-popup");return U(),S(q,null,[p("div",L,[p("div",A,[G,a(r),a(m,{"after-read":e.afterRead,style:{flex:"1",width:"100%",height:"100%",display:"flex","flex-direction":"column","align-items":"center","border-radius":"10px"}},{default:g(()=>[p("img",{src:e.CoverImage,style:{width:"100%",height:"180px","object-fit":"cover"}},null,8,H),a(t,{plain:"",type:"default",style:{width:"100%","border-radius":"0 0 10px 10px"}},{default:g(()=>[B(" 点我上传/修改封面图 ")]),_:1})]),_:1},8,["after-read"])]),p("div",J,[a(i,{required:"",maxlength:"15",modelValue:e.title,"onUpdate:modelValue":l[0]||(l[0]=o=>e.title=o),label:"投票标题",placeholder:"请输入标题"},null,8,["modelValue"]),a(i,{type:"textarea","label-align":"top",required:"",modelValue:e.rules,"onUpdate:modelValue":l[1]||(l[1]=o=>e.rules=o),label:"规则",placeholder:"请输入投票规则及奖品",autosize:"",maxlength:"150","show-word-limit":""},null,8,["modelValue"]),a(i,{required:"",type:"number",modelValue:e.vote_num,"onUpdate:modelValue":l[2]||(l[2]=o=>e.vote_num=o),label:"投票次数",placeholder:"请输入投票次数"},null,8,["modelValue"]),a(i,{modelValue:e.backgroundMusic,"onUpdate:modelValue":l[3]||(l[3]=o=>e.backgroundMusic=o),label:"背景音乐",placeholder:"音乐直链(选填)"},{button:g(()=>[]),_:1},8,["modelValue"]),a(i,{type:"datetime-local",onBlur:e.handleRegistrationTimeBlur,"label-align":"top",required:"",modelValue:e.registration_time,"onUpdate:modelValue":l[4]||(l[4]=o=>e.registration_time=o),label:"开始报名日期",placeholder:"请选择日期"},null,8,["onBlur","modelValue"]),a(i,{type:"datetime-local",onBlur:e.handleEndRegistrationTimeBlur,"label-align":"top",required:"",modelValue:e.end_registration_time,"onUpdate:modelValue":l[5]||(l[5]=o=>e.end_registration_time=o),label:"截止报名日期",placeholder:"请选择日期"},null,8,["onBlur","modelValue"]),a(i,{type:"datetime-local",onBlur:e.handleVotingEndTimeBlur,"label-align":"top",required:"",modelValue:e.date,"onUpdate:modelValue":l[6]||(l[6]=o=>e.date=o),label:"投票结束日期",placeholder:"请选择日期"},null,8,["onBlur","modelValue"])]),a(t,{style:{"margin-top":"10px"},block:"",onClick:e.publish},{default:g(()=>[B("发布")]),_:1},8,["onClick"])]),a(h,{show:e.music,"onUpdate:show":l[8]||(l[8]=o=>e.music=o),round:"",closeable:"",position:"bottom",style:{height:"70%",padding:"50px 10px"}},{default:g(()=>[a(b,{modelValue:e.musicName,"onUpdate:modelValue":l[7]||(l[7]=o=>e.musicName=o),onSearch:e.搜索音乐,placeholder:"请输入音乐名称"},null,8,["modelValue","onSearch"])]),_:1},8,["show"])],64)}const X=T(F,[["render",K],["__scopeId","data-v-67beb3d4"]]);export{X as default};
