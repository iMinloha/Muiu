# Muiu开源啦！

作为Minloha开发的一次挑战性插件，Minloha在上面花费300天的心血达到了一个全方位的功能，而且这个插件也陪伴了我接近5个月的高中生活，我也是很想纪念一下的，接下来的很长一段时间Minloha都不会维护Muiu了，因为要高考了，所以有些问题可能不会及时解决，提前说声抱歉，任何问题在issus里面提问，未来我都会一一解答，已经编译好的Muiu在MAKE文件夹下

## 声明

Muiu开源不许任何形式的收费活动，一旦发现将关闭仓库

## 目前的Muiu都有哪些功能

1、可以对玩家不合规的言论进行屏蔽操作，是基于自然语言处理的哦~

2、支持在网页端在线编辑封禁、禁言列表

3、可以进行解封、解除禁言、导出html等功能

4、可以对玩家弹窗警告

5、可以分析玩家说的话，并且进行多种处理

6、支持机器人的问答功能，用户可以向Muiu提问，不过词库需要服主修改

## 模板开发教程

导出的禁言列表使用<%ForbidSpeakList%>进行标识，导出的封禁列表使用<%BanList%>标识

### 放置位置

每一个标识内部输出都包含<tr>与<td>标签，换言之，在开发模板时要将标识放置在<thead></thead>之间，最简单的办法就是直接修改index.tmp

### 须知

不要修改index.tmp的名字

标识要单独成行!(必须必须)

中文的使用可能会出现编码异常

## 插件开发

请保留Minloha的名字，因为Minloha想要看看Muiu可以成长到什么程度，还请尊重原作者

所有java文件内部都有关键的注释，具体功能不赘叙，下载自己看看吧~

> 纪念

Muiu从2021/2/6日企划，2/7日发布第一个版本0.3.6以来，经历了300天

Muiu交流群：907661938
