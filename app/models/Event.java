package models;

import com.avaje.ebean.ExpressionList;
import java.util.Date;
import play.data.validation.Constraints;
import java.util.*;
import javax.persistence.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import play.Logger;

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

    @JsonIgnore
    @ManyToOne
    @Constraints.Required
    public User createdBy;

    public Date createdDate;

    @JsonProperty("start")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date startTime;

    @JsonProperty("end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date endTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "events")
    public Set<OrgUnit> parties;

    public static Finder<Long, Event> find =
            new Finder<Long, Event>(Long.class, Event.class);

    public static Set<Event> forParty(OrgUnit u, Date start, Date end) {
        return find
                .where()
                    .ge("startTime", start)
                    .le("endTime", end)
                    .eq("parties.id", u.id)
                .findSet();
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", title=" + title + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }


}
