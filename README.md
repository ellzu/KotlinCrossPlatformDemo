# ezxx1

#### 介绍
{**以下是 Gitee 平台说明，您可以替换此简介**
Gitee 是 OSCHINA 推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用 Gitee 实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 软件架构
	本仓库是一个Kotlin跨平台Demo 里面有 Android + iOS + MAC OS + Server 端的实现参考



#### 使用说明

1. kmm 是公共目录 andoid + iOS + mac os 都会使用到里面的源代码 属于客户端的公共目录
2. server端暂时没有使用kmm目录的内容，server端内部有跨js端的实现参考例子
3. android端的Gradle在android目录下，io端的grqdle在kmm目录下
4. 本例子在xcode编译的时候，需要在xcode添加编译前脚本
```
cd "$SRCROOT/kmm"
echo "kmmdir `pwd`"
source ~/.bash_profile
./gradlew :kmm:embedAndSignAppleFrameworkForXcode
```

