package com.opennxt.resources.config.objs

import com.opennxt.ext.*
import com.opennxt.filesystem.Filesystem
import com.opennxt.resources.FilesystemResourceCodec
import com.opennxt.resources.Js5Archive
import com.opennxt.resources.Js5ConfigGroup
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
import java.nio.ByteBuffer
import com.opennxt.resources.config.objs.ObjTypeEncodingKey.*
import java.util.*


object ObjFilesystemCodec : FilesystemResourceCodec<ObjDefinition> {

    override fun getMaxId(fs: Filesystem): Int {
        val table = fs.getReferenceTable(Js5Archive.JS5_CONFIG_OBJ) ?: return 0
        val groupCount = table.highestEntry() - 1
        val lastGroupSize = table.archives[groupCount]!!.files.lastKey()

        return groupCount * Js5ConfigGroup.OBJTYPE.groupSize + lastGroupSize
    }

    override fun list(fs: Filesystem): Map<Int, ObjDefinition> {
        val result = Int2ObjectAVLTreeMap<ObjDefinition>()
        for (i in 0 until getMaxId(fs) + 1) {
            val def = load(fs, i) ?: continue
            post_decode(def, fs)

            result[i] = def
        }
        return result
    }

    override fun load(fs: Filesystem, id: Int): ObjDefinition? {
        val groupId = Js5ConfigGroup.OBJTYPE.getClientGroupId(id)
        val fileId = Js5ConfigGroup.OBJTYPE.getClientFileId(id)
        val table = fs.getReferenceTable(Js5Archive.JS5_CONFIG_OBJ) ?: return null
        val archive = table.loadArchive(groupId) ?: return null
        val file = archive.files[fileId] ?: return null

        val definition = ObjDefinition(id)
        val buffer = ByteBuffer.wrap(file.data)

        if(buffer != null) {
            definition.tradeability = true
        }

        while (buffer.hasRemaining()) {
            val code = buffer.getUnsignedByte()
            val encodingType = ObjTypeEncodingKey.op(code)
            if (encodingType == EOF) return definition
            when (encodingType) {
                MESH -> definition.baseModel = buffer.getSmartInt()
                NAME -> definition.name = buffer.getString()
                BUFF -> definition.buffEffect = buffer.getString()
                ZOOM2D -> definition.zoom2D = buffer.getUnsignedShort()
                XAN2D -> definition.xAngle2D = buffer.getUnsignedShort()
                YAN2D -> definition.yAngle2D = buffer.getUnsignedShort()
                XOF2D -> definition.xOffset2D = buffer.getSignedShort()
                YOF2D -> definition.xOffset2D = buffer.getSignedShort()
                STACKABLE -> definition.stackability = ObjStackability.ALWAYS
                COST -> definition.cost = buffer.int
                WEARPOS -> definition.wearpos = buffer.getUnsignedByte()
                WEARPOS2 -> definition.wearpos2 = buffer.getUnsignedByte()
                TRADABLE -> definition.tradeability = false
                MEMBERS -> definition.members = true
                MULTISTACKSIZE -> definition.multiStackSize = buffer.getUnsignedShort()
                MANWEAR -> definition.maleEquip1 = buffer.getSmartInt()
                MANWEAR2 -> definition.maleEquip2 = buffer.getSmartInt()
                WOMANWEAR -> definition.femaleEquip1 = buffer.getSmartInt()
                WOMANWEAR2 -> definition.femaleEquip2 = buffer.getSmartInt()
                WEARPOS3 -> definition.wearpos3 = buffer.getUnsignedByte()
                OPS -> definition.groundOptions[code - encodingType.opcode] = buffer.getString()
                IOPS -> definition.inventoryOptions[code - encodingType.opcode] = buffer.getString()
                RECOL -> {
                    val len = buffer.getUnsignedByte()
                    definition.recolorSrc = ShortArray(len)
                    definition.recolorDst = ShortArray(len)
                    for (i in 0 until len) {
                        definition.recolorSrc!![i] = buffer.getUnsignedShort().toShort()
                        definition.recolorDst!![i] = buffer.getUnsignedShort().toShort()
                    }
                }
                RETEX -> {
                    val len = buffer.getUnsignedByte()
                    definition.retextureSrc = ShortArray(len)
                    definition.retextureDst = ShortArray(len)
                    for (i in 0 until len) {
                        definition.retextureSrc!![i] = buffer.getUnsignedShort().toShort()
                        definition.retextureDst!![i] = buffer.getUnsignedShort().toShort()
                    }
                }
                RECOL_PALETTE -> {
                    val len = buffer.getUnsignedByte()
                    definition.recolorDstPalette = ByteArray(len)
                    for (i in 0 until len) {
                        definition.recolorDstPalette!![i] = buffer.get()
                    }
                }
                MINIMENU_COL -> {
                    definition.nameColor = buffer.int
                    definition.hasCustomNameColor = true
                }
                RECOL_INDEX -> {
                    val input = buffer.getUnsignedShort()
                    var len = 0
                    var i = input

                    while (i > 0) { //log2(input)+1
                        len++
                        i = i shr 1
                    }
                    definition.recolorDstIndices = ByteArray(len)
                    var dstIdx: Byte = 0
                    for (idx in 0 until len) {
                        if (input and 1 shl idx > 0) {
                            definition.recolorDstIndices!![idx] = dstIdx
                            dstIdx++
                        } else definition.recolorDstIndices!![idx] = (-1).toByte()
                    }
                }
                RETEX_INDEX -> {
                    val input = buffer.getUnsignedShort()
                    var len = 0
                    var i = input

                    while (i > 0) { //log2(input)+1
                        len++
                        i = i shr 1
                    }
                    definition.retextureDstIndices = ByteArray(len)
                    var dstIdx: Byte = 0
                    for (idx in 0 until len) {
                        if (input and 1 shl idx > 0) {
                            definition.retextureDstIndices!![idx] = dstIdx
                            dstIdx++
                        } else definition.retextureDstIndices!![idx] = (-1).toByte()
                    }
                }
                STOCKMARKET -> definition.stockmarket = true
                STOCKMARKET_LIMIT -> definition.stockmarketLimit = buffer.getInt()
                MANWEAR3 -> definition.manEquip3 = buffer.getSmartInt()
                WOMANWEAR3 -> definition.femaleEquip3 = buffer.getSmartInt()
                MANHEAD -> definition.manHead = buffer.getSmartInt()
                WOMANHEAD -> definition.womanHead = buffer.getSmartInt()
                MANHEAD2 -> definition.manHead2 = buffer.getSmartInt()
                WOMANHEAD2 -> definition.womanHead2 = buffer.getSmartInt()
                CATEGORY -> definition.category = buffer.getUnsignedShort()
                ZAN2D -> definition.zAngle2D = buffer.getUnsignedShort()
                DUMMYITEM -> definition.dummyItem = buffer.getUnsignedByte()
                CERT_LINK -> definition.certLink = buffer.getUnsignedShort()
                CERT_TEMPLATE -> definition.certTemplate = buffer.getUnsignedShort()
                COUNT -> {
                    if (definition.stackIds == null) {
                        definition.stackIds = IntArray(10)
                        definition.stackAmounts = IntArray(10)
                    }
                    definition.stackIds!![code - encodingType.opcode] = buffer.getUnsignedShort()
                    definition.stackAmounts!![code - encodingType.opcode] = buffer.getUnsignedShort()
                }
                RESIZEX -> definition.resizeX = buffer.getUnsignedShort()
                RESIZEY -> definition.resizeY = buffer.getUnsignedShort()
                RESIZEZ -> definition.resizeZ = buffer.getUnsignedShort()
                AMBIENT -> definition.ambient = buffer.getSignedByte()
                CONTRAST -> definition.contrast = buffer.getSignedByte()
                TEAM -> definition.team = buffer.getUnsignedByte()
                LENT_LINK -> definition.lentLink = buffer.getUnsignedShort()
                LENT_TEMPLATE -> definition.lentTemplate = buffer.getUnsignedShort()
                MANWEAROF -> {
                    definition.manWearXOffset = buffer.getUnsignedByte() shl 2
                    definition.manWearYOffset = buffer.getUnsignedByte() shl 2
                    definition.manWearZOffset = buffer.getUnsignedByte() shl 2
                }
                WOMANWEAROF -> {
                    definition.womanWearXOffset = buffer.getUnsignedByte() shl 2
                    definition.womanWearYOffset = buffer.getUnsignedByte() shl 2
                    definition.womanWearZOffset = buffer.getUnsignedByte() shl 2
                }
                CURSOR1 -> {
                    definition.groundCursorOp = buffer.getUnsignedByte()
                    definition.groundCursor = buffer.getUnsignedShort()
                }
                CURSOR2 -> {
                    definition.cursor2op = buffer.getUnsignedByte()
                    definition.cursor2 = buffer.getUnsignedShort()
                }
                ICURSOR1 -> {
                    definition.cursor1iop = buffer.getUnsignedByte()
                    definition.icursor1 = buffer.getUnsignedShort()
                }
                ICURSOR2 -> {
                    definition.cursor2iop = buffer.getUnsignedByte()
                    definition.icursor2 = buffer.getUnsignedShort()
                }
                UNKNOWN_NEW -> definition.unknown131 = buffer.getString()
                QUESTS -> {
                    val len = buffer.getUnsignedByte()
                    definition.quests = IntArray(len)
                    for (i in 0 until len) {
                        definition.quests!![i] = buffer.getUnsignedShort()
                    }
                }
                PICKSIZESHIFT -> definition.pickSizeShift = buffer.getUnsignedByte()
                BIND_LINK -> definition.bindLink = buffer.getUnsignedShort()
                BIND_TEMPLATE -> definition.bindTemplate = buffer.getUnsignedShort()
                CURSORS -> {
                    if (definition.groundMenuCursors == null) {
                        definition.groundMenuCursors = IntArray(6)
                        Arrays.fill(definition.groundMenuCursors, -1)
                    }
                    definition.groundMenuCursors!![code - encodingType.opcode] = buffer.getUnsignedShort()
                }
                ICURSORS -> {
                    if (definition.interfaceMenuCursors == null) {
                        definition.interfaceMenuCursors = IntArray(5)
                        Arrays.fill(definition.interfaceMenuCursors, -1)
                    }
                    definition.interfaceMenuCursors!![code - encodingType.opcode] = buffer.getUnsignedShort()
                }
                NXT_BOOL2 -> definition.op156 = false
                RANDOMPOS -> definition.randomizeGroundPos = true
                SHARD_LINK -> definition.shardLink = buffer.getUnsignedShort()
                SHARD_TEMPLATE -> definition.shardTemplate = buffer.getUnsignedShort()
                SHARD_REQ -> definition.shardCombineAmount = buffer.getUnsignedShort()
                SHARD_NAME -> definition.shardName = buffer.getString()
                STACKABLE_NEVER -> definition.stackability = ObjStackability.NEVER
                BOND -> definition.bond = true
                NXT_BOOL -> definition.nxtBoolOp168 = false
                PARAMS -> {
                    val size = buffer.getUnsignedByte()
                    for(i in 0 until size) {
                        val isString = buffer.getUnsignedByte() == 1
                        definition.params[buffer.getMedium()] = if (isString) buffer.getString() else buffer.int
                    }
                }
                else -> throw IllegalArgumentException("Unrecognized .obj config opcode $encodingType")
            }
        }

        return definition
    }

    val language: String = "en"

    fun post_decode(def: ObjDefinition, fs: Filesystem) {
        if (def.certTemplate !== -1) {
            def.setup_derived_object_cert(
                load(fs, def.certTemplate)!!,
                load(fs, def.certLink)!!,
                language
            )
        } else if (def.lentTemplate !== -1) {
            def.setup_derived_object_lent(
                load(fs, def.lentTemplate)!!,
                load(fs, def.lentLink)!!,
                language
            )
        } else if (def.bindTemplate !== -1) {
            def.setup_derived_object_bought(
                load(fs, def.bindTemplate)!!,
                load(fs, def.bindLink)!!,
                language
            )
        } else if (def.shardTemplate !== -1) {
            def.setup_derived_object_shard(
                load(fs, def.shardTemplate)!!,
                load(fs, def.shardLink)!!,
                language
            )
        }
        if (def.dummyItem !== 0) {
            def.tradeability = false
        }
    }


    override fun store(fs: Filesystem, id: Int, data: ObjDefinition) {
        TODO("Not yet implemented")
    }

}