package com.copperleaf.javadoc.json.formatter

import com.copperleaf.javadoc.json.models.JavaField
import com.copperleaf.javadoc.json.models.SignatureComponent
import com.sun.javadoc.FieldDoc
import com.sun.javadoc.Type

fun FieldDoc.toField(): JavaField {
    val modifiers = listOf(this.modifiers())
    return JavaField(
            this,
            this.name(),
            this.qualifiedName(),
            this.commentText(),
            this.inlineTags().asCommentTags(),
            this.tags().asCommentTagsMap(),
            modifiers,
            this.type().simpleTypeName(),
            this.type().qualifiedTypeName(),
            this.fieldSignature(
                    modifiers,
                    this.type()
            )
    )
}

fun FieldDoc.fieldSignature(
        modifiers: List<String>,
        type: Type
): List<SignatureComponent> {
    val list = mutableListOf<SignatureComponent>()

    list.addAll(modifiers.toModifierListSignature())
    list.addAll(type.toTypeSignature())
    list.add(SignatureComponent("name", " ${this.name()}", ""))

    return list
}