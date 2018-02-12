package com.brilliantzhao.baselibrary.webview

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.PopupMenu
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.brilliantzhao.baselibrary.R
import com.brilliantzhao.baselibrary.base.BaseBingingFragment
import com.brilliantzhao.baselibrary.constant.WEBVIEW_URL
import com.brilliantzhao.baselibrary.databinding.FragmentBaseWebviewBinding
import com.brilliantzhao.baselibrary.webview.client.MiddlewareChromeClient
import com.brilliantzhao.baselibrary.webview.client.MiddlewareWebViewClient
import com.brilliantzhao.baselibrary.webview.common.UIController
import com.google.gson.Gson
import com.just.agentweb.*
import com.just.agentweb.DownloadListener
import java.util.*

/**
 * description:基于AgentWeb封装的webview公共类,可以作为公用fragment使用
 * Date: 2018/2/7 14:17
 * User: BrilliantZhao
 */
class BaseWebViewFragment : BaseBingingFragment<FragmentBaseWebviewBinding>(), FragmentKeyDown {

    //##########################  custom variables start ##########################################

    private var mBackImageView: ImageView? = null

    private var mLineView: View? = null

    private var mTitleTextView: TextView? = null

    protected var mAgentWeb: AgentWeb? = null

    private var mPopupMenu: PopupMenu? = null

    private val mGson = Gson() //用于方便打印测试

    private var mMiddleWareWebClient: MiddlewareWebClientBase? = null

    private var mMiddleWareWebChrome: MiddlewareWebChromeBase? = null

    var webview_url = ""

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?,
                                   savedInstanceState: Bundle?): FragmentBaseWebviewBinding {
        return FragmentBaseWebviewBinding.inflate(inflater!!, container, false)
    }

    override fun initView(view: View) {
        webview_url = arguments?.getString(WEBVIEW_URL) ?: ""
        LogUtils.i(webview_url)

        (view.findViewById(R.id.iv_back) as ImageView).setOnClickListener(mOnClickListener)
        (view.findViewById(R.id.iv_finish) as ImageView).setOnClickListener(mOnClickListener)
        (view.findViewById(R.id.iv_more) as ImageView).setOnClickListener(mOnClickListener)
    }

    override fun initEvent() {
    }

    override fun initData() {
        initAgentWeb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            mAgentWeb?.getWebCreator()?.webView.setWebContentsDebuggingEnabled(true)
            WebView.setWebContentsDebuggingEnabled(true)
        }

        pageNavigator(View.GONE)

        //AgentWeb 4.0 开始，删除该类以及删除相关的API
//        DefaultMsgConfig.DownloadMsgConfig mDownloadMsgConfig = mAgentWeb.getDefaultMsgConfig().getDownloadMsgConfig();
        //  mDownloadMsgConfig.setCancel("放弃");  // 修改下载提示信息，这里可以语言切换
        //AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        mAgentWeb?.getWebCreator()?.webView?.overScrollMode = WebView.OVER_SCROLL_NEVER
        //mAgentWeb.getWebCreator().getWebView()  获取WebView .
    }

    override fun onLazyLoadOnce() {
        LogUtils.i(className + "-->onLazyLoadOnce")
    }

    override fun onVisibleToUser() {
        LogUtils.i(className + "-->onVisibleToUser")
    }

    override fun onInvisibleToUser() {
        LogUtils.i(className + "-->onInvisibleToUser")
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    companion object {
        fun newInstance(bundle: Bundle?): BaseWebViewFragment {
            val fragment = BaseWebViewFragment()
            if (bundle != null) {
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    fun initAgentWeb() {
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(view as LinearLayout, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .setIndicatorColorWithHeight(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(getSettings())//设置 AgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(UIController(activity)) //自定义UI  AgentWeb3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
                .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
                .setDownloadListener(mDownloadListener) //下载回调
                //                .openParallelDownload()// 4.0.0删除该api 打开并行下载 , 默认串行下载。
                //                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownScheme() //拦截找不到相关页面的Scheme AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(webview_url) //WebView载入该url地址的页面并显示。
    }

    fun getSettings(): AgentWebSettings<*> {
        return WebDefaultSettingsManager.getInstance()
    }

    protected var mPermissionInterceptor: PermissionInterceptor = PermissionInterceptor { url, permissions, action ->
        //AgentWeb 在触发某些敏感的 Action 时候会回调该方法， 比如定位触发 。
        //例如 https//:www.baidu.com 该 Url 需要定位权限， 返回false ，如果版本大于等于23 ， agentWeb 会动态申请权限 ，true 该Url对应页面请求定位失败。
        //该方法是每次都会优先触发的 ， 开发者可以做一些敏感权限拦截 。
        LogUtils.i("url:" + url + "  permission:" + mGson.toJson(permissions) + " action:" + action)
        false
    }

    private var mDownloadingService: DownloadingService? = null

    internal var mExtraService: AgentWebDownloader.ExtraService? = null

    /**
     * 修改 AgentWeb  4.0.0
     */
    protected var mDownloadListener: DownloadListener = object : DownloadListener.DownloadListenerAdapter() {

        /**
         *
         * @param url                下载链接
         * @param userAgent          userAgent
         * @param contentDisposition contentDisposition
         * @param mimetype           资源的媒体类型
         * @param contentLength      文件长度
         * @param extra              下载配置 ， 用户可以通过 Extra 修改下载icon ， 关闭进度条 ， 是否强制下载。
         * @return true 表示用户处理了该下载事件 ， false 交给 AgentWeb 下载
         */
        override fun start(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long, extra: AgentWebDownloader.Extra?): Boolean {
            //            extra.setOpenBreakPointDownload(false).setIcon(R.mipmap.app_logo);
            return false
        }

        /**
         *
         * @param url
         * @param downloadingService  开发者可以通过 DownloadingService#shutdownNow 终止下载
         */
        fun onBindService(url: String, downloadingService: DownloadingService) {
//            super.onBindService(url, downloadingService)
            mDownloadingService = downloadingService
        }

        fun onUnbindService(url: String, downloadingService: DownloadingService) {
//            super.onUnbindService(url, downloadingService)
            mDownloadingService = null
        }

        /**
         *
         * @param url  下载链接
         * @param loaded  已经下载的长度
         * @param length    文件的总大小
         * @param usedTime   耗时,单位ms
         * 注意该方法回调在子线程 ，线程名 AsyncTask #XX or AgentWeb # XX
         */
        fun progress(url: String, loaded: Long, length: Long, usedTime: Long) {
            val mProgress = (loaded / java.lang.Float.valueOf(length.toFloat())!! * 100).toInt()
            LogUtils.i("progress:" + mProgress)
//            super.progress(url, loaded, length, usedTime)
        }

        /**
         *
         * @param path 文件的绝对路径
         * @param url  下载地址
         * @param throwable    如果异常，返回给用户异常
         * @return true 表示用户处理了下载完成后续的事件 ，false 默认交给AgentWeb 处理
         */
        override fun result(path: String?, url: String?, throwable: Throwable?): Boolean {
            if (null == throwable) { //下载成功
                //do you work
            } else {//下载失败

            }
            return false // true  不会发出下载完成的通知 , 或者打开文件
        }
    }

    protected var mWebChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            //  super.onProgressChanged(view, newProgress);
            LogUtils.i("onProgressChanged:$newProgress  view:$view")
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            var title = title
            super.onReceivedTitle(view, title)
            if (mTitleTextView != null && !TextUtils.isEmpty(title))
                if (title.length > 10)
                    title = title.substring(0, 10) + "..."
            mTitleTextView?.setText(title)
        }
    }

    protected var mWebViewClient: WebViewClient = object : WebViewClient() {

        private val timer = HashMap<String, Long>()

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return shouldOverrideUrlLoading(view, request.url.toString() + "")
        }

        //
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            LogUtils.i("view:" + Gson().toJson(view.hitTestResult))
            LogUtils.i("mWebViewClient shouldOverrideUrlLoading:" + url)
            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            return if (url.startsWith("intent://") && url.contains("com.youku.phone")) true else false
            /*else if (isAlipay(view, url))   //1.2.5开始不用调用该方法了 ，只要引入支付宝sdk即可 ， DefaultWebClient 默认会处理相应url调起支付宝
                return true;*/
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            LogUtils.i("url:" + url + " onPageStarted  target:" + webview_url)
            timer[url] = System.currentTimeMillis()
            if (url == webview_url) {
                pageNavigator(View.GONE)
            } else {
                pageNavigator(View.VISIBLE)
            }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (timer[url] != null) {
                val overTime = System.currentTimeMillis()
                val startTime = timer[url]
                LogUtils.i("  page url:" + url + "  used time:" + (overTime - startTime!!))
            }
        }
        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
        /* public void onMainFrameError(AgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String failingUrl) {
            Log.i(TAG, "AgentWebFragment onMainFrameError");
            agentWebUIController.onMainFrameError(view,errorCode,description,failingUrl);
        }*/

        override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
            super.onReceivedHttpError(view, request, errorResponse)
            LogUtils.i("onReceivedHttpError:" + 3 + "  request:" + mGson.toJson(request) + "  errorResponse:" + mGson.toJson(errorResponse))
        }

        override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            LogUtils.i("onReceivedError:$errorCode  description:$description  errorResponse:$failingUrl")
        }
    }

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    private fun openBrowser(targetUrl: String) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            Toast.makeText(this.context, targetUrl + " 该链接无法使用浏览器打开。", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val mUri = Uri.parse(targetUrl)
        intent.data = mUri
        startActivity(intent)
    }

    private fun pageNavigator(tag: Int) {
        mBackImageView?.setVisibility(tag)
        mLineView?.setVisibility(tag)
    }

    private val mOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.iv_back ->
                if (!(mAgentWeb?.back() ?: false))
                // true表示AgentWeb处理了该事件
                    this@BaseWebViewFragment.getActivity()?.finish()
            R.id.iv_finish -> this@BaseWebViewFragment.getActivity()?.finish()
            R.id.iv_more -> showPoPup(v)
        }
    }

    /**
     * 显示更多菜单
     *
     * @param view 菜单依附在该View下面
     */
    private fun showPoPup(view: View) {
        if (mPopupMenu == null) {
            mPopupMenu = PopupMenu(context, view)
            mPopupMenu?.inflate(R.menu.toolbar_menu)
            mPopupMenu?.setOnMenuItemClickListener(mOnMenuItemClickListener)
        }
        mPopupMenu?.show()
    }

    // 菜单事件
    private val mOnMenuItemClickListener = PopupMenu.OnMenuItemClickListener { item ->
        when (item.itemId) {
            R.id.refresh -> {
                if (mAgentWeb != null)
                    mAgentWeb?.getLoader()?.reload() //刷新
                true
            }

            R.id.copy -> {
                if (mAgentWeb != null)
                    toCopy(this@BaseWebViewFragment.getContext(), mAgentWeb?.getWebCreator()?.webView?.url
                            ?: "")
                true
            }
            R.id.default_browser -> {
                if (mAgentWeb != null)
                    openBrowser(mAgentWeb?.getWebCreator()?.webView?.url ?: "")
                true
            }
            R.id.default_clean -> {
                toCleanWebCache()
                true
            }
            R.id.error_website -> {
                //                    loadErrorWebSite();
                if (mDownloadingService == null) {
                    return@OnMenuItemClickListener true
                }
                if (!(mDownloadingService?.isShutdown() ?: false)) {
                    mExtraService = mDownloadingService?.shutdownNow()
                    return@OnMenuItemClickListener true
                }
                if (mExtraService != null) {
                    mExtraService?.toReDownload()
                }
                true
            }
            else -> false
        }
    }

    //这里用于测试错误页的显示
    private fun loadErrorWebSite() {
        if (mAgentWeb != null) {
            mAgentWeb?.getLoader()?.loadUrl("http://www.unkownwebsiteblog.me")
        }
    }

    //清除 WebView 缓存
    private fun toCleanWebCache() {
        if (this.mAgentWeb != null) {
            //清理所有跟WebView相关的缓存 ，数据库， 历史记录 等。
            this.mAgentWeb?.clearWebCache()
            Toast.makeText(activity, "已清理缓存", Toast.LENGTH_SHORT).show()
            //清空所有 AgentWeb 硬盘缓存，包括 WebView 的缓存 , AgentWeb 下载的图片 ，视频 ，apk 等文件。
            //            AgentWebConfig.clearDiskCache(this.getContext());
        }
    }

    //复制字符串
    private fun toCopy(context: Context, text: String) {
        val mClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        mClipboardManager.primaryClip = ClipData.newPlainText(null, text)
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    override fun onResume() {
        mAgentWeb?.getWebLifeCycle()?.onResume()//恢复
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.getWebLifeCycle()?.onPause() //暂停应用内所有WebView ， 调用mWebView.resumeTimers(); 恢复。
        super.onPause()
    }

    override fun onFragmentKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return mAgentWeb?.handleKeyEvent(keyCode, event) ?: false
    }

    override fun onDestroyView() {
        mAgentWeb?.getWebLifeCycle()?.onDestroy()
        super.onDestroyView()
    }

    //
    protected fun getMiddlewareWebClient(): MiddlewareWebClientBase {
        mMiddleWareWebClient = MiddlewareWebViewClient()
        return mMiddleWareWebClient as MiddlewareWebViewClient
    }

    protected fun getMiddlewareWebChrome(): MiddlewareWebChromeBase {
        mMiddleWareWebChrome = MiddlewareChromeClient()
        return mMiddleWareWebChrome as MiddlewareChromeClient
    }

    //######################   override third methods end  ########################################

}