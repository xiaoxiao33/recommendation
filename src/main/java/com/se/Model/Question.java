package com.se.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "questions")
public class Question extends Audit {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    @Setter
    @Getter
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    @Column(columnDefinition = "text")
    private String description;
}
