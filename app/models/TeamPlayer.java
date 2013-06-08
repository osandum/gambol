package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;


/**
 * @author      osa
 * @since       11-05-2013
 * @version     $Id: TeamPlayer.java -1 11-05-2013 18:25:43 osa $
 */
@Entity
public class TeamPlayer extends Model {

    @Id
    public Long id;

    @ManyToOne
    @Constraints.Required
    public Team team;

    @ManyToOne
    @Constraints.Required
    public User player;

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date signedOn;

    public Integer number;

}