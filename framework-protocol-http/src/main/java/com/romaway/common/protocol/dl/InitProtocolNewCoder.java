package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2017/9/11.
 */
public class InitProtocolNewCoder extends AProtocolCoder<InitProtocolNew> {
    @Override
    protected byte[] encode(InitProtocolNew protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(InitProtocolNew protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("InitProtocolNewCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("InitProtocolNewCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONObject jsonObject1 = new BaseJSONObject(DES3.decode(DES3.decodeHex(str)));
                    if (jsonObject1.has("site_url")) {
                        protocol.resp_site_ip = jsonObject1.getString("site_url");
                    }
                    if (jsonObject1.has("is_holiday")) {
                        protocol.resp_is_holiday = jsonObject1.getString("is_holiday");
                    }
                    if (jsonObject1.has("spread_img")) {
                        protocol.resp_spread_img = jsonObject1.getString("spread_img");
                    }
                    if (jsonObject1.has("spread_id")) {
                        protocol.resp_spread_id = jsonObject1.getString("spread_id");
                    }
                    if (jsonObject1.has("spread_url")) {
                        protocol.resp_spread_url = jsonObject1.getString("spread_url");
                    }
                    if (jsonObject1.has("device")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("device"));
                        if (jsonObject2.has("type")) {
                            protocol.resp_type = jsonObject2.getString("type");
                        }
                        if (jsonObject2.has("version")) {
                            protocol.resp_version = jsonObject2.getString("version");
                        }
                        if (jsonObject2.has("download_url")) {
                            protocol.resp_download_url = jsonObject2.getString("download_url");
                        }
                        if (jsonObject2.has("intro")) {
                            protocol.resp_intro = jsonObject2.getString("intro");
                        }
                        if (jsonObject2.has("update_type")) {
                            protocol.resp_update_type = jsonObject2.getString("update_type");
                        }
                    }
                    if (jsonObject1.has("data")) {
                        BaseJSONObject jsonObject2 = new BaseJSONObject(jsonObject1.getString("data"));
                        if (jsonObject2.has("index")) {
                            BaseJSONObject jsonObject3 = new BaseJSONObject(jsonObject2.getString("index"));
                            if (jsonObject3.has("jpgpc")) {
                                BaseJSONObject jsonObject4 = new BaseJSONObject(jsonObject3.getString("jpgpc"));
                                if (jsonObject4.has("id")) {
                                    protocol.resp_jpgpc_id = jsonObject4.getString("id");
                                }
                            }
                            if (jsonObject3.has("ztdj")) {
                                BaseJSONObject jsonObject4 = new BaseJSONObject(jsonObject3.getString("ztdj"));
                                if (jsonObject4.has("id")) {
                                    protocol.resp_ztdj_id = jsonObject4.getString("id");
                                }
                            }
                            if (jsonObject3.has("aijqr")) {
                                BaseJSONObject jsonObject4 = new BaseJSONObject(jsonObject3.getString("aijqr"));
                                if (jsonObject4.has("id")) {
                                    protocol.resp_ai_id = jsonObject4.getString("id");
                                }
                            }
                            if (jsonObject3.has("menu")) {
                                BaseJSONArray jsonArray = new BaseJSONArray(jsonObject3.getString("menu"));
                                protocol.resp_menu = new String[jsonArray.length()][3];
                                protocol.resp_menu_json = jsonArray.toString();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    BaseJSONObject jsonObject4 = jsonArray.getJSONObject(i);
//                                    protocol.resp_menu_json[i] = jsonObject4.toString();
                                    if (jsonObject4.has("title")) {
                                        protocol.resp_menu[i][0] = jsonObject4.getString("title");
                                    }
                                    if (jsonObject4.has("pic_url")) {
                                        protocol.resp_menu[i][1] = jsonObject4.getString("pic_url");
                                    }
                                    if (jsonObject4.has("jump_url")) {
                                        protocol.resp_menu[i][2] = jsonObject4.getString("jump_url");
                                    }
                                }
                            }
                        }
                    }
                    if (jsonObject1.has("qidong_add")) {
                        Logger.d("init_info", "qidong_add");
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject1.getString("qidong_add"));
                        if (jsonArray != null) {
                            protocol.resp_advtInfo = new String[jsonArray.length()][3];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BaseJSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                if (jsonObject2.has("title_pic")) {
                                    Logger.d("init_info", "title_pic = " + jsonObject2.getString("title_pic"));
                                    protocol.resp_advtInfo[i][0] = jsonObject2.getString("title_pic");
                                }
                                if (jsonObject2.has("url")) {
                                    Logger.d("init_info", "url = " + jsonObject2.getString("url"));
                                    protocol.resp_advtInfo[i][1] = jsonObject2.getString("url");
                                }
                                if (jsonObject2.has("title_pic_2")) {
                                    Logger.d("init_info", "title_pic_2 = " + jsonObject2.getString("title_pic_2"));
                                    protocol.resp_advtInfo[i][2] = jsonObject2.getString("title_pic_2");
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
