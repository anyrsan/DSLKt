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

### 网络api支持

```
    http<HttpBaseModel>{
    
    }
```