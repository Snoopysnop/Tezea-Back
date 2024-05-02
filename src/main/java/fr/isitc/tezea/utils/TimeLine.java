package fr.isitc.tezea.utils;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TimeLine {

    private LocalDateTime begin;
    private LocalDateTime end;

    public TimeLine(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }

    private boolean isDateInTimeline(LocalDateTime date) {
        return (date.isAfter(begin) && date.isBefore(end) || date.equals(begin) || date.equals(end));
    }

    public static boolean areTimelineInConflict(TimeLine tl1, TimeLine tl2) {
        return 
            tl2.isDateInTimeline(tl1.getBegin()) || 
            tl2.isDateInTimeline(tl1.getEnd()) ||
            tl1.isDateInTimeline(tl2.getBegin()) ||
            tl1.isDateInTimeline(tl2.getEnd());
    }

}
