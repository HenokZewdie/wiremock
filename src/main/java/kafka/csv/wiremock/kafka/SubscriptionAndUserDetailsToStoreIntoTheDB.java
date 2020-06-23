package kafka.csv.wiremock.kafka;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document(collection = "SubscriptionAndUserDetailsToStoreIntoTheDB")
public class SubscriptionAndUserDetailsToStoreIntoTheDB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    private OnStarProfileSubscription onStarProfileSubscription;
    private Users users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OnStarProfileSubscription getOnStarProfileSubscription() {
        return onStarProfileSubscription;
    }

    public void setOnStarProfileSubscription(OnStarProfileSubscription onStarProfileSubscription) {
        this.onStarProfileSubscription = onStarProfileSubscription;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}

