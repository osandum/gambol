
package models;

import com.avaje.ebean.annotation.EnumValue;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * @author      osa
 * @since       29-05-2013
 * @version     $Id: Signup.java -1 29-05-2013 22:40:00 osa $
 */
@Entity
public class Signup extends Model {

    @Id
    public Long id;

    @ManyToOne
    @Constraints.Required
    public Event event;

    @ManyToOne
    @Constraints.Required
    public User person;

    public static enum Answer {
        @EnumValue("Y")
        YES,
        @EnumValue("N")
        NO
    }

    @Constraints.Required
    public Answer answer;

    public Date answeredDate;

    public String comment;
}
