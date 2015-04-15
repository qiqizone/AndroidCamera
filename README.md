AndroidCamera
==================
AndroidCamera是基于android系统[Camera](https://android.googlesource.com/platform/packages/apps/Camera/)和[Gallery3D](https://android.googlesource.com/platform/packages/apps/Gallery3D/)源码，实现相机和图库的还原。


####遇到的问题及解决方法
---------------------------------
1. 问题：运行时报INSTALL_FAILED_VERSION_DOWNGRADE错误
    原因：工程中gallery3D包和系统中图库包名称冲突
    解决：修改Gallery3D包名，如com.android.test.gallery3d.xxx


> 备注：
> 
> * Camera源码中语言包没有并入.
> * android.os.systemproperties没有处理, 只是注释掉.
> * Gallery3D其他[下载地址](http://www.java2s.com/Open-Source/Android_Free_Code/3D/Download_Free_code_Gallery_3D.htmc)
     
