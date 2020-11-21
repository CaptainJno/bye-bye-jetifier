package com.dipien.byebyejetifier.scanner.bytecode

import com.dipien.byebyejetifier.archive.ArchiveFile
import com.dipien.byebyejetifier.scanner.Scanner
import com.dipien.byebyejetifier.scanner.ScannerHelper
import org.gradle.api.logging.Logger
import org.objectweb.asm.ClassReader
import org.objectweb.asm.commons.ClassRemapper

class BytecodeScanner(private var logger: Logger, private val scannerHelper: ScannerHelper) : Scanner {

    override fun scan(archiveFile: ArchiveFile) {
        val reader = ClassReader(archiveFile.data)
        val customRemapper = CustomRemapper(scannerHelper)
        val visitor = ClassRemapper(null, customRemapper)

        reader.accept(visitor, 0 /* flags */)

        archiveFile.dependsOnSupportLibrary = customRemapper.oldDependencies.isNotEmpty()

        customRemapper.oldDependencies.forEach {
            logger.lifecycle("${archiveFile.relativePath} -> $it")
        }
    }

    override fun canScan(archiveFile: ArchiveFile): Boolean = archiveFile.isClassFile()
}