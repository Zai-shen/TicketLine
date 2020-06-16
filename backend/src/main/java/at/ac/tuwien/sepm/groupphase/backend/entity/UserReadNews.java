package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class UserReadNews {

    @EmbeddedId
    private UserReadNewsPrimaryKey id;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @MapsId("news")
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private News news;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserReadNews that = (UserReadNews) o;
        return Objects.equals(user, that.user) && Objects.equals(news, that.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, news);
    }

    @Override
    public String toString() {
        return "UserReadNews{" + "id=" + id + ", user=" + user + ", news=" + news + '}';
    }
}
