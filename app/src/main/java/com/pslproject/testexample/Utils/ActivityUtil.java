package com.pslproject.testexample.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtil {
    public List<Activity> activityList=new ArrayList<>();
    public static ActivityUtil instance;

    //单例模式中获取唯一的ExitApplication实例
    public static synchronized ActivityUtil getInstance(){
        if(instance==null){
                instance=new ActivityUtil();
        }
        return instance;
        }

        //添加到List
        public void addActivity(Activity activity){
            if(activityList==null){
                activityList=new ArrayList<>();
            }
            activityList.add(activity);
        }

        //从List中移除
        public void removeActivity(Activity activity){
            if(activityList!=null){
                activityList.remove(activity);
            }
        }

        //finish全部Activity
        public void finishAllActivity(){
            for(Activity activity:activityList){
                if(activity!=null)
                    activity.finish();
            }

            System.exit(0);
        }

    }


