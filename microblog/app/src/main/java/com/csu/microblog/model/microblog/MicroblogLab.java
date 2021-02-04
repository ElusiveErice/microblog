package com.csu.microblog.model.microblog;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MicroblogLab {
    private static MicroblogLab sMicroblogLab;

    private List<Microblog> mMicroblogs;

    public static MicroblogLab get(Context context) {
        if (sMicroblogLab == null) {
            synchronized (MicroblogLab.class) {
                if (sMicroblogLab == null) {
                    sMicroblogLab = new MicroblogLab(context);
                }
            }
        }
        return sMicroblogLab;
    }

    private MicroblogLab(Context context) {
        mMicroblogs = new ArrayList<>();

        //TODO 暂时模拟10个微博
        for (int i = 0; i < 10; i++) {
            mMicroblogs.add(new Microblog());
        }
    }

    public List<Microblog> getMicroblogs() {
        return mMicroblogs;
    }

    public Microblog getMicroblog(long microblogId) {
        for (Microblog microblog : mMicroblogs) {
            if (microblog.getMicroblogId() == microblogId) {
                return microblog;
            }
        }
        return null;
    }
}
