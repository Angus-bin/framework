package com.romaway.android.phone.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;

import com.romaway.android.phone.R;
import com.romaway.commons.log.Logger;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * Author: wenchao.song
 * Time: 2016-10-12.
 */
public class Util {

    /**
     * 获取未安装Apk的签名
     *
     * @param apkPath apk包的文件路径
     */
    public static String getApkSignature(Context context,String apkPath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageSign = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_SIGNATURES);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(packageSign.signatures[0].toByteArray());
            byte[] digest = md.digest();

            String toRet = "";
            for (int i = 0; i < digest.length; i++) {
                if (i != 0) {
                    toRet += ":";
                }
                int b = digest[i] & 0xff;
                String hex = Integer.toHexString(b);
                if (hex.length() == 1) {
                    toRet += "0";
                }
                toRet += hex;
            }
            Logger.d("md5Util","下载的apk的 md5: " + toRet.toUpperCase());
            return toRet.toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取当前 app 的签名
     * @param manager manager
     * @param packagename app 包名
     */
    public static String getAppInfo(PackageManager manager, String packagename) {
        try {
            PackageInfo packageInfo = manager.getPackageInfo(packagename, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sign.toByteArray());
            byte[] digest = md.digest();
            Logger.d("md5Util","当前app的 md5: " + toHexString(digest));
            return toHexString(digest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String toHexString(byte[] block) {

        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) {
                buf.append(":");
            }
        }
        return buf.toString();

    }

    private static void byte2hex(byte b, StringBuffer buf) {

        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);

    }

    /**
     * 根据公钥加密 md5 字符串
     * md5 格式为：1A:2B:33:CC:CD:44:55:EE:F6:F7:88:9F:F9:9F:F9:FF
     */
    public static String decode(Context context, String md5) {
        try {
            // 从文件中得到公钥
            InputStream inPublic = context.getResources().getAssets().open("public_key.pem");
            PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);
            // 加密
            byte[] encryptByte = RSAUtils.encryptData(md5.getBytes(), publicKey);

            Logger.d("md5Util","加密后的 md5: " + Base64Utils.encode(encryptByte));
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            return Base64Utils.encode(encryptByte);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据私钥解密 md5 字符串
     */
    public static String encode(Context context) {
        String encryptContent = context.getResources().getString(R.string.installAPK_md5);
        if (!TextUtils.isEmpty(encryptContent)) {
            try {
                // 从文件中得到私钥
                InputStream inPrivate = context.getResources().getAssets().open("private_key.pem");
                PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
                // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
                byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(encryptContent), privateKey);

                return new String(decryptByte);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getMessage(boolean flag){
        if (flag)
            return "您当前下载安装的软件已被篡改存在不安全隐患，为保证你的账户安全请重新下载安装，请拨打电话95587或当地营业部联系";
        else
            return "您当前下载的安装包已被篡改存在不安全隐患，为保证你的账户安全请不要安装，请拨打电话95587或与当地营业部联系";
    }
}
