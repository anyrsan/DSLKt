package com.any.routercomplie

import com.any.routerannotation.KInterceptor
import com.any.routerannotation.KNavigation
import com.any.routerannotation.KRouter
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class RouterProcessor : AbstractProcessor() {

    //核心库
    private val packName = "com.any.routercompliecore"

    private val moduleNameTag = "RouterModule"
    private val moduleAppTag = "RouterApp"

    private val routerAppInit = "RouterAppInit"

    private val routerModuleInit = "RouterModuleInit_"

    private lateinit var filer: Filer
    private lateinit var messager: Messager

    private var moduleName = ""
    private var moduleApp = ""


    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)

        filer = processingEnvironment.filer

        messager = processingEnvironment.messager

        val map: Map<String, String> = processingEnvironment.options

        map.keys.forEach {
            if (it == moduleNameTag) {
                moduleName = map[it] ?: ""
            }
            if (it == moduleAppTag) {
                moduleApp = map[it] ?: ""
            }
        }

        debug("RouterProcessor  moduleName=$moduleName")

        debug("RouterProcessor  moduleApp=$moduleApp")

        debug("RouterProcessor init...进入")
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }


    override fun getSupportedAnnotationTypes(): Set<String> {
        val set = HashSet<String>()
        set.add(KRouter::class.java.canonicalName)
        set.add(KInterceptor::class.java.canonicalName)
        set.add(KNavigation::class.java.canonicalName)
        return set
    }

    override fun process(set: Set<TypeElement>?, roundEv: RoundEnvironment): Boolean {
        debug("########################   process  ###################################")

        if (set == null || set.isEmpty()) return false
        debug("start process annotation Router....")
        handlerRouterApp(roundEv)
        handlerRouterModule(roundEv)
        debug("end process annotation Router....")
        return true
    }


    private fun handlerRouterApp(roundEv: RoundEnvironment) {

        val set = roundEv.getElementsAnnotatedWith(KRouter::class.java)

        set.filterNotNull().let {

            val appClass = TypeSpec.objectBuilder(routerAppInit)

            val initFun = FunSpec.builder("init")

            if (moduleApp.isNotEmpty()) {
                val list = moduleApp.split(",")
                list.forEach {
                    debug("输出的module app$it")
                    //调用register方法
                    initFun.addStatement("$routerModuleInit$it.register()")
                    //调用addInterceptor方法完成添加
                    initFun.addStatement("$routerModuleInit$it.addInterceptor()")
                }

                //追加写入
                handlerNavigation(roundEv,initFun)

                appClass.addFunction(initFun.build())

                val kotlinFile = FileSpec.builder(packName, routerAppInit)
                    .addType(appClass.build())
                    .build()

                kotlinFile.writeTo(filer)

                debug("这个文件会被重新写入吗？？？？")
            }
        }
    }


    //对一个方法增加自己的元素，
    private fun handlerNavigation(roundEv: RoundEnvironment,initFun: FunSpec.Builder){
        // 添加导航回调
        val navigationSet = roundEv.getElementsAnnotatedWith(KNavigation::class.java)
        navigationSet.filterNotNull().let {
            if(it.isNotEmpty()){
                val element = it.last()   //取出最后一个
                if (element.kind == ElementKind.CLASS) {  // 注解是class类型
                    val typeElement = element as TypeElement
                    initFun.addStatement(
                        "$packName.Router.setNavigationCallBack(%T())", typeElement.asType()
                    )
                }
            }
        }
    }


    // 处理模块
    private fun handlerRouterModule(roundEv: RoundEnvironment) {
        //添加注册
        val registerFun = provideRegisterFun(roundEv)
        // 添加 拦截
        val addInterceptorFun = provideAddInterceptor(roundEv)
        //确定类文件函数
        val moduleClass = TypeSpec.objectBuilder(routerModuleInit + moduleName)
            .addFunction(registerFun)
            .addFunction(addInterceptorFun)
            .build()
        // 构建类文件
        val kotlinFile = FileSpec.builder(packName, routerModuleInit + moduleName)
            .addType(moduleClass)
            .build()
        // 输出类文件
        kotlinFile.writeTo(filer)

    }


    //拦截器
    private fun provideAddInterceptor(roundEv: RoundEnvironment): FunSpec {
        val set = roundEv.getElementsAnnotatedWith(KInterceptor::class.java)
        val iterator = set.iterator()
        val addInterceptorFun = FunSpec.builder("addInterceptor")
        while (iterator.hasNext()) {
            val element = iterator.next()
            val router = element.getAnnotation(KInterceptor::class.java)
            if (element.kind == ElementKind.CLASS) {
                val typeElement = element as TypeElement
                val value = router.priority
                addInterceptorFun.addStatement(
                    "$packName.Router.addInterceptorItem($value,%T())", typeElement.asType()
                )
                debug("TypeElement..." + typeElement.simpleName + "->" + typeElement.qualifiedName + "..." + value)
            }
        }


        return addInterceptorFun.build()
    }


    //注册
    private fun provideRegisterFun(roundEv: RoundEnvironment): FunSpec {
        val set = roundEv.getElementsAnnotatedWith(KRouter::class.java)
        val iterator = set.iterator()
        val registerFun = FunSpec.builder("register")
        while (iterator.hasNext()) {
            val element = iterator.next()
            val router = element.getAnnotation(KRouter::class.java)
            if (element.kind == ElementKind.CLASS) {
                val typeElement = element as TypeElement
                val value = router.value
                value.forEach {
                    registerFun.addStatement(
                        "$packName.Router.registerPage(%S, %T::class.java)",
                        it,
                        typeElement.asType()
                    )
                    debug("TypeElement..." + typeElement.simpleName + "->" + typeElement.qualifiedName + "..." + value)
                }
            }
        }
        return registerFun.build()
    }


    private fun debug(msg: String) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg)
    }

    private fun error(msg: String) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg)
    }
}
