package com.brilliantzhao.andframework.deeplink;

import com.airbnb.deeplinkdispatch.DeepLinkModule;

/**
 * description:
 * Optional: If your Android application contains multiple modules (eg. separated Android library projects),
 * you'll want to add one @DeepLinkModule class for every module in your application, so that
 * DeepLinkDispatch can collect all your annotations in one "loader" class per module:
 * Date: 2018/2/7 16:08
 * User: BrilliantZhao
 */

/**
 * This will generate a LibraryDeepLinkModuleLoader class
 */
@DeepLinkModule
public class LibraryDeepLinkModule {
}
