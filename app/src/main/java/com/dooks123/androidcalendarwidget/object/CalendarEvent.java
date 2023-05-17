package com.dooks123.androidcalendarwidget.object;

import android.database.Cursor;
import android.provider.CalendarContract;

import androidx.annotation.NonNull;

public class CalendarEvent {
    private int ID;
    private int CALENDAR_ID;
    private int ACCESS_LEVEL;
    private String ACCOUNT_NAME;
    private String ACCOUNT_TYPE;
    private boolean ALL_DAY;
    private int AVAILABILITY;
    private String CALENDAR_DISPLAY_NAME;
    private String CALENDAR_TIME_ZONE;
    private boolean DELETED;
    private String DESCRIPTION;
    private int DISPLAY_COLOR;
    private long DTEND;
    private long DTSTART;
    private String DURATION;
    private int EVENT_COLOR;
    private String EVENT_COLOR_KEY;
    private String EVENT_END_TIMEZONE;
    private String EVENT_LOCATION;
    private String EVENT_TIMEZONE;
    private String EXDATE;
    private String EXRULE;
    private String IS_ORGANIZER;
    private String IS_PRIMARY;
    private long LAST_DATE;
    private boolean LAST_SYNCED;
    private String ORGANIZER;
    private boolean ORIGINAL_ALL_DAY;
    private String ORIGINAL_ID;
    private long ORIGINAL_INSTANCE_TIME;
    private String ORIGINAL_SYNC_ID;
    private String OWNER_ACCOUNT;
    private String RDATE;
    private String RRULE;
    private int SELF_ATTENDEE_STATUS;
    private int STATUS;
    private String TITLE;
    private String UID_2445;
    private boolean VISIBLE;

    public CalendarEvent() {
    }

    public CalendarEvent(Cursor cursor) {
        int indexID = cursor.getColumnIndex(CalendarContract.Events._ID);
        int indexCALENDAR_ID = cursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID);
        int indexACCESS_LEVEL = cursor.getColumnIndex(CalendarContract.Events.ACCESS_LEVEL);
        int indexACCOUNT_NAME = cursor.getColumnIndex(CalendarContract.Events.ACCOUNT_NAME);
        int indexACCOUNT_TYPE = cursor.getColumnIndex(CalendarContract.Events.ACCOUNT_TYPE);
        int indexALL_DAY = cursor.getColumnIndex(CalendarContract.Events.ALL_DAY);
        int indexAVAILABILITY = cursor.getColumnIndex(CalendarContract.Events.AVAILABILITY);
        int indexCALENDAR_DISPLAY_NAME = cursor.getColumnIndex(CalendarContract.Events.CALENDAR_DISPLAY_NAME);
        int indexCALENDAR_TIME_ZONE = cursor.getColumnIndex(CalendarContract.Events.CALENDAR_TIME_ZONE);
        int indexDELETED = cursor.getColumnIndex(CalendarContract.Events.DELETED);
        int indexDESCRIPTION = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
        int indexDISPLAY_COLOR = cursor.getColumnIndex(CalendarContract.Events.DISPLAY_COLOR);
        int indexDTEND = cursor.getColumnIndex(CalendarContract.Events.DTEND);
        int indexDTSTART = cursor.getColumnIndex(CalendarContract.Events.DTSTART);
        int indexDURATION = cursor.getColumnIndex(CalendarContract.Events.DURATION);
        int indexEVENT_COLOR = cursor.getColumnIndex(CalendarContract.Events.EVENT_COLOR);
        int indexEVENT_COLOR_KEY = cursor.getColumnIndex(CalendarContract.Events.EVENT_COLOR_KEY);
        int indexEVENT_END_TIMEZONE = cursor.getColumnIndex(CalendarContract.Events.EVENT_END_TIMEZONE);
        int indexEVENT_LOCATION = cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);
        int indexEVENT_TIMEZONE = cursor.getColumnIndex(CalendarContract.Events.EVENT_TIMEZONE);
        int indexEXDATE = cursor.getColumnIndex(CalendarContract.Events.EXDATE);
        int indexEXRULE = cursor.getColumnIndex(CalendarContract.Events.EXRULE);
        int indexIS_ORGANIZER = cursor.getColumnIndex(CalendarContract.Events.IS_ORGANIZER);
        int indexIS_PRIMARY = cursor.getColumnIndex(CalendarContract.Events.IS_PRIMARY);
        int indexLAST_DATE = cursor.getColumnIndex(CalendarContract.Events.LAST_DATE);
        int indexLAST_SYNCED = cursor.getColumnIndex(CalendarContract.Events.LAST_SYNCED);
        int indexORGANIZER = cursor.getColumnIndex(CalendarContract.Events.ORGANIZER);
        int indexORIGINAL_ALL_DAY = cursor.getColumnIndex(CalendarContract.Events.ORIGINAL_ALL_DAY);
        int indexORIGINAL_ID = cursor.getColumnIndex(CalendarContract.Events.ORIGINAL_ID);
        int indexORIGINAL_INSTANCE_TIME = cursor.getColumnIndex(CalendarContract.Events.ORIGINAL_INSTANCE_TIME);
        int indexORIGINAL_SYNC_ID = cursor.getColumnIndex(CalendarContract.Events.ORIGINAL_SYNC_ID);
        int indexOWNER_ACCOUNT = cursor.getColumnIndex(CalendarContract.Events.OWNER_ACCOUNT);
        int indexRDATE = cursor.getColumnIndex(CalendarContract.Events.RDATE);
        int indexRRULE = cursor.getColumnIndex(CalendarContract.Events.RRULE);
        int indexSELF_ATTENDEE_STATUS = cursor.getColumnIndex(CalendarContract.Events.SELF_ATTENDEE_STATUS);
        int indexSTATUS = cursor.getColumnIndex(CalendarContract.Events.STATUS);
        int indexTITLE = cursor.getColumnIndex(CalendarContract.Events.TITLE);
        int indexUID_2445 = cursor.getColumnIndex(CalendarContract.Events.UID_2445);
        int indexVISIBLE = cursor.getColumnIndex(CalendarContract.Events.VISIBLE);

        ID = cursor.getInt(indexID);
        CALENDAR_ID = cursor.getInt(indexCALENDAR_ID);
        ACCESS_LEVEL = cursor.getInt(indexACCESS_LEVEL);
        ACCOUNT_NAME = cursor.getString(indexACCOUNT_NAME);
        ACCOUNT_TYPE = cursor.getString(indexACCOUNT_TYPE);
        ALL_DAY = cursor.getInt(indexALL_DAY) == 1;
        AVAILABILITY = cursor.getInt(indexAVAILABILITY);
        CALENDAR_DISPLAY_NAME = cursor.getString(indexCALENDAR_DISPLAY_NAME);
        CALENDAR_TIME_ZONE = cursor.getString(indexCALENDAR_TIME_ZONE);
        DELETED = cursor.getInt(indexDELETED) == 1;
        DESCRIPTION = cursor.getString(indexDESCRIPTION);
        DISPLAY_COLOR = cursor.getInt(indexDISPLAY_COLOR);
        DTEND = cursor.getLong(indexDTEND);
        DTSTART = cursor.getLong(indexDTSTART);
        DURATION = cursor.getString(indexDURATION);
        EVENT_COLOR = cursor.getInt(indexEVENT_COLOR);
        EVENT_COLOR_KEY = cursor.getString(indexEVENT_COLOR_KEY);
        EVENT_END_TIMEZONE = cursor.getString(indexEVENT_END_TIMEZONE);
        EVENT_LOCATION = cursor.getString(indexEVENT_LOCATION);
        EVENT_TIMEZONE = cursor.getString(indexEVENT_TIMEZONE);
        EXDATE = cursor.getString(indexEXDATE);
        EXRULE = cursor.getString(indexEXRULE);
        IS_ORGANIZER = cursor.getString(indexIS_ORGANIZER);
        IS_PRIMARY = cursor.getString(indexIS_PRIMARY);
        LAST_DATE = cursor.getLong(indexLAST_DATE);
        LAST_SYNCED = cursor.getInt(indexLAST_SYNCED) == 1;
        ORGANIZER = cursor.getString(indexORGANIZER);
        ORIGINAL_ALL_DAY = cursor.getInt(indexORIGINAL_ALL_DAY) == 1;
        ORIGINAL_ID = cursor.getString(indexORIGINAL_ID);
        ORIGINAL_INSTANCE_TIME = cursor.getLong(indexORIGINAL_INSTANCE_TIME);
        ORIGINAL_SYNC_ID = cursor.getString(indexORIGINAL_SYNC_ID);
        OWNER_ACCOUNT = cursor.getString(indexOWNER_ACCOUNT);
        RDATE = cursor.getString(indexRDATE);
        RRULE = cursor.getString(indexRRULE);
        SELF_ATTENDEE_STATUS = cursor.getInt(indexSELF_ATTENDEE_STATUS);
        STATUS = cursor.getInt(indexSTATUS);
        TITLE = cursor.getString(indexTITLE);
        UID_2445 = cursor.getString(indexUID_2445);
        VISIBLE = cursor.getInt(indexVISIBLE) == 1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCALENDAR_ID() {
        return CALENDAR_ID;
    }

    public void setCALENDAR_ID(int CALENDAR_ID) {
        this.CALENDAR_ID = CALENDAR_ID;
    }

    public int getACCESS_LEVEL() {
        return ACCESS_LEVEL;
    }

    public void setACCESS_LEVEL(int ACCESS_LEVEL) {
        this.ACCESS_LEVEL = ACCESS_LEVEL;
    }

    public String getACCOUNT_NAME() {
        return ACCOUNT_NAME;
    }

    public void setACCOUNT_NAME(String ACCOUNT_NAME) {
        this.ACCOUNT_NAME = ACCOUNT_NAME;
    }

    public String getACCOUNT_TYPE() {
        return ACCOUNT_TYPE;
    }

    public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
    }

    public boolean isALL_DAY() {
        return ALL_DAY;
    }

    public void setALL_DAY(boolean ALL_DAY) {
        this.ALL_DAY = ALL_DAY;
    }

    public int getAVAILABILITY() {
        return AVAILABILITY;
    }

    public void setAVAILABILITY(int AVAILABILITY) {
        this.AVAILABILITY = AVAILABILITY;
    }

    public String getCALENDAR_DISPLAY_NAME() {
        return CALENDAR_DISPLAY_NAME;
    }

    public void setCALENDAR_DISPLAY_NAME(String CALENDAR_DISPLAY_NAME) {
        this.CALENDAR_DISPLAY_NAME = CALENDAR_DISPLAY_NAME;
    }

    public String getCALENDAR_TIME_ZONE() {
        return CALENDAR_TIME_ZONE;
    }

    public void setCALENDAR_TIME_ZONE(String CALENDAR_TIME_ZONE) {
        this.CALENDAR_TIME_ZONE = CALENDAR_TIME_ZONE;
    }

    public boolean isDELETED() {
        return DELETED;
    }

    public void setDELETED(boolean DELETED) {
        this.DELETED = DELETED;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getDISPLAY_COLOR() {
        return DISPLAY_COLOR;
    }

    public void setDISPLAY_COLOR(int DISPLAY_COLOR) {
        this.DISPLAY_COLOR = DISPLAY_COLOR;
    }

    public long getDTEND() {
        return DTEND;
    }

    public void setDTEND(long DTEND) {
        this.DTEND = DTEND;
    }

    public long getDTSTART() {
        return DTSTART;
    }

    public void setDTSTART(long DTSTART) {
        this.DTSTART = DTSTART;
    }

    public String getDURATION() {
        return DURATION;
    }

    public void setDURATION(String DURATION) {
        this.DURATION = DURATION;
    }

    public int getEVENT_COLOR() {
        return EVENT_COLOR;
    }

    public void setEVENT_COLOR(int EVENT_COLOR) {
        this.EVENT_COLOR = EVENT_COLOR;
    }

    public String getEVENT_COLOR_KEY() {
        return EVENT_COLOR_KEY;
    }

    public void setEVENT_COLOR_KEY(String EVENT_COLOR_KEY) {
        this.EVENT_COLOR_KEY = EVENT_COLOR_KEY;
    }

    public String getEVENT_END_TIMEZONE() {
        return EVENT_END_TIMEZONE;
    }

    public void setEVENT_END_TIMEZONE(String EVENT_END_TIMEZONE) {
        this.EVENT_END_TIMEZONE = EVENT_END_TIMEZONE;
    }

    public String getEVENT_LOCATION() {
        return EVENT_LOCATION;
    }

    public void setEVENT_LOCATION(String EVENT_LOCATION) {
        this.EVENT_LOCATION = EVENT_LOCATION;
    }

    public String getEVENT_TIMEZONE() {
        return EVENT_TIMEZONE;
    }

    public void setEVENT_TIMEZONE(String EVENT_TIMEZONE) {
        this.EVENT_TIMEZONE = EVENT_TIMEZONE;
    }

    public String getEXDATE() {
        return EXDATE;
    }

    public void setEXDATE(String EXDATE) {
        this.EXDATE = EXDATE;
    }

    public String getEXRULE() {
        return EXRULE;
    }

    public void setEXRULE(String EXRULE) {
        this.EXRULE = EXRULE;
    }

    public String getIS_ORGANIZER() {
        return IS_ORGANIZER;
    }

    public void setIS_ORGANIZER(String IS_ORGANIZER) {
        this.IS_ORGANIZER = IS_ORGANIZER;
    }

    public String getIS_PRIMARY() {
        return IS_PRIMARY;
    }

    public void setIS_PRIMARY(String IS_PRIMARY) {
        this.IS_PRIMARY = IS_PRIMARY;
    }

    public long getLAST_DATE() {
        return LAST_DATE;
    }

    public void setLAST_DATE(long LAST_DATE) {
        this.LAST_DATE = LAST_DATE;
    }

    public boolean isLAST_SYNCED() {
        return LAST_SYNCED;
    }

    public void setLAST_SYNCED(boolean LAST_SYNCED) {
        this.LAST_SYNCED = LAST_SYNCED;
    }

    public String getORGANIZER() {
        return ORGANIZER;
    }

    public void setORGANIZER(String ORGANIZER) {
        this.ORGANIZER = ORGANIZER;
    }

    public boolean isORIGINAL_ALL_DAY() {
        return ORIGINAL_ALL_DAY;
    }

    public void setORIGINAL_ALL_DAY(boolean ORIGINAL_ALL_DAY) {
        this.ORIGINAL_ALL_DAY = ORIGINAL_ALL_DAY;
    }

    public String getORIGINAL_ID() {
        return ORIGINAL_ID;
    }

    public void setORIGINAL_ID(String ORIGINAL_ID) {
        this.ORIGINAL_ID = ORIGINAL_ID;
    }

    public long getORIGINAL_INSTANCE_TIME() {
        return ORIGINAL_INSTANCE_TIME;
    }

    public void setORIGINAL_INSTANCE_TIME(long ORIGINAL_INSTANCE_TIME) {
        this.ORIGINAL_INSTANCE_TIME = ORIGINAL_INSTANCE_TIME;
    }

    public String getORIGINAL_SYNC_ID() {
        return ORIGINAL_SYNC_ID;
    }

    public void setORIGINAL_SYNC_ID(String ORIGINAL_SYNC_ID) {
        this.ORIGINAL_SYNC_ID = ORIGINAL_SYNC_ID;
    }

    public String getOWNER_ACCOUNT() {
        return OWNER_ACCOUNT;
    }

    public void setOWNER_ACCOUNT(String OWNER_ACCOUNT) {
        this.OWNER_ACCOUNT = OWNER_ACCOUNT;
    }

    public String getRDATE() {
        return RDATE;
    }

    public void setRDATE(String RDATE) {
        this.RDATE = RDATE;
    }

    public String getRRULE() {
        return RRULE;
    }

    public void setRRULE(String RRULE) {
        this.RRULE = RRULE;
    }

    public int getSELF_ATTENDEE_STATUS() {
        return SELF_ATTENDEE_STATUS;
    }

    public void setSELF_ATTENDEE_STATUS(int SELF_ATTENDEE_STATUS) {
        this.SELF_ATTENDEE_STATUS = SELF_ATTENDEE_STATUS;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getUID_2445() {
        return UID_2445;
    }

    public void setUID_2445(String UID_2445) {
        this.UID_2445 = UID_2445;
    }

    public boolean isVISIBLE() {
        return VISIBLE;
    }

    public void setVISIBLE(boolean VISIBLE) {
        this.VISIBLE = VISIBLE;
    }

    @NonNull
    @Override
    public String toString() {
        return "CalendarEvent{" +
                "ID=" + ID +
                ", CALENDAR_ID=" + CALENDAR_ID +
                ", ACCESS_LEVEL=" + ACCESS_LEVEL +
                ", ACCOUNT_NAME='" + ACCOUNT_NAME + '\'' +
                ", ACCOUNT_TYPE='" + ACCOUNT_TYPE + '\'' +
                ", ALL_DAY=" + ALL_DAY +
                ", AVAILABILITY=" + AVAILABILITY +
                ", CALENDAR_DISPLAY_NAME='" + CALENDAR_DISPLAY_NAME + '\'' +
                ", CALENDAR_TIME_ZONE='" + CALENDAR_TIME_ZONE + '\'' +
                ", DELETED=" + DELETED +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", DISPLAY_COLOR=" + DISPLAY_COLOR +
                ", DTEND=" + DTEND +
                ", DTSTART=" + DTSTART +
                ", DURATION='" + DURATION + '\'' +
                ", EVENT_COLOR=" + EVENT_COLOR +
                ", EVENT_COLOR_KEY='" + EVENT_COLOR_KEY + '\'' +
                ", EVENT_END_TIMEZONE='" + EVENT_END_TIMEZONE + '\'' +
                ", EVENT_LOCATION='" + EVENT_LOCATION + '\'' +
                ", EVENT_TIMEZONE='" + EVENT_TIMEZONE + '\'' +
                ", EXDATE='" + EXDATE + '\'' +
                ", EXRULE='" + EXRULE + '\'' +
                ", IS_ORGANIZER='" + IS_ORGANIZER + '\'' +
                ", IS_PRIMARY='" + IS_PRIMARY + '\'' +
                ", LAST_DATE=" + LAST_DATE +
                ", LAST_SYNCED=" + LAST_SYNCED +
                ", ORGANIZER='" + ORGANIZER + '\'' +
                ", ORIGINAL_ALL_DAY=" + ORIGINAL_ALL_DAY +
                ", ORIGINAL_ID='" + ORIGINAL_ID + '\'' +
                ", ORIGINAL_INSTANCE_TIME=" + ORIGINAL_INSTANCE_TIME +
                ", ORIGINAL_SYNC_ID='" + ORIGINAL_SYNC_ID + '\'' +
                ", OWNER_ACCOUNT='" + OWNER_ACCOUNT + '\'' +
                ", RDATE='" + RDATE + '\'' +
                ", RRULE='" + RRULE + '\'' +
                ", SELF_ATTENDEE_STATUS=" + SELF_ATTENDEE_STATUS +
                ", STATUS=" + STATUS +
                ", TITLE='" + TITLE + '\'' +
                ", UID_2445='" + UID_2445 + '\'' +
                ", VISIBLE=" + VISIBLE +
                '}';
    }
}
