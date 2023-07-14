package io.pilp.quarkusgraphqlschemabug;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class CountryResourceGraphQL {
    private Country country;

    public CountryResourceGraphQL() {
        this.country = new Country();
        this.country.setId(1L);
        this.country.setName("Country");
    }

    @Query("country")
    public Country get() {
        return country;
    }
}
