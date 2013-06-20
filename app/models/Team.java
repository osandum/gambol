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
@DiscriminatorValue("team")
public class Team extends OrgUnit {

  @OneToMany(mappedBy="team")
  public List<TeamPlayer> players;

  @ManyToMany(mappedBy="teams")
  public Set<Event> events;

  public static Model.Finder<Long, Team> find = new Model.Finder(Long.class, Team.class);

  public static Team findBySlug(Club club, String slug) {
    return find.where()
            .eq("parent", club)
            .eq("slug", slug)
            .findUnique();
  }


    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", parent=" + parent + ", name=" + name + ", slug=" + slug + '}';
    }

}
