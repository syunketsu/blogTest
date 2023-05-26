package com.example.demo.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="like")
public class Like {
	@Id
	@Column(name="like_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long likeId;
	
	@ManyToOne(optional=true)
	private Blog blog;
	
	@ManyToOne(optional=true)
	private Account account;
}
