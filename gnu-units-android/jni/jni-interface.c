/*
 *  Units for Android
 *
 *  Copyright (C) 2011, Keith Flower
 *  
 *  Based on GNU units, a program for units conversion
 *  Copyright (C) 1996, 1997, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006,
 *                2007, 2009
 *  Free Software Foundation, Inc
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 *  Boston, MA 02110-1301 USA
 *     
 *  The original GNU Units was written by Adrian Mariano (adrian@cam.cornell.edu)
 */

#include <string.h>
#include <jni.h>
#include <android/log.h>

static void logv(int level, char *tag, const char *format, ...) {
	va_list arglist;
	va_start(arglist, format);
	__android_log_vprint(level, tag, format, arglist);
	va_end(arglist);
}


JNIEXPORT jstring 
Java_org_quexotic_gnuunits_GnuUnits_unitsJNI (JNIEnv* env,
                                              jobject thiz,
                                              jstring j_units_file_path,
                                              jstring j_units_input,
                                              jint units_argc,
                                              jobject jargv) {
	const char *result;
	int i;
	char **argv = (char **) malloc (units_argc * sizeof(char *));
	memset (argv, 0, sizeof(char *) *units_argc);

	const char *units_file_path = (*env)->GetStringUTFChars(env, j_units_file_path, 0);
	const char *units_input = (*env)->GetStringUTFChars(env, j_units_input, 0);

	// Load an array of strings that will represent "argv"
	for (i = 0; i < units_argc; i++) {
		jstring jstr = (*env)->GetObjectArrayElement(env, jargv, i);
		const char *s_argv = (*env)->GetStringUTFChars(env, jstr, 0);
		argv[i] = (char *) s_argv;
	}

	result = (char *) smain (units_file_path, argv, units_argc);
	free (argv);
	return (*env)->NewStringUTF(env, result);

}

JNIEXPORT jstring
Java_org_quexotic_gnuunits_GnuUnits_unitnamesJNI (JNIEnv* env,
                                                  jobject thiz,
                                                  jobject pat) {
  const char *result;

  result = (char *) getAllUnitNames();
  return (*env)->NewStringUTF(env, result);

}



