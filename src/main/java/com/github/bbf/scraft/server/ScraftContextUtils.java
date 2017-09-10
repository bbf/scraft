package com.github.bbf.scraft.server;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public final class ScraftContextUtils {
	private ScraftContextUtils() {
	}

	private static final JavaVoidCallback sysout = new JavaVoidCallback() {
		@Override
		public void invoke(V8Object receiver, V8Array parameters) {
			if (parameters.length() > 0) {
				for (String key : parameters.getKeys()) {
					Object param = parameters.get(key);
					System.out.println(param);
					if (param instanceof Releasable) {
						((Releasable) param).release();
					}
				}
			}
		}
	};

	public static void inject(V8 runtime) {
		runtime.registerJavaMethod(sysout, "sysout");
	}

}
