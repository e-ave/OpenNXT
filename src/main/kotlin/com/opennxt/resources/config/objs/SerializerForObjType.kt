package com.opennxt.resources.config.objs

import com.google.gson.*
import com.opennxt.resources.config.objs.ObjDefinition.Companion.DEFAULT
import java.lang.reflect.Type
import kotlin.reflect.full.memberProperties

class SerializerForObjType : JsonSerializer<ObjDefinition?> {
    override fun serialize(obj: ObjDefinition?, type: Type, jsc: JsonSerializationContext): JsonElement {
        val gson = Gson()
        val jObj = gson.toJsonTree(obj) as JsonObject
        val defaultObj = DEFAULT
        if(obj != null) {
            for (d in ObjDefinition::class.memberProperties) {
                val prop = d.get(obj)
                if (prop != null) {
                    println("${d.name} = $prop")
                    if (prop == d.get(defaultObj)) {
                        jObj.remove(d.name)
                    }
                }
            }
        }
        return jObj
    }
}