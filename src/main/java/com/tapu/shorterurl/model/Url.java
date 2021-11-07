package com.tapu.shorterurl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tapu.shorterurl.unique.UniqueShortUrl;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Urls")
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "after_url")
    @UniqueShortUrl
    private String afterUrl;

    @Column(name = "before_url")
    private String beforeUrl;

    @ManyToOne
    @JsonIgnore
    private User user;

}
