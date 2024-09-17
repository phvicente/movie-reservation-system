package org.wogoo.moviereservationsystem.specification;

import org.springframework.data.jpa.domain.Specification;
import org.wogoo.moviereservationsystem.domain.model.Movie;

public class MovieSpecification {

    public static Specification<Movie> titleContains(String title) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Movie> genreEquals(String genre) {
        return (root, query, builder) ->
                builder.equal(builder.lower(root.get("genre")), genre.toLowerCase());
    }
}
