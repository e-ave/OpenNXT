package com.opennxt.resources


enum class Js5Archive(
    val archiveId: Int,
    val archiveName: String,
    val translated: Boolean,
    val runetek7: Boolean,
    val streamed: Boolean
) {
    JS5_ANIMS(0, "client.anims.js5", false, false, false),
    JS5_BASES(1, "client.bases.js5", false, false, false),
    JS5_CONFIG(2, "client.config.js5", true, false, false),
    JS5_INTERFACES(3, "client.interfaces.js5", true, false, false),
    JS5_SYNTH(4, "client.synths.js5", false, false, false),
    JS5_MAPS(5, "client.mapsv2.js5", false, false, false),
    JS5_MUSIC(6, "client.music.js5", false, false, false),//songs
    JS5_MODELS(7, "client.models.js5", false, false, false),
    JS5_SPRITES(8, "client.sprites.js5", false, false, false),
    JS5_TEXTURES(9, "client.textures.js5", false, false, false),
    JS5_BINARY(10, "client.binary.js5", false, false, false),
    JS5_JINGLES(11, "client.jingles.js5", false, false, false),
    JS5_CLIENTSCRIPTS(12, "client.scripts.js5", true, false, false),
    JS5_FONTMETRICS(13, "client.fontmetrics.js5", false, false, false),
    JS5_VORBIS(14, "client.vorbis", false, false, false),
    JS5_SPEECH(14, "client.speech.js5", false, false, false),//was samples
    JS5_PATCHES(15, "client.patches.js5", false, false, false),
    JS5_CONFIG_LOC(16, "client.loc.config.js5", true, false, false),
    JS5_CONFIG_ENUM(17, "client.enum.config.js5", true, false, false),
    JS5_CONFIG_NPC(18, "client.npc.config.js5", true, false, false),
    JS5_CONFIG_OBJ(19, "client.obj.config.js5", true, false, false),
    JS5_CONFIG_SEQ(20, "client.seq.config.js5", true, false, false),
    JS5_CONFIG_SPOT(21, "client.spot.config.js5", true, false, false),
    JS5_CONFIG_STRUCT(22, "client.struct.config.js5", true, false, false),
    JS5_WORLDMAPDATA(23, "client.worldmap.js5", true, false, false),
    JS5_QUICKCHAT(24, "client.quickchat.js5", true, false, false),
    JS5_QUICKCHAT_GLOBAL(25, "client.global.quickchat.js5", true, false, false),
    JS5_MATERIALS(26, "client.materials.js5", false, false, false),
    JS5_CONFIG_PARTICLE(27, "client.particles.js5", false, false, false),
    JS5_DEFAULTS(28, "client.defaults.js5", false, false, false),
    JS5_CONFIG_BILLBOARD(29, "client.billboards.js5", false, false, false),
    JS5_DLLS(30, "client.dlls.js5", false, false, false),
    JS5_SHADERS(31, "client.shaders.js5", false, false, false),
    JS5_LOADING_SPRITES(32, "client.loadingsprites.js5", false, false, false),
    JS5_LOADING_SCREENS(33, "client.loadingscreens.js5", true, false, false),
    JS5_LOADING_SPRITES_RAW(34, "client.loadingspritesraw.js5", false, false, false),
    JS5_CUTSCENES(35, "client.cutscenes.js5", true, false, false),
    JS5_BROWSERADS(36, "client.adverts.js5", false, false, false),
    JS5_TEXTURES_HDR(37, "client.textures.hdr.js5", false, false, false),
    JS5_AUDIOSTREAMS(40, "client.audiostreams", false, false, true),
    JS5_WORLDMAPAREADATA(41, "client.worldmapareas.js5", false, false, false),
    JS5_WORLDMAPLABELS(42, "client.worldmaplabels.js5", false, false, false),
    JS5_TEXTURES_DIFFUSE_PNG(43, "client.textures.diffuse.png.js5", false, false, false),
    JS5_TEXTURES_HDR_PNG(44, "client.textures.hdr.png.js5", false, false, false),
    JS5_TEXTURES_DIFFUSE_DXT(45, "client.textures.diffuse.dxt.js5", false, false, false),
    JS5_TEXTURES_HDR_PNG_MIPPED(46, "client.textures.hdr.png.mipped.js5", false, false, false),
    JS5_MODELSRT7(47, "client.modelsrt7.js5", false, true, false),
    JS5_ANIMSRT7(48, "client.animsrt7.js5", false, true, false),
    JS5_DBTABLEINDEX(49, "client.dbtableindex.js5", true, true, false),
    JS5_TEXTURES_DXT(52, "client.textures.dxt.js5", false, true, false),
    JS5_TEXTURES_PNG(53, "client.textures.png.js5", false, true, false),
    JS5_TEXTURES_PNG_MIPPED(54, "client.textures.png.mipped.js5", false, true, false),
    JS5_TEXTURES_ETC(55, "client.textures.etc.js5", false, true, false),
    JS5_ANIMS_KEYFRAMES(56, "client.anims.keyframes.js5", false, false, false),
    JS5_CONFIG_ACHIEVEMENT(57, "client.achievement.config.js5", true, false, false),
    JS5_FONTMETRICSRT7(58, "client.fontmetricsv2.js5", false, true, false),
    JS5_TYPEFONTS(59, "client.typefonts.js5", false, true, false),
    JS5_STYLESHEETS(60, "client.stylesheets.config.js5", false, true, false),
    JS5_PARTICLESRT7(61, "client.particlesrt7.js5", false, true, false),
    JS5_UIANIM(62, "client.uianim.js5", false, true, false),// guessed based on the strings in the data and recent cs2 name leak
    JS5_EMPTY1(63,  "client.empty1.js5", false, true, false),
    JS5_EMPTY2(64,  "client.empty2.js5", false, true, false),
    JS5_UNKNOWN_CONFIG(65, "client.unknown.config.js5", false, true, false),
    JS5_CUTSCENE2D(66, "client.cutscene2d.js5", false, true, false);

    companion object {
        val JS5_ARCHIVESET = values()
        const val MINIMUM_SCALE_NON_HIGHEST_PRIORITY = 0x3e800000
        val requiredArrayLength: Int by lazy {
            JS5_ARCHIVESET.maxOf { it.archiveId }.plus(1)
        }
    }
}
