package com.opennxt.resources

import com.opennxt.resources.config.enums.EnumDefinition
import com.opennxt.resources.config.params.ParamDefinition
import com.opennxt.resources.config.structs.StructDefinition
import kotlin.reflect.KClass

enum class ResourceType(val identifier: String, val kclass: KClass<*>) {
    ENUM("enum", EnumDefinition::class),
    PARAM("param", ParamDefinition::class),
    STRUCT("struct", StructDefinition::class),
    // TODO : Add support for VarTypes
    VAR_PLAYER("varp", Object::class),
    VAR_NPC("varn", Object::class),
    VAR_CLIENT("varc", Object::class),
    VAR_WORLD("varworld", Object::class),
    VAR_REGION("regionvar", Object::class),
    VAR_OBJECT("varobj", Object::class),
    VAR_CLAN("varclan", Object::class),
    VAR_CLAN_SETTING("varclansetting", Object::class);

    companion object {
        private val values = values()

        fun getArchive(id: Int, size: Int): Int = id.ushr(size)

        fun getFile(id: Int, size: Int): Int = (id and (1 shl size) - 1)

        fun forClass(kclass: KClass<*>): ResourceType? = values.firstOrNull { it.kclass == kclass }
    }
}