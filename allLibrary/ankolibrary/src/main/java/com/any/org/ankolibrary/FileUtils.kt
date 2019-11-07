@file:Suppress("unused")
package com.any.org.ankolibrary

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 *
 * @author any
 * @time 2019/9/7 17.34
 * @details
 */

// 复制文件或文件夹到指定位置
infix fun File.copyAll(targetDir: String) {
    //如果是文件，直接复制
    if(isFile){
        val targetFile = File(targetDir, name)
        copyTo(targetFile, true)
    }else{
        listFiles().forEach { mk ->
            val targetFile = File(targetDir, mk.name)
            if (mk.isDirectory) {
                val bool = targetFile.mkdirs()
                println(bool)
                mk.copyAll(targetFile.absolutePath)
            } else {
                mk.copyTo(targetFile, true)
            }
        }
    }

}

fun File.zipInputStream() = ZipInputStream(this.inputStream())

fun File.zipOutputStream() = let {
    val b = it.parentFile.mkdirs()  //创建对应的目录
    println("create dir $b   ${it.absolutePath}")
    ZipOutputStream(this.outputStream())
}

fun InputStream.zipInputStream() = ZipInputStream(this)

fun OutputStream.zipOutputStream() = ZipOutputStream(this)

infix fun File.unZipTo(path: String) {
    checkUnzipFolder(path)
    ZipFile(this) unZipTo path
}

infix fun ZipFile.unZipTo(path: String) {
    checkUnzipFolder(path)
    for (entry in entries()) {
        //判断是否为文件夹
        if (entry.isDirectory) {
            File("${path}/${entry.name}").mkdirs()
        } else {
            val input = getInputStream(entry)
            val outputFile = File("${path}/${entry.name}")
            if (!outputFile.exists()) outputFile.smartCreateNewFile()
            val output = outputFile.outputStream()
            input.writeTo(output, DEFAULT_BUFFER_SIZE)
        }
    }
}

/**
 * 检查路径正确性
 */
private fun checkUnzipFolder(path: String) {
    val file = File(path)
    if (file.isFile) throw RuntimeException("路径不能是文件")
    if (!file.exists()) {
        if (!file.mkdirs()) throw RuntimeException("创建文件夹失败")
    }
}

fun ZipOutputStream.zipFrom(vararg srcs: String) {

    val files = srcs.map { File(it) }

    files.forEach {
        if (it.isFile) {
            zip(arrayOf(it), null)
        } else if (it.isDirectory) {
            zip(it.listFiles(), null)   //it.name  这样就传入了多一层目录 结构
        }
    }
    this.close()
}

private fun ZipOutputStream.zip(files: Array<File>, path: String?) {
    //前缀,用于构造路径
    val prefix = if (path == null) "" else "$path/"

    if (files.isEmpty()) createEmptyFolder(prefix)

    files.forEach {
        if (it.isFile) {
            val entry = ZipEntry("$prefix${it.name}")
            val ins = it.inputStream().buffered()
            putNextEntry(entry)
            ins.writeTo(this, DEFAULT_BUFFER_SIZE, closeOutput = false)
            closeEntry()
        } else {
            zip(it.listFiles(), "$prefix${it.name}")
        }
    }
}

/**
 * inputstream内容写入outputstream
 */
fun InputStream.writeTo(
    outputStream: OutputStream, bufferSize: Int = 1024 * 2,
    closeInput: Boolean = true, closeOutput: Boolean = true
) {

    val buffer = ByteArray(bufferSize)
    val br = this.buffered()
    val bw = outputStream.buffered()
    var length = 0

    while ({ length = br.read(buffer);length != -1 }()) {
        bw.write(buffer, 0, length)
    }

    bw.flush()

    if (closeInput) {
        close()
    }

    if (closeOutput) {
        outputStream.close()
    }
}

/**
 * 生成一个压缩文件的文件夹
 */
private fun ZipOutputStream.createEmptyFolder(location: String) {
    putNextEntry(ZipEntry(location))
    closeEntry()
}

fun File.smartCreateNewFile(): Boolean {

    if (exists()) return true
    if (parentFile.exists()) return createNewFile()

    if (parentFile.mkdirs()) {
        if (this.createNewFile()) {
            return true
        }
    }
    return false
}
