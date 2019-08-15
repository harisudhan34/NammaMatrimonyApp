package com.skyappz.namma.model;

import java.io.Serializable;

public class Plan implements Serializable {
    private Details details;

    private String id;

    private String category;

    private String plan_id;

    private String plan_name;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    @Override
    public String toString() {
        return "ClassPojo [details = " + details + ", id = " + id + ", category = " + category + ", plan_id = " + plan_id + ", plan_name = " + plan_name + "]";
    }


    public class Details implements Serializable {
        private String profile_highlighter;

        private String unlimited_outtime;

        private String online_chat;

        private String unlimited_message;

        private String default_horoscope;

        private String unlimited_horoscope;

        private String default_allaccess;

        private String default_message;

        private String default_mobileno;

        private String video_upload;

        private String unlimited_day;

        private String highlighter_days;

        private String unlimited_noofdays;

        private String unlimited_allaccess;

        private String unlimited_mobileno;

        private String unlimited_intime;

        public String getProfile_highlighter() {
            return profile_highlighter;
        }

        public void setProfile_highlighter(String profile_highlighter) {
            this.profile_highlighter = profile_highlighter;
        }

        public String getUnlimited_outtime() {
            return unlimited_outtime;
        }

        public void setUnlimited_outtime(String unlimited_outtime) {
            this.unlimited_outtime = unlimited_outtime;
        }

        public String getOnline_chat() {
            return online_chat;
        }

        public void setOnline_chat(String online_chat) {
            this.online_chat = online_chat;
        }

        public String getUnlimited_message() {
            return unlimited_message;
        }

        public void setUnlimited_message(String unlimited_message) {
            this.unlimited_message = unlimited_message;
        }

        public String getDefault_horoscope() {
            return default_horoscope;
        }

        public void setDefault_horoscope(String default_horoscope) {
            this.default_horoscope = default_horoscope;
        }

        public String getUnlimited_horoscope() {
            return unlimited_horoscope;
        }

        public void setUnlimited_horoscope(String unlimited_horoscope) {
            this.unlimited_horoscope = unlimited_horoscope;
        }

        public String getDefault_allaccess() {
            return default_allaccess;
        }

        public void setDefault_allaccess(String default_allaccess) {
            this.default_allaccess = default_allaccess;
        }

        public String getDefault_message() {
            return default_message;
        }

        public void setDefault_message(String default_message) {
            this.default_message = default_message;
        }

        public String getDefault_mobileno() {
            return default_mobileno;
        }

        public void setDefault_mobileno(String default_mobileno) {
            this.default_mobileno = default_mobileno;
        }

        public String getVideo_upload() {
            return video_upload;
        }

        public void setVideo_upload(String video_upload) {
            this.video_upload = video_upload;
        }

        public String getUnlimited_day() {
            return unlimited_day;
        }

        public void setUnlimited_day(String unlimited_day) {
            this.unlimited_day = unlimited_day;
        }

        public String getHighlighter_days() {
            return highlighter_days;
        }

        public void setHighlighter_days(String highlighter_days) {
            this.highlighter_days = highlighter_days;
        }

        public String getUnlimited_noofdays() {
            return unlimited_noofdays;
        }

        public void setUnlimited_noofdays(String unlimited_noofdays) {
            this.unlimited_noofdays = unlimited_noofdays;
        }

        public String getUnlimited_allaccess() {
            return unlimited_allaccess;
        }

        public void setUnlimited_allaccess(String unlimited_allaccess) {
            this.unlimited_allaccess = unlimited_allaccess;
        }

        public String getUnlimited_mobileno() {
            return unlimited_mobileno;
        }

        public void setUnlimited_mobileno(String unlimited_mobileno) {
            this.unlimited_mobileno = unlimited_mobileno;
        }

        public String getUnlimited_intime() {
            return unlimited_intime;
        }

        public void setUnlimited_intime(String unlimited_intime) {
            this.unlimited_intime = unlimited_intime;
        }

        @Override
        public String toString() {
            return "ClassPojo [profile_highlighter = " + profile_highlighter + ", unlimited_outtime = " + unlimited_outtime + ", online_chat = " + online_chat + ", unlimited_message = " + unlimited_message + ", default_horoscope = " + default_horoscope + ", unlimited_horoscope = " + unlimited_horoscope + ", default_allaccess = " + default_allaccess + ", default_message = " + default_message + ", default_mobileno = " + default_mobileno + ", video_upload = " + video_upload + ", unlimited_day = " + unlimited_day + ", highlighter_days = " + highlighter_days + ", unlimited_noofdays = " + unlimited_noofdays + ", unlimited_allaccess = " + unlimited_allaccess + ", unlimited_mobileno = " + unlimited_mobileno + ", unlimited_intime = " + unlimited_intime + "]";
        }
    }
}
