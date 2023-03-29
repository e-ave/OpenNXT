package com.opennxt.resources.config.objs

enum class ObjTypeEncodingKey(val opcode: Int, val lastOpcode: Int = opcode) {
    EOF(0),
    MESH(1),
    NAME(2),
    BUFF(3),
    ZOOM2D(4),
    XAN2D(5),
    YAN2D(6),
    XOF2D(7),
    YOF2D(8),
    STACKABLE(11),
    COST(12),
    WEARPOS(13),
    WEARPOS2(14),
    TRADABLE(15),
    MEMBERS(16),
    MULTISTACKSIZE(18),
    MANWEAR(23),
    MANWEAR2(24),
    WOMANWEAR(25),
    WOMANWEAR2(26),
    WEARPOS3(27),
    OPS(30, 34),
    IOPS(35, 39),
    RECOL(40),
    RETEX(41),
    RECOL_PALETTE(42),
    MINIMENU_COL(43),
    RECOL_INDEX(44),
    RETEX_INDEX(45),
    STOCKMARKET(65),
    STOCKMARKET_LIMIT(69),
    MANWEAR3(78),
    WOMANWEAR3(79),
    MANHEAD(90),
    WOMANHEAD(91),
    MANHEAD2(92),
    WOMANHEAD2(93),
    CATEGORY(94),
    ZAN2D(95),
    DUMMYITEM(96),
    CERT_LINK(97),
    CERT_TEMPLATE(98),
    COUNT(100, 109),
    RESIZEX(110),
    RESIZEY(111),
    RESIZEZ(112),
    AMBIENT(113),
    CONTRAST(114),
    TEAM(115),
    LENT_LINK(121),
    LENT_TEMPLATE(122),
    MANWEAROF(125),
    WOMANWEAROF(126),
    CURSOR1(127),
    CURSOR2(128),
    ICURSOR1(129),
    ICURSOR2(130),
    UNKNOWN_NEW(131),
    QUESTS(132),
    PICKSIZESHIFT(134),
    BIND_LINK(139),
    BIND_TEMPLATE(140),
    CURSORS(142, 146),
    ICURSORS(150, 154),
    NXT_BOOL2(156),
    RANDOMPOS(157),
    SHARD_LINK(161),
    SHARD_TEMPLATE(162),
    SHARD_REQ(163),
    SHARD_NAME(164),
    STACKABLE_NEVER(165),
    BOND(167),
    NXT_BOOL(168),
    PARAMS(249);

    companion object {
        fun op(op: Int): ObjTypeEncodingKey {
            val codes = enumValues<ObjTypeEncodingKey>()
            for(code in codes) {
                if (code.lastOpcode == code.opcode && op == code.opcode) {
                    return code
                } else if (op in code.opcode .. code.lastOpcode) {
                    return code
                }
            }
            return EOF
        }
    }

}