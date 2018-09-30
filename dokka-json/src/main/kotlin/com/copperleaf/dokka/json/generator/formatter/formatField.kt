package com.copperleaf.dokka.json.generator.formatter

import com.copperleaf.dokka.json.models.KotlinField
import com.copperleaf.dokka.json.models.SignatureComponent
import org.jetbrains.dokka.DocumentationNode
import org.jetbrains.dokka.NodeKind

val DocumentationNode.isField: Boolean get() = this.kind == NodeKind.Field || this.kind == NodeKind.Property

fun DocumentationNode.toField(): KotlinField {
    assert(this.isField) { "node must be a Field or Property" }
    val modifiers = this.modifiers
    val nullable = this.nullable
    return KotlinField(
            this,
            this.simpleName,
            this.qualifiedName,
            this.contentText,
            this.summary.textLength,
            modifiers,
            this.simpleType,
            this.qualifiedType,
            nullable,
            this.fieldSignature(
                    modifiers,
                    this.asType()
            )
    )
}

fun DocumentationNode.fieldSignature(
        modifiers: List<String>,
        type: DocumentationNode
): List<SignatureComponent> {
    val signatureComponents = mutableListOf<SignatureComponent>()

    signatureComponents.appendModifierList(modifiers)
    signatureComponents.add(SignatureComponent("name", this.simpleName, ""))
    signatureComponents.add(SignatureComponent("punctuation", ": ", ""))
    signatureComponents.appendParameterType(type)

    return signatureComponents
}