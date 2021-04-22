package pl.kalksztejn.mateusz.reactiveapi;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
@Data
public class Article {
    @Id
    private  String id;

    private String text;
}
