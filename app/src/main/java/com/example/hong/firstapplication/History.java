package com.example.hong.firstapplication;

/**
 * Created by Hong on 3/23/2016.
 */
public class History {

    private int _id;
    private String _subject;
    private String _date;
    private String _startTime;
    private String _endTime;
    private String _logTime;
    private String _instructor;
    private String _checked;


    public History(String _date, String _subject, String _startTime, String _endTime, String _logTime, String _instructor, String _checked) {
        this._date = _date;
        this._endTime = _endTime;
        this._startTime = _startTime;
        this._subject = _subject;
        this._logTime = _logTime;
        this._instructor = _instructor;
        this._checked = _checked;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_endTime() {
        return _endTime;
    }

    public void set_endTime(String _endTime) {
        this._endTime = _endTime;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_startTime() {
        return _startTime;
    }

    public void set_startTime(String _startTime) {
        this._startTime = _startTime;
    }

    public String get_subject() {
        return _subject;
    }

    public void set_subject(String _subject) {
        this._subject = _subject;
    }

    public String get_logTime() {
        return _logTime;
    }

    public void set_logTime(String _logTime) {
        this._logTime = _logTime;
    }

    public String get_checked() {
        return _checked;
    }

    public void set_checked(String _checked) {
        this._checked = _checked;
    }

    public String get_instructor() {
        return _instructor;
    }

    public void set_instructor(String _instructor) {
        this._instructor = _instructor;
    }


}
