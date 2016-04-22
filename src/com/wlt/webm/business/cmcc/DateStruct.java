package com.wlt.webm.business.cmcc;

/**
 * 
 */
public class DateStruct {
    /**
     * YEAR FIELD
     */
    private int year = -1;

    /**
     * MONTH FIELD
     */
    private int month = -1;

    /**
     * DAY FIELD
     */
    private int day = -1;

    /**
     * HOUR FIELD
     */
    private int hour = -1;

    /**
     * MINUTE FIELD
     */
    private int minute = -1;

    /**
     * SECOND FIELD
     */
    private int second = -1;

    /**
     * 函数功能：创建一个时间对象
     */
    public DateStruct() {

    }

    /**
     * 函数功能：创建一个时间对象
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 时
     * @param minute 分
     * @param second 秒
     */
    public DateStruct(int year, int month, int day, int hour, int minute,
                      int second) {
        setYear(year);
        setMonth(month);
        setDay(day);
        setHour(hour);
        setMinute(minute);
        setSecond(second);
    }

    /**
     * 函数功能：设置时间的年份
     * @param year 日期的年份 范围在1900～2500
     */
    public void setYear(int year) {
        if (year == -1 || (year >= 1900 && year <= 2500)) {
            this.year = year;
        }
    }

    /**
     * 函数功能：设置时间的分
     * @param minute 时间的分 范围在0～59
     */
    public void setMinute(int minute) {
        if (minute == -1 || (minute >= 0 && minute <= 59)) {
            this.minute = minute;
        }
    }

    /**
     * 函数功能：设置秒
     * @param second 秒 范围在0～59之间
     */
    public void setSecond(int second) {
        if (second == -1 || (second >= 0 && second <= 59)) {
            this.second = second;
        }
    }

    /**
     * 函数功能：设置月份
     * @param month 月份 范围在1～12
     */
    public void setMonth(int month) {
        if (month == -1 || (month >= 1 && month <= 12)) {
            this.month = month;
        }
    }

    /**
     * 函数功能：设置小时
     * @param hour 小时 范围在0～23
     */
    public void setHour(int hour) {
        if (hour == -1 || (hour >= 0 && hour <= 23)) {
            this.hour = hour;
        }
    }

    /**
     * 函数功能：设置日期
     * @param day 日期不能超过当月的最大日
     */
    public void setDay(int day) {
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            if (day == -1 || (1 <= day && day <= 31)) {
                this.day = day;
            }
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            if (day == -1 || (1 <= day && day <= 30)) {
                this.day = day;
            }
            break;
        case 2:
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                if (day == -1 || (1 <= day && day <= 29)) {
                    this.day = day;
                }
            } else if (day == -1 || (1 <= day && day <= 28)) {
                this.day = day;
            }

            break;
        default:
            if (day == -1 || (1 <= day && day <= 31)) {
                this.day = day;
            }
            break;
        }
    }

    /**
     * 函数功能：获取当前对象的日期
     * @return 日期 或 －1
     */
    public int getDay() {
        return day;
    }

    /**
     * 函数功能：获取当前对象的小时
     * @return 小时 或 －1
     */
    public int getHour() {
        return hour;
    }

    /**
     * 函数功能：获取当前对象的月份
     * @return 月份或－1
     */
    public int getMonth() {
        return month;
    }

    /**
     * 函数功能：获取当前对象的秒
     * @return 秒或－1
     */
    public int getSecond() {
        return second;
    }

    /**
     * 函数功能：获取当前对象的年份
     * @return 当前对象的年份或－1
     */
    public int getYear() {
        return year;
    }

    /**
     * 函数功能：获取当前时间的分
     * @return 当前对象的分或－1
     */
    public int getMinute() {
        return minute;
    }
}
