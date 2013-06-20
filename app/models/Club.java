package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
//import play.data.format.*;
import play.data.validation.Constraints;
//import play.data.validation.*;

/**
 * @author      osa
 * @since       11-05-2013
 * @version     $Id: Club.java -1 11-05-2013 17:44:47 osa $
 */
@Entity
public class Club extends Model {

  @Id
  @Constraints.Min(10)
  public Long id;

  @Column(unique = true)
  @Constraints.Required
  public String name;

  @Column(unique = true)
  @Constraints.Required
  @Constraints.Pattern("[a-z0-9]+")
  public String slug;

  @ManyToMany(mappedBy="clubs")
  public Set<Event> events;

  @OneToMany(mappedBy="club")
  public List<Team> teams;

  @ManyToOne
  public User createdBy;

  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  public Date created;

  public static Model.Finder<Long, Club> find = new Model.Finder(Long.class, Club.class);

  public static Club findBySlug(String slug) {
    return find.where().eq("slug", slug).findUnique();
  }

  @Override
  public String toString() {
      return "Club{" + "id=" + id + ", name=" + name + ", slug=" + slug + ", created=" + created +" by " + createdBy + '}';
  }

  public static Map<String, String> allClubsOptions() {
      Map<String, String> r = new HashMap<String, String>();
      for (Club c : find.all())
          r.put(c.slug, c.name);
      return r;
  }
}
