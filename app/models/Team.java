package models;

import java.util.*;
import javax.persistence.*;
import static models.Club.find;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import java.net.URI;

/**
 * @author      osa
 * @since       11-05-2013
 * @version     $Id: Team.java -1 11-05-2013 18:19:43 osa $
 */
@Entity
public class Team extends Model {

  @Id
  public Long id;

  @ManyToOne
  public Club club;

  @Constraints.Required
  public String name;

  public URI rssFeed;

  public String calendarId;

  @Constraints.Required
  @Constraints.Pattern("[a-z0-9]+")
  public String slug;

  @OneToMany(mappedBy="team")
  public List<TeamPlayer> players;

  @ManyToMany(mappedBy="teams")
  public Set<Event> events;

  public static Model.Finder<Long, Team> find = new Model.Finder(Long.class, Team.class);

  public static Team findBySlug(Club club, String slug) {
    return find.where()
            .eq("club", club)
            .eq("slug", slug)
            .findUnique();
  }


    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", club=" + club + ", name=" + name + ", slug=" + slug + '}';
    }

}
