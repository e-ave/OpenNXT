package com.opennxt.resources.config.objs

import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import com.opennxt.resources.DiskResourceCodec
import com.opennxt.resources.config.vars.ScriptVarType
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.nio.file.Files
import java.nio.file.Path
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

object ObjDiskCodec : DiskResourceCodec<ObjDefinition> {
    override fun list(path: Path): Map<String, Path> {
        val result = Object2ObjectOpenHashMap<String, Path>()
        Files.list(path).forEach { file ->
            if (Files.isRegularFile(file)) {
                val name = file.fileName.toString().toLowerCase()
                if (name.endsWith(".toml")) {
                    result[name.substring(0, name.length - 5)] = file
                }
            } else if (Files.isDirectory(file)) {
                result.putAll(list(file))
            }
        }
        return result
    }

    override fun load(path: Path): ObjDefinition? {
        TODO("tes")
        return null
    }

    override fun store(path: Path, data: ObjDefinition) {
        Files.createDirectories(path.parent)
        Files.deleteIfExists(path)

        val defaultObj = ObjDefinition.DEFAULT
        val map = mutableMapOf<Any, Any>()

        for(d in ObjDefinition::class.memberProperties){
            val prop = d.get(data)
            if(prop != null) {
                println("${d.name} = $prop")
                if (prop != d.get(defaultObj)) {
                    map[d.name] = prop
                }
            }
        }

        TomlWriter().write(map, path.toFile())
    }

    override fun getFileExtension(resource: ObjDefinition): String? = "toml"
}