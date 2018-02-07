package com.brilliantzhao.andframework.deeplink;

import com.airbnb.deeplinkdispatch.DeepLinkModule;

/**
 * description:
 * Create your deep link module(s) (new on DeepLinkDispatch v3). For every class you annotate with
 * @DeepLinkModule, DeepLinkDispatch will generate a "Loader" class, which contains a registry of all your @DeepLink annotations.
 * Date: 2018/2/7 16:07
 * User: BrilliantZhao
 */

/**
 * This will generate a AppDeepLinkModuleLoader class
 */
@DeepLinkModule
public class AppDeepLinkModule {
}
