# PumeloBoot

<pre>
     _____   _   _       ___  ___   _____   _       _____
    |  _  \\| | | |     /   |/   | | ____| | |     /  _  \\
    | |_| | | | | |    / /|   /| | | |__   | |     | | | |
    |  ___/ | | | |   / / |__/ | | |  __|  | |     | | | |
    | |     | |_| |  / /       | | | |___  | |___  | |_| |
    |_|     \\_____//_/        |_| |_____| |_____| \\____/
</pre>

基于SpringBoot2.0实现的一整套快速开发脚手架

## 技术栈
- MySQL 5.6/5.7
- JPA
- Redis 3.x or later

## 模块说明
- mod-ali-oss 阿里云oss
- mod-aop aop处理
- mod-authorizion 授权token
- mod-permission 权限相关
- mod-common 共用方法类
- mod-cors-filter 跨域处理
- mod-db-base 数据库相关
- mod-error 全局错误增强处理
- mod-idgens id生成
- mod-mail 邮件相关(未实现，可以用spring mail实现)
- mod-redis redis相关
- mod-service-init 启动事件监听
- mod-sms 阿里云短信验证
- mod-swagger api文档
- mod-utils 工具类
- mod-activemq 开源消息队列

## 数据相关
- brg-data 全局数据桥，包含dto，vo，repo，entity，vo

## 服务相关
对于不同的端（web，app，微信等），在不同的服务进行输出API

## 技术支持
- 535553297@qq.com（pumelotea）

