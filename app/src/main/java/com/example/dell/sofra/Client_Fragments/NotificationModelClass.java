package com.example.dell.sofra.Client_Fragments;

public class NotificationModelClass {
    public int getNoti_image() {
        return noti_image;
    }

    public void setNoti_image(int noti_image) {
        this.noti_image = noti_image;
    }

    public String getNotificationtitle() {
        return notificationtitle;
    }

    public void setNotificationtitle(String notificationtitle) {
        this.notificationtitle = notificationtitle;
    }

    public String getNotificationdate() {
        return notificationdate;
    }

    public void setNotificationdate(String notificationdate) {
        this.notificationdate = notificationdate;
    }

    public String getNotificationtime() {
        return notificationtime;
    }

    public void setNotificationtime(String notificationtime) {
        this.notificationtime = notificationtime;
    }

    int noti_image;
    String notificationtitle,notificationdate,notificationtime;
}
