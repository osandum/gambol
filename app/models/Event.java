package models;

import java.util.Date;
import play.data.validation.Constraints;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

/**
 * @author      osa
 * @since       16-05-2013
 * @version     $Id: Event.java -1 16-05-2013 22:21:13 osa $
 */
@Entity
public class Event extends Model {

    @Id
    public Long id;

    public String title;

//    @ManyToOne
//    @Constraints.Required
//    public Club club;

    @ManyToOne
    @Constraints.Required
    public User createdBy;

    public Date createdDate;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date startTime;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date endTime;

    public static Finder<Long, Event> find =
            new Finder<Long, Event>(Long.class, Event.class);

    @ManyToMany
    public Set<Club> clubs;

    @ManyToMany
    public Set<Team> teams;

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", title=" + title + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }


}
