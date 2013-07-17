
package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import java.net.URI;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * @author      osa
 * @since       20-06-2013
 * @version     $Id: OrgUnit.java -1 20-06-2013 21:49:49 osa $
 */
@Entity
public class OrgUnit extends Model {
    @Id
    public Long id;

    @ManyToOne
    public OrgUnit parent;

    @Constraints.Required
    public String name;

    public URI rssFeed;

    public String extCalendarId;

    public String eventsColor;

    public String calendarId;

    @ManyToMany//(mappedBy = "parties")
    public Set<Event> events;

    @Constraints.Required
    @Constraints.Pattern("[a-z0-9]+")
    @Constraints.MaxLength(10)
    public String slug;

    public String path;

    public OrgUnitType orgUnitType;

    @ManyToOne
    public User createdBy;

    @CreatedTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date created;

    @OneToMany(mappedBy = "party")
    public List<TeamPlayer> players;

    public static Finder<Long, OrgUnit> find = new Finder(Long.class, OrgUnit.class);

    public static OrgUnit findBySlug(String slug) {
        return find.where().eq("slug", slug).findUnique();
    }

    public static OrgUnit findBySlug(OrgUnit parent, String slug) {
        return find.where().eq("parent", parent).eq("slug", slug).findUnique();
    }

    public static Map<String, String> allClubsOptions() {
        Map<String, String> r = new HashMap<String, String>();
        for (OrgUnit c : find.where().eq("orgUnitType", OrgUnitType.CLUB).findList())
            r.put(c.slug, c.name);
        return r;
    }


    public void collectEvents(Set<Event> all) {
    //    if (parent != null)
    //      parent.collectEvents(all);

        for (Event evt : events)
            all.add(evt);
    }

    public static String getPath(OrgUnit u) {
        if (u.parent == null)
            return u.slug;

        return getPath(u.parent) + "-" + u.slug;
    }

    @Override
    public String toString() {
        return "OrgUnit{" + "id=" + id + ", parent=" + parent + ", name=" + name + ", slug=" + slug + ", path=" + path + ", orgUnitType=" + orgUnitType + '}';
    }

}
