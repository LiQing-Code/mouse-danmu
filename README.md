# 鼠鼠弹幕

用于显示哔哩哔哩直播代码的《我的世界》Fabric MOD

## 特点
- 自定义弹幕颜色
- 自定义显示的信息
- BoosBar显示醒目留言

## 前置

- 我的世界 >= 1.19.1
- [fabric-loader](https://github.com/FabricMC/fabric-loader) >= 0.14.9
- [Cloth Config](https://github.com/shedaniel/cloth-config) >= 8.2.88
- [可选,推荐安装] [ModMenu](https://github.com/TerraformersMC/ModMenu)
 安装这个以后才能打开MOD选项，想实现用命令打开选项界面的，可我不会啊！！！

## 安装

可以看看这个视频 [Mod新手教程](https://www.bilibili.com/video/BV1cX4y1T7RZ)

## 使用说明

安装完成以后游戏中会添加一个 `/mousedanmu` 命令，用来控制直播间操作。

嫌 `/mousedanmu` 这个命令太长的话，可以使用 `/dm` 这个重定向命令，使用时会重定向到 `/mousedanmu`

下面的操作都将使用 `/dm` 这个重定向命令

在游戏中按`T`键打开聊天框，输入下列命令就能执行。

### 命令列表

- `/dm connect <roomId>` 连接一个直播间 `<roomId>` 为直播间号

  列如：`/dm connect 24256088` 就能连接到[我的直播间](https://live.bilibili.com/24256088)了

- `/dm close` 断开直播间
- `/dm status` 查看直播间连接状态
- `/dm test [<delay>]` 显示一些测试弹幕 `[<delay>]` 是一个可选参数，单位是毫秒，默认是500毫秒，用于控制测试弹幕发送间隔

  列如 `/dm test 1000` 就是每秒发送一条测试弹幕

## 联系方式
- [QQ群](https://jq.qq.com/?_wv=1027&k=nImOUpnV)
- [哔哩哔哩](https://space.bilibili.com/345259002)
- 邮箱: oh-0.0@qq.com | oh-0.0@outlook.com

## 更多

英文语言文件不可用状态，欢迎有能力的人补充语言文件，更欢迎大家参与开发

如果遇到问题可以提交 [issues](https://github.com/LiQing-Code/mouse-danmu/issues)

表情弹幕是可以点击打开网页查看的，用户名也可以点击查看主页

项目依赖的库 [BLiveDanmu](https://github.com/LiQing-Code/BLiveDanmu/tree/master)

![效果演示](https://user-images.githubusercontent.com/51829935/206930179-9e6a5419-0127-4cf3-9f36-7554a66e448a.png)
![选项界面](https://user-images.githubusercontent.com/51829935/206930213-3c101622-2f8f-4fcd-949d-46b971d9d552.png)
