package com.copperleaf.javadoc.json.models

import com.copperleaf.json.common.CommentComponent
import com.copperleaf.json.common.CommentTag
import com.copperleaf.json.common.DocElement
import com.copperleaf.json.common.ElementType
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JSON

interface JavaClasslike : DocElement

interface JavaMemberlike : DocElement {
    val modifiers: List<String>
}

/**
 * The result of executing Javadoc and transforming the results to JSON.
 */
class JavadocRootdoc(
        val packages: List<JavaPackageDoc>,
        val classes: List<JavaClassDoc>
)

/**
 * The docs for a single class. Includes a list of the constructors, methods, and fields in the class, as well as the
 * KDoc commentComponents on the class.
 */
@Serializable
data class JavaClassDoc(
    @Transient
        val node: Any? = null,

    val `package`: String,
    val modifiers: List<String>,
    override val kind: String,
    override val name: String,
    override val id: String,
    override val commentComponents: List<CommentComponent>,
    override val commentTags: Map<String, CommentTag>,
    val constructors: List<JavaConstructor>,
    val methods: List<JavaMethod>,
    val fields: List<JavaField>,
    val signature: List<CommentComponent>
) : JavaClasslike {

    val simpleSignature: String = signature.map { it.text }.joinToString("")

    companion object {
        fun fromJson(json: String): JavaClassDoc {
            return JSON.parse(JavaClassDoc.serializer(), json)
        }
    }

    fun toJson(): String {
        return JSON.indented.stringify(JavaClassDoc.serializer(), this)
    }
}

/**
 * The docs for a single package. Includes a list of the classes in the package, as well as the KDoc commentComponents on the
 * package. Class definitions only include metadata, but do not include information about their members.
 */
@Serializable
data class JavaPackageDoc(
    @Transient
        val node: Any? = null,

    override val name: String,
    override val id: String,
    override val commentComponents: List<CommentComponent>,
    override val commentTags: Map<String, CommentTag>,
    val classes: List<JavaClassDoc>
) : DocElement {
    override val kind = "Package"

    companion object {
        fun fromJson(json: String): JavaPackageDoc {
            return JSON.parse(JavaPackageDoc.serializer(), json)
        }
    }

    fun toJson(): String {
        return JSON.indented.stringify(JavaPackageDoc.serializer(), this)
    }
}

/**
 * The docs for a constructor of a class.
 */
@Serializable
data class JavaConstructor(
    @Transient
        val node: Any? = null,

    override val name: String,
    override val id: String,
    override val commentComponents: List<CommentComponent>,
    override val commentTags: Map<String, CommentTag>,
    override val modifiers: List<String>,
    val parameters: List<JavaParameter>,
    val signature: List<CommentComponent>
) : JavaMemberlike {
    override val kind = "Constructor"
}

/**
 * The docs for a method or function in a class.
 */
@Serializable
data class JavaMethod(
    @Transient
        val node: Any? = null,

    override val name: String,
    override val id: String,
    override val commentComponents: List<CommentComponent>,
    override val commentTags: Map<String, CommentTag>,
    override val modifiers: List<String>,
    val parameters: List<JavaParameter>,
    val returnValue: JavaReturnType,
    val signature: List<CommentComponent>
) : JavaMemberlike {
    override val kind = "Method"
}

/**
 * The docs for a field or property in a class.
 */
@Serializable
data class JavaField(
    @Transient
        val node: Any? = null,

    override val name: String,
    override val id: String,
    override val commentComponents: List<CommentComponent>,
    override val commentTags: Map<String, CommentTag>,
    override val modifiers: List<String>,

    override val typeName: String,
    override val typeId: String,
    override val signature: List<CommentComponent>
) : JavaMemberlike, ElementType {
    override val kind = "Field"
}

/**
 * The docs for a parameter of a constructor or method
 */
@Serializable
data class JavaParameter(
    @Transient
        val node: Any? = null,

    override val name: String,
    override val id: String,
    override val commentComponents: List<CommentComponent>,
    override val commentTags: Map<String, CommentTag>,

    override val typeName: String,
    override val typeId: String,
    override val signature: List<CommentComponent>
) : DocElement, ElementType {
    override val kind = "Parameter"
}

/**
 * The docs for a parameter of a constructor or method
 */
@Serializable
data class JavaReturnType(
    @Transient
        val node: Any? = null,

    override val name: String,
    override val id: String,
    override val commentComponents: List<CommentComponent>,
    override val commentTags: Map<String, CommentTag>,

    override val typeName: String,
    override val typeId: String,
    override val signature: List<CommentComponent>
) : DocElement, ElementType {
    override val kind = "ReturnValue"
}
