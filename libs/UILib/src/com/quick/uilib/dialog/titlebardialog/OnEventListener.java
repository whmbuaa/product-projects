package com.quick.uilib.dialog.titlebardialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface OnEventListener {
	void onEvent(Fragment frag, int eventId, Object arg);
}
