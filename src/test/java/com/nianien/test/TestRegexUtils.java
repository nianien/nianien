package com.nianien.test;

import com.nianien.core.text.RegexUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skyfalling
 */
public class TestRegexUtils {


    @Test
    public void test() {
        String html = "<div class=\"line mt-10 app-and-share\">  <div class=\"app-banner-container\"> <div class=\"line cur-pointer app-banner-default\"> <span class=\"app-banner-flash\" id=\"app-banner-flash\"></span> <span class=\"app-flash-layer\"></span> </div> <div class=\"app-banner-test hide\"> <span class=\"app-banner-arrow\"></span> <div class=\"app-banner-tip\"> <p class=\"app-tip-title mb-15 f-yahei f-green\">下载知道APP，提问优先解答!<span class=\"app-tip-phone ml-10\"></span></p> <div class=\"line app-tip-method\"> <div class=\"grid app-tip-code\"> <p class=\"mb-10\">方式一：扫描二维码下载</p> <p> <img src=\"http://img.iknow.bdimg.com/app/qrcode.png\" /> </p> </div> <div class=\"grid app-tip-msg\"> <p class=\"mb-10\">方式二：短信免费下载</p> <p> <input class=\"app-input-phone\" placeholder=\"请输入手机号\" type=\"text\" /> </p> <p class=\"app-msg-error f-stress f-12\"></p> <p> <a class=\"app-msg-submit btn btn-32-green\"><em><b>发送短信</b></em></a> </p> </div> </div> </div> </div> </div>  <div class=\"wgt-share\" id=\"wgt-share\"> <div class=\"line f-aid mt-5\">   <span class=\"share-to grid\">分享到：</span> <div alog-group=\"qb-share-btn\" id=\"bdshare\" class=\"bdshare_t bds_tools get-codes-bdshare\"> <a class=\"bds_tsina\" log=\"pos:tsina\"></a> <a class=\"bds_tqq\" log=\"pos:tqq\"></a> <a class=\"bds_qzone\" log=\"pos:qzone\"></a> <a class=\"bds_renren\" log=\"pos:renren\"></a> <a class=\"bds_tieba\" log=\"pos:tieba\"></a> <span class=\"bds_more\"></span> </div> </div> </div><script type=\"text/javascript\" id=\"bdshare_js\" data=\"type=tools&amp;uid=281447\" ></script><script type=\"text/javascript\" id=\"bdshell_js\"></script><script type=\"text/javascript\">F.use('/static/widget/question/share/share.js');</script> </div> </div><script type=\"text/javascript\">F.use('/static/widget/question/ask/ask.js');</script>    <script type=\"text/javascript\">F.context('answers')['1388104447'] = {uid:\"343158165\",imId:\"952d77786c3737387414\",isBest:\"1\",id:\"1388104447\",userName:\"wxl778\",userNameEnc:\"wxl778\",user:{sex:\"2\",iconType:\"6\",gradeIndex:\"4\",grAnswerNum:\"24\",carefield: [{cid:\"1285\",cname:\"Linux\"}],isAuth:\"0\",authTitle:\"\",isUserAdmin:\"0\",userAdminLevel:\"\",userAdminTitle:\"\",isFamous:\"0\",isMaster:\"0\",goodRate:\"60\",applyExcType:\"4\"},isAnonymous:\"0\",isCurrentUser:\"0\",mapUrl:\"\",refer:\"\",replyAskNum:\"1\",threadId:\"5552417789\",hasComment:\"1\",qid:\"549390002\",oldQid:\"549390002\",raid:\"98108536\",recommendCanceled:\"0\"};</script> <div  class=\"wgt-best \" id=\"best-answer-1388104447\"><div class=\"hd line mb-10\"><span class=\"grid-r f-aid pos-time mt-20\"><ins class=\"accuse-area\" alog-alias=\"qb-accuse-link-best\"></ins> 2013-05-16 09:43</span> <span class=\"answer-type answer-best grid\"></span><span class=\"answer-title h2 grid\">提问者采纳</span></div><div class=\"bd answer\" id=\"answer-1388104447\"><div class=\"line info f-aid\"></div><div class=\"line content\"> <pre id=\"best-content-1388104447\" accuse=\"aContent\" class=\"best-text mb-10\">如果还没有提交执行git checkout -- [文件名]，如果已经提交执行git reset [前一个提交]，再重新提一次。如果已经推到远程库就只能git revert 再提一次了。</pre><dl class=\"replyask line pt-5 pb-5\" id=\"replyask-98101180\"><dt class=\"ask f-12 grid\">追问</dt> <dd class=\"replyask-content grid ml-10 \" accuse=\"qRA\" id=\"replyask-content-98101180\"> <pre accuse=\"qRA\">git checkout是删除仓库中文件的意思吗?</pre></dd></dl> <dl class=\"replyask line pt-5 pb-5\" id=\"replyask-98108536\"><dt class=\"reply f-12 grid\">回答</dt> <dd class=\"replyask-content grid ml-10 \" accuse=\"aRA\" id=\"replyask-content-98108536\"> <pre accuse=\"aRA\">因为还没有提交，所以刚刚提交的文件还在暂存区，checkout是把这个文件从暂存区中拿出来，不提交入库。估计你的情况应该是已经提交了的。</pre></dd></dl> <dl class=\"thank line pt-5 pb-5\"><dt class=\"thank-title grid f-12\">提问者评价</dt><dd class=\"grid ml-10\"><pre accuse=\"qThanks\">您很专业, 谢谢</pre></dd></dl><div class=\"grid-r f-aid \"><span alog-action=\"qb-comment-btnbestbox\" class=\"comment f-blue\" id=\"comment-1388104447\">评论</span><span class=\"f-pipe\">|</span><span alog-action=\"qb-zan-btnbestbox\" class=\"evaluate evaluate-32\" id=\"evaluate-1388104447\" data-evaluate=\"0\" ></span><script type=\"text/javascript\">F.use('/static/widget/question/value-comment/value-comment.js',function(comment){comment.init('1388104447');});</script></div>   </div><div class=\"line wgt-replyer-best mt-10 pt-20\">  <div class=\"grid mr-15\"> <p class=\"avatar-48 avatar-static\"> <a alog-action=\"qb-username\" log=\"pms:newqb,pos:bestreplyer\" class=\"avatar-normal-a\" rel=\"nofollow\" href=\"http://www.baidu.com/p/wxl778?from=zhidao\" target=\"_blank\" data-img=\"http://img.iknow.bdimg.com/avatar/48/r6s2g4.gif\"> </a> </p> </div> <div class=\"grid f-aid\"> <p> <a alog-action=\"qb-username\" log=\"pms:newqb,pos:bestreplyer\" class=\"user-name\" rel=\"nofollow\" href=\"http://www.baidu.com/p/wxl778?from=zhidao\" target=\"_blank\">wxl778</a>          <span class=\"f-pipe\">|</span><a class=\"f-aid\" href=\"http://www.baidu.com/search/zhidao_help.html#如何选择头衔\" target=\"_blank\">四级</a><span class=\"ml-10\">采纳率60%</span></p><p class=\"carefield\"><span>擅长：</span><a class=\"mr-5 f-aid\" href=\"/browse/1285\" target=\"_blank\">Linux</a></p></div></div></div></div>        <script>window.PDC && PDC.first_screen && PDC.first_screen();</script>  ";

        List list = RegexUtils.find(html, "(?:href=)\".*?\"");
        System.out.println(list);
        list =  RegexUtils.find(html,"href=\"","\"") ;
        System.out.println(list);

    }

    public static void main(String[] args) {
        System.out.println("${a}".replaceAll("\\$\\{.*}", "a"));
        Map map = new HashMap();
        map.put("${a}", "b");
        System.out.println(RegexUtils.replace("${a}", "\\$\\{.*}", map));
    }
}
