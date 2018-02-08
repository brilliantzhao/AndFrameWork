package com.brilliantzhao.baselibrary.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.orhanobut.hawk.Hawk

/**
 * 对SharedPreference文件中的各种类型的数据进行存取操作
 */
class SharedPrefUtils private constructor() {

    /**
     * 获取Hawk存储条数
     *
     * @return
     */
    val hawkCount: Long
        get() = Hawk.count()

    /**
     * @param key
     * @param value
     */
    fun setSharedIntData(key: String, value: Int) {
        if (sp != null) {
            sp!!.edit().putInt(key, value).commit()
        }
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    fun getSharedIntData(key: String, defValue: Int): Int {
        return if (sp != null) {
            sp!!.getInt(key, defValue)
        } else {
            defValue
        }
    }

    /**
     * @param key
     * @param value
     */
    fun setSharedlongData(key: String, value: Long) {
        if (sp != null) {
            sp!!.edit().putLong(key, value).commit()
        }
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    fun getSharedlongData(key: String, defValue: Long): Long {
        return if (sp != null) {
            sp!!.getLong(key, defValue)
        } else {
            defValue
        }
    }

    /**
     * @param key
     * @param value
     */
    fun setSharedFloatData(key: String, value: Float) {
        if (sp != null) {
            sp!!.edit().putFloat(key, value).commit()
        }
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    fun getSharedFloatData(key: String, defValue: Float?): Float? {
        return if (sp != null) {
            sp!!.getFloat(key, defValue!!)
        } else {
            defValue
        }
    }

    /**
     * @param key
     * @param value
     */
    fun setSharedBooleanData(key: String, value: Boolean) {
        if (sp != null) {
            sp!!.edit().putBoolean(key, value).commit()
        }
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    fun getSharedBooleanData(key: String, defValue: Boolean): Boolean? {
        return if (sp != null) {
            sp!!.getBoolean(key, defValue)
        } else {
            defValue
        }
    }

    /**
     * @param key
     * @param value
     */
    fun setSharedStringData(key: String, value: String) {
        if (sp != null) {
            sp!!.edit().putString(key, value).commit()
        }
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    fun getSharedStringData(key: String, defValue: String): String? {
        return if (sp != null) {
            sp!!.getString(key, defValue)
        } else {
            defValue
        }
    }

    /**
     * 删除某一项的值
     *
     * @param key
     */
    fun sharedPrefRemove(key: String) {
        sp!!.edit().remove(key).commit()
    }

    /**
     * 清空所有的sharedPref的值
     */
    fun sharedPrefClear() {
        sp!!.edit().clear().commit()
    }

    /**
     * 使用hawk存储对象
     *
     * @param key
     * @param value
     */
    fun setHawkObject(key: String, value: Any) {
        Hawk.put(key, value)
    }

    /**
     * 获取Hawk数据
     *
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
    </T> */
    fun <T> getHawkObject(key: String, defaultValue: T): T {
        return if (!isEmpty(key) && Hawk.contains(key)) {
            Hawk.get(key, defaultValue)
        } else {
            defaultValue
        }
    }

    /**
     * 判断是否包含某一条Hawk数据
     *
     * @param key
     * @return
     */
    fun containsHawkObject(key: String): Boolean {
        return Hawk.contains(key)
    }

    /**
     * 删除某一条Hawk数据
     *
     * @param key
     */
    fun deleteHawkObject(key: String) {
        if (!isEmpty(key) && Hawk.contains(key)) {
            Hawk.delete(key)
        }
    }

    /**
     * 清空Hawk所有数据
     */
    fun clearHawkObject() {
        Hawk.deleteAll()
    }

    companion object {

        private var sp: SharedPreferences? = null

        /**
         * 初始化
         *
         * @param context
         */
        fun init(context: Context) {
            if (sp == null) {
                sp = PreferenceManager.getDefaultSharedPreferences(context)
            }
        }
    }
}