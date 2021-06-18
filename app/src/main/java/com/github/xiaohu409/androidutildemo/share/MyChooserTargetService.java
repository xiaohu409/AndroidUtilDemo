package com.github.xiaohu409.androidutildemo.share;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;

import java.util.List;

public class MyChooserTargetService extends ChooserTargetService {

    @Override
    public List<ChooserTarget> onGetChooserTargets(ComponentName targetActivityName, IntentFilter matchedFilter) {
        return null;
    }
}
