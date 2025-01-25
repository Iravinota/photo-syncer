package com.ws.demos;

public class Main {

    /**
     * 测试PhotoServer对H2数据库的操作，可以同时从浏览器控制台和Java程序对数据库进行insert和select
     */
    public static void main(String[] args) {
        // 测试登录
        boolean loginSucc = PhotoSyncServer.loginUser("user1", "password1");
        System.out.println("user1 login " + (loginSucc ? "succ" : "fail"));

        // 测试注册
        boolean registRet = PhotoSyncServer.registerUser("test", "test", "test@email.com");
        System.out.println("user[test] register " + (registRet ? "succ" : "fail"));

        // 测试上传照片
        boolean uploadRet = PhotoSyncServer.uploadPhoto(1, "photo-test", "/path/to/photo-test");
        System.out.println("upload photo " + (uploadRet ? "succ" : "fail"));
    }
}