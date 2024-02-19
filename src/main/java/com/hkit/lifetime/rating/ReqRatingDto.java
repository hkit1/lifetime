package com.hkit.lifetime.rating;

import java.io.Serializable;

public record ReqRatingDto (String id, String name, String star, String text) implements Serializable {
}
