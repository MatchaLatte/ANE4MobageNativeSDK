/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 DeNA Co., Ltd.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package com.mobage.air.extension.social.common;

import org.json.JSONArray;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.mobage.air.extension.ArgsParser;
import com.mobage.air.extension.Dispatcher;
import com.mobage.android.Error;
import com.mobage.android.social.common.RemoteNotification;
import com.mobage.android.social.common.RemoteNotification.OnGetRemoteNotificationsEnabledComplete;

public class RemoteNotification_getRemoteNotificationsEnabled implements FREFunction{

	@Override
	public FREObject call(final FREContext context, FREObject[] args) {
		
		try{
			ArgsParser a = new ArgsParser(args);
			final String onGetRemoteEnabledSuccessId = a.nextString();
			final String onErrorId = a.nextString();
			a.finish();
			
			OnGetRemoteNotificationsEnabledComplete cb = 
					new RemoteNotification.OnGetRemoteNotificationsEnabledComplete(){

						@Override
						public void onError(Error error) {
							
							Dispatcher.dispatch(context, onErrorId, error);
						}

						@Override
						public void onSuccess(boolean rmEnabled) {
							
							try{
								JSONArray args = new JSONArray();
								args.put(rmEnabled);
								Dispatcher.dispatch(context, onGetRemoteEnabledSuccessId, args);
							}catch (Exception e) {
								Dispatcher.exception(context, e);
							}
						}
				};
			RemoteNotification.getRemoteNotificationsEnabled(cb);
			
			} catch (Exception e) {
				Dispatcher.exception(context, e);
		}
		return null;
	}
}
