package com.opennxt.resources

enum class Js5ConfigGroup (
    val id: Int,
    val groupName: String,
    private val groupSizeInBits: Int = 0,
    val isServerSided: Boolean = false
) {
    FLUTYPE(1, ".flu"),
    IDENTIKIT(3, ".idk"),
    FLOTYPE(4, ".flo"),
    INVTYPE(5, ".inv"),
    LOCTYPE(6, ".loc", 8),
    ENUMTYPE(8, ".enum", 8),
    NPCTYPE(9, ".npc", 7),
    OBJTYPE(10, ".obj", 8),
    PARAMTYPE(11, ".param"),
    SEQTYPE(12, ".seq", 7),
    SPOTTYPE(13, ".spotanim", 8),
    AREATYPE(18, ".area", true),
    STRUCTTYPE(26, ".struct", 5),
    CHATPHRASETYPE(27, ".chatphrase", true),
    CHATCATTYPE(28, ".chatcat", true),
    SKYBOX(29, ".skybox"),
    SUN(30, ".sun"),
    LIGHTTYPE(31, ".light"),
    BASTYPE(32, ".bas"),
    CURSORTYPE(33, ".cursor"),
    MSITYPE(34, ".msi"),
    QUEST(35, ".quest"),
    MELTYPE(36, ".mel"),
    DBTABLETYPE(40, ".dbtable"),
    DBROWTYPE(41, ".dbrow"),
    HITMARKTYPE(46, ".hitmark"),
    ITEMCODETYPE(48, ".itemcode", true),
    CATEGORIES(49, ".category", true),
    VAR_PLAYER(60, ".var_player"),
    VAR_NPC(61, ".var_npc"),
    VAR_CLIENT(62, ".var_client"),
    VAR_WORLD(63, ".var_world"),
    VAR_REGION(64, ".var_region"),
    VAR_OBJECT(65, ".var_object"),
    VAR_CLAN(66, ".var_clan"),
    VAR_CLAN_SETTING(67, ".var_clan_setting"),
    VAR_EXCHANGE(68, ".var_68_unknown"),
    VAR_BIT(69, ".var_bit"),
    GAMELOGEVENT(70, ".gamelogevent", true),
    HEADBARTYPE(72, ".headbar"),
    VAR_GLOBAL(75, ".var_global"),
    WATERTYPE(76, ".water"),
    SEQGROUPTYPE(77, ".seq_group"),
    VAR_PLAYER_GROUP(80, ".var_player_group");

    val groupSize: Int
        get() = 1 shl groupSizeInBits

    constructor(id: Int, name: String, server: Boolean) : this(id, name, 0, server) {}

    fun getClientFileId(var1: Int): Int {
        return var1 and (1 shl groupSizeInBits) - 1
    }

    fun getClientGroupId(var1: Int): Int {
        return var1 ushr groupSizeInBits
    }

    companion object {
        val JS5_CONFIGSET: Array<Js5ConfigGroup> = values()
    }
}