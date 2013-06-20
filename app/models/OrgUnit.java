
package models;

import java.net.URI;
import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * @author      osa
 * @since       20-06-2013
 * @version     $Id: OrgUnit.java -1 20-06-2013 21:49:49 osa $
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "org_unit_type", discriminatorType = DiscriminatorType.STRING)
public abstract class OrgUnit extends Model {
    @Id
    public Long id;

    @ManyToOne
    public OrgUnit parent;

    @Constraints.Required
    public String name;

    public URI rssFeed;

    public String calendarId;

    @Constraints.Required
    @Constraints.Pattern("[a-z0-9]+")
    @Constraints.MaxLength(10)
    public String slug;

    public String path;

    @ManyToOne
    public User createdBy;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date created;

    public static Finder<Long, OrgUnit> find = new Finder(Long.class, OrgUnit.class);

    public static String getPath(OrgUnit u) {
        if (u.parent == null)
            return u.slug;

        return getPath(u.parent) + "-" + u.slug;
    }

}
