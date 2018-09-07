package com.ykrc17.android.kizuna.cli

import com.ykrc17.android.kizuna.kizuna
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import java.io.File

fun main(args: Array<String>) {
    val options = Options()
    options.addOption("d", "src-dir", true, "source directory")
    options.addOption("p", "package", true, "generated java class's package name")
    options.addOption("h", "help", false, "help")
    val line = DefaultParser().parse(options, args)
    if (line.argList.isEmpty() || line.hasOption("h")) {
        HelpFormatter().printHelp("java -jar kizuna.jar [options] [layoutXml]", options)
        return
    }

    val layoutXmlFile = File(line.argList[0])
    val inSrcDir: File? = line.getOptionValue("d")?.let { File(it) }
    val targetPackage: String? = line.getOptionValue("p")
    kizuna(layoutXmlFile, inSrcDir, "", targetPackage) {}
}