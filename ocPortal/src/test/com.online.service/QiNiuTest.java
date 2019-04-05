package com.online.service;

import com.online.common.storage.QiniuStorage;
import com.online.common.util.CommonUtil;
import org.junit.Test;

import java.io.File;

public class QiNiuTest {
    @Test
    public void testImages(){
        //测试上传图片
        byte[] buff = CommonUtil.getFileBytes(new File("F://IDEAwork//ocProject//ocPortal//src//main//webapp//res//i//c5.jpg"));
        String key = QiniuStorage.uploadImage(buff);
        System.out.println("key = " + key);

//        String key = "/default/all/0/c2984b11587c4c0da1f9db2a4f26e13a.jpeg";
        //测试下载图片
//        String url = QiniuStorage.getUrl(key);
//        System.out.println(url);

        //测试下载不同大小的图片
//        String url = QiniuStorage.getUrl(key, ThumbModel.THUMB_32);
//        System.out.println(url);
    }
}
