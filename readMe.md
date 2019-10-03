### 项目组件化结构

+allLibrary 目录放置所有的依赖库
  
   -dsl 语法定义网络请求
   
   -dsl 切换线程

+router 目录放置路由处理核心逻辑代码

+module 目录放置业务功能代码

+app  项目主入口文件


### 配置基础加载网址以及debug开关  [configApi.properties]  [参数 commonlibrary gradle配置]

```
       //加载 配置文件
       Properties properties = new Properties()
       InputStream inputStream = project.rootProject.file('configApi.properties').newDataInputStream()
       properties.load(inputStream)
       def URL = properties.getProperty("URL")
       def DEBUG_LOG = properties.getProperty("DEBUG_LOG")


      //设置属性
      buildConfigField "String", "BASE_URL", "${URL}"
      buildConfigField "boolean", "DEBUG_LOG", "${DEBUG_LOG}"  

```



### 配置公共debug资源 gradle 配置   创建对应的资源目录   [参数 commonlibrary gradle配置]

```
           sourceSets {
            main {
                java {
                    exclude 'debug/**'
                }
                //组件项目时，加载这些资源
                if (isApp) {
                    res.srcDirs "src/main/res-debug"
                } else {
                    res.srcDirs "src/main/res"
                }
            }
        }

```

### 配置debug 组件调试   [参数 module 中 gradle配置]

```
 sourceSets {
        main {
            if (isApp) {
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
                java {
                    exclude 'debug/**'
                }
            }
        }
    }
```


### 配置组件APT处理注解 

```
kapt {
    correctErrorTypes = true
    arguments {
        arg("RouterModule", project.name)
    }
}
```

### 网络api支持  【login模块中LoginPresenter实现】

```
     val requestData = serviceApi.testLogin()
     http<HttpBaseModel> {
                data = requestData
                context = mContext
                lifecycleProvider = lProvider
                successCallBack {
                    onResult.invoke(it, false)
                }
                errorCallBack { errorCode, errorMessage ->
                    onResult.invoke(null, true)
                    CustomToast.showNetMsg(mContext, errorMessage)
                }
    
      }
```

### 简单实现 eventbus 【注，实际项目中尽可能少的用，使用过多，代码可读性变差】
```
   示例代码中，MainActivity 中 注册，然后LoginActivity中传回结果
   
   login 发送
   RxNewBus.postEvent(RxModel("完成了，可以把数据传回注册的地方"))
    
   main注册接收
   1  RxNewBus.registerEvent(this)
   2  @Subscribe(threadModel = ThreadModel.MAIN)
      fun getData(userInfo: RxModel) {
             KLog.e("接收来自login的数据回传  $userInfo")
         } 
   3  RxNewBus.unRegisterEvent(this)

```

### 项目可以分别以组件方式运行，通过 config.gradle 配置，isApplication变量设置为true即可,自定义 router ，页面之间跳转，可以查看代码中Activity

```
   Router.jump 支持 fragment跳转  支持content/activity 

   Router.jump(this) {
       target = USER
       requestCode = 1000  // 如果设置了，就会有forResult回调
       callDefaultBack {
            KLog.e("我是自己实现用户页面加载判断")
       }
   }

```


### 项目中尽可能采用DSL语法糖，代码看起来会更优雅，后续继续完整，整理基础常用代码
