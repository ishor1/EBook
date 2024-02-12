package com.EBook.Model;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int bookId;
	@NotBlank
private String title;
@Column(length = 10000)
private String description;
@NotBlank
private String author;
private Date addedDate;
private boolean live;
private String BookImg;


@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "categoryId")
@JsonBackReference
private Category category;
}
