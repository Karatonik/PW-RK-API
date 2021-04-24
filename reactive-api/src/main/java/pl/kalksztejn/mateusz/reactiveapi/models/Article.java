package pl.kalksztejn.mateusz.reactiveapi.models;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
@Data
public class Article {
    @Id
    private  String id;

    private String title;
    private String header;

    private String text;
    private List<String> imagesId;


    public Article(String id, String title, String header, String text) {
        this.id = id;
        this.title = title;
        this.header = header;
        this.text = text;
        this.imagesId=new ArrayList<>();
    }
}
