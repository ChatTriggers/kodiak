package com.copperleaf.javadoc.json.formatter

import com.copperleaf.json.common.CommentComponent
import com.copperleaf.json.common.CommentTag
import com.sun.javadoc.SeeTag
import com.sun.javadoc.Tag

fun Array<Tag>.asCommentComponents(): List<CommentComponent> {
    return this.map(Tag::toCommentComponent)
}

fun Array<Tag>.asCommentComponentsMap(): Map<String, CommentTag> {
    return this.map { tag ->
        val commentComponent = tag.toCommentComponent()
        commentComponent.kind to commentComponent.toCommentTag()
    }.toMap()
}

fun Tag.toCommentComponent(): CommentComponent {
    val key = kind().let { if (it.startsWith("@")) it.drop(1) else it }
    val name: String
    val qualifiedName: String
    if (this is SeeTag && referencedClass() != null) {
        name = referencedClass().simpleTypeName()
        qualifiedName = referencedClass().qualifiedTypeName()
    } else {
        name = text()
        qualifiedName = text()
    }
    return CommentComponent(
        key,
        name,
        qualifiedName
    )
}

fun CommentComponent.toCommentTag(): CommentTag {
    return CommentTag(listOf(this))
}

fun List<String>.toModifierListSignature(): List<CommentComponent> {
    return this.map { CommentComponent("modifier", "$it ") }
}