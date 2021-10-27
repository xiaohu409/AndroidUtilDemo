package com.github.xiaohu409.androidutildemo;

import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import androidx.annotation.RequiresApi;

import com.github.xiaohu409.androidutil.ToastUtil;

@RequiresApi(api = Build.VERSION_CODES.N)
public class QuickSettingService extends TileService {

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onClick() {
        super.onClick();
        ToastUtil.showShort("点击：" + getQsTile().getState());
        if (getQsTile().getState() == Tile.STATE_INACTIVE) {
            getQsTile().setState(Tile.STATE_ACTIVE);
            getQsTile().setLabel("关闭");
        }
        else {
            getQsTile().setState(Tile.STATE_INACTIVE);
            getQsTile().setLabel("打开");
        }

        getQsTile().updateTile();
    }
}
