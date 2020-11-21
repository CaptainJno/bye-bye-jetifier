package com.dipien.byebyejetifier.archive

import java.nio.file.Path

/**
 * Represents a file in the archive that is not an archive.
 */
class ArchiveFile(
    relativePath: Path,
    data: ByteArray
) : ArchiveItem {

    override var relativePath = relativePath
        private set

    override var fileName: String = relativePath.fileName.toString()
        private set

    var data: ByteArray = data
        private set

    var dependsOnSupportLibrary: Boolean = false

    override fun dependsOnSupportLibrary(): Boolean = dependsOnSupportLibrary

    override fun accept(visitor: ArchiveItemVisitor) {
        visitor.visit(this)
    }
}