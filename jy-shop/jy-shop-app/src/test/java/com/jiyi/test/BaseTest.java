
package com.jiyi.test;

import cn.binarywang.wx.miniapp.api.WxMaLiveService;
import cn.binarywang.wx.miniapp.bean.WxMaLiveInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
    @Autowired
    private WxMaLiveService wxMaLiveService;
    public void test(){
        WxMaLiveInfo.RoomInfo roomInfo = new WxMaLiveInfo.RoomInfo();
        roomInfo.setName("测试直播间");
    }

}
