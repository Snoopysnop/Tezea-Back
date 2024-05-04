package fr.isitc.tezea.utils;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TimeLine implements Serializable {

    private LocalDateTime begin;
    private LocalDateTime end;

    protected TimeLine() {

    }

    public TimeLine(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }

    public boolean isInConcurrenceWith(LocalDateTime date) {
        return (date.isAfter(begin) && date.isBefore(end) || date.equals(begin) || date.equals(end));
    }

    public static boolean areTimelineInConcurrence(TimeLine tl1, TimeLine tl2) {
        return 
            tl2.isInConcurrenceWith(tl1.getBegin()) || 
            tl2.isInConcurrenceWith(tl1.getEnd()) ||
            tl1.isInConcurrenceWith(tl2.getBegin()) ||
            tl1.isInConcurrenceWith(tl2.getEnd());
    }

}
