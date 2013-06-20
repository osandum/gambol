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
@DiscriminatorValue("club")
public class Club extends OrgUnit {

  @ManyToMany(mappedBy="clubs")
  public Set<Event> events;

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
