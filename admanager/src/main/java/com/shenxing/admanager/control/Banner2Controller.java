package com.shenxing.admanager.control;

import android.app.Activity;
import android.graphics.Point;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.shenxing.admanager.doc.GDTDocument;

/**
 * Created by zhaobinsir
 * on 2020/7/22.
 * banner2.0 控制类
 */
public class Banner2Controller {

   private ViewGroup bannerContainer;
   private UnifiedBannerView bv;
   private int width,height;

    /**
     * 加载banner广告 不展示
     * @param context 当前activity
     * @param viewGroup 展示banner的父View
     * @param posId 广告id
     * @param listener 回调监听
     * @see GDTDocument listener解释 -》133colum
     */
   public UnifiedBannerView loadBanner(@NonNull Activity context, @NonNull ViewGroup viewGroup, @NonNull String posId, @NonNull UnifiedBannerADListener listener){
       initbanner(context, viewGroup, posId, listener);
       return bv;
   }

   //解释同上 加载banner广告，展示
    public void showBanner(@NonNull Activity context, @NonNull ViewGroup viewGroup, @NonNull String posId, @NonNull UnifiedBannerADListener listener){
        initbanner(context, viewGroup, posId, listener);
        bv.loadAD();
    }

    private void initbanner(@NonNull Activity context, @NonNull ViewGroup viewGroup, @NonNull String posId, @NonNull UnifiedBannerADListener listener) {
        if (bv != null && bannerContainer != null) {
            bannerContainer.removeView(bv);
            bv.destroy();
        }
        bannerContainer = viewGroup;
        bv = new UnifiedBannerView(context, posId, listener);
        bv.setRefresh(30);//默认30s刷新
        // 不需要传递tags使用下面构造函数
        // this.bv = new UnifiedBannerView(this, Constants.APPID, posId, this);
        bannerContainer.addView(bv, getWHParams()!=null?getWHParams():getUnifiedBannerLayoutParams(context));
    }

    /**
     * banner2.0规定banner宽高比应该为6.4:1 , 开发者可自行设置符合规定宽高比的具体宽度和高度值
     *
     * @return
     */
    private FrameLayout.LayoutParams getUnifiedBannerLayoutParams(Activity context) {
        if (bannerContainer == null) {
            return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        }
        Point screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(screenSize);
        return new FrameLayout.LayoutParams(screenSize.x, Math.round(screenSize.x / 6.4F));
    }

    //自定义宽高 在showbanner或loadbanner之前调用
    public void setBannerSize(int width,int height){
        this.height=height;
        this.width=width;
    }

    private FrameLayout.LayoutParams getWHParams(){
        if (height==0&&width==0) {
            return null;
        }
        return new FrameLayout.LayoutParams(width,height);
    }

    public void closeBanner(){
        if (bannerContainer != null) {
            bannerContainer.removeAllViews();
        }
        if (bv != null) {
            bv.destroy();
            bv = null;
        }
    }

}
