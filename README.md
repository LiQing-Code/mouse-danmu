# 鼠鼠弹幕
![license](https://img.shields.io/github/license/LiQing-Code/mouse-danmu)
![version](https://img.shields.io/github/v/release/liqing-code/mouse-danmu)
![downloads](https://img.shields.io/github/downloads/liqing-code/mouse-danmu/total)

用于显示哔哩哔哩直播弹幕的《我的世界》Fabric MOD

## 代码托管
- [Github](https://github.com/LiQing-Code/mouse-danmu)
- [码云](https://gitee.com/LiQing-Code/mouse-danmu)

## 特点
- 自定义弹幕颜色
- 自定义要显示的信息
- BoosBar显示醒目留言
- 贴近官方风格

## 前置

- 我的世界 >= 1.19
- [Cloth Config](https://github.com/shedaniel/cloth-config) >= 8.3.115
- [可选,推荐安装] [ModMenu](https://github.com/TerraformersMC/ModMenu) >= 4.0.0
  安装这个以后才能打开MOD选项

## 安装

点击[下载](https://github.com/LiQing-Code/mouse-danmu/releases/latest)

可以看看这个视频 [Mod新手教程](https://www.bilibili.com/video/BV1cX4y1T7RZ)

## 使用说明

[视频教程](https://www.bilibili.com/video/BV1e887e3EV8)

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

- `/dm <message>` 发送弹幕(需要Cookie) `<message>` 是要发送的内容

  列如 `/dm 哈哈哈 真好玩` 就会向当前直播间发送`哈哈哈 真好玩`
## 更多

部分直播间 roomId 是会重定向的，列如直播间 https://live.bilibili.com/1 需要在浏览器中打开，然后按F12打开开发者工具，找到控制台执行以下代码，就能获取到真实roomId
```javascript 
window.__NEPTUNE_IS_MY_WAIFU__.roomInitRes.data.room_id
``` 


英文语言文件不可用状态，欢迎有能力的人补充语言文件，更欢迎大家参与开发

如果遇到问题可以提交 [issues](https://github.com/LiQing-Code/mouse-danmu/issues) 或到[QQ群](https://jq.qq.com/?_wv=1027&k=nImOUpnV)反馈

表情弹幕是可以点击打开网页查看的，用户名也可以点击查看主页

项目依赖的库 [bili-live-danmu](https://github.com/LiQing-Code/bili-live-danmu)

## 免责声明

本模组用于获取哔哩哔哩直播的弹幕并在《我的世界》游戏聊天框中显示，提供了使用用户Cookie获取弹幕信息及发送弹幕的功能。

请注意：
1. 使用用户Cookie获取弹幕信息或发送弹幕，可能存在被哔哩哔哩封禁账号的风险，尽管目前尚无明确案例。
2. 如果在使用本模组时进行《我的世界》直播，尽管游戏聊天框显示的弹幕来自哔哩哔哩，但不合规的弹幕可能导致直播间被封禁。

使用本模组即表示您理解并接受以上风险，开发者不对因使用本模组导致的任何账号或直播间封禁承担责任。

## 截图
![效果演示](https://user-images.githubusercontent.com/51829935/206930179-9e6a5419-0127-4cf3-9f36-7554a66e448a.png)
![选项界面](https://user-images.githubusercontent.com/51829935/206930213-3c101622-2f8f-4fcd-949d-46b971d9d552.png)