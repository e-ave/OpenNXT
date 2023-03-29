package com.opennxt.resources.config.objs

import com.opennxt.resources.DefaultStateChecker
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import kotlin.math.floor

data class ObjDefinition(
    var id: Int,
    var baseModel: Int = 0,
    var name: String? = null,
    var nameGerman: String? = null,
    var nameFrench: String? = null,
    var namePortugese: String? = null,
    var nameSpanish: String? = null,
    var desc: String? = null,
    var descGerman: String? = null,
    var descFrench: String? = null,
    var descPortugese: String? = null,
    var descSpanish: String? = null,
    var weight: Int = 0,
    var buffEffect: String? = null,
    var zoom2D: Int = 2000,
    var xAngle2D: Int = 0,
    var yAngle2D: Int = 0,
    var xOffset2D: Int = 0,
    var yOffset2D: Int = 0,
    var stackability: ObjStackability = ObjStackability.SOMETIMES,
    var cost: Int = 1,
    var wearpos: Int = -1,
    var wearpos2: Int = -1,
    var tradeability: Boolean = false,
    var members: Boolean = false,
    var multiStackSize: Int = -1,
    var maleEquip1: Int = -1,
    var femaleEquip1: Int = -1,
    var maleEquip2: Int = -1,
    var femaleEquip2: Int = -1,
    var wearpos3: Int = -1,
    var groundOptions: Array<String?> = arrayOf(null, null, "Take", null, null),
    var inventoryOptions: Array<String?> = arrayOf(null, null, null, null, "Drop"),
    var recolorSrc: ShortArray? = null,
    var recolorDst: ShortArray? = null,
    var retextureSrc: ShortArray? = null,
    var retextureDst: ShortArray? = null,
    var recolorDstPalette: ByteArray? = null,
    var recolorDstIndices: ByteArray? = null,
    var nameColor: Int = 0,
    var hasCustomNameColor: Boolean = false,
    var retextureDstIndices: ByteArray? = null,
    var stockmarket: Boolean = false,
    var stockmarketLimit: Int = 0,
    var manEquip3: Int = -1,
    var femaleEquip3: Int = -1,
    var manHead: Int = 0,
    var manHead2: Int = 0,
    var womanHead: Int = 0,
    var womanHead2: Int = 0,
    var category: Int = -1,
    var zAngle2D: Int = 0,
    var dummyItem: Int = 0,
    var certLink: Int = -1,
    var certTemplate: Int = -1,
    var stackIds: IntArray? = null,
    var stackAmounts: IntArray? = null,
    var resizeX: Int = 128,
    var resizeY: Int = 128,
    var resizeZ: Int = 128,
    var ambient: Int = 0,
    var contrast: Int = 0,
    var team: Int = 0,
    var lentLink: Int = -1,
    var lentTemplate: Int = -1,
    var manWearXOffset: Int = 0,
    var manWearYOffset: Int = 0,
    var manWearZOffset: Int = 0,
    var womanWearXOffset: Int = 0,
    var womanWearYOffset: Int = 0,
    var womanWearZOffset: Int = 0,
    var groundCursorOp: Int = -1,
    var groundCursor: Int = -1,
    var cursor2op: Int = -1,
    var cursor2: Int = -1,
    var cursor1iop: Int = -1,
    var icursor1: Int = -1,
    var cursor2iop: Int = -1,
    var icursor2: Int = -1,
    var unknown131: String? = null,
    var quests: IntArray? = null,
    var pickSizeShift: Int = 0,
    var bindLink: Int = -1,
    var bindTemplate: Int = -1,
    var groundMenuCursors: IntArray? = null,
    var interfaceMenuCursors: IntArray? = null,
    var op156: Boolean = true,
    var randomizeGroundPos: Boolean = false,
    var shardLink: Int = -1,
    var shardTemplate: Int = -1,
    var shardCombineAmount: Int = 0,
    var shardName: String? = null,
    var bond: Boolean = false,
    var nxtBoolOp168: Boolean = true,
    var params: Int2ObjectOpenHashMap<Any> = Int2ObjectOpenHashMap(),
    var playerArmour: Int = 0,
    var meleeAttack: Int = 0,
    var meleeStrength: Int = 0,
    var meleeDefence: Int = 0,
    var magicAttack: Int = 0,
    var magicStrength: Int = 0,
    var magicDefence: Int = 0,
    var rangedAttack: Int = 0,
    var rangedStrength: Int = 0,
    var rangedDefence: Int = 0,
    var genericDefence: Int = 0

): DefaultStateChecker {
    companion object {
        public val DEFAULT = ObjDefinition(-1)
    }

    override fun isDefault(): Boolean = this == DEFAULT

    fun itemDiscard(language: String) :String {
        return when(language) {
            "de" -> "Ablegen"
            "fr" -> "Jeter"
            "pt" -> "Descartar"
            "es" -> "Descartar"
            else
            -> "Discard"
        }
    }
    fun itemCombine(language: String) :String {
        return when(language) {
            "de" -> "Kombinieren"
            "fr" -> "Combiner"
            "pt" -> "Combinar"
            "es" -> "Combinar"
            else
            -> "Combine"
        }
    }

    fun itemDrop(language: String) :String {
        return when(language) {
            "de" -> "Fallen lassen"
            "fr" -> "Poser"
            "pt" -> "Largar"
            "es" -> "Dejar"
            else
            -> "Drop"
        }
    }

    fun setup_derived_object_cert(template: ObjDefinition, link: ObjDefinition, language: String) {
        setup_derived_object(DerivedObjType.CERT, template, link, null, language)
    }

    fun setup_derived_object_lent(template: ObjDefinition, link: ObjDefinition, language: String) {
        setup_derived_object(DerivedObjType.LENT, template, link, itemDiscard(language), language)
    }

    fun setup_derived_object_bought(template: ObjDefinition, link: ObjDefinition, language: String) {
        setup_derived_object(DerivedObjType.BOUGHT, template, link, itemDiscard(language), language)
    }

    fun setup_derived_object_shard(template: ObjDefinition, link: ObjDefinition, language: String) {
        setup_derived_object(DerivedObjType.SHARD, template, link, itemDrop(language), language)
    }

    private fun setup_derived_object(
        type: DerivedObjType,
        template: ObjDefinition,
        link: ObjDefinition,
        drop_text: String?,
        language: String
    ) {
        baseModel = template.baseModel
        zoom2D = template.zoom2D
        xAngle2D = template.xAngle2D
        yAngle2D = template.yAngle2D
        zAngle2D = template.zAngle2D
        xOffset2D = template.xOffset2D
        yOffset2D = template.yOffset2D
        val look: ObjDefinition = if (type === DerivedObjType.CERT) template else link
        recolorSrc = look.recolorSrc
        recolorDst = look.recolorDst
        recolorDstPalette = look.recolorDstPalette
        retextureSrc = look.retextureSrc
        retextureDst = look.retextureDst
        name = link.name
        members = link.members
        if (DerivedObjType.CERT === type) {
            cost = link.cost
            stackability = ObjStackability.ALWAYS
            tradeability = if (link.bond) {
                false
            } else {
                link.tradeability
            }
        } else if (type === DerivedObjType.SHARD) {
            name = link.shardName
            cost = floor(link.cost / link.shardCombineAmount.toDouble()).toInt()
            stackability = ObjStackability.ALWAYS
            stockmarket = link.stockmarket
            tradeability = link.tradeability
            category = template.category
            groundMenuCursors = template.groundMenuCursors
            interfaceMenuCursors = template.interfaceMenuCursors
            inventoryOptions = arrayOfNulls<String>(5)
            inventoryOptions[0] = itemCombine(language)
            inventoryOptions[4] = drop_text
        } else {
            cost = 0
            stackability = link.stackability
            tradeability = false
            wearpos = link.wearpos
            wearpos2 = link.wearpos2
            wearpos3 = link.wearpos3
            maleEquip1 = link.maleEquip1
            maleEquip2 = link.maleEquip2
            manEquip3 = link.manEquip3
            femaleEquip1 = link.femaleEquip1
            femaleEquip2 = link.femaleEquip2
            femaleEquip3 = link.femaleEquip3
            manWearXOffset = link.manWearXOffset
            womanWearXOffset = link.womanWearXOffset
            manWearYOffset = link.manWearYOffset
            womanWearYOffset = link.womanWearYOffset
            manWearZOffset = link.manWearZOffset
            womanWearZOffset = link.womanWearZOffset
            manHead = link.manHead
            manHead2 = link.manHead2
            womanHead = link.womanHead
            womanHead2 = link.womanHead2
            category = link.category
            team = link.team
            groundOptions = link.groundOptions
            params = link.params
            inventoryOptions = arrayOfNulls<String>(5)
            if (link.inventoryOptions != null) {
                for (int_0 in 0..3) {
                    inventoryOptions[int_0] = link.inventoryOptions[int_0]
                }
            }
            inventoryOptions[4] = itemDrop(language)
            nxtBoolOp168 = false
        }
    }
}