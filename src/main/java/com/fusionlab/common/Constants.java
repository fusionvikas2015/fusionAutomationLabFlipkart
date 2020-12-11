package com.fusionlab.common;

/**
 * 
 * Constant class - All constant related to the apk testing resides here.
 *
 */
public class Constants {

	/**
	 * Constant for Android
	 */
	public static final String ANDROID = "ANDROID";
	/**
	 * Command to get the current screen' package and activity name.
	 */

	public static final String SCREEN_DUMPSYS = "shell dumpsys \"window windows | grep -E mCurrentFocus\"";

	/**
	 * Constant for app package name.
	 */
	public static final String ANDROID_APP_PACKAGE = "com.flipkart.android";

	/**
	 * Constant for launching activity name.
	 */
	public static final String ANDROID_APP_ACTIVITY = ".activity.HomeFragmentHolderActivity";

	/**
	 * Command can be used to launch the app using adb command.
	 */
	public static final String LaunchAppInHomeScreenDirectlyCmd = "shell am start -n com.flipkart.android/.activity.HomeFragmentHolderActivity";

	/**
	 * adb command to get the DUT(Device Under Test)'s android version.
	 */
	public static final String ANDROID_PLATFORM_VERSION_CMD = "shell getprop ro.build.version.release";

	/**
	 * adb command to get the android device model number.
	 */
	public static final String ANDROID_DEVICE_NAME_CMD = "shell getprop ro.product.model";

	/**
	 * logact command to clear the older logs present in the device.
	 */
	public static final String CLEAR_LOGCAT = "logcat -c";

	/**
	 * Logcat command to get the logcat particular the the app(amazon app)
	 */
	public static final String START_LOGCAT = "logcat com.flipkart.android -d";

}
