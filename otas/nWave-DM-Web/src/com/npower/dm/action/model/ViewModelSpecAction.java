/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/model/ViewModelSpecAction.java,v 1.9 2008/06/05 10:34:36 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2008/06/05 10:34:36 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.action.model;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/06/05 10:34:36 $
 */
public class ViewModelSpecAction extends BaseDispatchAction {

  private static Map<String, String> categoryMap = null;

  private static List<String>        categories  = null;
  static {
    categoryMap = new java.util.LinkedHashMap<String, String>();
    categories = new ArrayList<String>();

    categories.add("product_info");
    categories.add("wml_ui");
    categories.add("chtml_ui");
    categories.add("xhtml_ui");
    categories.add("markup");
    categories.add("cache");
    categories.add("display");
    categories.add("image_format");
    categories.add("bugs");
    categories.add("wta");
    categories.add("security");
    categories.add("storage");
    categories.add("object_download");
    categories.add("drm");
    categories.add("streaming");
    categories.add("wap_push");
    categories.add("j2me");
    categories.add("mms");
    categories.add("sms");
    categories.add("sound_format");
    categories.add("flash_lite");
    categories.add("other");

    categoryMap.put("brand_name", "product_info");
    categoryMap.put("model_name", "product_info");
    categoryMap.put("unique", "product_info");
    categoryMap.put("ununiqueness_handler", "product_info");
    categoryMap.put("is_wireless_device", "product_info");
    categoryMap.put("device_claims_web_support", "product_info");
    categoryMap.put("has_pointing_device", "product_info");
    categoryMap.put("has_qwerty_keyboard", "product_info");
    categoryMap.put("can_skip_aligned_link_row", "product_info");
    categoryMap.put("uaprof", "product_info");
    categoryMap.put("uaprof2", "product_info");
    categoryMap.put("uaprof3", "product_info");
    categoryMap.put("nokia_series", "product_info");
    categoryMap.put("nokia_edition", "product_info");
    categoryMap.put("device_os", "product_info");
    categoryMap.put("proportional_font", "wml_ui");
    categoryMap.put("built_in_back_button_support", "wml_ui");
    categoryMap.put("card_title_support", "wml_ui");
    categoryMap.put("softkey_support", "wml_ui");
    categoryMap.put("table_support", "wml_ui");
    categoryMap.put("numbered_menus", "wml_ui");
    categoryMap.put("menu_with_select_element_recommended", "wml_ui");
    categoryMap.put("menu_with_list_of_links_recommended", "wml_ui");
    categoryMap.put("icons_on_menu_items_support", "wml_ui");
    categoryMap.put("break_list_of_links_with_br_element_recommended", "wml_ui");
    categoryMap.put("access_key_support", "wml_ui");
    categoryMap.put("wrap_mode_support", "wml_ui");
    categoryMap.put("times_square_mode_support", "wml_ui");
    categoryMap.put("deck_prefetch_support", "wml_ui");
    categoryMap.put("elective_forms_recommended", "wml_ui");
    categoryMap.put("wizards_recommended", "wml_ui");
    categoryMap.put("image_as_link_support", "wml_ui");
    categoryMap.put("insert_br_element_after_widget_recommended", "wml_ui");
    categoryMap.put("wml_can_display_images_and_text_on_same_line", "wml_ui");
    categoryMap.put("wml_displays_image_in_center", "wml_ui");
    categoryMap.put("opwv_wml_extensions_support", "wml_ui");
    categoryMap.put("wml_make_phone_call_string", "wml_ui");
    categoryMap.put("chtml_display_accesskey", "chtml_ui");
    categoryMap.put("emoji", "chtml_ui");
    categoryMap.put("chtml_can_display_images_and_text_on_same_line", "chtml_ui");
    categoryMap.put("chtml_displays_image_in_center", "chtml_ui");
    categoryMap.put("imode_region", "chtml_ui");
    categoryMap.put("chtml_make_phone_call_string", "chtml_ui");
    categoryMap.put("chtml_table_support", "chtml_ui");
    categoryMap.put("xhtml_honors_bgcolor", "xhtml_ui");
    categoryMap.put("xhtml_supports_forms_in_table", "xhtml_ui");
    categoryMap.put("xhtml_support_wml2_namespace", "xhtml_ui");
    categoryMap.put("xhtml_autoexpand_select", "xhtml_ui");
    categoryMap.put("xhtml_select_as_dropdown", "xhtml_ui");
    categoryMap.put("xhtml_select_as_radiobutton", "xhtml_ui");
    categoryMap.put("xhtml_select_as_popup", "xhtml_ui");
    categoryMap.put("xhtml_display_accesskey", "xhtml_ui");
    categoryMap.put("xhtml_supports_invisible_text", "xhtml_ui");
    categoryMap.put("xhtml_supports_inline_input", "xhtml_ui");
    categoryMap.put("xhtml_supports_monospace_font", "xhtml_ui");
    categoryMap.put("xhtml_supports_table_for_layout", "xhtml_ui");
    categoryMap.put("xhtml_supports_css_cell_table_coloring", "xhtml_ui");
    categoryMap.put("xhtml_format_as_css_property", "xhtml_ui");
    categoryMap.put("xhtml_format_as_attribute", "xhtml_ui");
    categoryMap.put("xhtml_nowrap_mode", "xhtml_ui");
    categoryMap.put("xhtml_marquee_as_css_property", "xhtml_ui");
    categoryMap.put("xhtml_readable_background_color1", "xhtml_ui");
    categoryMap.put("xhtml_readable_background_color2", "xhtml_ui");
    categoryMap.put("xhtml_allows_disabled_form_elements", "xhtml_ui");
    categoryMap.put("xhtml_document_title_support", "xhtml_ui");
    categoryMap.put("xhtml_preferred_charset", "xhtml_ui");
    categoryMap.put("opwv_xhtml_extensions_support", "xhtml_ui");
    categoryMap.put("xhtml_make_phone_call_string", "xhtml_ui");
    categoryMap.put("xhtmlmp_preferred_mime_type", "xhtml_ui");
    categoryMap.put("xhtml_table_support", "xhtml_ui");
    categoryMap.put("xhtml_supports_file_upload", "xhtml_ui");
    categoryMap.put("", "markup");
    categoryMap.put("xhtml_support_level", "markup");
    categoryMap.put("preferred_markup", "markup");
    categoryMap.put("wml_1_1", "markup");
    categoryMap.put("wml_1_2", "markup");
    categoryMap.put("wml_1_3", "markup");
    categoryMap.put("html_wi_w3_xhtmlbasic", "markup");
    categoryMap.put("html_wi_oma_xhtmlmp_1_0", "markup");
    categoryMap.put("html_wi_imode_html_1", "markup");
    categoryMap.put("html_wi_imode_html_2", "markup");
    categoryMap.put("html_wi_imode_html_3", "markup");
    categoryMap.put("html_wi_imode_html_4", "markup");
    categoryMap.put("html_wi_imode_html_5", "markup");
    categoryMap.put("html_wi_imode_htmlx_1", "markup");
    categoryMap.put("html_wi_imode_htmlx_1_1", "markup");
    categoryMap.put("html_wi_imode_compact_generic", "markup");
    categoryMap.put("html_web_3_2", "markup");
    categoryMap.put("html_web_4_0", "markup");
    categoryMap.put("voicexml", "markup");
    categoryMap.put("multipart_support", "markup");
    categoryMap.put("total_cache_disable_support", "cache");
    categoryMap.put("time_to_live_support", "cache");
    categoryMap.put("resolution_width", "display");
    categoryMap.put("resolution_height", "display");
    categoryMap.put("rows", "display");
    categoryMap.put("columns", "display");
    categoryMap.put("max_image_width", "display");
    categoryMap.put("max_image_height", "display");
    categoryMap.put("wbmp", "image_format");
    categoryMap.put("bmp", "image_format");
    categoryMap.put("epoc_bmp", "image_format");
    categoryMap.put("gif", "image_format");
    categoryMap.put("gif_animated", "image_format");
    categoryMap.put("jpg", "image_format");
    categoryMap.put("png", "image_format");
    categoryMap.put("tiff", "image_format");
    categoryMap.put("svgt_1_1", "image_format");
    categoryMap.put("svgt_1_1_plus", "image_format");
    categoryMap.put("greyscale", "image_format");
    categoryMap.put("colors", "image_format");
    categoryMap.put("post_method_support", "bugs");
    categoryMap.put("basic_authentication_support", "bugs");
    categoryMap.put("empty_option_value_support", "bugs");
    categoryMap.put("emptyok", "bugs");
    categoryMap.put("nokia_voice_call", "wta");
    categoryMap.put("wta_voice_call", "wta");
    categoryMap.put("wta_phonebook", "wta");
    categoryMap.put("wta_misc", "wta");
    categoryMap.put("wta_pdc", "wta");
    categoryMap.put("https_support", "security");
    categoryMap.put("https_detectable", "security");
    categoryMap.put("phone_id_provided", "security");
    categoryMap.put("max_deck_size", "storage");
    categoryMap.put("max_url_length_in_requests", "storage");
    categoryMap.put("max_url_length_homepage", "storage");
    categoryMap.put("max_url_length_bookmark", "storage");
    categoryMap.put("max_url_length_cached_page", "storage");
    categoryMap.put("max_no_of_connection_settings", "storage");
    categoryMap.put("max_no_of_bookmarks", "storage");
    categoryMap.put("max_length_of_username", "storage");
    categoryMap.put("max_length_of_password", "storage");
    categoryMap.put("max_object_size", "storage");
    categoryMap.put("downloadfun_support", "object_download");
    categoryMap.put("directdownload_support", "object_download");
    categoryMap.put("inline_support", "object_download");
    categoryMap.put("oma_support", "object_download");
    categoryMap.put("ringtone", "object_download");
    categoryMap.put("ringtone_midi_monophonic", "object_download");
    categoryMap.put("ringtone_midi_polyphonic", "object_download");
    categoryMap.put("ringtone_imelody", "object_download");
    categoryMap.put("ringtone_digiplug", "object_download");
    categoryMap.put("ringtone_compactmidi", "object_download");
    categoryMap.put("ringtone_mmf", "object_download");
    categoryMap.put("ringtone_rmf", "object_download");
    categoryMap.put("ringtone_xmf", "object_download");
    categoryMap.put("ringtone_amr", "object_download");
    categoryMap.put("ringtone_awb", "object_download");
    categoryMap.put("ringtone_aac", "object_download");
    categoryMap.put("ringtone_wav", "object_download");
    categoryMap.put("ringtone_mp3", "object_download");
    categoryMap.put("ringtone_spmidi", "object_download");
    categoryMap.put("ringtone_qcelp", "object_download");
    categoryMap.put("ringtone_voices", "object_download");
    categoryMap.put("ringtone_df_size_limit", "object_download");
    categoryMap.put("ringtone_directdownload_size_limit", "object_download");
    categoryMap.put("ringtone_inline_size_limit", "object_download");
    categoryMap.put("ringtone_oma_size_limit", "object_download");
    categoryMap.put("wallpaper", "object_download");
    categoryMap.put("wallpaper_max_width", "object_download");
    categoryMap.put("wallpaper_max_height", "object_download");
    categoryMap.put("wallpaper_preferred_width", "object_download");
    categoryMap.put("wallpaper_preferred_height", "object_download");
    categoryMap.put("wallpaper_resize", "object_download");
    categoryMap.put("wallpaper_wbmp", "object_download");
    categoryMap.put("wallpaper_bmp", "object_download");
    categoryMap.put("wallpaper_gif", "object_download");
    categoryMap.put("wallpaper_jpg", "object_download");
    categoryMap.put("wallpaper_png", "object_download");
    categoryMap.put("wallpaper_tiff", "object_download");
    categoryMap.put("wallpaper_greyscale", "object_download");
    categoryMap.put("wallpaper_colors", "object_download");
    categoryMap.put("wallpaper_df_size_limit", "object_download");
    categoryMap.put("wallpaper_directdownload_size_limit", "object_download");
    categoryMap.put("wallpaper_inline_size_limit", "object_download");
    categoryMap.put("wallpaper_oma_size_limit", "object_download");
    categoryMap.put("screensaver", "object_download");
    categoryMap.put("screensaver_max_width", "object_download");
    categoryMap.put("screensaver_max_height", "object_download");
    categoryMap.put("screensaver_preferred_width", "object_download");
    categoryMap.put("screensaver_preferred_height", "object_download");
    categoryMap.put("screensaver_resize", "object_download");
    categoryMap.put("screensaver_wbmp", "object_download");
    categoryMap.put("screensaver_bmp", "object_download");
    categoryMap.put("screensaver_gif", "object_download");
    categoryMap.put("screensaver_jpg", "object_download");
    categoryMap.put("screensaver_png", "object_download");
    categoryMap.put("screensaver_greyscale", "object_download");
    categoryMap.put("screensaver_colors", "object_download");
    categoryMap.put("screensaver_df_size_limit", "object_download");
    categoryMap.put("screensaver_directdownload_size_limit", "object_download");
    categoryMap.put("screensaver_inline_size_limit", "object_download");
    categoryMap.put("screensaver_oma_size_limit", "object_download");
    categoryMap.put("picture", "object_download");
    categoryMap.put("picture_max_width", "object_download");
    categoryMap.put("picture_max_height", "object_download");
    categoryMap.put("picture_preferred_width", "object_download");
    categoryMap.put("picture_preferred_height", "object_download");
    categoryMap.put("picture_resize", "object_download");
    categoryMap.put("picture_wbmp", "object_download");
    categoryMap.put("picture_bmp", "object_download");
    categoryMap.put("picture_gif", "object_download");
    categoryMap.put("picture_jpg", "object_download");
    categoryMap.put("picture_png", "object_download");
    categoryMap.put("picture_greyscale", "object_download");
    categoryMap.put("picture_colors", "object_download");
    categoryMap.put("picture_df_size_limit", "object_download");
    categoryMap.put("picture_directdownload_size_limit", "object_download");
    categoryMap.put("picture_inline_size_limit", "object_download");
    categoryMap.put("picture_oma_size_limit", "object_download");
    categoryMap.put("video", "object_download");
    categoryMap.put("video_real_media_8", "object_download");
    categoryMap.put("video_real_media_9", "object_download");
    categoryMap.put("video_real_media_10", "object_download");
    categoryMap.put("video_3gpp", "object_download");
    categoryMap.put("video_3gpp2", "object_download");
    categoryMap.put("video_mp4", "object_download");
    categoryMap.put("video_wmv", "object_download");
    categoryMap.put("video_mov", "object_download");
    categoryMap.put("video_max_frame_rate", "object_download");
    categoryMap.put("video_max_width", "object_download");
    categoryMap.put("video_max_height", "object_download");
    categoryMap.put("video_qcif", "object_download");
    categoryMap.put("video_sqcif", "object_download");
    categoryMap.put("video_preferred_width", "object_download");
    categoryMap.put("video_preferred_height", "object_download");
    categoryMap.put("video_df_size_limit", "object_download");
    categoryMap.put("video_directdownload_size_limit", "object_download");
    categoryMap.put("video_inline_size_limit", "object_download");
    categoryMap.put("video_oma_size_limit", "object_download");
    categoryMap.put("video_vcodec_h263_0", "object_download");
    categoryMap.put("video_vcodec_h263_3", "object_download");
    categoryMap.put("video_vcodec_mpeg4", "object_download");
    categoryMap.put("video_acodec_amr", "object_download");
    categoryMap.put("video_acodec_awb", "object_download");
    categoryMap.put("video_acodec_aac", "object_download");
    categoryMap.put("video_acodec_aac_ltp", "object_download");
    categoryMap.put("video_acodec_qcelp", "object_download");
    categoryMap.put("ringtone_3gpp", "object_download");
    categoryMap.put("oma_v_1_0_forwardlock", "drm");
    categoryMap.put("oma_v_1_0_combined_delivery", "drm");
    categoryMap.put("oma_v_1_0_separate_delivery", "drm");
    categoryMap.put("streaming_video", "streaming");
    categoryMap.put("streaming_real_media_8", "streaming");
    categoryMap.put("streaming_real_media_9", "streaming");
    categoryMap.put("streaming_real_media_10", "streaming");
    categoryMap.put("streaming_3gpp", "streaming");
    categoryMap.put("streaming_mp4", "streaming");
    categoryMap.put("streaming_wmv", "streaming");
    categoryMap.put("streaming_mov", "streaming");
    categoryMap.put("streaming_video_qcif", "streaming");
    categoryMap.put("streaming_video_qcif_max_width", "streaming");
    categoryMap.put("streaming_video_qcif_max_height", "streaming");
    categoryMap.put("streaming_video_sqcif", "streaming");
    categoryMap.put("streaming_video_sqcif_max_width", "streaming");
    categoryMap.put("streaming_video_sqcif_max_height", "streaming");
    categoryMap.put("streaming_video_max_bit_rate", "streaming");
    categoryMap.put("streaming_video_max_video_bit_rate", "streaming");
    categoryMap.put("streaming_video_min_video_bit_rate", "streaming");
    categoryMap.put("streaming_video_max_audio_bit_rate", "streaming");
    categoryMap.put("streaming_video_max_frame_rate", "streaming");
    categoryMap.put("streaming_video_size_limit", "streaming");
    categoryMap.put("streaming_video_vcodec_h263_0", "streaming");
    categoryMap.put("streaming_video_vcodec_h263_3", "streaming");
    categoryMap.put("streaming_video_vcodec_mpeg4", "streaming");
    categoryMap.put("streaming_video_acodec_amr", "streaming");
    categoryMap.put("streaming_video_acodec_awb", "streaming");
    categoryMap.put("streaming_video_acodec_aac", "streaming");
    categoryMap.put("streaming_video_acodec_aac_ltp", "streaming");
    categoryMap.put("wap_push_support", "wap_push");
    categoryMap.put("connectionless_service_indication", "wap_push");
    categoryMap.put("connectionless_service_load", "wap_push");
    categoryMap.put("connectionless_cache_operation", "wap_push");
    categoryMap.put("connectionoriented_unconfirmed_service_indication", "wap_push");
    categoryMap.put("connectionoriented_unconfirmed_service_load", "wap_push");
    categoryMap.put("connectionoriented_unconfirmed_cache_operation", "wap_push");
    categoryMap.put("connectionoriented_confirmed_service_indication", "wap_push");
    categoryMap.put("connectionoriented_confirmed_service_load", "wap_push");
    categoryMap.put("connectionoriented_confirmed_cache_operation", "wap_push");
    categoryMap.put("utf8_support", "wap_push");
    categoryMap.put("ascii_support", "wap_push");
    categoryMap.put("iso8859_support", "wap_push");
    categoryMap.put("expiration_date", "wap_push");
    categoryMap.put("j2me_cldc_1_0", "j2me");
    categoryMap.put("j2me_cldc_1_1", "j2me");
    categoryMap.put("j2me_midp_1_0", "j2me");
    categoryMap.put("j2me_midp_2_0", "j2me");
    categoryMap.put("doja_1_0", "j2me");
    categoryMap.put("doja_1_5", "j2me");
    categoryMap.put("doja_2_0", "j2me");
    categoryMap.put("doja_2_1", "j2me");
    categoryMap.put("doja_2_2", "j2me");
    categoryMap.put("doja_3_0", "j2me");
    categoryMap.put("doja_3_5", "j2me");
    categoryMap.put("doja_4_0", "j2me");
    categoryMap.put("j2me_jtwi", "j2me");
    categoryMap.put("j2me_mmapi_1_0", "j2me");
    categoryMap.put("j2me_mmapi_1_1", "j2me");
    categoryMap.put("j2me_wmapi_1_0", "j2me");
    categoryMap.put("j2me_wmapi_1_1", "j2me");
    categoryMap.put("j2me_wmapi_2_0", "j2me");
    categoryMap.put("j2me_btapi", "j2me");
    categoryMap.put("j2me_3dapi", "j2me");
    categoryMap.put("j2me_loctapi", "j2me");
    categoryMap.put("j2me_nokia_ui", "j2me");
    categoryMap.put("j2me_motorola_lwt", "j2me");
    categoryMap.put("j2me_siemens_color_game", "j2me");
    categoryMap.put("j2me_siemens_extension", "j2me");
    categoryMap.put("j2me_heap_size", "j2me");
    categoryMap.put("j2me_max_jar_size", "j2me");
    categoryMap.put("j2me_storage_size", "j2me");
    categoryMap.put("j2me_max_record_store_size", "j2me");
    categoryMap.put("j2me_screen_width", "j2me");
    categoryMap.put("j2me_screen_height", "j2me");
    categoryMap.put("j2me_canvas_width", "j2me");
    categoryMap.put("j2me_canvas_height", "j2me");
    categoryMap.put("j2me_bits_per_pixel", "j2me");
    categoryMap.put("j2me_audio_capture_enabled", "j2me");
    categoryMap.put("j2me_video_capture_enabled", "j2me");
    categoryMap.put("j2me_photo_capture_enabled", "j2me");
    categoryMap.put("j2me_capture_image_formats", "j2me");
    categoryMap.put("j2me_http", "j2me");
    categoryMap.put("j2me_https", "j2me");
    categoryMap.put("j2me_socket", "j2me");
    categoryMap.put("j2me_udp", "j2me");
    categoryMap.put("j2me_serial", "j2me");
    categoryMap.put("j2me_gif", "j2me");
    categoryMap.put("j2me_gif89a", "j2me");
    categoryMap.put("j2me_jpg", "j2me");
    categoryMap.put("j2me_png", "j2me");
    categoryMap.put("j2me_bmp", "j2me");
    categoryMap.put("j2me_bmp3", "j2me");
    categoryMap.put("j2me_wbmp", "j2me");
    categoryMap.put("j2me_midi", "j2me");
    categoryMap.put("j2me_wav", "j2me");
    categoryMap.put("j2me_amr", "j2me");
    categoryMap.put("j2me_mp3", "j2me");
    categoryMap.put("j2me_mp4", "j2me");
    categoryMap.put("j2me_imelody", "j2me");
    categoryMap.put("j2me_rmf", "j2me");
    categoryMap.put("j2me_au", "j2me");
    categoryMap.put("j2me_aac", "j2me");
    categoryMap.put("j2me_realaudio", "j2me");
    categoryMap.put("j2me_xmf", "j2me");
    categoryMap.put("j2me_wma", "j2me");
    categoryMap.put("j2me_3gpp", "j2me");
    categoryMap.put("j2me_h263", "j2me");
    categoryMap.put("j2me_svgt", "j2me");
    categoryMap.put("j2me_mpeg4", "j2me");
    categoryMap.put("j2me_realvideo", "j2me");
    categoryMap.put("j2me_real8", "j2me");
    categoryMap.put("j2me_realmedia", "j2me");
    categoryMap.put("j2me_left_softkey_code", "j2me");
    categoryMap.put("j2me_right_softkey_code", "j2me");
    categoryMap.put("j2me_middle_softkey_code", "j2me");
    categoryMap.put("j2me_select_key_code", "j2me");
    categoryMap.put("j2me_return_key_code", "j2me");
    categoryMap.put("j2me_clear_key_code", "j2me");
    categoryMap.put("j2me_datefield_no_accepts_null_date", "j2me");
    categoryMap.put("j2me_datefield_broken", "j2me");
    categoryMap.put("receiver", "mms");
    categoryMap.put("sender", "mms");
    categoryMap.put("mms_max_size", "mms");
    categoryMap.put("mms_max_height", "mms");
    categoryMap.put("mms_max_width", "mms");
    categoryMap.put("built_in_recorder", "mms");
    categoryMap.put("built_in_camera", "mms");
    categoryMap.put("mms_jpeg_baseline", "mms");
    categoryMap.put("mms_jpeg_progressive", "mms");
    categoryMap.put("mms_gif_static", "mms");
    categoryMap.put("mms_gif_animated", "mms");
    categoryMap.put("mms_png", "mms");
    categoryMap.put("mms_bmp", "mms");
    categoryMap.put("mms_wbmp", "mms");
    categoryMap.put("mms_amr", "mms");
    categoryMap.put("mms_wav", "mms");
    categoryMap.put("mms_midi_monophonic", "mms");
    categoryMap.put("mms_midi_polyphonic", "mms");
    categoryMap.put("mms_midi_polyphonic_voices", "mms");
    categoryMap.put("mms_spmidi", "mms");
    categoryMap.put("mms_mmf", "mms");
    categoryMap.put("mms_mp3", "mms");
    categoryMap.put("mms_evrc", "mms");
    categoryMap.put("mms_qcelp", "mms");
    categoryMap.put("mms_ota_bitmap", "mms");
    categoryMap.put("mms_nokia_wallpaper", "mms");
    categoryMap.put("mms_nokia_operatorlogo", "mms");
    categoryMap.put("mms_nokia_3dscreensaver", "mms");
    categoryMap.put("mms_nokia_ringingtone", "mms");
    categoryMap.put("mms_rmf", "mms");
    categoryMap.put("mms_xmf", "mms");
    categoryMap.put("mms_symbian_install", "mms");
    categoryMap.put("mms_jar", "mms");
    categoryMap.put("mms_jad", "mms");
    categoryMap.put("mms_vcard", "mms");
    categoryMap.put("mms_vcalendar", "mms");
    categoryMap.put("mms_wml", "mms");
    categoryMap.put("mms_wbxml", "mms");
    categoryMap.put("mms_wmlc", "mms");
    categoryMap.put("mms_video", "mms");
    categoryMap.put("mms_mp4", "mms");
    categoryMap.put("mms_3gpp", "mms");
    categoryMap.put("mms_3gpp2", "mms");
    categoryMap.put("mms_max_frame_rate", "mms");
    categoryMap.put("nokiaring", "sms");
    categoryMap.put("picturemessage", "sms");
    categoryMap.put("operatorlogo", "sms");
    categoryMap.put("largeoperatorlogo", "sms");
    categoryMap.put("callericon", "sms");
    categoryMap.put("nokiavcard", "sms");
    categoryMap.put("nokiavcal", "sms");
    categoryMap.put("sckl_ringtone", "sms");
    categoryMap.put("sckl_operatorlogo", "sms");
    categoryMap.put("sckl_groupgraphic", "sms");
    categoryMap.put("sckl_vcard", "sms");
    categoryMap.put("sckl_vcalendar", "sms");
    categoryMap.put("text_imelody", "sms");
    categoryMap.put("ems", "sms");
    categoryMap.put("ems_variablesizedpictures", "sms");
    categoryMap.put("ems_imelody", "sms");
    categoryMap.put("ems_odi", "sms");
    categoryMap.put("ems_upi", "sms");
    categoryMap.put("ems_version", "sms");
    categoryMap.put("siemens_ota", "sms");
    categoryMap.put("siemens_logo_width", "sms");
    categoryMap.put("siemens_logo_height", "sms");
    categoryMap.put("siemens_screensaver_width", "sms");
    categoryMap.put("siemens_screensaver_height", "sms");
    categoryMap.put("gprtf", "sms");
    categoryMap.put("sagem_v1", "sms");
    categoryMap.put("sagem_v2", "sms");
    categoryMap.put("panasonic", "sms");
    categoryMap.put("wav", "sound_format");
    categoryMap.put("mmf", "sound_format");
    categoryMap.put("smf", "sound_format");
    categoryMap.put("mld", "sound_format");
    categoryMap.put("midi_monophonic", "sound_format");
    categoryMap.put("midi_polyphonic", "sound_format");
    categoryMap.put("sp_midi", "sound_format");
    categoryMap.put("rmf", "sound_format");
    categoryMap.put("xmf", "sound_format");
    categoryMap.put("compactmidi", "sound_format");
    categoryMap.put("digiplug", "sound_format");
    categoryMap.put("nokia_ringtone", "sound_format");
    categoryMap.put("imelody", "sound_format");
    categoryMap.put("au", "sound_format");
    categoryMap.put("amr", "sound_format");
    categoryMap.put("awb", "sound_format");
    categoryMap.put("aac", "sound_format");
    categoryMap.put("mp3", "sound_format");
    categoryMap.put("voices", "sound_format");
    categoryMap.put("qcelp", "sound_format");
    categoryMap.put("evrc", "sound_format");
    categoryMap.put("flash_lite_version", "flash_lite");
    categoryMap.put("fl_wallpaper", "flash_lite");
    categoryMap.put("fl_screensaver", "flash_lite");
    categoryMap.put("fl_standalone", "flash_lite");
    categoryMap.put("fl_browser", "flash_lite");
    categoryMap.put("fl_sub_lcd", "flash_lite");
  }

  public class ModelCapabilityItem implements Comparable<ModelCapabilityItem> {
    private int    index = 0;

    private String key   = null;

    private String name  = null;

    private String value = null;

    /**
     * 
     */
    public ModelCapabilityItem() {
      super();
    }

    /**
     * @param index
     * @param key
     * @param value
     */
    public ModelCapabilityItem(int index, String key, String value) {
      super();
      this.index = index;
      this.key = key;
      this.value = value;
    }

    /**
     * @return the index
     */
    public int getIndex() {
      return index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(int index) {
      this.index = index;
    }

    /**
     * @return the key
     */
    public String getKey() {
      return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
      this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
      return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(ModelCapabilityItem o) {
      return this.getKey().compareToIgnoreCase(o.getKey());
    }

    /**
     * @return the name
     */
    public String getName() {
      if (this.name == null) {
        this.name = convertName(this.key);
      }
      return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
      this.name = name;
    }

  }

  /**
   * 
   */
  private String convertName(String s) {
    String tmp = s.replace('_', ' ');
    return WordUtils.capitalize(tmp, new char[] { ' ' });
  }

  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getModelIcon(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    String modelID = request.getParameter("modelID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    Model model = modelBean.getModelByID(modelID);
    if (model != null) {
      response.setHeader("Content-Disposition", "inline;filename=\"" + modelID + ".jpg\";");
      response.setContentType("image/jpeg");
      OutputStream out = response.getOutputStream();
      byte[] bytes = model.getIconBinary();
      if (bytes != null && bytes.length > 0) {
        out.write(bytes);
        out.flush();
        return null;
      } else {
        return mapping.findForward("DefaultIcon");
      }
    } else {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not found model spec by model id: " + modelID);
      return null;
    }
  }

  /**
   * Return Model belongs to manufacturerID.
   * 
   * @param mapping
   * @param form
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  public ActionForward getModelCapabilityMatrix(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    String modelID = request.getParameter("modelID");
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    Model model = modelBean.getModelByID(modelID);
    if (model != null) {
      request.setAttribute("model", model);
      Set<ModelCharacter> characters = model.getCharacters();
      List<ModelCharacter> generalCharacters = new ArrayList<ModelCharacter>();
      List<ModelCharacter> sizeCharacters = new ArrayList<ModelCharacter>();
      List<ModelCharacter> displayCharacters = new ArrayList<ModelCharacter>();
      List<ModelCharacter> ringtonesCharacters = new ArrayList<ModelCharacter>();
      List<ModelCharacter> memoryCharacters = new ArrayList<ModelCharacter>();
      List<ModelCharacter> dataCharacters = new ArrayList<ModelCharacter>();
      List<ModelCharacter> featuresCharacters = new ArrayList<ModelCharacter>();
      List<ModelCharacter> batteryCharacters = new ArrayList<ModelCharacter>();
      for (ModelCharacter character : characters) {
        if (StringUtils.equals(character.getCategory(), "general")) {
          generalCharacters.add(character);
        } else if (StringUtils.equals(character.getCategory(), "size")) {
          sizeCharacters.add(character);
        } else if (StringUtils.equals(character.getCategory(), "display")) {
          displayCharacters.add(character);
        } else if (StringUtils.equals(character.getCategory(), "ringtones")) {
          ringtonesCharacters.add(character);
        } else if (StringUtils.equals(character.getCategory(), "memory")) {
          memoryCharacters.add(character);
        } else if (StringUtils.equals(character.getCategory(), "data")) {
          dataCharacters.add(character);
        } else if (StringUtils.equals(character.getCategory(), "features")) {
          featuresCharacters.add(character);
        } else if (StringUtils.equals(character.getCategory(), "battery")) {
          batteryCharacters.add(character);
        }
      }
      if (generalCharacters != null && generalCharacters.size() > 0) {
        request.setAttribute("generalCharacters", generalCharacters);
      }
      if (sizeCharacters != null && sizeCharacters.size() > 0) {
        request.setAttribute("sizeCharacters", sizeCharacters);
      }
      if (displayCharacters != null && displayCharacters.size() > 0) {
        request.setAttribute("displayCharacters", displayCharacters);
      }
      if (ringtonesCharacters != null && ringtonesCharacters.size() > 0) {
        request.setAttribute("ringtonesCharacters", ringtonesCharacters);
      }
      if (memoryCharacters != null && memoryCharacters.size() > 0) {
        request.setAttribute("memoryCharacters", memoryCharacters);
      }
      if (dataCharacters != null && dataCharacters.size() > 0) {
        request.setAttribute("dataCharacters", dataCharacters);
      }
      if (featuresCharacters != null && featuresCharacters.size() > 0) {
        request.setAttribute("featuresCharacters", featuresCharacters);
      }
      if (batteryCharacters != null && batteryCharacters.size() > 0) {
        request.setAttribute("batteryCharacters", batteryCharacters);
      }

      String announced = model.getCharacterValue("general", "announced");
      request.setAttribute("announced", announced);
      String os = model.getCharacterValue("general", "operating.system");
      request.setAttribute("os", os);
      String platform = model.getCharacterValue("general", "developer.platform");
      request.setAttribute("platform", platform);

      Map<String, String> capability = modelBean.getCapabilityMatrix(model);
      Map<String, Set<ModelCapabilityItem>> capabilityItems = new LinkedHashMap<String, Set<ModelCapabilityItem>>();
      for (String category : categories) {
        capabilityItems.put(convertName(category), new TreeSet<ModelCapabilityItem>());
      }
      int index = 1;
      for (String capaKey : capability.keySet()) {
        String capaValue = capability.get(capaKey);
        String category = categoryMap.get(capaKey);
        category = (category == null) ? "other" : category;
        Set<ModelCapabilityItem> itemsMap = capabilityItems.get(convertName(category));
        itemsMap.add(new ModelCapabilityItem(index, capaKey, capaValue));
        index++;
      }
      request.setAttribute("modelCapabilities", capabilityItems);

      // Model capabilities matrix
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      List<ProfileCategory> categories = templateBean.findProfileCategories("from ProfileCategoryEntity");
      request.setAttribute("categories", categories);
      ModelMatrixItem item = ModelMatrixItem.getModelMatrixItem(factory, model);
      request.setAttribute("matrix_item", item);
    }
    return mapping.findForward("capability4view");
  }

}
