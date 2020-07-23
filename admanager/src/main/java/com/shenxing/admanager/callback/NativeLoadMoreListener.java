package com.shenxing.admanager.callback;

import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/23.
 * 加载更多native
 */
public interface NativeLoadMoreListener<T> {

   void onAdLoad(List<T> list);

   void onNoAd(List<T> list);

}
