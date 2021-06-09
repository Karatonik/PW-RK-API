package pl.kalksztejn.mateusz.reactiveapi.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ArticleDTO {
    String title;
    String header;
    String text;
}
