package com.Sairaa.onewel.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import com.Sairaa.onewel.R;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_prferance), Context.MODE_PRIVATE);
    }


    public void writePromoterName(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.PromoterName), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readPromoterName() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.PromoterName), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writePromoterPhone(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.PromoterPhone), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readPromoterPhone() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.PromoterPhone), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writePromoterREF_num(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.PromoterREF_num), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readPromoterREF_num() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.PromoterREF_num), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writePromoterAddress(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.PromoterAddress), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readPromoterAddress() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.PromoterAddress), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writePromoterUPI_num(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.PromoterUPI_num), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readPromoterUPI_num() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.PromoterUPI_num), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writePromoterLat(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.PromoterLat), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readPromoterLat() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.PromoterLat), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writePromoterLon(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.PromoterLon), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readPromoterLon() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.PromoterLon), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeSigned_image(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.Signed_image), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readSigned_image() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.Signed_image), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserName(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserName), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserName() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserName), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserPhone(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserPhone), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserPhone() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserPhone), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserRef_num(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserRef_num), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserRef_num() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserRef_num), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserShopType(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserShopType), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserShopType() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserShopType), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserAddress(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserAddress), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserAddress() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserAddress), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserLandmark(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserLandmark), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserLandmark() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserLandmark), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserFromTime(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserFromTime), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserFromTime() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserFromTime), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserToTime(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserToTime), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserToTime() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserToTime), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserShopDesc(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserShopDesc), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserShopDesc() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserShopDesc), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserLat(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserLat), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserLat() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserLat), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserLon(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserLon), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserLon() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserLon), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeAdvertiserImage(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.AdvertiserImage), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readAdvertiserImage() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.AdvertiserImage), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeCustomer_name(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.Customer_name), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readCustomer_name() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.Customer_name), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeCustomer_phone(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.Customer_phone), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readCustomer_phone() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.Customer_phone), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeCustomer_ref(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.Customer_ref), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readCustomer_ref() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.Customer_ref), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeGoogle_image(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.Google_image), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readGoogle_image() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.Google_image), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

    public void writeGoogle_email(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.Google_email), String.valueOf(url));
        Log.i("SharedPreferenceWrite: ", "" + url);
        editor.apply();
    }

    public String readGoogle_email() {
        String url;
        url = sharedPreferences.getString(context.getResources().getString(R.string.Google_email), "no");
        Log.i("SharedPreferenceRead: ", "" + url);
        return url;
    }

}