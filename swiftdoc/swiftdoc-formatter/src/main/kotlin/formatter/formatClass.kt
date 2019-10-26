package com.copperleaf.kodiak.swift.formatter

import com.copperleaf.kodiak.common.CommentComponent
import com.copperleaf.kodiak.common.CommentComponent.Companion.PUNCTUATION
import com.copperleaf.kodiak.common.CommentComponent.Companion.TEXT
import com.copperleaf.kodiak.common.CommentComponent.Companion.TYPE_NAME
import com.copperleaf.kodiak.swift.internal.models.SourceKittenSubstructure
import com.copperleaf.kodiak.swift.internal.models.SwiftSubstructureKind.CLASS_METHOD
import com.copperleaf.kodiak.swift.internal.models.SwiftSubstructureKind.CLASS_VARIABLE
import com.copperleaf.kodiak.swift.internal.models.SwiftSubstructureKind.INIT_METHOD
import com.copperleaf.kodiak.swift.internal.models.SwiftSubstructureKind.INSTANCE_METHOD
import com.copperleaf.kodiak.swift.internal.models.SwiftSubstructureKind.INSTANCE_VARIABLE
import com.copperleaf.kodiak.swift.internal.models.SwiftSubstructureKind.STATIC_METHOD
import com.copperleaf.kodiak.swift.internal.models.SwiftSubstructureKind.STATIC_VARIABLE
import com.copperleaf.kodiak.swift.models.SwiftClass

fun SourceKittenSubstructure.toClassDoc(structure: SourceKittenSubstructure, deep: Boolean = false): SwiftClass {
    return SwiftClass(
        this,
        sourceFile,
        this.kind.name,
        this.name,
        "${sourceFile}/${this.name}",
        this.getModifiers(),
        this.getComment(),
        if (deep) this.childrenOfType(INIT_METHOD) { it.toInitializerDoc(structure) } else emptyList(),
        if (deep) this.childrenOfType(STATIC_METHOD, CLASS_METHOD, INSTANCE_METHOD) { it.toFunctionDoc(structure) } else emptyList(),
        if (deep) this.childrenOfType(STATIC_VARIABLE, CLASS_VARIABLE, INSTANCE_VARIABLE) { it.toVariableDoc(structure) } else emptyList(),
        classSignature()
    )
}

fun SourceKittenSubstructure.toEnumCaseDoc(structure: SourceKittenSubstructure): SwiftClass {
    return this.toClassDoc(structure, false)
}

fun SourceKittenSubstructure.classSignature(): List<CommentComponent> {
    val list = mutableListOf<CommentComponent>()

    list.add(CommentComponent(TEXT, this.kind.kindName))
    list.add(CommentComponent(TYPE_NAME, " ${this.name}", this.name))

    if(this.inheritedtypes.isNotEmpty()) {
        list.add(CommentComponent(PUNCTUATION, ":"))

        this.inheritedtypes.forEachIndexed { index, type ->
            list.add(CommentComponent(TYPE_NAME, " ${type.name}", type.name))

            if (index < this.inheritedtypes.size - 1) {
                list.add(CommentComponent(PUNCTUATION, ", "))
            }
        }
    }

    return list
}